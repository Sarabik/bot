package lv.boardgame.bot.mybot;

import lv.boardgame.bot.messages.CreateTable;
import lv.boardgame.bot.messages.EditTable;
import lv.boardgame.bot.messages.MenuMessage;
import lv.boardgame.bot.model.GameSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoardGameBot extends TelegramLongPollingBot {

	@Autowired
	private GameSessionConstructor gameSessionConstructor;

	@Autowired
	private CreateTable createTable;

	@Autowired
	private MenuMessage menuMessage;

	@Autowired
	private EditTable editTable;

	@Autowired
	private MenuReplyKeyboard menuReplyKeyboard;

	@Autowired
	private AllCommands allCommands;

	@Value("${telegram.bot.username}")
	private String botUsername;

	@Value("${telegram.bot.token}")
	private String botToken;

	@Override
	public void onUpdateReceived(final Update update) {
		List<SendMessage> messageList = new ArrayList<>();

		if(update.hasMessage()){
			Message receivedMessage = update.getMessage();
			String username = receivedMessage.getFrom().getUserName();
			String chatIdString = String.valueOf(receivedMessage.getChatId());
			BotState botState = gameSessionConstructor.getBotState(username);

			if(receivedMessage.hasText()){
				String receivedText = receivedMessage.getText();

				if (allCommands.ifContainsKey(receivedText)) {
					messageList = allCommands.getCommand(receivedText).execute(chatIdString, username, receivedText);
				} else if (botState != null && allCommands.ifContainsKey(botState.toString())) {
					messageList = allCommands.getCommand(botState.toString()).execute(chatIdString, username, receivedText);
				} else {
					messageList.add(menuMessage.getMenuMessage(chatIdString));
				}
				messageList.forEach(this::safeExecute);
			}
		} else if (update.hasCallbackQuery()) {
			CallbackQuery callbackQuery = update.getCallbackQuery();
			String data = callbackQuery.getData();
			String username = callbackQuery.getFrom().getUserName();
			int messageId = callbackQuery.getMessage().getMessageId();
			String messageText = callbackQuery.getMessage().getText();
			long chatId = callbackQuery.getMessage().getChatId();
			String chatIdString = String.valueOf(chatId);

			SendMessage message1 = SendMessage.builder()
				.chatId(chatIdString)
				.parseMode("HTML")
				.text(data)
				.build();
			messageList.add(message1);

			if (gameSessionConstructor.getBotState(username) == BotState.WAITING_DATE && "Выберите дату".equals(data)) {
				messageList.add(createTable.askForDate(chatIdString));
			} else if (gameSessionConstructor.getBotState(username) == BotState.WAITING_DATE) {
				gameSessionConstructor.setDate(username, data);
				messageList.add(createTable.askForTime(chatIdString));
			} else if (gameSessionConstructor.getBotState(username) == BotState.WAITING_TIME) {
				gameSessionConstructor.setTime(username, data);
				messageList.add(createTable.askForPlace(chatIdString));
			} else if (gameSessionConstructor.getBotState(username) == BotState.WAITING_IF_ORGANIZER_PLAYING) {
				gameSessionConstructor.setIfOrganizerPlaying(username, data);
				message1.setText("true".equals(data) ? "Вы участвуете в игре сами" : "Вы не участвуете в игре, а только ее проводите");
				messageList.add(createTable.askForMaxPlayerCount(chatIdString));
			} else if (gameSessionConstructor.getBotState(username) == BotState.WAITING_MAX_PLAYER_COUNT) {
				gameSessionConstructor.setMaxPlayerCount(username, data);
				messageList.add(createTable.askForComment(chatIdString));
			} else if (gameSessionConstructor.getBotState(username) == BotState.WAITING_COMMENT) {
				gameSessionConstructor.setComment(username, data);
				messageList.add(createTable.savingTable(chatIdString, gameSessionConstructor.getGameSession(username)));
				gameSessionConstructor.clear(username);
			} else if ("ИГРОВАЯ ВСТРЕЧА ОТМЕНЕНА:".equals(data)) {
				String date = callbackQuery.getMessage().getEntities().get(1).getText();
				String organizer = callbackQuery.getMessage().getEntities().get(8).getText().substring(1);
				GameSession session = editTable.deleteTable(date, organizer);
				messageList.add(editTable.getEditedSession(chatIdString, session));
			} else if ("ВЫ ПРИСОЕДИНИЛИСЬ К ВСТРЕЧЕ:".equals(data)) {
				String date = callbackQuery.getMessage().getEntities().get(1).getText();
				String organizer = callbackQuery.getMessage().getEntities().get(8).getText().substring(1);
				GameSession session = editTable.addPlayerToTable(date, organizer, username);
				messageList.add(editTable.getEditedSession(chatIdString, session));
			} else if ("ВЫ ОТПИСАЛИСЬ ОТ ИГРОВОЙ ВСТРЕЧИ:".equals(data)) {
				String date = callbackQuery.getMessage().getEntities().get(1).getText();
				String organizer = callbackQuery.getMessage().getEntities().get(8).getText().substring(1);
				GameSession session = editTable.leaveGameTable(date, organizer, username);
				messageList.add(editTable.getEditedSession(chatIdString, session));
			} else {
				SendMessage sendMessage = SendMessage.builder()
					.chatId(chatIdString)
					.parseMode("HTML")
					.text("<b>Используйте кнопки меню внизу</b>")
					.replyMarkup(menuReplyKeyboard)
					.build();
				messageList.add(sendMessage);
			}
			messageList.forEach(this::safeExecute);
			disableInlineKeyboardButtons(chatId, messageId);
		}
	}

	@Override
	public String getBotUsername() {
		return botUsername;
	}

	@Override
	public String getBotToken() {
		return botToken;
	}

	public void disableInlineKeyboardButtons(Long chatId, Integer messageId) {
		EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
			.chatId(String.valueOf(chatId))
			.messageId(messageId)
			.replyMarkup(null)
			.build();
		try {
			execute(editMessageReplyMarkup);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	private void safeExecute(SendMessage message) {
		try {
			execute(message);
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}
}

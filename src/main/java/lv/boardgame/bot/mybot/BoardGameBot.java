package lv.boardgame.bot.mybot;

import lv.boardgame.bot.messages.CreateTable;
import lv.boardgame.bot.messages.ViewTables;
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

	private BotState botState = BotState.START;

	@Autowired
	private GameSessionConstructor gameSessionConstructor;

	@Autowired
	private CreateTable createTable;

	@Autowired
	private ViewTables viewTables;

	@Autowired
	private MenuReplyKeyboard menuReplyKeyboard;

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
			long chatId = receivedMessage.getChatId();
			String chatIdString = String.valueOf(chatId);

			if(receivedMessage.hasText()){
				String receivedText = receivedMessage.getText();
				if ("Создать игровой стол".equals(receivedText)) {
					gameSessionConstructor.clear();
					botState = BotState.WAITING_DATE;
					gameSessionConstructor.setOrganizerUsername(username);
					messageList.add(createTable.askForDate(chatIdString));
				} else if ("Все столы".equals(receivedText)) {
					botState = BotState.START;
					messageList.addAll(viewTables.getAllTables(chatIdString, username));
				} else if (botState == BotState.WAITING_PLACE) {
					gameSessionConstructor.setPlace(receivedText);
					botState = BotState.WAITING_GAME_NAME;
					messageList.add(createTable.askForGameName(chatIdString));
				} else if (botState == BotState.WAITING_GAME_NAME) {
					gameSessionConstructor.setGameName(receivedText);
					botState = BotState.WAITING_IF_ORGANIZER_PLAYING;
					messageList.add(createTable.askForIfOrganizerPlaying(chatIdString));
				} else if (botState == BotState.WAITING_COMMENT) {
					gameSessionConstructor.setComment(receivedText);
					botState = BotState.SAVING_TABLE;
					messageList.add(createTable.savingTable(chatIdString, gameSessionConstructor.getGameSession()));
				} else if (botState == BotState.START) {
					SendMessage sendMessage = SendMessage.builder()
						.chatId(chatIdString)
						.parseMode("HTML")
						.text("<b>Используйте кнопки меню внизу</b>")
						.replyMarkup(menuReplyKeyboard)
						.build();
					messageList.add(sendMessage);
				}
				messageList.forEach(s -> {
					try {
						execute(s);
					} catch (TelegramApiException e) {
						throw new RuntimeException(e);
					}
				});
				if (botState == BotState.SAVING_TABLE) {
					botState = BotState.START;
				}
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

			if (botState == BotState.WAITING_DATE) {
				gameSessionConstructor.setDate(data);
				botState = BotState.WAITING_TIME;
				messageList.add(createTable.askForTime(chatIdString));
			} else if (botState == BotState.WAITING_TIME) {
				gameSessionConstructor.setTime(data);
				gameSessionConstructor.setDateTime();
				botState = BotState.WAITING_PLACE;
				messageList.add(createTable.askForPlace(chatIdString));
			} else if (botState == BotState.WAITING_IF_ORGANIZER_PLAYING) {
				gameSessionConstructor.setIfOrganizerPlaying(data);
				message1.setText("true".equals(data) ? "Вы участвуете в игре сами" : "Вы не участвуете в игре, а только ее проводите");
				botState = BotState.WAITING_MAX_PLAYER_COUNT;
				messageList.add(createTable.askForMaxPlayerCount(chatIdString));
			} else if (botState == BotState.WAITING_MAX_PLAYER_COUNT) {
				gameSessionConstructor.setMaxPlayerCount(data);
				botState = BotState.WAITING_COMMENT;
				messageList.add(createTable.askForComment(chatIdString));
			} else if (botState == BotState.WAITING_COMMENT) {
				gameSessionConstructor.setComment(data);
				botState = BotState.SAVING_TABLE;
				messageList.add(createTable.savingTable(chatIdString, gameSessionConstructor.getGameSession()));
			} else if ("Игровая встреча отменена".equals(data)) {
				String date = callbackQuery.getMessage().getEntities().get(1).getText();
				String organizer = callbackQuery.getMessage().getEntities().get(8).getText().substring(1);
				viewTables.deleteTable(date, organizer);
			} else if ("Вы присоединились к столу".equals(data)) {
				String date = callbackQuery.getMessage().getEntities().get(1).getText();
				String organizer = callbackQuery.getMessage().getEntities().get(8).getText().substring(1);
				viewTables.addPlayerToTable(date, organizer, username);
			} else if ("Запись отменена".equals(data)) {
				String date = callbackQuery.getMessage().getEntities().get(1).getText();
				String organizer = callbackQuery.getMessage().getEntities().get(8).getText().substring(1);
				viewTables.leaveGameTable(date, organizer, username);
			}
			messageList.forEach(s -> {
				try {
					execute(s);
				} catch (TelegramApiException e) {
					throw new RuntimeException(e);
				}
			});
			if (botState == BotState.SAVING_TABLE) {
				botState = BotState.START;
			}
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
}

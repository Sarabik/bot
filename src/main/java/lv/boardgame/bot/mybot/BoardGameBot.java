package lv.boardgame.bot.mybot;

import lv.boardgame.bot.messages.CreateTable;
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

@Component
public class BoardGameBot extends TelegramLongPollingBot {

	private BotState botState = BotState.START;

	@Autowired
	private GameSessionConstructor gameSessionConstructor;

	@Autowired
	private CreateTable createTable;

	@Autowired
	private MenuReplyKeyboard menuReplyKeyboard;

	@Value("${telegram.bot.username}")
	private String botUsername;

	@Value("${telegram.bot.token}")
	private String botToken;

	@Override
	public void onUpdateReceived(final Update update) {

		if(update.hasMessage()){
			Message receivedMessage = update.getMessage();
			String username = receivedMessage.getFrom().getUserName();
			long chatId = receivedMessage.getChatId();
			String chatIdString = String.valueOf(chatId);

			if(receivedMessage.hasText()){
				String receivedText = receivedMessage.getText();

				SendMessage sendMessage1 = null;
				if ("Создать игровой стол".equals(receivedText)) {
					gameSessionConstructor.clear();
					botState = BotState.WAITING_DATE;
					gameSessionConstructor.setOrganizerUsername(username);
					sendMessage1 = createTable.askForDate(chatIdString);
				} else if (botState == BotState.WAITING_PLACE) {
					gameSessionConstructor.setPlace(receivedText);
					botState = BotState.WAITING_GAME_NAME;
					sendMessage1 = createTable.askForGameName(chatIdString);
				} else if (botState == BotState.WAITING_GAME_NAME) {
					gameSessionConstructor.setGameName(receivedText);
					botState = BotState.WAITING_IF_ORGANIZER_PLAYING;
					sendMessage1 = createTable.askForIfOrganizerPlaying(chatIdString);
				} else if (botState == BotState.WAITING_COMMENT) {
					gameSessionConstructor.setComment(receivedText);
					botState = BotState.SAVING_TABLE;
					sendMessage1 = createTable.savingTable(chatIdString, gameSessionConstructor.getGameSession());
				} else if (botState == BotState.START) {
					sendMessage1 = SendMessage.builder()
						.chatId(chatIdString)
						.parseMode("HTML")
						.text("<b>Используй кнопки меню внизу</b>")
						.replyMarkup(menuReplyKeyboard)
						.build();
				}
				try {
					execute(sendMessage1);
					if (botState == BotState.SAVING_TABLE) {
						botState = BotState.START;
					}
				} catch (TelegramApiException e) {
					throw new RuntimeException(e);
				}
			}
		} else if (update.hasCallbackQuery()) {
			CallbackQuery callbackQuery = update.getCallbackQuery();
			String data = callbackQuery.getData();
			int messageId = callbackQuery.getMessage().getMessageId();
			long chatId = callbackQuery.getMessage().getChatId();
			String chatIdString = String.valueOf(chatId);

			SendMessage message1 = SendMessage.builder()
				.chatId(chatIdString)
				.parseMode("HTML")
				.text(data)
				.build();
			SendMessage message2 = null;

			if (botState == BotState.WAITING_DATE) {
				gameSessionConstructor.setDate(data);
				botState = BotState.WAITING_TIME;
				message2 = createTable.askForTime(chatIdString);
			} else if (botState == BotState.WAITING_TIME) {
				gameSessionConstructor.setTime(data);
				gameSessionConstructor.setDateTime();
				botState = BotState.WAITING_PLACE;
				message2 = createTable.askForPlace(chatIdString);
			} else if (botState == BotState.WAITING_IF_ORGANIZER_PLAYING) {
				gameSessionConstructor.setIfOrganizerPlaying(data);
				message1.setText("true".equals(data) ? "Вы участвуете в игре сами" : "Вы не участвуете в игре, а только ее проводите");
				botState = BotState.WAITING_MAX_PLAYER_COUNT;
				message2 = createTable.askForMaxPlayerCount(chatIdString);
			} else if (botState == BotState.WAITING_MAX_PLAYER_COUNT) {
				gameSessionConstructor.setMaxPlayerCount(data);
				botState = BotState.WAITING_COMMENT;
				message2 = createTable.askForComment(chatIdString);
			} else if (botState == BotState.WAITING_COMMENT) {
				gameSessionConstructor.setComment(data);
				botState = BotState.SAVING_TABLE;
				message2 = createTable.savingTable(chatIdString, gameSessionConstructor.getGameSession());
			}
			try {
				execute(message1);
				if(message2 != null) {
					execute(message2);
				}
				if (botState == BotState.SAVING_TABLE) {
					gameSessionConstructor.clear();
					botState = BotState.START;
				}
			} catch (TelegramApiException e) {
				e.printStackTrace();
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

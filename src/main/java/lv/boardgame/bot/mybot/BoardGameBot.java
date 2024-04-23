package lv.boardgame.bot.mybot;

import lv.boardgame.bot.commands.AllCallbackQueryCommands;
import lv.boardgame.bot.commands.AllMessageCommands;
import lv.boardgame.bot.messages.MenuMessage;
import lv.boardgame.bot.model.BotState;
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
	private MenuMessage menuMessage;

	@Autowired
	private AllMessageCommands allMessageCommands;

	@Autowired
	private AllCallbackQueryCommands allCallbackQueryCommands;

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

				if (allMessageCommands.ifContainsKey(receivedText)) {
					messageList = allMessageCommands
						.getCommand(receivedText).execute(chatIdString, username, receivedText);
				} else if (botState != null && allMessageCommands.ifContainsKey(botState.toString())) {
					messageList = allMessageCommands
						.getCommand(botState.toString()).execute(chatIdString, username, receivedText);
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
			Message callbackQueryMessage = callbackQuery.getMessage();
			long chatId = callbackQuery.getMessage().getChatId();
			String chatIdString = String.valueOf(chatId);
			BotState botState = gameSessionConstructor.getBotState(username);

			if (botState != null && allCallbackQueryCommands.ifContainsKey(botState.toString())) {
				messageList = allCallbackQueryCommands.getCommand(botState.toString())
					.execute(chatIdString, username, data, callbackQueryMessage);
			} else if (allCallbackQueryCommands.ifContainsKey(data)) {
				messageList = allCallbackQueryCommands.getCommand(data)
					.execute(chatIdString, username, data, callbackQueryMessage);
			} else {
				messageList.add(menuMessage.getMenuMessage(chatIdString));
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

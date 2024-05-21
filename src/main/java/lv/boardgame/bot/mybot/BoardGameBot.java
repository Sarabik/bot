package lv.boardgame.bot.mybot;

import lv.boardgame.bot.commands.AllAdminCommands;
import lv.boardgame.bot.commands.AllCallbackQueryCommands;
import lv.boardgame.bot.commands.AllMessageCommands;
import lv.boardgame.bot.commands.callbackQueryCommand.GameSessionDeletedCallback;
import lv.boardgame.bot.messages.MenuMessage;
import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoardGameBot extends TelegramLongPollingBot {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	@Autowired
	private GameSessionConstructor gameSessionConstructor;

	@Autowired
	private MenuMessage menuMessage;

	@Autowired
	private AllMessageCommands allMessageCommands;

	@Autowired
	private AllCallbackQueryCommands allCallbackQueryCommands;

	@Autowired
	private AllAdminCommands allAdminCommands;

	@Value("${telegram.bot.username}")
	private String botUsername;

	@Value("${telegram.bot.token}")
	private String botToken;

	@Override
	public void onUpdateReceived(final Update update) {
		List<SendMessage> messageList = new ArrayList<>();

		if(update.hasMessage() && update.getMessage().getChat().isUserChat()){
			Message receivedMessage = update.getMessage();
			String chatIdString = String.valueOf(receivedMessage.getChatId());
			Player player = getPlayer(chatIdString, receivedMessage.getFrom());
			BotState botState = gameSessionConstructor.getBotState(player);

			if(receivedMessage.hasText()){
				String receivedText = receivedMessage.getText();

				if (allMessageCommands.ifContainsKey(receivedText)) {
					messageList = allMessageCommands
						.getCommand(receivedText).execute(chatIdString, player, receivedText);
				} else if (botState != null && allMessageCommands.ifContainsKey(botState.toString())) {
					messageList = allMessageCommands
						.getCommand(botState.toString()).execute(chatIdString, player, receivedText);
				} else if (allAdminCommands.ifContainsKey(receivedText.split(" ")[0])) {
					messageList = allAdminCommands
						.getCommand(receivedText.split(" ")[0])
						.execute(chatIdString, player, receivedText);
				} else {
					messageList.add(menuMessage.getMenuMessage(chatIdString));
				}
				messageList.forEach(this::safeExecute);
			}
		} else if (update.hasCallbackQuery()) {
			CallbackQuery callbackQuery = update.getCallbackQuery();
			String data = callbackQuery.getData();
			int messageId = callbackQuery.getMessage().getMessageId();
			Message callbackQueryMessage = (Message) callbackQuery.getMessage();
			long chatId = callbackQuery.getMessage().getChatId();
			String chatIdString = String.valueOf(chatId);
			Player player = getPlayer(chatIdString, callbackQuery.getFrom());
			BotState botState = gameSessionConstructor.getBotState(player);

			if (botState != null && allCallbackQueryCommands.ifContainsKey(botState.toString())) {
				messageList = allCallbackQueryCommands.getCommand(botState.toString())
					.execute(chatIdString, player, data, callbackQueryMessage);
			} else if (allCallbackQueryCommands.ifContainsKey(data)) {
				messageList = allCallbackQueryCommands.getCommand(data)
					.execute(chatIdString, player, data, callbackQueryMessage);
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
			LOG.error(e.getMessage());
		}
	}

	private void safeExecute(SendMessage message) {
		try {
			execute(message);
		} catch (TelegramApiException e) {
			throw new RuntimeException(e);
		}
	}

	private Player getPlayer(String chatId, User user) {
		return new Player(chatId, user.getUserName(), user.getFirstName(), user.getLastName());
	}
}

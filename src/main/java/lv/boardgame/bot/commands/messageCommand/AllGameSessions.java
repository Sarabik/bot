package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.commands.callbackQueryCommand.GameSessionDeletedCallback;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import lv.boardgame.bot.mybot.MenuReplyKeyboard;
import lv.boardgame.bot.service.GameSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import static lv.boardgame.bot.TextFinals.*;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static lv.boardgame.bot.messages.MessageUtil.convertGameSessionToString;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessage;

@Component
@AllArgsConstructor
public class AllGameSessions implements MessageCommand {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private GameSessionConstructor gameSessionConstructor;

	private GameSessionService gameSessionService;

	private MenuReplyKeyboard menuReplyKeyboard;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.clear(username);
		messageList.add(getAllTables(chatId, username));
		messageList.add(getMenuMessage(chatId));
		return messageList;
	}

	public SendMessage getAllTables (final String chatIdString, final String username) {
		gameSessionService.deleteOutdatedGameSessions();
		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		LOG.info("{} -> Got game session list: {}", username, gameSessionList);
		if (gameSessionList.isEmpty()) {
			return getCustomMessage(chatIdString, NO_SESSIONS);
		}
		StringJoiner joiner = new StringJoiner(System.lineSeparator());
		joiner.add(SESSIONS_LIST);
		joiner.add("");

		int counter = 1;
		for (GameSession session : gameSessionList) {
			joiner.add(counter++ + ".");
			joiner.add(convertGameSessionToString(session));
			joiner.add("");
		}
		return getCustomMessage(chatIdString, joiner.toString());
	}

	public SendMessage getMenuMessage(String chatIdString) {
		SendMessage message = getCustomMessage(chatIdString, MENU_MESSAGE);
		message.setReplyMarkup(menuReplyKeyboard);
		return message;
	}
}

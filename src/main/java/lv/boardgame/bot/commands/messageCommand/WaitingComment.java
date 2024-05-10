package lv.boardgame.bot.commands.messageCommand;

import lv.boardgame.bot.commands.callbackQueryCommand.GameSessionDeletedCallback;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import lv.boardgame.bot.service.GameSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import static lv.boardgame.bot.TextFinals.*;

import java.util.ArrayList;
import java.util.List;

import static lv.boardgame.bot.messages.MessageUtil.convertGameSessionToString;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessage;

@Component
public class WaitingComment implements MessageCommand {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private final GameSessionConstructor gameSessionConstructor;

	private final GameSessionService gameSessionService;

	@Value("${telegram.groupId}")
	private String groupId;

	@Value("${telegram.group2Id}")
	private String group2Id;

	@Value("${telegram.bot.username}")
	private String botUsername;

	private WaitingComment(
		final GameSessionConstructor gameSessionConstructor,
		final GameSessionService gameSessionService
	) {
		this.gameSessionConstructor = gameSessionConstructor;
		this.gameSessionService = gameSessionService;
	}

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.setComment(username, receivedText);
		GameSession savedGameSession = gameSessionConstructor.getGameSession(username);
		LOG.info("{} -> Game session saved: {}", username, savedGameSession);
		messageList.addAll(savingTable(chatId, savedGameSession));
		gameSessionConstructor.clear(username);
		return messageList;
	}

	public List<SendMessage> savingTable(final String chatIdString, final GameSession gameSession) {
		List<SendMessage> list = new ArrayList<>();
		GameSession gmSession = gameSessionService.saveNewGameSession(gameSession);
		String str = GAME_SESSION_CREATED + System.lineSeparator() + convertGameSessionToString(gmSession);
		list.add(getCustomMessage(chatIdString, str));
		/*list.add(getMessageForGroup(str, groupId));*/
		list.add(getMessageForGroup(str, group2Id));
		return list;
	}

	private SendMessage getMessageForGroup(String str, String groupId) {
		String botPrivateChatUrl = "<b><a href='https://t.me/" + botUsername + "'>ПЕРЕЙТИ В БОТ</a></b>";
		return getCustomMessage(groupId, str +
			System.lineSeparator() +
			System.lineSeparator() +
			botPrivateChatUrl);
	}
}

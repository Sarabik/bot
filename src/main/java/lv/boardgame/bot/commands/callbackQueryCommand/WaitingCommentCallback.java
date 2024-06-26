package lv.boardgame.bot.commands.callbackQueryCommand;

import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import lv.boardgame.bot.service.GameSessionService;
import lv.boardgame.bot.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil.getStartList;
import static lv.boardgame.bot.messages.MessageUtil.*;
import static lv.boardgame.bot.TextFinals.*;

import java.util.List;

@Component
public class WaitingCommentCallback implements CallbackQueryCommand {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	@Value("${telegram.bot.username}")
	private String botUsername;

	/*@Value("${telegram.groupIds}")
	private String groupIds;*/

	private final GroupService groupService;

	private final GameSessionService gameSessionService;

	private final GameSessionConstructor gameSessionConstructor;

	private WaitingCommentCallback(
		final GroupService groupService, final GameSessionService gameSessionService,
		final GameSessionConstructor gameSessionConstructor
	) {
		this.groupService = groupService;
		this.gameSessionService = gameSessionService;
		this.gameSessionConstructor = gameSessionConstructor;
	}

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String data, final Message message) {
		List<SendMessage> messageList = getStartList(chatId, data);
		GameSession savedGameSession = gameSessionConstructor.getGameSession(player);
		messageList.addAll(savingTable(chatId, savedGameSession, player));
		gameSessionConstructor.clear(player);
		return messageList;
	}

	public List<SendMessage> savingTable(final String chatIdString, final GameSession gameSession, final Player player) {
		GameSession gmSession = gameSessionService.saveNewGameSession(gameSession);
		LOG.info("{} -> Game session saved: {}", player, gmSession);
		String str = GAME_SESSION_CREATED + System.lineSeparator() + convertGameSessionToString(gmSession);
		List<SendMessage> list = getStartList(chatIdString, str);
		list.addAll(getGroupSendMessages(str, groupService.findAllGroups(), botUsername));
		return list;
	}
}

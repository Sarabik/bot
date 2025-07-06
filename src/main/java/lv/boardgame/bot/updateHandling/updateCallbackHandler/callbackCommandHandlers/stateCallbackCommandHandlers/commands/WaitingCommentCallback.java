package lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.stateCallbackCommandHandlers.commands;

import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.CallbackQueryCommand;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.gameSessionConstructor.GameSessionConstructor;
import lv.boardgame.bot.service.GameSessionService;
import lv.boardgame.bot.service.GroupService;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.directDataCallbackCommandHandlers.commands.GameSessionDeletedCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static lv.boardgame.bot.constants.TextFinals.GAME_SESSION_CREATED;
import static lv.boardgame.bot.messages.MessageUtil.*;
import static lv.boardgame.bot.updateHandling.util.QueryUtil.getStartList;

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
		GameSession savedGameSession = gameSessionService.saveNewGameSession(gameSessionConstructor.getGameSession(player));
		LOG.info("{} -> Game session saved: {}", player, savedGameSession);
		String sessionInfoMessage = GAME_SESSION_CREATED + System.lineSeparator() + convertGameSessionToString(savedGameSession);
		messageList.add(getCustomMessage(chatId, sessionInfoMessage));
		messageList.addAll(getGroupSendMessages(sessionInfoMessage, groupService.findAllGroups(), botUsername));
		gameSessionConstructor.clear(player);
		return messageList;
	}
}

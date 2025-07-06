package lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.stateMessageCommandHandlers.commands;

import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.directDataCallbackCommandHandlers.commands.GameSessionDeletedCallback;
import lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.MessageCommand;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.gameSessionConstructor.GameSessionConstructor;
import lv.boardgame.bot.service.GameSessionService;
import lv.boardgame.bot.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

import static lv.boardgame.bot.constants.TextFinals.GAME_SESSION_CREATED;
import static lv.boardgame.bot.messages.MessageUtil.*;

@Component
public class WaitingComment implements MessageCommand {

	@Value("${telegram.bot.username}")
	private String botUsername;

	/*@Value("${telegram.groupIds}")
	private String groupIds;*/

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private final GroupService groupService;

	private final GameSessionConstructor gameSessionConstructor;

	private final GameSessionService gameSessionService;

	private WaitingComment(
		final GroupService groupService, final GameSessionConstructor gameSessionConstructor,
		final GameSessionService gameSessionService
	) {
		this.groupService = groupService;
		this.gameSessionConstructor = gameSessionConstructor;
		this.gameSessionService = gameSessionService;
	}

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.setComment(player, receivedText);
		GameSession savedGameSession = gameSessionService.saveNewGameSession(gameSessionConstructor.getGameSession(player));
		LOG.info("{} -> Game session saved: {}", player, savedGameSession);
		String sessionInfoMessage = GAME_SESSION_CREATED + System.lineSeparator() + convertGameSessionToString(savedGameSession);
		messageList.add(getCustomMessage(chatId, sessionInfoMessage));
		messageList.addAll(getGroupSendMessages(sessionInfoMessage, groupService.findAllGroups(), botUsername));
		gameSessionConstructor.clear(player);
		return messageList;
	}
}

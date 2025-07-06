package lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.directDataCallbackCommandHandlers.commands;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.CallbackQueryCommand;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.service.GameSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static lv.boardgame.bot.constants.TextFinals.PLAYER_JOINED_YOUR_GAME_SESSION;
import static lv.boardgame.bot.updateHandling.util.QueryUtil.getGameSession;
import static lv.boardgame.bot.updateHandling.util.QueryUtil.getStartList;
import static lv.boardgame.bot.messages.MessageUtil.*;

@Component
@AllArgsConstructor
public class JoinedGameSessionCallback implements CallbackQueryCommand {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private final GameSessionService gameSessionService;

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String data, final Message message) {
		List<SendMessage> messageList = getStartList(chatId, data);
		GameSession gameSession = getGameSession(message, gameSessionService);
		gameSession.getPlayers().add(player);
		gameSession = gameSessionService.updateGameSession(gameSession);
		LOG.info("{} -> Joined game session: {}", player, gameSession);
		messageList.add(getEditedSession(chatId, gameSession));
		/*message to organizer*/
		String organizerChatId = gameSession.getOrganizer().getChatId();
		messageList.add(getCustomMessage(organizerChatId, PLAYER_JOINED_YOUR_GAME_SESSION + getPlayerNameString(player)));
		messageList.add(getEditedSession(organizerChatId, gameSession));
		return messageList;
	}
}

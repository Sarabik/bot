package lv.boardgame.bot.commands.callbackQueryCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.service.GameSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static lv.boardgame.bot.TextFinals.PLAYER_LEAVED_YOUR_GAME_SESSION;
import static lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil.getGameSession;
import static lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil.getStartList;
import static lv.boardgame.bot.messages.MessageUtil.*;

import java.util.List;

@Component
@AllArgsConstructor
public class GameSessionLeavedCallback implements CallbackQueryCommand {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private final GameSessionService gameSessionService;

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String data, final Message message) {
		List<SendMessage> messageList = getStartList(chatId, data);
		GameSession gameSession = getGameSession(message, gameSessionService);
		gameSession.getPlayers().remove(player);
		gameSession = gameSessionService.updateGameSession(gameSession);
		LOG.info("{} -> Leaved game session: {}", player, gameSession);
		messageList.add(getEditedSession(chatId, gameSession));
		/*message to organizer*/
		String organizerChatId = gameSession.getOrganizer().getChatId();
		messageList.add(getCustomMessage(organizerChatId, PLAYER_LEAVED_YOUR_GAME_SESSION + getPlayerNameString(player)));
		messageList.add(getEditedSession(organizerChatId, gameSession));
		return messageList;
	}
}

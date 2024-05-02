package lv.boardgame.bot.commands.callbackQueryCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.service.GameSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil.getGameSession;
import static lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil.getStartList;
import static lv.boardgame.bot.messages.MessageUtil.*;

import java.util.List;

@Component
@AllArgsConstructor
public class JoinedGameSessionCallback implements CallbackQueryCommand {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private final GameSessionService gameSessionService;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String data, final Message message) {
		List<SendMessage> messageList = getStartList(chatId, data);
		GameSession gameSession = getGameSession(message, gameSessionService);
		gameSession.getPlayers().add(username);
		gameSession = gameSessionService.updateGameSession(gameSession);
		LOG.info("{} -> Joined game session: {}", username, gameSession);
		messageList.add(getEditedSession(chatId, gameSession));
		return messageList;
	}
}

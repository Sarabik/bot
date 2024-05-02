package lv.boardgame.bot.commands.callbackQueryCommand;

import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.service.GameSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil.getGameSession;
import static lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil.getStartList;
import static lv.boardgame.bot.messages.MessageUtil.*;

import java.util.List;

@Component
public class GameSessionDeletedCallback implements CallbackQueryCommand {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private final GameSessionService gameSessionService;

	private GameSessionDeletedCallback(final GameSessionService gameSessionService) {
		this.gameSessionService = gameSessionService;
	}

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String data, final Message message) {
		List<SendMessage> messageList = getStartList(chatId, data);
		GameSession gameSession = getGameSession(message, gameSessionService);
		gameSessionService.deleteGameSessionById(gameSession.getId());
		LOG.info("{} -> Deleted game session: {}", username, gameSession);
		messageList.add(getEditedSession(chatId, gameSession));

		if (!gameSession.getPlayers().isEmpty()) {
			List<Player> players = gameSession.getPlayers()
				.stream()
				.filter(s -> !s.getUsername().equals(username))
				.toList();
			if (!players.isEmpty()) {
				for (Player player : players) {
					messageList.add(getCustomMessage(player.getChatId(), data));
					messageList.add(getEditedSession(player.getChatId(), gameSession));
				}
				LOG.info("{} -> Sent messages to players: {}", username, players);
			}
		}
		return messageList;
	}
}

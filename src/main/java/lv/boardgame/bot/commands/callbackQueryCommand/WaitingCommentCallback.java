package lv.boardgame.bot.commands.callbackQueryCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import lv.boardgame.bot.service.GameSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil.getStartList;
import static lv.boardgame.bot.messages.MessageUtil.*;
import static lv.boardgame.bot.TextFinals.*;

import java.util.List;

@Component
@AllArgsConstructor
public class WaitingCommentCallback implements CallbackQueryCommand {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private final GameSessionService gameSessionService;

	private final GameSessionConstructor gameSessionConstructor;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String data, final Message message) {
		List<SendMessage> messageList = getStartList(chatId, data);
		GameSession savedGameSession = gameSessionConstructor.getGameSession(username);
		messageList.add(savingTable(chatId, savedGameSession, username));
		gameSessionConstructor.clear(username);
		return messageList;
	}

	public SendMessage savingTable(final String chatIdString, final GameSession gameSession, final String username) {
		GameSession gmSession = gameSessionService.saveNewGameSession(gameSession);
		LOG.info("{} -> Game session saved: {}", username, gmSession);
		String str = GAME_SESSION_CREATED + System.lineSeparator() + convertGameSessionToString(gmSession);
		return getCustomMessage(chatIdString, str);
	}
}

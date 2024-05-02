package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.commands.callbackQueryCommand.GameSessionDeletedCallback;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import lv.boardgame.bot.service.GameSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import static lv.boardgame.bot.TextFinals.*;

import java.util.ArrayList;
import java.util.List;

import static lv.boardgame.bot.messages.MessageUtil.convertGameSessionToString;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessage;

@Component
@AllArgsConstructor
public class WaitingComment implements MessageCommand {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private GameSessionConstructor gameSessionConstructor;

	private GameSessionService gameSessionService;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.setComment(username, receivedText);
		GameSession savedGameSession = gameSessionConstructor.getGameSession(username);
		LOG.info("{} -> Game session saved: {}", username, savedGameSession);
		messageList.add(savingTable(chatId, savedGameSession));
		gameSessionConstructor.clear(username);
		return messageList;
	}

	public SendMessage savingTable(final String chatIdString, final GameSession gameSession) {
		GameSession gmSession = gameSessionService.saveNewGameSession(gameSession);
		String str = GAME_SESSION_CREATED + System.lineSeparator() + convertGameSessionToString(gmSession);
		return getCustomMessage(chatIdString, str);
	}
}

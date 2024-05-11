package lv.boardgame.bot.commands.messageCommand;

import lv.boardgame.bot.commands.callbackQueryCommand.GameSessionDeletedCallback;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
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
import static lv.boardgame.bot.messages.MessageUtil.getGroupSendMessages;

@Component
public class WaitingComment implements MessageCommand {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private final GameSessionConstructor gameSessionConstructor;

	private final GameSessionService gameSessionService;

	private WaitingComment(
		final GameSessionConstructor gameSessionConstructor,
		final GameSessionService gameSessionService
	) {
		this.gameSessionConstructor = gameSessionConstructor;
		this.gameSessionService = gameSessionService;
	}

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.setComment(player, receivedText);
		GameSession savedGameSession = gameSessionConstructor.getGameSession(player);
		LOG.info("{} -> Game session saved: {}", player, savedGameSession);
		messageList.addAll(savingTable(chatId, savedGameSession));
		gameSessionConstructor.clear(player);
		return messageList;
	}

	public List<SendMessage> savingTable(final String chatIdString, final GameSession gameSession) {
		List<SendMessage> list = new ArrayList<>();
		GameSession gmSession = gameSessionService.saveNewGameSession(gameSession);
		String str = GAME_SESSION_CREATED + System.lineSeparator() + convertGameSessionToString(gmSession);
		list.add(getCustomMessage(chatIdString, str));
		list.addAll(getGroupSendMessages(str));
		return list;
	}
}

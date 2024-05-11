package lv.boardgame.bot.commands.callbackQueryCommand;

import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
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

import java.util.ArrayList;
import java.util.List;

@Component
public class WaitingCommentCallback implements CallbackQueryCommand {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private final GameSessionService gameSessionService;

	private final GameSessionConstructor gameSessionConstructor;

	private WaitingCommentCallback(
		final GameSessionService gameSessionService,
		final GameSessionConstructor gameSessionConstructor
	) {
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
		List<SendMessage> list = new ArrayList<>();
		list.add(getCustomMessage(chatIdString, str));
		list.addAll(getGroupSendMessages(str));
		return list;
	}
}

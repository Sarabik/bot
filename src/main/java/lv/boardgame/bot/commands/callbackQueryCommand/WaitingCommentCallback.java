package lv.boardgame.bot.commands.callbackQueryCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import lv.boardgame.bot.service.GameSessionService;
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

	private final GameSessionService gameSessionService;

	private final GameSessionConstructor gameSessionConstructor;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String data, final Message message) {
		List<SendMessage> messageList = getStartList(chatId, data);

		if (!NO_COMMENTS.equals(data)) {
			gameSessionConstructor.setComment(username, data);
		}
		messageList.add(savingTable(chatId, gameSessionConstructor.getGameSession(username)));
		gameSessionConstructor.clear(username);
		return messageList;
	}

	public SendMessage savingTable(final String chatIdString, final GameSession gameSession) {
		GameSession gmSession = gameSessionService.saveNewGameSession(gameSession);
		String str = GAME_SESSION_CREATED + System.lineSeparator() + convertGameSessionToString(gmSession);
		return getCustomMessage(chatIdString, str);
	}
}

package lv.boardgame.bot.commands.callbackQueryCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.service.GameSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil.getGameSession;
import static lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil.getStartList;
import static lv.boardgame.bot.messages.MessageUtil.*;

import java.util.List;

@Component
@AllArgsConstructor
public class GameSessionDeletedCallback implements CallbackQueryCommand {

	private final GameSessionService gameSessionService;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String data, final Message message) {
		List<SendMessage> messageList = getStartList(chatId, data);
		GameSession gameSession = getGameSession(message, gameSessionService);
		gameSessionService.deleteGameSessionById(gameSession.getId());
		messageList.add(getEditedSession(chatId, gameSession));
		return messageList;
	}
}

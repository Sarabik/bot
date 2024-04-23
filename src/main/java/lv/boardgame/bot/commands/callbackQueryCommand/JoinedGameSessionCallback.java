package lv.boardgame.bot.commands.callbackQueryCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.service.GameSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static lv.boardgame.bot.TextFinals.DATE_TIME_FORMATTER;
import static lv.boardgame.bot.messages.MessageUtil.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class JoinedGameSessionCallback implements CallbackQueryCommand {

	private final GameSessionService gameSessionService;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String data, final Message message) {
		List<SendMessage> messageList = new ArrayList<>();
		messageList.add(getCustomMessage(chatId, data));

		String date = message.getEntities().get(1).getText();
		String organizer = message.getEntities().get(8).getText().substring(1);

		LocalDateTime dateTime = LocalDateTime.parse(date, DATE_TIME_FORMATTER);
		GameSession gameSession = gameSessionService.findGameSessionByDateAndOrganizer(dateTime, organizer);
		gameSession.getPlayers().add(username);
		gameSession = gameSessionService.updateGameSession(gameSession);
		messageList.add(getEditedSession(chatId, gameSession));
		return messageList;
	}
}

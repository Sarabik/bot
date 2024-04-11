package lv.boardgame.bot.messages;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.service.GameSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.StringJoiner;

import static lv.boardgame.bot.messages.ConvertGameSessionToString.getString;

@Component
@AllArgsConstructor
public class ViewTables {

	private GameSessionService gameSessionService;

	public SendMessage getAllTables (final String chatIdString) {
		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		StringJoiner joiner = new StringJoiner(System.lineSeparator());
		for (GameSession session : gameSessionList) {
			joiner.add(getString(session));
			joiner.add("");
		}
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text(joiner.toString())
//			.replyMarkup(dateInlineKeyboardMarkup)
			.build();
	}

}

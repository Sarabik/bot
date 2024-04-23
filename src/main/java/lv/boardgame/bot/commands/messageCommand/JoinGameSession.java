package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.inlineKeyboard.JoinGameInlineKeyboardMarkup;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import lv.boardgame.bot.service.GameSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import static lv.boardgame.bot.messages.MessageUtil.*;
import static lv.boardgame.bot.TextFinals.*;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class JoinGameSession implements MessageCommand {

	private GameSessionConstructor gameSessionConstructor;

	private GameSessionService gameSessionService;

	private JoinGameInlineKeyboardMarkup joinGameInlineKeyboardMarkup;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.clear(username);
		messageList.add(getCustomMessage(chatId, SESSIONS_TO_JOIN));
		messageList.addAll(getAllTablesToJoin(chatId, username));
		return messageList;
	}

	public List<SendMessage> getAllTablesToJoin (final String chatIdString, final String username) {
		gameSessionService.deleteOutdatedGameSessions();

		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		List<GameSession> gameSessionToJoin = gameSessionList.stream()
			.filter(s -> !(username.equals(s.getOrganizerUsername()) || s.getPlayers().contains(username))
				&& s.getMaxPlayerCount() - s.getPlayers().size() > 0)
			.toList();
		if (gameSessionToJoin.isEmpty()) {
			return List.of(getCustomMessage(chatIdString, NO_SESSIONS_TO_JOIN));
		}
		return getListOfMessages(gameSessionToJoin, joinGameInlineKeyboardMarkup, chatIdString);
	}
}

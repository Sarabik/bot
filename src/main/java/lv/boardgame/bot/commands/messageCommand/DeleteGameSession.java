package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.inlineKeyboard.DeleteGameInlineKeyboardMarkup;
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
public class DeleteGameSession implements MessageCommand {

	private GameSessionConstructor gameSessionConstructor;

	private GameSessionService gameSessionService;

	private DeleteGameInlineKeyboardMarkup deleteGameInlineKeyboardMarkup;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.clear(username);
		messageList.add(getCustomMessage(chatId, SESSION_TO_DELETE));
		messageList.addAll(getAllTablesToDelete(chatId, username));
		return messageList;
	}

	public List<SendMessage> getAllTablesToDelete (final String chatIdString, final String username) {
		gameSessionService.deleteOutdatedGameSessions();
		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		List<GameSession> gameSessionToDelete = gameSessionList.stream()
			.filter(s -> username.equals(s.getOrganizer().getUsername()))
			.toList();
		if (gameSessionToDelete.isEmpty()) {
			return List.of(getCustomMessage(chatIdString, NO_ORGANIZED));
		}
		return getListOfMessages(gameSessionToDelete, deleteGameInlineKeyboardMarkup, chatIdString);
	}
}

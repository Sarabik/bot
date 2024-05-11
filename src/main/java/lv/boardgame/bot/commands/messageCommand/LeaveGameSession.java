package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.inlineKeyboard.LeaveGameInlineKeyboardMarkup;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
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
public class LeaveGameSession implements MessageCommand {

	private GameSessionConstructor gameSessionConstructor;

	private GameSessionService gameSessionService;

	private LeaveGameInlineKeyboardMarkup leaveGameInlineKeyboardMarkup;

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.clear(player);
		messageList.add(getCustomMessage(chatId, SESSION_TO_LEAVE));
		messageList.addAll(getAllTablesToLeave(chatId, player));
		return messageList;
	}

	public List<SendMessage> getAllTablesToLeave (final String chatIdString, final Player player) {
		gameSessionService.deleteOutdatedGameSessions();
		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		List<GameSession> gameSessionToLeave = gameSessionList.stream()
			.filter(s -> (!player.equals(s.getOrganizer()) &&
				s.getPlayers().contains(player)))
			.toList();
		if (gameSessionToLeave.isEmpty()) {
			return List.of(getCustomMessage(chatIdString, DONT_JOIN));
		}
		return getListOfMessages(gameSessionToLeave, leaveGameInlineKeyboardMarkup, chatIdString);
	}


}

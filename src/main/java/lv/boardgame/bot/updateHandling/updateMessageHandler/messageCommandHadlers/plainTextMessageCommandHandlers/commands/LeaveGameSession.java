package lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.plainTextMessageCommandHandlers.commands;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.keyboards.LeaveGameInlineKeyboardMarkup;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.gameSessionConstructor.GameSessionConstructor;
import lv.boardgame.bot.service.GameSessionService;
import lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.MessageCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

import static lv.boardgame.bot.constants.TextFinals.DONT_JOIN;
import static lv.boardgame.bot.constants.TextFinals.SESSION_TO_LEAVE;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessage;
import static lv.boardgame.bot.messages.MessageUtil.getListOfMessages;

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

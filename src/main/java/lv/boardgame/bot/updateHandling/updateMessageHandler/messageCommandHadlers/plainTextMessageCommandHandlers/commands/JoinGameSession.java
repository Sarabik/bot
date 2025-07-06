package lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.plainTextMessageCommandHandlers.commands;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.keyboards.JoinGameInlineKeyboardMarkup;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.gameSessionConstructor.GameSessionConstructor;
import lv.boardgame.bot.service.GameSessionService;
import lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.MessageCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

import static lv.boardgame.bot.constants.TextFinals.NO_SESSIONS_TO_JOIN;
import static lv.boardgame.bot.constants.TextFinals.SESSIONS_TO_JOIN;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessage;
import static lv.boardgame.bot.messages.MessageUtil.getListOfMessages;

@Component
@AllArgsConstructor
public class JoinGameSession implements MessageCommand {

	private GameSessionConstructor gameSessionConstructor;

	private GameSessionService gameSessionService;

	private JoinGameInlineKeyboardMarkup joinGameInlineKeyboardMarkup;

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.clear(player);
		messageList.add(getCustomMessage(chatId, SESSIONS_TO_JOIN));
		messageList.addAll(getAllTablesToJoin(chatId, player));
		return messageList;
	}

	public List<SendMessage> getAllTablesToJoin (final String chatIdString, final Player player) {
		gameSessionService.deleteOutdatedGameSessions();

		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		List<GameSession> gameSessionToJoin = gameSessionList.stream()
			.filter(s -> !(player.equals(s.getOrganizer()) ||
					s.getPlayers().contains(player))
				&& s.getFreePlayerSlots() - s.getPlayers().size() > 0)
			.toList();
		if (gameSessionToJoin.isEmpty()) {
			return List.of(getCustomMessage(chatIdString, NO_SESSIONS_TO_JOIN));
		}
		return getListOfMessages(gameSessionToJoin, joinGameInlineKeyboardMarkup, chatIdString);
	}
}

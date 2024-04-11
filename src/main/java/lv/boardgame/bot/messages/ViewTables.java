package lv.boardgame.bot.messages;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.inlineKeyboard.DeleteGameInlineKeyboardMarkup;
import lv.boardgame.bot.inlineKeyboard.JoinGameInlineKeyboardMarkup;
import lv.boardgame.bot.inlineKeyboard.LeaveGameInlineKeyboardMarkup;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.service.GameSessionService;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static lv.boardgame.bot.messages.ConvertGameSessionToString.getString;

@Component
@AllArgsConstructor
public class ViewTables {

	private GameSessionService gameSessionService;

	private DeleteGameInlineKeyboardMarkup deleteGameInlineKeyboardMarkup;

	private LeaveGameInlineKeyboardMarkup leaveGameInlineKeyboardMarkup;

	private JoinGameInlineKeyboardMarkup joinGameInlineKeyboardMarkup;

	public List<SendMessage> getAllTables (final String chatIdString, final String username) {
		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		List<SendMessage> resultList = new ArrayList<>();
		for (GameSession session : gameSessionList) {
			SendMessage message = SendMessage.builder()
				.chatId(chatIdString)
				.parseMode("HTML")
				.text(getString(session))
				.build();
			if (Objects.equals(session.getOrganizerUsername(), username)) {
				message.setReplyMarkup(deleteGameInlineKeyboardMarkup);
			} else if (session.getPlayers().contains(username)) {
				message.setReplyMarkup(leaveGameInlineKeyboardMarkup);
			} else if (session.getMaxPlayerCount() - session.getPlayers().size() > 0) {
				message.setReplyMarkup(joinGameInlineKeyboardMarkup);
			}
			resultList.add(message);
		}
		return resultList;
	}

	public void deleteTable(String date, String organizer) {
		gameSessionService.deleteGameSessionById(getGameSession(date, organizer).getId());
	}

	public void addPlayerToTable(String date, String organizer, String playerUsername) {
		GameSession gameSession = getGameSession(date, organizer);
		gameSession.getPlayers().add(playerUsername);
		gameSessionService.updateGameSession(gameSession);
	}

	public void leaveGameTable(String date, String organizer, String playerUsername) {
		GameSession gameSession = getGameSession(date, organizer);
		gameSession.getPlayers().remove(playerUsername);
		gameSessionService.updateGameSession(gameSession);
	}

	private GameSession getGameSession(String date, String organizer) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
		return gameSessionService.findGameSessionByDateAndOrganizer(dateTime, organizer);
	}

}

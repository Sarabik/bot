package lv.boardgame.bot.messages;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.inlineKeyboard.DeleteGameInlineKeyboardMarkup;
import lv.boardgame.bot.inlineKeyboard.JoinGameInlineKeyboardMarkup;
import lv.boardgame.bot.inlineKeyboard.LeaveGameInlineKeyboardMarkup;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.service.GameSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static lv.boardgame.bot.messages.ConvertGameSessionToString.getString;

@Component
@AllArgsConstructor
public class EditTable {

	private GameSessionService gameSessionService;

	private DeleteGameInlineKeyboardMarkup deleteGameInlineKeyboardMarkup;

	private LeaveGameInlineKeyboardMarkup leaveGameInlineKeyboardMarkup;

	private JoinGameInlineKeyboardMarkup joinGameInlineKeyboardMarkup;

	public List<SendMessage> getAllTables (final String chatIdString, final String username) {
		deleteAllOutdatedGameSessions();
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

	public List<SendMessage> getAllTablesToJoin (final String chatIdString, final String username) {
		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		List<GameSession> gameSessionToJoin = gameSessionList.stream()
			.filter(s -> !(username.equals(s.getOrganizerUsername()) || s.getPlayers().contains(username))
			&& s.getMaxPlayerCount() - s.getPlayers().size() > 0)
			.toList();
		return getListOfMessages(gameSessionToJoin, joinGameInlineKeyboardMarkup, chatIdString);
	}

	public List<SendMessage> getAllTablesToLeave (final String chatIdString, final String username) {
		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		List<GameSession> gameSessionToLeave = gameSessionList.stream()
			.filter(s -> (!username.equals(s.getOrganizerUsername()) && s.getPlayers().contains(username)))
			.toList();
		return getListOfMessages(gameSessionToLeave, leaveGameInlineKeyboardMarkup, chatIdString);
	}

	public List<SendMessage> getAllTablesToDelete (final String chatIdString, final String username) {
		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		List<GameSession> gameSessionToDelete = gameSessionList.stream()
			.filter(s -> username.equals(s.getOrganizerUsername()))
			.toList();
		return getListOfMessages(gameSessionToDelete, deleteGameInlineKeyboardMarkup, chatIdString);
	}

	private List<SendMessage> getListOfMessages(List<GameSession> list, InlineKeyboardMarkup markup, String chatId) {
		List<SendMessage> resultList = new ArrayList<>();
		for (GameSession session : list) {
			SendMessage message = SendMessage.builder()
				.chatId(chatId)
				.parseMode("HTML")
				.text(getString(session))
				.replyMarkup(markup)
				.build();
			resultList.add(message);
		}
		deleteAllOutdatedGameSessions();
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

	private void deleteAllOutdatedGameSessions() {
		gameSessionService.deleteOutdatedGameSessions();
	}

}

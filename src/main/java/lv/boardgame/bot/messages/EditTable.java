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
import java.util.StringJoiner;

import static lv.boardgame.bot.messages.ConvertGameSessionToString.getString;

@Component
@AllArgsConstructor
public class EditTable {

	private GameSessionService gameSessionService;

	private DeleteGameInlineKeyboardMarkup deleteGameInlineKeyboardMarkup;

	private LeaveGameInlineKeyboardMarkup leaveGameInlineKeyboardMarkup;

	private JoinGameInlineKeyboardMarkup joinGameInlineKeyboardMarkup;

	public List<SendMessage> getAllTablesToJoin (final String chatIdString, final String username) {
		deleteAllOutdatedGameSessions();
		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		List<GameSession> gameSessionToJoin = gameSessionList.stream()
			.filter(s -> !(username.equals(s.getOrganizerUsername()) || s.getPlayers().contains(username))
			&& s.getMaxPlayerCount() - s.getPlayers().size() > 0)
			.toList();
		if (gameSessionToJoin.isEmpty()) {
			String str = "НЕТ ИГРОВЫХ ВСТРЕЧ, К КОТОРЫМ ВЫ МОГЛИ БЫ ПРИСОЕДИНИТЬСЯ";
			return List.of(getCustomMessage(chatIdString, str));
		}
		return getListOfMessages(gameSessionToJoin, joinGameInlineKeyboardMarkup, chatIdString);
	}

	public List<SendMessage> getAllTablesToLeave (final String chatIdString, final String username) {
		deleteAllOutdatedGameSessions();
		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		List<GameSession> gameSessionToLeave = gameSessionList.stream()
			.filter(s -> (!username.equals(s.getOrganizerUsername()) && s.getPlayers().contains(username)))
			.toList();
		if (gameSessionToLeave.isEmpty()) {
			String str = "ВЫ НЕ УЧАСТВУЕТЕ НИ В ОДНОЙ ИЗ ИГРОВЫХ ВСТРЕЧ, ОРГАНИЗОВАННЫХ ДРУГИМИ ИГРОКАМИ";
			return List.of(getCustomMessage(chatIdString, str));
		}
		return getListOfMessages(gameSessionToLeave, leaveGameInlineKeyboardMarkup, chatIdString);
	}

	public List<SendMessage> getAllTablesToDelete (final String chatIdString, final String username) {
		deleteAllOutdatedGameSessions();
		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		List<GameSession> gameSessionToDelete = gameSessionList.stream()
			.filter(s -> username.equals(s.getOrganizerUsername()))
			.toList();
		if (gameSessionToDelete.isEmpty()) {
			String str = "ВЫ НЕ ОРГАНИЗОВАЛИ НИОДНОЙ ИГРОВОЙ ВСТРЕЧИ";
			return List.of(getCustomMessage(chatIdString, str));
		}
		return getListOfMessages(gameSessionToDelete, deleteGameInlineKeyboardMarkup, chatIdString);
	}

	public SendMessage getAllTables (final String chatIdString) {
		deleteAllOutdatedGameSessions();
		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		if (gameSessionList.isEmpty()) {
			return getCustomMessage(chatIdString, "В БЛИЖАЙШЕЕ ВРЕМЯ НЕТ ЗАПЛАНИРОВАННЫХ ИГРОВЫХ ВСТРЕЧ");
		}
		StringJoiner joiner = new StringJoiner(System.lineSeparator());
		joiner.add("<b>СПИСОК ВСЕХ ИГРОВЫХ ВСТРЕЧ:</b>");
		joiner.add("");

		int counter = 1;
		for (GameSession session : gameSessionList) {
			joiner.add(counter++ + ".");
			joiner.add(getString(session));
			joiner.add("");
		}
		return getCustomMessage(chatIdString, joiner.toString());
	}

	private List<SendMessage> getListOfMessages(List<GameSession> list, InlineKeyboardMarkup markup, String chatId) {
		List<SendMessage> resultList = new ArrayList<>();
		for (GameSession session : list) {
			resultList.add(getCustomMessageWithMarkup(chatId, getString(session), markup));
		}
		return resultList;
	}

	public GameSession deleteTable(String date, String organizer) {
		GameSession gameSession = getGameSession(date, organizer);
		gameSessionService.deleteGameSessionById(gameSession.getId());
		return gameSession;
	}

	public GameSession addPlayerToTable(String date, String organizer, String playerUsername) {
		GameSession gameSession = getGameSession(date, organizer);
		gameSession.getPlayers().add(playerUsername);
		return gameSessionService.updateGameSession(gameSession);
	}

	public GameSession leaveGameTable(String date, String organizer, String playerUsername) {
		GameSession gameSession = getGameSession(date, organizer);
		gameSession.getPlayers().remove(playerUsername);
		return gameSessionService.updateGameSession(gameSession);
	}

	private GameSession getGameSession(String date, String organizer) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
		return gameSessionService.findGameSessionByDateAndOrganizer(dateTime, organizer);
	}

	private void deleteAllOutdatedGameSessions() {
		gameSessionService.deleteOutdatedGameSessions();
	}

	public SendMessage getEditedSession(String chatIdString, GameSession gameSession) {
		return getCustomMessage(chatIdString, getString(gameSession));
	}

	public SendMessage getCustomMessage(String chatIdString, String text) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text(text)
			.build();
	}

	public SendMessage getCustomMessageWithMarkup(String chatIdString, String text, InlineKeyboardMarkup markup) {
		SendMessage message = getCustomMessage(chatIdString, text);
		message.setReplyMarkup(markup);
		return message;
	}
}

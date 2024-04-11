package lv.boardgame.bot.mybot;

import lv.boardgame.bot.model.GameSession;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class GameSessionConstructor {
	private GameSession gameSession = new GameSession();
	private LocalDate date;
	private LocalTime time;

	public GameSession getGameSession() {
		return gameSession;
	}

	public void clear() {
		gameSession.setId(null);
		gameSession.setOrganizerUsername(null);
		gameSession.setOrganizerPlaying(false);
		gameSession.setDate(null);
		gameSession.setPlace(null);
		gameSession.setGameName(null);
		gameSession.setMaxPlayerCount(0);
		gameSession.getPlayers().clear();
		gameSession.setComment(null);
	}

	public void setOrganizerUsername(String organizerUsername) {
		gameSession.setOrganizerUsername(organizerUsername);
	}

	public void setDate(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		date = LocalDate.parse(dateString, formatter);
	}

	public void setTime(String timeString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		time = LocalTime.parse(timeString, formatter);
	}

	public void setDateTime() {
		gameSession.setDate(LocalDateTime.of(date, time));
	}

	public void setIfOrganizerPlaying(String ifOrganizerPlaying) {
		gameSession.setOrganizerPlaying(Boolean.parseBoolean(ifOrganizerPlaying));
	}

	public void setPlace(String place) {
		gameSession.setPlace(place);
	}

	public void setGameName(String gameName) {
		gameSession.setGameName(gameName);
	}

	public void setComment(String comment) {
		if (!"Нет комментариев".equals(comment)) {
			gameSession.setComment(comment);
		}
	}

	public void setMaxPlayerCount(String count) {
		gameSession.setMaxPlayerCount(Integer.parseInt(count));
	}
}

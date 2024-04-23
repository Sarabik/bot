package lv.boardgame.bot.mybot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.GameSession;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class GameSessionConstructor {

	private final Map<String, GameSessionBotStatePair> constructorMap = new HashMap<>();

	public GameSession getGameSession(String username) {
		return getGameSessionByUsername(username).getGameSession();
	}

	private GameSessionBotStatePair getGameSessionByUsername(String username) {
		return constructorMap.get(username);
	}

	public BotState getBotState(String username) {
		if (constructorMap.containsKey(username)) {
			return getGameSessionByUsername(username).getBotState();
		} else {
			return null;
		}
	}

	public void clear(String username) {
		constructorMap.remove(username);
	}

	public void start(String username) {
		GameSession gs = new GameSession();
		gs.setOrganizerUsername(username);
		constructorMap.put(username, new GameSessionBotStatePair(gs, BotState.WAITING_DATE));
	}

	public void setDate(String username, String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
		pair.setBotState(BotState.WAITING_TIME);
		LocalDate date = LocalDate.parse(dateString, formatter);
		LocalTime time = LocalTime.of(0,0, 0);
		pair.getGameSession().setDate(LocalDateTime.of(date, time));
	}

	public void setTime(String username, String timeString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime time = LocalTime.parse(timeString, formatter);
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
		pair.setBotState(BotState.WAITING_PLACE);
		LocalDateTime dateTime = pair.getGameSession().getDate();
		pair.getGameSession().setDate(dateTime.toLocalDate().atTime(time));
	}

	public void setIfOrganizerPlaying(String username, String ifOrganizerPlaying) {
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
		pair.setBotState(BotState.WAITING_MAX_PLAYER_COUNT);
		pair.getGameSession().setOrganizerPlaying(Boolean.parseBoolean(ifOrganizerPlaying));
	}

	public void setPlace(String username, String place) {
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
		pair.setBotState(BotState.WAITING_GAME_NAME);
		pair.getGameSession().setPlace(place);
	}

	public void setGameName(String username, String gameName) {
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
		pair.setBotState(BotState.WAITING_IF_ORGANIZER_PLAYING);
		pair.getGameSession().setGameName(gameName);
	}

	public void setComment(String username, String comment) {
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
			pair.getGameSession().setComment(comment);
	}

	public void setMaxPlayerCount(String username, String count) {
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
		pair.setBotState(BotState.WAITING_COMMENT);
		pair.getGameSession().setMaxPlayerCount(Integer.parseInt(count));
	}

	@Data
	@AllArgsConstructor
	public static final class GameSessionBotStatePair {
		private GameSession gameSession;
		private BotState botState;
	}
}

package lv.boardgame.bot.mybot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lv.boardgame.bot.commands.callbackQueryCommand.GameSessionDeletedCallback;
import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static lv.boardgame.bot.TextFinals.DATE_FORMATTER;
import static lv.boardgame.bot.TextFinals.TIME_FORMATTER;

@Component
public class GameSessionConstructor {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private final Map<String, GameSessionBotStatePair> constructorMap = new HashMap<>();

	public GameSession getGameSession(String username) {
		return getGameSessionByUsername(username).getGameSession();
	}

	private GameSessionBotStatePair getGameSessionByUsername(String username) {
		return constructorMap.get(username);
	}

	public BotState getBotState(String username) {
		if (constructorMap.containsKey(username)) {
			BotState botState = getGameSessionByUsername(username).getBotState();
			LOG.info("{} -> Current bot state: {}", username, botState);
			return botState;
		} else {
			LOG.info("{} -> There is no active bot state", username);
			return null;
		}
	}

	public void clear(String username) {
		LOG.info("{} -> Session constructor cleared", username);
		constructorMap.remove(username);
	}

	public void start(String chatId, String username) {
		LOG.info("{} -> New session creation is started", username);
		GameSession gs = new GameSession();
		gs.setOrganizer(new Player(chatId, username));
		constructorMap.put(username, new GameSessionBotStatePair(gs, BotState.WAITING_DATE));
		LOG.info("{} -> Bot state is changed to WAITING_DATE", username);
	}

	public void setDate(String username, String dateString) {
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
		LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
		LocalTime time = LocalTime.of(0,0, 0);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		pair.getGameSession().setDate(dateTime);
		LOG.info("{} -> Added game session date: {}", username, date);
		pair.setBotState(BotState.WAITING_TIME);
		LOG.info("{} -> Bot state is changed to WAITING_TIME", username);
	}

	public void setTime(String username, String timeString) {
		LocalTime time = LocalTime.parse(timeString, TIME_FORMATTER);
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
		LocalDateTime dateTime = pair.getGameSession().getDate();
		pair.getGameSession().setDate(dateTime.toLocalDate().atTime(time));
		LOG.info("{} -> Added game session time: {}", username, time);
		pair.setBotState(BotState.WAITING_PLACE);
		LOG.info("{} -> Bot state is changed to WAITING_PLACE", username);
	}

	public void setIfOrganizerPlaying(String username, String ifOrganizerPlaying) {
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
		boolean ifPlaying = Boolean.parseBoolean(ifOrganizerPlaying);
		pair.getGameSession().setOrganizerPlaying(ifPlaying);
		LOG.info("{} -> Organizer will play: {}", username, ifPlaying);
		pair.setBotState(BotState.WAITING_MAX_PLAYER_COUNT);
		LOG.info("{} -> Bot state is changed to WAITING_MAX_PLAYER_COUNT", username);
	}

	public void setPlace(String username, String place) {
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
		pair.getGameSession().setPlace(place);
		LOG.info("{} -> Added game session place: {}", username, place);
		pair.setBotState(BotState.WAITING_GAME_NAME);
		LOG.info("{} -> Bot state is changed to WAITING_GAME_NAME", username);
	}

	public void setGameName(String username, String gameName) {
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
		pair.getGameSession().setGameName(gameName);
		LOG.info("{} -> Added game name: {}", username, gameName);
		pair.setBotState(BotState.WAITING_IF_ORGANIZER_PLAYING);
		LOG.info("{} -> Bot state is changed to WAITING_IF_ORGANIZER_PLAYING", username);
	}

	public void setComment(String username, String comment) {
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
		pair.getGameSession().setComment(comment);
		LOG.info("{} -> Added comment for game session: {}", username, comment);
	}

	public void setMaxPlayerCount(String username, String count) {
		GameSessionBotStatePair pair = getGameSessionByUsername(username);
		pair.getGameSession().setMaxPlayerCount(Integer.parseInt(count));
		LOG.info("{} -> Added game session max player count: {}", username, count);
		pair.setBotState(BotState.WAITING_COMMENT);
		LOG.info("{} -> Bot state is changed to WAITING_COMMENT", username);
	}

	@Data
	@AllArgsConstructor
	public static final class GameSessionBotStatePair {
		private GameSession gameSession;
		private BotState botState;
	}
}

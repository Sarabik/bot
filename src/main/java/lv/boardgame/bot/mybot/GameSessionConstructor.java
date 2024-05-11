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

	private final Map<Player, GameSessionBotStatePair> constructorMap = new HashMap<>();

	public GameSession getGameSession(Player player) {
		return getGameSessionBotStatePairByPlayer(player).getGameSession();
	}

	private GameSessionBotStatePair getGameSessionBotStatePairByPlayer(Player player) {
		return constructorMap.get(player);
	}

	public BotState getBotState(Player player) {
		if (constructorMap.containsKey(player)) {
			BotState botState = getGameSessionBotStatePairByPlayer(player).getBotState();
			LOG.info("{} -> Current bot state: {}", player, botState);
			return botState;
		} else {
			LOG.info("{} -> There is no active bot state", player);
			return null;
		}
	}

	public void clear(Player player) {
		constructorMap.remove(player);
		LOG.info("{} -> Session constructor cleared", player);
	}

	public void start(String chatId, Player player) {
		LOG.info("{} -> New session creation is started", player);
		GameSession gs = new GameSession();
		gs.setOrganizer(player);
		constructorMap.put(player, new GameSessionBotStatePair(gs, BotState.WAITING_DATE));
		LOG.info("{} -> Bot state is changed to WAITING_DATE", player);
	}

	public void setDate(Player player, String dateString) {
		GameSessionBotStatePair pair = getGameSessionBotStatePairByPlayer(player);
		LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
		LocalTime time = LocalTime.of(0,0, 0);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		pair.getGameSession().setDate(dateTime);
		LOG.info("{} -> Added game session date: {}", player, date);
		pair.setBotState(BotState.WAITING_TIME);
		LOG.info("{} -> Bot state is changed to WAITING_TIME", player);
	}

	public void setTime(Player player, String timeString) {
		LocalTime time = LocalTime.parse(timeString, TIME_FORMATTER);
		GameSessionBotStatePair pair = getGameSessionBotStatePairByPlayer(player);
		LocalDateTime dateTime = pair.getGameSession().getDate();
		pair.getGameSession().setDate(dateTime.toLocalDate().atTime(time));
		LOG.info("{} -> Added game session time: {}", player, time);
		pair.setBotState(BotState.WAITING_PLACE);
		LOG.info("{} -> Bot state is changed to WAITING_PLACE", player);
	}

	public void setIfOrganizerPlaying(Player player, String ifOrganizerPlaying) {
		GameSessionBotStatePair pair = getGameSessionBotStatePairByPlayer(player);
		boolean ifPlaying = Boolean.parseBoolean(ifOrganizerPlaying);
		pair.getGameSession().setOrganizerPlaying(ifPlaying);
		LOG.info("{} -> Organizer will play: {}", player, ifPlaying);
		pair.setBotState(BotState.WAITING_MAX_PLAYER_COUNT);
		LOG.info("{} -> Bot state is changed to WAITING_MAX_PLAYER_COUNT", player);
	}

	public void setPlace(Player player, String place) {
		GameSessionBotStatePair pair = getGameSessionBotStatePairByPlayer(player);
		pair.getGameSession().setPlace(place);
		LOG.info("{} -> Added game session place: {}", player, place);
		pair.setBotState(BotState.WAITING_GAME_NAME);
		LOG.info("{} -> Bot state is changed to WAITING_GAME_NAME", player);
	}

	public void setGameName(Player player, String gameName) {
		GameSessionBotStatePair pair = getGameSessionBotStatePairByPlayer(player);
		pair.getGameSession().setGameName(gameName);
		LOG.info("{} -> Added game name: {}", player, gameName);
		pair.setBotState(BotState.WAITING_IF_ORGANIZER_PLAYING);
		LOG.info("{} -> Bot state is changed to WAITING_IF_ORGANIZER_PLAYING", player);
	}

	public void setComment(Player player, String comment) {
		GameSessionBotStatePair pair = getGameSessionBotStatePairByPlayer(player);
		pair.getGameSession().setComment(comment);
		LOG.info("{} -> Added comment for game session: {}", player, comment);
	}

	public void setMaxPlayerCount(Player player, String count) {
		GameSessionBotStatePair pair = getGameSessionBotStatePairByPlayer(player);
		pair.getGameSession().setMaxPlayerCount(Integer.parseInt(count));
		LOG.info("{} -> Added game session max player count: {}", player, count);
		pair.setBotState(BotState.WAITING_COMMENT);
		LOG.info("{} -> Bot state is changed to WAITING_COMMENT", player);
	}

	@Data
	@AllArgsConstructor
	public static final class GameSessionBotStatePair {
		private GameSession gameSession;
		private BotState botState;
	}
}

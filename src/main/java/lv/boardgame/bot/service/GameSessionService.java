package lv.boardgame.bot.service;

import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

public interface GameSessionService {

	GameSession saveNewGameSession(GameSession gameSession);

	GameSession findGameSessionById(ObjectId id);

	void deleteGameSessionById(ObjectId id);

	List<GameSession> findAllGameSessionsByPlayer(final Player player);

	List<GameSession> findAllGameSessions();

	GameSession findGameSessionByDateAndOrganizer(LocalDateTime date, String organizer);

	GameSession updateGameSession(final GameSession gameSession);

	void deleteOutdatedGameSessions();

	GameSession findGameSessionByDatePlaceAndGame(LocalDateTime dateTime, String place, String game);

}

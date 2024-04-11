package lv.boardgame.bot.service;

import lv.boardgame.bot.model.GameSession;
import org.bson.types.ObjectId;

import java.util.List;

public interface GameSessionService {

	GameSession saveNewGameSession(GameSession gameSession);

	GameSession findGameSessionById(ObjectId id);

	void deleteGameSessionById(ObjectId id);

	List<GameSession> findAllGameSessionsByPlayer(final String username);

	List<GameSession> findAllGameSessions();

	void updateGameSession(final GameSession gameSession);

}

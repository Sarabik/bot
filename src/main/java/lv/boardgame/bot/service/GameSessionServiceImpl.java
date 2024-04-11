package lv.boardgame.bot.service;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.repository.GameSessionRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameSessionServiceImpl implements GameSessionService {

	private final GameSessionRepository repository;

	@Override
	public GameSession saveNewGameSession(final GameSession gameSession) {
		if (gameSession.isOrganizerPlaying()) {
			gameSession.getPlayers().add(gameSession.getOrganizerUsername());
		}
		return repository.save(gameSession);
	}

	@Override
	/*exception!*/
	public GameSession findGameSessionById(final ObjectId id) {
		Optional<GameSession> opt =  repository.findById(id);
		return opt.orElse(null);
	}

	@Override
	public void deleteGameSessionById(final ObjectId id) {
		repository.deleteById(id);
	}

	@Override
	public List<GameSession> findAllGameSessionsByPlayer(final String username) {
		return findAllGameSessions()
			.stream()
			.filter(s -> s.getPlayers().contains(username))
			.sorted(Comparator.comparing(GameSession::getDate))
			.toList();
	}

	@Override
	public List<GameSession> findAllGameSessions() {
		return repository.findAll().stream().sorted(Comparator.comparing(GameSession::getDate)).toList();
	}

	@Override
	public void updateGameSession(final GameSession gameSession) {
		repository.save(gameSession);
	}

	@Override
	public GameSession findGameSessionByDateAndOrganizer(LocalDateTime date, String organizer) {
		Optional<GameSession> opt = findAllGameSessions().stream()
			.filter(s -> s.getDate().equals(date) && s.getOrganizerUsername().equals(organizer))
			.findFirst();
		return opt.orElse(null);
	}
}

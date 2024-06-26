package lv.boardgame.bot.service;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.commands.callbackQueryCommand.GameSessionDeletedCallback;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.repository.GameSessionRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameSessionServiceImpl implements GameSessionService {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private final GameSessionRepository repository;

	@Override
	public GameSession saveNewGameSession(final GameSession gameSession) {
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
	public List<GameSession> findAllGameSessionsByPlayer(final Player player) {
		return findAllGameSessions()
			.stream()
			.filter(s -> s.getPlayers().contains(player))
			.sorted(Comparator.comparing(GameSession::getDate))
			.toList();
	}

	@Override
	public List<GameSession> findAllGameSessions() {
		return repository.findAll().stream().sorted(Comparator.comparing(GameSession::getDate)).toList();
	}

	@Override
	public GameSession updateGameSession(final GameSession gameSession) {
		return repository.save(gameSession);
	}

	@Override
	public void deleteOutdatedGameSessions() {
		LOG.info("Ready to delete outdated sessions");
		List<GameSession> list = findAllGameSessions();
		LocalDateTime date = LocalDateTime.now(ZoneId.of("UTC+3"));
		date = date.minusHours(1);
		LOG.info("If session date was before {}, delete it", date);
		for (GameSession gs : list) {
			LOG.info("Game session with game name {} - time: {}", gs.getGameName(), gs.getDate());
			if (gs.getDate().isBefore(date)) {
				repository.deleteById(gs.getId());
				LOG.info("Deleted outdated game session: {}", gs);
			}
		}
	}

	@Override
	public GameSession findGameSessionByDatePlaceAndGame(
		final LocalDateTime date,
		final String place,
		final String game
	) {
		Optional<GameSession> opt = findAllGameSessions().stream()
			.filter(s -> date.equals(s.getDate()) && place.equals(s.getPlace()) && game.equals(s.getGameName()))
			.findFirst();
		return opt.orElse(null);
	}

	@Override
	public GameSession findGameSessionByDateAndOrganizer(LocalDateTime date, String organizer) {
		Optional<GameSession> opt;
		if (organizer.startsWith("@")) {
			opt = findAllGameSessions().stream()
				.filter(s -> date.equals(s.getDate()) && organizer.substring(1).equals(s.getOrganizer().getUsername()))
				.findFirst();
		} else {
			String[] array = organizer.split(" ");
			opt = findAllGameSessions().stream()
				.filter(s -> date.equals(s.getDate()) && array[0].equals(s.getOrganizer().getFirstName()))
				.findFirst();
		}
		return opt.orElse(null);
	}


}

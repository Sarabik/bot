package lv.boardgame.bot.repository;

import lv.boardgame.bot.model.GameSession;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameSessionRepository extends MongoRepository<GameSession, ObjectId> {
}

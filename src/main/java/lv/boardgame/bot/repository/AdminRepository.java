package lv.boardgame.bot.repository;

import lv.boardgame.bot.model.Admin;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends MongoRepository<Admin, ObjectId> {
	void deleteByUsername(String username);
}

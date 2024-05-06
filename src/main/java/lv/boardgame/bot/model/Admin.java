package lv.boardgame.bot.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admins")
@Data
@NoArgsConstructor
public class Admin {
	@Id
	private ObjectId id;

	private String username;

	public Admin(final String username) {
		this.username = username;
	}
}

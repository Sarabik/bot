package lv.boardgame.bot.model;

import com.mongodb.lang.NonNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admins")
@Data
@NoArgsConstructor
public class Admin {
	@Id
	private ObjectId id;
	@Indexed(unique = true)
	@NonNull
	private String username;

	public Admin(@NonNull final String username) {
		this.username = username;
	}
}

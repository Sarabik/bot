package lv.boardgame.bot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "game_session")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameSession {
	@Id
	private ObjectId id;
	private String organizerUsername;
	private boolean isOrganizerPlaying;
	private LocalDateTime date;
	private String place;
	private String gameName;
	private int maxPlayerCount;
	private Set<String> players = new HashSet<>();
	private String comment;
}

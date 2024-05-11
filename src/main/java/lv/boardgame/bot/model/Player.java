package lv.boardgame.bot.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Player {
	@EqualsAndHashCode.Include
	private String chatId;

	private String username;

	private String firstName;

	private String lastName;
}

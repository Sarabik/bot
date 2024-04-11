package lv.boardgame.bot.messages;

import lv.boardgame.bot.model.GameSession;

import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

public class ConvertGameSessionToString {
	public static String getString(GameSession gmSession) {
		StringJoiner joiner = new StringJoiner(System.lineSeparator());
		String date = gmSession.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		joiner.add("<b>Когда:  </b><i>" + date + "</i>");
		joiner.add("<b>Где:  </b><i>" + gmSession.getPlace() + "</i>");
		joiner.add("<b>Игра / игры:  </b><i>" + gmSession.getGameName() + "</i>");
		joiner.add("<b>Организатор:  </b><i>@" + gmSession.getOrganizerUsername() + "</i>");
		if (gmSession.getComment() != null) {
			joiner.add("<b>Комментарий:  </b><i>" + gmSession.getComment() + "</i>");
		}
		if (!gmSession.getPlayers().isEmpty()) {
			StringJoiner nameJoiner = new StringJoiner(", ");
			for (String name : gmSession.getPlayers()) {
				nameJoiner.add("@" + name);
			}
			joiner.add("<b>Играют:  </b><i>" + nameJoiner + "</i>");
		}
		joiner.add("<b>Свободных мест:  </b><i>" + (gmSession.getMaxPlayerCount() - gmSession.getPlayers().size()) + "</i>");
		return joiner.toString();
	}
}

package lv.boardgame.bot.messages;

import lv.boardgame.bot.model.GameSession;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static lv.boardgame.bot.TextFinals.DATE_TIME_FORMATTER;

public class MessageUtil {

	public static SendMessage getCustomMessage(String chatIdString, String text) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text(text)
			.build();
	}

	public static SendMessage getCustomMessageWithMarkup(String chatIdString, String text, InlineKeyboardMarkup markup) {
		SendMessage message = getCustomMessage(chatIdString, text);
		message.setReplyMarkup(markup);
		return message;
	}

	public static List<SendMessage> getListOfMessages(List<GameSession> list, InlineKeyboardMarkup markup, String chatId) {
		List<SendMessage> resultList = new ArrayList<>();
		for (GameSession session : list) {
			resultList.add(getCustomMessageWithMarkup(chatId, convertGameSessionToString(session), markup));
		}
		return resultList;
	}

	public static SendMessage getEditedSession(String chatIdString, GameSession gameSession) {
		return getCustomMessage(chatIdString, convertGameSessionToString(gameSession));
	}

	public static String convertGameSessionToString(GameSession gmSession) {
		StringJoiner joiner = new StringJoiner(System.lineSeparator());
		String date = gmSession.getDate().format(DATE_TIME_FORMATTER);
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

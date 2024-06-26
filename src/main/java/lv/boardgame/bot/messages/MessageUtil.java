package lv.boardgame.bot.messages;

import lv.boardgame.bot.commands.callbackQueryCommand.GameSessionDeletedCallback;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Group;
import lv.boardgame.bot.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static lv.boardgame.bot.TextFinals.DATE_TIME_FORMATTER;

public class MessageUtil {

	private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

	private static List<String> getGroupList(String groupIds) {
		String[] array = groupIds.split(" ");
		List<String> list = new ArrayList<>(List.of(array));
		System.out.println(list);
		return list;
	}

	private static SendMessage getMessageForGroup(String str, String groupId, String botUsername) {
		String botPrivateChatUrl = "<b><a href='https://t.me/" + botUsername + "'>ПЕРЕЙТИ В БОТ</a></b>";
		return getCustomMessage(groupId, str +
			System.lineSeparator() +
			System.lineSeparator() +
			botPrivateChatUrl);
	}

	public static List<SendMessage> getGroupSendMessages(String str, List<Group> groupList, String botUsername) {
		List<SendMessage> list = new ArrayList<>();
		for (Group group : groupList) {
			SendMessage message = getMessageForGroup(str, group.getId(), botUsername);
			if (group.getThreadId() != null) {
				message.setMessageThreadId(group.getThreadId());
			}
			list.add(message);
			LOG.info("Prepared message to group with name: {}", group.getName());
		}
		return list;
	}

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
		Player organizer = gmSession.getOrganizer();
		joiner.add("<b>Организатор:  </b><i>" + getPlayerNameString(organizer) + "</i>");
		if (gmSession.getComment() != null) {
			joiner.add("<b>Комментарий:  </b><i>" + gmSession.getComment() + "</i>");
		}
		if (!gmSession.getPlayers().isEmpty()) {
			StringJoiner nameJoiner = new StringJoiner(", ");
			for (Player player : gmSession.getPlayers()) {
				nameJoiner.add(getPlayerNameString(player));
			}
			joiner.add("<b>Играют:  </b><i>" + nameJoiner + "</i>");
		}
		joiner.add("<b>Свободных мест:  </b><i>" + (gmSession.getFreePlayerSlots() - gmSession.getPlayers().size()) + "</i>");
		return joiner.toString();
	}

	public static String getPlayerNameString(Player player) {
		String orgStr = "";
		if (player.getUsername() != null) {
			orgStr = "@" + player.getUsername();
		} else {
			if (player.getFirstName() != null) {
				orgStr = player.getFirstName();
			} else if (player.getLastName() != null) {
				orgStr = orgStr + " " + player.getLastName();
			}
		}
		return orgStr;
	}

}

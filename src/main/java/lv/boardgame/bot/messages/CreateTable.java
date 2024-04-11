package lv.boardgame.bot.messages;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.inlineKeyboard.DateInlineKeyboardMarkup;
import lv.boardgame.bot.inlineKeyboard.IfOrganizerPlayingInlineKeyboardMarkup;
import lv.boardgame.bot.inlineKeyboard.MaxPlayerCountInlineKeyboardMarkup;
import lv.boardgame.bot.inlineKeyboard.NoCommentInlineKeyboardMarkup;
import lv.boardgame.bot.inlineKeyboard.TimeInlineKeyboardMarkup;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.service.GameSessionService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

@Component
@AllArgsConstructor
public class CreateTable {

	private DateInlineKeyboardMarkup dateInlineKeyboardMarkup;

	private TimeInlineKeyboardMarkup timeInlineKeyboardMarkup;

	private MaxPlayerCountInlineKeyboardMarkup maxPlayerCountInlineKeyboardMarkup;

	private IfOrganizerPlayingInlineKeyboardMarkup ifOrganizerPlayingInlineKeyboardMarkup;

	private NoCommentInlineKeyboardMarkup noCommentInlineKeyboardMarkup;

	private GameSessionService gameSessionService;

	public SendMessage askForDate(String chatIdString) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text("<b>Дата:</b>")
			.replyMarkup(dateInlineKeyboardMarkup)
			.build();
	}

	public SendMessage askForTime(String chatIdString) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text("<b>Время:</b>")
			.replyMarkup(timeInlineKeyboardMarkup)
			.build();
	}

	public SendMessage askForPlace(String chatIdString) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text("<b>Место (адрес, заведение и т.д.):</b>")
			.build();
	}

	public SendMessage askForGameName(String chatIdString) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text("<b>Название игры или нескольких игр:</b>")
			.build();
	}

	public SendMessage askForMaxPlayerCount(String chatIdString) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text("<b>Максимальное количество игроков (включая вас, если играете):</b>")
			.replyMarkup(maxPlayerCountInlineKeyboardMarkup)
			.build();
	}

	public SendMessage askForIfOrganizerPlaying(String chatIdString) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text("<b>Будете ли вы сами играть?</b>")
			.replyMarkup(ifOrganizerPlayingInlineKeyboardMarkup)
			.build();
	}

	public SendMessage askForComment(final String chatIdString) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text("<b>Если хотите, напишите комментарий или дополнительную информацию:</b>")
			.replyMarkup(noCommentInlineKeyboardMarkup)
			.build();
	}

	public SendMessage savingTable(final String chatIdString, final GameSession gameSession) {
		GameSession gmSession = gameSessionService.saveNewGameSession(gameSession);
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text(resume(gmSession))
			.build();
	}

	private String resume(GameSession gmSession) {
		StringJoiner joiner = new StringJoiner(System.lineSeparator());
		joiner.add("<b>ГОТОВО! СТОЛ СОЗДАН!</b>");
		joiner.add("");
		String date = gmSession.getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
		joiner.add("<b>Когда:  </b><i>" + date + "</i>");
		joiner.add("<b>Где:  </b><i>" + gmSession.getPlace() + "</i>");
		joiner.add("<b>Игра / игры:  </b><i>" + gmSession.getGameName() + "</i>");
		joiner.add("<b>Организатор:  </b><i>" + gmSession.getOrganizerUsername() + "</i>");
		if (gmSession.getComment() != null) {
			joiner.add("<b>Комментарий:  </b><i>" + gmSession.getComment() + "</i>");
		}
		if (!gmSession.getPlayers().isEmpty()) {
			StringJoiner nameJoiner = new StringJoiner(", ");
			for (String name : gmSession.getPlayers()) {
				nameJoiner.add(name);
			}
			joiner.add("<b>Играют:  </b><i>" + nameJoiner + "</i>");
		}
		joiner.add("<b>Свободных мест:  </b><i>" + (gmSession.getMaxPlayerCount() - gmSession.getPlayers().size()) + "</i>");
		return joiner.toString();
	}
}

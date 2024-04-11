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

import static lv.boardgame.bot.messages.ConvertGameSessionToString.getString;

@Component
@AllArgsConstructor
public class CreateTable {

	private DateInlineKeyboardMarkup dateInlineKeyboardMarkup;

	private TimeInlineKeyboardMarkup timeInlineKeyboardMarkup;

	private MaxPlayerCountInlineKeyboardMarkup maxPlayerCountInlineKeyboardMarkup;

	private IfOrganizerPlayingInlineKeyboardMarkup ifOrganizerPlayingInlineKeyboardMarkup;

	private NoCommentInlineKeyboardMarkup noCommentInlineKeyboardMarkup;

	private GameSessionService gameSessionService;

	public SendMessage askForDate(final String chatIdString) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text("<b>Дата:</b>")
			.replyMarkup(dateInlineKeyboardMarkup)
			.build();
	}

	public SendMessage askForTime(final String chatIdString) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text("<b>Время:</b>")
			.replyMarkup(timeInlineKeyboardMarkup)
			.build();
	}

	public SendMessage askForPlace(final String chatIdString) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text("<b>Место (адрес, заведение и т.д.):</b>")
			.build();
	}

	public SendMessage askForGameName(final String chatIdString) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text("<b>Название игры или нескольких игр:</b>")
			.build();
	}

	public SendMessage askForMaxPlayerCount(final String chatIdString) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text("<b>Максимальное количество игроков (включая вас, если играете):</b>")
			.replyMarkup(maxPlayerCountInlineKeyboardMarkup)
			.build();
	}

	public SendMessage askForIfOrganizerPlaying(final String chatIdString) {
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
			.text("<b>ГОТОВО! СТОЛ СОЗДАН!</b>" + System.lineSeparator() + getString(gmSession))
			.build();
	}
}

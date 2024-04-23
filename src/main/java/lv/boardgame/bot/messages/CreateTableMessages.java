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

import static lv.boardgame.bot.messages.MessageUtil.*;

@Component
@AllArgsConstructor
public class CreateTableMessages {

	private DateInlineKeyboardMarkup dateInlineKeyboardMarkup;

	private TimeInlineKeyboardMarkup timeInlineKeyboardMarkup;

	private MaxPlayerCountInlineKeyboardMarkup maxPlayerCountInlineKeyboardMarkup;

	private IfOrganizerPlayingInlineKeyboardMarkup ifOrganizerPlayingInlineKeyboardMarkup;

	private NoCommentInlineKeyboardMarkup noCommentInlineKeyboardMarkup;

	private GameSessionService gameSessionService;

	private final static String DATA = "<b>Дата:</b>";

	private final static String TIME = "<b>Время:</b>";

	private final static String PLACE = "<b>Место (адрес, заведение и т.д.):</b>";

	private final static String GAME_NAME = "<b>Название игры или нескольких игр:</b>";

	private final static String ORGANIZER_PLAYING = "<b>Будете ли вы сами играть?</b>";

	private final static String GAME_SESSION_CREATED = "<b>ИГРОВАЯ ВСТРЕЧА СОЗДАНА!</b>";

	private final static String WRITE_COMMENT = "<b>Если хотите, напишите комментарий или дополнительную информацию:</b>";

	private final static String MAX_PLAYER_COUNT = "<b>Максимальное количество игроков (включая вас, если играете):</b>";

	private final static String START = "Пожалуйста, последовательно дайте ответы на все вопросы. Когда запись о игровой встрече будет создана, вы увидите сообщение об этом.";

	public SendMessage askForDate(final String chatIdString) {
		return getCustomMessageWithMarkup(chatIdString, DATA, dateInlineKeyboardMarkup);
	}

	public SendMessage createTable(final String chatIdString) {
		return getCustomMessage(chatIdString, START);
	}

	public SendMessage askForTime(final String chatIdString) {
		return getCustomMessageWithMarkup(chatIdString, TIME, timeInlineKeyboardMarkup);
	}

	public SendMessage askForPlace(final String chatIdString) {
		return getCustomMessage(chatIdString, PLACE);
	}

	public SendMessage askForGameName(final String chatIdString) {
		return getCustomMessage(chatIdString, GAME_NAME);
	}

	public SendMessage askForMaxPlayerCount(final String chatIdString) {
		return getCustomMessageWithMarkup(chatIdString, MAX_PLAYER_COUNT, maxPlayerCountInlineKeyboardMarkup);
	}

	public SendMessage askForIfOrganizerPlaying(final String chatIdString) {
		return getCustomMessageWithMarkup(chatIdString, ORGANIZER_PLAYING, ifOrganizerPlayingInlineKeyboardMarkup);
	}

	public SendMessage askForComment(final String chatIdString) {
		return getCustomMessageWithMarkup(chatIdString, WRITE_COMMENT, noCommentInlineKeyboardMarkup);
	}

	public SendMessage savingTable(final String chatIdString, final GameSession gameSession) {
		GameSession gmSession = gameSessionService.saveNewGameSession(gameSession);
		String str = GAME_SESSION_CREATED + System.lineSeparator() + convertGameSessionToString(gmSession);
		return getCustomMessage(chatIdString, str);
	}
}

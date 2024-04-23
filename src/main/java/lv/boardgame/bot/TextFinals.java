package lv.boardgame.bot;

import java.time.format.DateTimeFormatter;

public class TextFinals {

	public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

	/*Menu*/

	public final static String ALL_GAME_SESSIONS = "Все игровые встречи";

	public final static String JOIN = "Присоединиться";

	public final static String LEAVE = "Отписаться";

	public final static String ORGANIZE = "Организовать встречу";

	public final static String DELETE = "Отменить встречу";

	/*GameSessionCreation*/

	public final static String DATA = "<b>Дата:</b>";

	public static final String CHOSE_DATE = "Выберите дату";

	public final static String TIME = "<b>Время:</b>";

	public final static String PLACE = "<b>Место (адрес, заведение и т.д.):</b>";

	public final static String GAME_NAME = "<b>Название игры или нескольких игр:</b>";

	public final static String ORGANIZER_PLAYING = "<b>Будете ли вы сами играть?</b>";

	public final static String I_WILL_PLAY = "Да, тоже буду играть";

	public final static String I_WILL_NOT_PLAY = "Нет, хочу провести игру для других";

	public static final String PLAYING = "Вы участвуете в игре сами";

	public static final String NOT_PLAYING = "Вы не участвуете в игре, а только ее проводите";

	public final static String MAX_PLAYER_COUNT = "<b>Максимальное количество игроков (включая вас, если играете):</b>";

	public final static String WRITE_COMMENT = "<b>Если хотите, напишите комментарий или дополнительную информацию:</b>";

	public final static String NO_COMMENTS = "Нет комментариев";

	public final static String GAME_SESSION_CREATED = "<b>ИГРОВАЯ ВСТРЕЧА СОЗДАНА!</b>";

	/*AllGameSessions*/

	public final static String SESSIONS_LIST = "<b>СПИСОК ВСЕХ ИГРОВЫХ ВСТРЕЧ:</b>";

	public final static String NO_SESSIONS = "В БЛИЖАЙШЕЕ ВРЕМЯ НЕТ ЗАПЛАНИРОВАННЫХ ИГРОВЫХ ВСТРЕЧ";

	/*JoinGameSession*/

	public final static String SESSIONS_TO_JOIN = "<b>Укажите к какой игровой встрече вы хотели бы присоединиться</b>";

	public final static String NO_SESSIONS_TO_JOIN = "НЕТ ИГРОВЫХ ВСТРЕЧ, К КОТОРЫМ ВЫ МОГЛИ БЫ ПРИСОЕДИНИТЬСЯ";

	public final static String JOIN_SESSION = "Присоединиться к встрече";

	public final static String JOINED_SESSION = "ВЫ ПРИСОЕДИНИЛИСЬ К ВСТРЕЧЕ:";

	/*LeaveGameSession*/

	public final static String SESSION_TO_LEAVE = "<b>Укажите от участия в какой игровой встрече вы хотели бы отписаться</b>";

	public final static String DONT_JOIN = "ВЫ НЕ УЧАСТВУЕТЕ НИ В ОДНОЙ ИЗ ИГРОВЫХ ВСТРЕЧ, ОРГАНИЗОВАННЫХ ДРУГИМИ ИГРОКАМИ";

	public final static String LEAVE_SESSION = "Отписаться";

	public final static String SESSION_LEAVED = "ВЫ ОТПИСАЛИСЬ ОТ ИГРОВОЙ ВСТРЕЧИ:";

	/*DeleteGameSession*/

	public final static String SESSION_TO_DELETE = "<b>Укажите какую организованную вами игровую встречу вы хотели бы отменить</b>";

	public final static String NO_ORGANIZED = "ВЫ НЕ ОРГАНИЗОВАЛИ НИОДНОЙ ИГРОВОЙ ВСТРЕЧИ";

	public final static String SESSION_DELETED = "ИГРОВАЯ ВСТРЕЧА ОТМЕНЕНА:";

	public final static String DELETE_SESSION = "Отменить игровую встречу";

	/*MenuMessage*/

	public final static String START = "Пожалуйста, последовательно дайте ответы на все вопросы. Когда запись о игровой встрече будет создана, вы увидите сообщение об этом.";

	public final static String MENU_MESSAGE = "<b>Для того чтобы</b>" + System.lineSeparator() +
		"  <i>1) присоединиться или отписаться от игровой встречи</i>" + System.lineSeparator() +
		"  <i>2) создать или отменить свою игровую встречу</i>" + System.lineSeparator() +
		"<b>используйте кнопки меню внизу</b>";

}

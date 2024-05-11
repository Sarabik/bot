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

	public static final String CHOSE_TIME = "Выберите время";

	public final static String PLACE = "<b>Место (адрес, заведение и т.д.):</b>";

	public final static String GAME_NAME = "<b>Название игры или нескольких игр:</b>";

	public final static String FREE_PLAYER_SLOTS = "<b>Количество свободных мест для записи на встречу:</b>";

	public final static String CHOSE_FREE_PLAYER_SLOTS = "Укажите количество свободных мест для записи";

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

	public final static String PLAYER_JOINED_YOUR_GAME_SESSION = "К ВАШЕЙ ИГРОВОЙ ВСТРЕЧЕ ПРИСОЕДИНИЛСЯ ИГРОК: ";

	/*LeaveGameSession*/

	public final static String SESSION_TO_LEAVE = "<b>Укажите от участия в какой игровой встрече вы хотели бы отписаться</b>";

	public final static String DONT_JOIN = "ВЫ НЕ УЧАСТВУЕТЕ НИ В ОДНОЙ ИЗ ИГРОВЫХ ВСТРЕЧ, ОРГАНИЗОВАННЫХ ДРУГИМИ ИГРОКАМИ";

	public final static String LEAVE_SESSION = "Отписаться";

	public final static String SESSION_LEAVED = "ВЫ ОТПИСАЛИСЬ ОТ ИГРОВОЙ ВСТРЕЧИ:";

	public final static String PLAYER_LEAVED_YOUR_GAME_SESSION = "ВАШУ ИГРОВУЮ ВСТРЕЧУ ПОКИНУЛ ИГРОК: ";

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

	/*SuperAdminCommands*/

	public final static String ADD_ADMIN = "add_admin";
	public final static String DELETE_ADMIN = "delete_admin";
	public final static String GET_ADMIN_LIST = "get_admin_list";

	public final static String ADMIN_DELETED = "Удален админ: ";
	public final static String NO_ADMIN_ACCESS = "У вас нет доступа к этой команде";
	public final static String NO_ADMINS = "В списке нет админов";
	public final static String ADMIN_ADDED = "Добавлен админ: ";

	/*AdminCommands*/
	public final static String ADMIN_DELETE_SESSION = "delete_session";
}

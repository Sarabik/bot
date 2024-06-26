package lv.boardgame.bot.commands.messageCommand;

import lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil;
import lv.boardgame.bot.inlineKeyboard.DeleteGameInlineKeyboardMarkup;
import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.service.AdminService;
import lv.boardgame.bot.service.GameSessionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static lv.boardgame.bot.TextFinals.NO_ADMIN_ACCESS;
import static lv.boardgame.bot.TextFinals.SESSION_TO_DELETE;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessage;
import static lv.boardgame.bot.messages.MessageUtil.getListOfMessages;

@Component
public class AdminDeleteSession implements MessageCommand {

	@Value("${telegram.adminUsername}")
	private String superAdmin;

	private final AdminService adminService;

	private final GameSessionService gameSessionService;

	private final DeleteGameInlineKeyboardMarkup deleteGameInlineKeyboardMarkup;

	private AdminDeleteSession(final AdminService adminService, final GameSessionService gameSessionService,
		final DeleteGameInlineKeyboardMarkup deleteGameInlineKeyboardMarkup
	) {
		this.adminService = adminService;
		this.gameSessionService = gameSessionService;
		this.deleteGameInlineKeyboardMarkup = deleteGameInlineKeyboardMarkup;
	}

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String receivedText) {
		if (!(ifUserIsAdmin(player) || superAdmin.equals(player.getUsername()))) {
			return List.of(getCustomMessage(chatId, NO_ADMIN_ACCESS));
		}
		List<SendMessage> messageList = CallbackQueryUtil.getStartList(chatId, SESSION_TO_DELETE);
		messageList.addAll(getAllSessionsToDelete(chatId));
		return messageList;
	}

	private boolean ifUserIsAdmin(Player player) {
		return adminService.findAllAdminUsernameList().contains(player.getUsername());
	}

	public List<SendMessage> getAllSessionsToDelete (final String chatIdString) {
		List<GameSession> gameSessionList = gameSessionService.findAllGameSessions();
		return getListOfMessages(gameSessionList, deleteGameInlineKeyboardMarkup, chatIdString);
	}
}

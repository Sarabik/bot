package lv.boardgame.bot.commands.messageCommand;

import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.service.AdminService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static lv.boardgame.bot.TextFinals.ADMIN_DELETED;
import static lv.boardgame.bot.TextFinals.NO_ADMIN_ACCESS;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessage;

@Component
public class AdminDelete implements MessageCommand {

	private final AdminService adminService;

	@Value("${telegram.adminUsername}")
	private String superAdmin;

	private AdminDelete(final AdminService adminService) {
		this.adminService = adminService;
	}

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String receivedText) {
		if (!superAdmin.equals(player.getUsername())) {
			return List.of(getCustomMessage(chatId, NO_ADMIN_ACCESS));
		}
		String adminUsername = receivedText.split(" ")[1];
		adminService.deleteByUsername(adminUsername);
		return List.of(getCustomMessage(chatId, ADMIN_DELETED + adminUsername));
	}
}

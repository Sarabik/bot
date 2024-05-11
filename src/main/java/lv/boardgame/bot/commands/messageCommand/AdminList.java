package lv.boardgame.bot.commands.messageCommand;

import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.service.AdminService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.StringJoiner;

import static lv.boardgame.bot.TextFinals.NO_ADMINS;
import static lv.boardgame.bot.TextFinals.NO_ADMIN_ACCESS;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessage;

@Component
public class AdminList implements MessageCommand {

	private final AdminService adminService;

	@Value("${telegram.adminUsername}")
	private String superAdmin;

	private AdminList(final AdminService adminService) {
		this.adminService = adminService;
	}

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String receivedText) {
		if (!superAdmin.equals(player.getUsername())) {
			return List.of(getCustomMessage(chatId, NO_ADMIN_ACCESS));
		}
		List<String> list = adminService.findAllAdminUsernameList();
		if (list.isEmpty()) {
			return List.of(getCustomMessage(chatId, NO_ADMINS));
		}
		StringJoiner joiner = new StringJoiner(System.lineSeparator());
		list.forEach(joiner::add);
		return List.of(getCustomMessage(chatId, joiner.toString()));
	}
}

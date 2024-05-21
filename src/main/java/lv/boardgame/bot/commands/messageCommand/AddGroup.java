package lv.boardgame.bot.commands.messageCommand;

import lv.boardgame.bot.model.Group;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.service.GroupService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static lv.boardgame.bot.TextFinals.ERROR;
import static lv.boardgame.bot.TextFinals.GROUP_ADDED;
import static lv.boardgame.bot.TextFinals.NO_ADMIN_ACCESS;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessage;

@Component
public class AddGroup implements MessageCommand {

	@Value("${telegram.adminUsername}")
	private String superAdmin;

	private final GroupService groupService;

	public AddGroup(final GroupService groupService) {
		this.groupService = groupService;
	}

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String receivedText) {
		if (!superAdmin.equals(player.getUsername())) {
			return List.of(getCustomMessage(chatId, NO_ADMIN_ACCESS));
		}
		Group group = new Group();
		String[] arr = receivedText.split(" ");
		if (arr.length == 3) {
			group.setId(arr[1]);
			group.setName(arr[2]);
			groupService.saveGroup(group);
		} else if (arr.length == 4) {
			group.setId(arr[1]);
			group.setName(arr[2]);
			group.setThreadId(Integer.valueOf(arr[3]));
			groupService.saveGroup(group);
		} else {
			return List.of(getCustomMessage(chatId, ERROR));
		}
		return List.of(getCustomMessage(chatId, GROUP_ADDED + group.getName()));
	}
}

package lv.boardgame.bot.commands;

import lv.boardgame.bot.commands.messageCommand.AdminAdd;
import lv.boardgame.bot.commands.messageCommand.AdminDelete;
import lv.boardgame.bot.commands.messageCommand.AdminDeleteSession;
import lv.boardgame.bot.commands.messageCommand.AdminList;
import lv.boardgame.bot.commands.messageCommand.MessageCommand;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static lv.boardgame.bot.TextFinals.ADD_ADMIN;
import static lv.boardgame.bot.TextFinals.ADMIN_DELETE_SESSION;
import static lv.boardgame.bot.TextFinals.DELETE_ADMIN;
import static lv.boardgame.bot.TextFinals.GET_ADMIN_LIST;

@Component
public class AllAdminCommands {

	private final Map<String, MessageCommand> commands;

	private AllAdminCommands(
		final AdminAdd adminAdd,
		final AdminDelete adminDelete,
		final AdminList adminList,
		final AdminDeleteSession adminDeleteSession
	) {
		commands = new HashMap<>();
		commands.put(ADD_ADMIN, adminAdd);
		commands.put(DELETE_ADMIN, adminDelete);
		commands.put(GET_ADMIN_LIST, adminList);
		commands.put(ADMIN_DELETE_SESSION, adminDeleteSession);
	}

	public MessageCommand getCommand(String key) {
		return commands.get(key);
	}

	public boolean ifContainsKey(String key) {
		return commands.containsKey(key);
	}

}

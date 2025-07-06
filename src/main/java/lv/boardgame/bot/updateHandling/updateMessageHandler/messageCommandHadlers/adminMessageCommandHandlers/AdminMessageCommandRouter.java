package lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.adminMessageCommandHandlers;

import lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.MessageCommand;
import lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.adminMessageCommandHandlers.commands.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static lv.boardgame.bot.constants.TextFinals.*;

@Component
public class AdminMessageCommandRouter {

	private final Map<String, MessageCommand> commands;

	private AdminMessageCommandRouter(
			final AdminAdd adminAdd,
			final AdminDelete adminDelete,
			final AdminList adminList,
			final AdminDeleteSession adminDeleteSession,
			final AddGroup addGroup
	) {
		commands = new HashMap<>();
		commands.put(ADD_ADMIN, adminAdd);
		commands.put(DELETE_ADMIN, adminDelete);
		commands.put(GET_ADMIN_LIST, adminList);
		commands.put(ADMIN_DELETE_SESSION, adminDeleteSession);
		commands.put(ADD_GROUP, addGroup);
	}

	public MessageCommand getCommand(String key) {
		return commands.get(key);
	}

	public boolean ifContainsKey(String key) {
		return commands.containsKey(key);
	}
}

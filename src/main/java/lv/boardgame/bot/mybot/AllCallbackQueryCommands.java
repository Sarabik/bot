package lv.boardgame.bot.mybot;

import lv.boardgame.bot.command.CallbackQueryCommand;
import lv.boardgame.bot.command.GameSessionDeleted;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AllCallbackQueryCommands {

	private final Map<String, CallbackQueryCommand> commands;

	private AllCallbackQueryCommands(
		final GameSessionDeleted gameSessionDeleted
	) {
		commands = new HashMap<>();
		commands.put("ИГРОВАЯ ВСТРЕЧА ОТМЕНЕНА:", gameSessionDeleted);
	}

	public CallbackQueryCommand getCommand(String key) {
		return commands.get(key);
	}

	public boolean ifContainsKey(String key) {
		return commands.containsKey(key);
	}
}

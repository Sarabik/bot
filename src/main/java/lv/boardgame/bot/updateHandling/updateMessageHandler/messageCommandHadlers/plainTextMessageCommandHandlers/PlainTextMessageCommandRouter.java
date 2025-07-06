package lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.plainTextMessageCommandHandlers;

import lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.plainTextMessageCommandHandlers.commands.*;
import org.springframework.stereotype.Component;
import lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.MessageCommand;

import java.util.HashMap;
import java.util.Map;

import static lv.boardgame.bot.constants.TextFinals.*;

@Component
public class PlainTextMessageCommandRouter {

	private final Map<String, MessageCommand> commands;

	private PlainTextMessageCommandRouter(
		final OrganizeGameSession organizeGameSession,
		final AllGameSessions allGameSessions,
		final JoinGameSession joinGameSession,
		final LeaveGameSession leaveGameSession,
		final DeleteGameSession deleteGameSession
	) {
		commands = new HashMap<>();
		commands.put(ORGANIZE, organizeGameSession);
		commands.put(ALL_GAME_SESSIONS, allGameSessions);
		commands.put(JOIN, joinGameSession);
		commands.put(LEAVE, leaveGameSession);
		commands.put(DELETE, deleteGameSession);
	}

	public MessageCommand getCommand(String key) {
		return commands.get(key);
	}

	public boolean ifContainsKey(String key) {
		return commands.containsKey(key);
	}
}

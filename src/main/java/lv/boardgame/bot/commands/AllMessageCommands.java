package lv.boardgame.bot.commands;

import lv.boardgame.bot.commands.messageCommand.AllGameSessions;
import lv.boardgame.bot.commands.messageCommand.MessageCommand;
import lv.boardgame.bot.commands.messageCommand.DeleteGameSession;
import lv.boardgame.bot.commands.messageCommand.JoinGameSession;
import lv.boardgame.bot.commands.messageCommand.LeaveGameSession;
import lv.boardgame.bot.commands.messageCommand.OrganizeGameSession;
import lv.boardgame.bot.commands.messageCommand.WaitingComment;
import lv.boardgame.bot.commands.messageCommand.WaitingDate;
import lv.boardgame.bot.commands.messageCommand.WaitingGameName;
import lv.boardgame.bot.commands.messageCommand.WaitingPlace;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AllMessageCommands {

	private final Map<String, MessageCommand> commands;

	private AllMessageCommands(
		final OrganizeGameSession organizeGameSession,
		final AllGameSessions allGameSessions,
		final JoinGameSession joinGameSession,
		final LeaveGameSession leaveGameSession,
		final DeleteGameSession deleteGameSession,
		final WaitingDate waitingDate,
		final WaitingPlace waitingPlace,
		final WaitingGameName waitingGameName,
		final WaitingComment waitingComment
	) {
		commands = new HashMap<>();
		commands.put("Организовать встречу", organizeGameSession);
		commands.put("Все игровые встречи", allGameSessions);
		commands.put("Присоединиться", joinGameSession);
		commands.put("Отписаться", leaveGameSession);
		commands.put("Отменить встречу", deleteGameSession);
		commands.put("WAITING_DATE", waitingDate);
		commands.put("WAITING_PLACE", waitingPlace);
		commands.put("WAITING_GAME_NAME", waitingGameName);
		commands.put("WAITING_COMMENT", waitingComment);
	}

	public MessageCommand getCommand(String key) {
		return commands.get(key);
	}

	public boolean ifContainsKey(String key) {
		return commands.containsKey(key);
	}
}

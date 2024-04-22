package lv.boardgame.bot.mybot;

import lv.boardgame.bot.command.AllGameSessions;
import lv.boardgame.bot.command.Command;
import lv.boardgame.bot.command.DeleteGameSession;
import lv.boardgame.bot.command.JoinGameSession;
import lv.boardgame.bot.command.LeaveGameSession;
import lv.boardgame.bot.command.OrganizeGameSession;
import lv.boardgame.bot.command.WaitingComment;
import lv.boardgame.bot.command.WaitingDate;
import lv.boardgame.bot.command.WaitingGameName;
import lv.boardgame.bot.command.WaitingPlace;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AllCommands {

	private final Map<String, Command> commands;

	private AllCommands(
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

	public Command getCommand(String key) {
		return commands.get(key);
	}

	public boolean ifContainsKey(String key) {
		return commands.containsKey(key);
	}
}

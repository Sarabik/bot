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
import lv.boardgame.bot.commands.messageCommand.WaitingMaxPlayerCount;
import lv.boardgame.bot.commands.messageCommand.WaitingPlace;
import lv.boardgame.bot.commands.messageCommand.WaitingTime;
import lv.boardgame.bot.model.BotState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static lv.boardgame.bot.TextFinals.*;

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
		final WaitingTime waitingTime,
		final WaitingPlace waitingPlace,
		final WaitingGameName waitingGameName,
		final WaitingMaxPlayerCount waitingMaxPlayerCount,
		final WaitingComment waitingComment
	) {
		commands = new HashMap<>();
		commands.put(ORGANIZE, organizeGameSession);
		commands.put(ALL_GAME_SESSIONS, allGameSessions);
		commands.put(JOIN, joinGameSession);
		commands.put(LEAVE, leaveGameSession);
		commands.put(DELETE, deleteGameSession);
		commands.put(BotState.WAITING_DATE.toString(), waitingDate);
		commands.put(BotState.WAITING_TIME.toString(), waitingTime);
		commands.put(BotState.WAITING_PLACE.toString(), waitingPlace);
		commands.put(BotState.WAITING_GAME_NAME.toString(), waitingGameName);
		commands.put(BotState.WAITING_MAX_PLAYER_COUNT.toString(), waitingMaxPlayerCount);
		commands.put(BotState.WAITING_COMMENT.toString(), waitingComment);
	}

	public MessageCommand getCommand(String key) {
		return commands.get(key);
	}

	public boolean ifContainsKey(String key) {
		return commands.containsKey(key);
	}
}

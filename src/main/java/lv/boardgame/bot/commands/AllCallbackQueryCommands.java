package lv.boardgame.bot.commands;

import lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryCommand;
import lv.boardgame.bot.commands.callbackQueryCommand.GameSessionDeletedCallback;

import lv.boardgame.bot.commands.callbackQueryCommand.GameSessionLeavedCallback;
import lv.boardgame.bot.commands.callbackQueryCommand.JoinedGameSessionCallback;
import lv.boardgame.bot.commands.callbackQueryCommand.WaitingCommentCallback;
import lv.boardgame.bot.commands.callbackQueryCommand.WaitingDateCallback;
import lv.boardgame.bot.commands.callbackQueryCommand.WaitingFreePlayerSlotsCallback;
import lv.boardgame.bot.commands.callbackQueryCommand.WaitingTimeCallback;
import lv.boardgame.bot.model.BotState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static lv.boardgame.bot.TextFinals.*;

@Component
public class AllCallbackQueryCommands {

	private final Map<String, CallbackQueryCommand> commands;

	private AllCallbackQueryCommands(
		final GameSessionDeletedCallback gameSessionDeletedCallback,
		final WaitingDateCallback waitingDateCallback,
		final WaitingTimeCallback waitingTimeCallback,
		final WaitingFreePlayerSlotsCallback waitingFreePlayerSlotsCallback,
		final WaitingCommentCallback waitingCommentCallback,
		final JoinedGameSessionCallback joinedGameSessionCallback,
		final GameSessionLeavedCallback gameSessionLeavedCallback
	) {
		commands = new HashMap<>();
		commands.put(SESSION_DELETED, gameSessionDeletedCallback);
		commands.put(BotState.WAITING_DATE.toString(), waitingDateCallback);
		commands.put(BotState.WAITING_TIME.toString(), waitingTimeCallback);
		commands.put(BotState.WAITING_FREE_PLAYER_SLOTS.toString(), waitingFreePlayerSlotsCallback);
		commands.put(BotState.WAITING_COMMENT.toString(), waitingCommentCallback);
		commands.put(JOINED_SESSION, joinedGameSessionCallback);
		commands.put(SESSION_LEAVED, gameSessionLeavedCallback);
	}

	public CallbackQueryCommand getCommand(String key) {
		return commands.get(key);
	}

	public boolean ifContainsKey(String key) {
		return commands.containsKey(key);
	}
}

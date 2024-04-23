package lv.boardgame.bot.commands;

import lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryCommand;
import lv.boardgame.bot.commands.callbackQueryCommand.GameSessionDeletedCallback;

import lv.boardgame.bot.commands.callbackQueryCommand.GameSessionLeavedCallback;
import lv.boardgame.bot.commands.callbackQueryCommand.JoinedGameSessionCallback;
import lv.boardgame.bot.commands.callbackQueryCommand.WaitingCommentCallback;
import lv.boardgame.bot.commands.callbackQueryCommand.WaitingDateCallback;
import lv.boardgame.bot.commands.callbackQueryCommand.WaitingIfOrganizerPlayingCallback;
import lv.boardgame.bot.commands.callbackQueryCommand.WaitingMaxPlayerCountCallback;
import lv.boardgame.bot.commands.callbackQueryCommand.WaitingTimeCallback;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AllCallbackQueryCommands {

	private final Map<String, CallbackQueryCommand> commands;

	private AllCallbackQueryCommands(
		final GameSessionDeletedCallback gameSessionDeletedCallback,
		final WaitingDateCallback waitingDateCallback,
		final WaitingTimeCallback waitingTimeCallback,
		final WaitingIfOrganizerPlayingCallback waitingIfOrganizerPlayingCallback,
		final WaitingMaxPlayerCountCallback waitingMaxPlayerCountCallback,
		final WaitingCommentCallback waitingCommentCallback,
		final JoinedGameSessionCallback joinedGameSessionCallback,
		final GameSessionLeavedCallback gameSessionLeavedCallback
	) {
		commands = new HashMap<>();
		commands.put("ИГРОВАЯ ВСТРЕЧА ОТМЕНЕНА:", gameSessionDeletedCallback);
		commands.put("WAITING_DATE", waitingDateCallback);
		commands.put("WAITING_TIME", waitingTimeCallback);
		commands.put("WAITING_IF_ORGANIZER_PLAYING", waitingIfOrganizerPlayingCallback);
		commands.put("WAITING_MAX_PLAYER_COUNT", waitingMaxPlayerCountCallback);
		commands.put("WAITING_COMMENT", waitingCommentCallback);
		commands.put("ВЫ ПРИСОЕДИНИЛИСЬ К ВСТРЕЧЕ:", joinedGameSessionCallback);
		commands.put("ВЫ ОТПИСАЛИСЬ ОТ ИГРОВОЙ ВСТРЕЧИ:", gameSessionLeavedCallback);
	}

	public CallbackQueryCommand getCommand(String key) {
		return commands.get(key);
	}

	public boolean ifContainsKey(String key) {
		return commands.containsKey(key);
	}
}

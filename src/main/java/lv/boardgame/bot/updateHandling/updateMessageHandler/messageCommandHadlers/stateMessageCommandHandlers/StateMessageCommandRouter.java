package lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.stateMessageCommandHandlers;

import lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.MessageCommand;
import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.stateMessageCommandHandlers.commands.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StateMessageCommandRouter {

	private final Map<String, MessageCommand> commands;

	private StateMessageCommandRouter(
			final WaitingDate waitingDate,
			final WaitingTime waitingTime,
			final WaitingPlace waitingPlace,
			final WaitingGameName waitingGameName,
			final WaitingFreePlayerSlots waitingFreePlayerSlots,
			final WaitingComment waitingComment
	) {
		commands = new HashMap<>();
		commands.put(BotState.WAITING_DATE.toString(), waitingDate);
		commands.put(BotState.WAITING_TIME.toString(), waitingTime);
		commands.put(BotState.WAITING_PLACE.toString(), waitingPlace);
		commands.put(BotState.WAITING_GAME_NAME.toString(), waitingGameName);
		commands.put(BotState.WAITING_FREE_PLAYER_SLOTS.toString(), waitingFreePlayerSlots);
		commands.put(BotState.WAITING_COMMENT.toString(), waitingComment);
	}

	public MessageCommand getCommand(String key) {
		return commands.get(key);
	}

	public boolean ifContainsKey(String key) {
		return commands.containsKey(key);
	}
}

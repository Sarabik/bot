package lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.stateCallbackCommandHandlers;

import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.CallbackQueryCommand;
import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.stateCallbackCommandHandlers.commands.WaitingCommentCallback;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.stateCallbackCommandHandlers.commands.WaitingDateCallback;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.stateCallbackCommandHandlers.commands.WaitingFreePlayerSlotsCallback;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.stateCallbackCommandHandlers.commands.WaitingTimeCallback;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StateCallbackCommandRouter {
    private final Map<String, CallbackQueryCommand> commands;

    private StateCallbackCommandRouter(
            final WaitingDateCallback waitingDateCallback,
            final WaitingTimeCallback waitingTimeCallback,
            final WaitingFreePlayerSlotsCallback waitingFreePlayerSlotsCallback,
            final WaitingCommentCallback waitingCommentCallback
    ) {
        commands = new HashMap<>();
        commands.put(BotState.WAITING_DATE.toString(), waitingDateCallback);
        commands.put(BotState.WAITING_TIME.toString(), waitingTimeCallback);
        commands.put(BotState.WAITING_FREE_PLAYER_SLOTS.toString(), waitingFreePlayerSlotsCallback);
        commands.put(BotState.WAITING_COMMENT.toString(), waitingCommentCallback);
    }

    public CallbackQueryCommand getCommand(String key) {
        return commands.get(key);
    }

    public boolean ifContainsKey(String key) {
        return commands.containsKey(key);
    }
}

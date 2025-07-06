package lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.directDataCallbackCommandHandlers;

import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.CallbackQueryCommand;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.directDataCallbackCommandHandlers.commands.GameSessionDeletedCallback;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.directDataCallbackCommandHandlers.commands.GameSessionLeavedCallback;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.directDataCallbackCommandHandlers.commands.JoinedGameSessionCallback;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static lv.boardgame.bot.constants.TextFinals.*;

@Component
public class DirectDataCallbackCommandRouter {
    private final Map<String, CallbackQueryCommand> commands;

    private DirectDataCallbackCommandRouter(
            final GameSessionDeletedCallback gameSessionDeletedCallback,
            final JoinedGameSessionCallback joinedGameSessionCallback,
            final GameSessionLeavedCallback gameSessionLeavedCallback
    ) {
        commands = new HashMap<>();
        commands.put(SESSION_DELETED, gameSessionDeletedCallback);
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

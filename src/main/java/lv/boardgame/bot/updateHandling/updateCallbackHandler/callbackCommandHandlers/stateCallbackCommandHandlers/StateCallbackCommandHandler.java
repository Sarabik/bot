package lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.stateCallbackCommandHandlers;

import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.CallbackCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class StateCallbackCommandHandler implements CallbackCommandHandler {

    @Autowired
    private final StateCallbackCommandRouter stateCallbackCommandRouter;

    public StateCallbackCommandHandler(StateCallbackCommandRouter stateCallbackCommandRouter) {
        this.stateCallbackCommandRouter = stateCallbackCommandRouter;
    }

    @Override
    public boolean canHandle(String data, BotState botState) {
        return botState != null && stateCallbackCommandRouter.ifContainsKey(botState.toString());
    }

    @Override
    public List<SendMessage> handle(String chatIdString, Player player, String data, Message callbackQueryMessage, BotState botState) {
        return stateCallbackCommandRouter.getCommand(botState.toString())
                .execute(chatIdString, player, data, callbackQueryMessage);
    }
}

package lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.directDataCallbackCommandHandlers;

import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.CallbackCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class DirectDataCallbackCommandHandler implements CallbackCommandHandler {

    @Autowired
    private final DirectDataCallbackCommandRouter directDataCallbackCommandRouter;

    public DirectDataCallbackCommandHandler(DirectDataCallbackCommandRouter directDataCallbackCommandRouter) {
        this.directDataCallbackCommandRouter = directDataCallbackCommandRouter;
    }
    @Override
    public boolean canHandle(String data, BotState botState) {
        return directDataCallbackCommandRouter.ifContainsKey(data);
    }

    @Override
    public List<SendMessage> handle(String chatIdString, Player player, String data, Message callbackQueryMessage, BotState botState) {
        return directDataCallbackCommandRouter.getCommand(data)
                .execute(chatIdString, player, data, callbackQueryMessage);
    }
}

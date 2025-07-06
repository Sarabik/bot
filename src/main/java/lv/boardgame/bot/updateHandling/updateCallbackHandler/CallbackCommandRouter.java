package lv.boardgame.bot.updateHandling.updateCallbackHandler;

import lv.boardgame.bot.messages.MenuMessage;
import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Component
public class CallbackCommandRouter {

    private final List<CallbackCommandHandler> handlers;

    @Autowired
    private MenuMessage menuMessage;

    public CallbackCommandRouter(List<CallbackCommandHandler> handlers) {
        this.handlers = handlers;
    }

    public List<SendMessage> route(String chatIdString, Player player, BotState botState, Message callbackQueryMessage, String data) {
        for (CallbackCommandHandler handler : handlers) {
            if (handler.canHandle(data, botState)) {
                return handler.handle(chatIdString, player, data, callbackQueryMessage, botState);
            }
        }
        return List.of(menuMessage.getMenuMessage(chatIdString));
    }
}

package lv.boardgame.bot.updateHandling.updateMessageHandler;

import lv.boardgame.bot.messages.MenuMessage;
import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class MessageCommandRouter {
    private final List<MessageCommandHandler> handlers;

    @Autowired
    private MenuMessage menuMessage;

    @Autowired
    public MessageCommandRouter(List<MessageCommandHandler> handlers) {
        this.handlers = handlers;
    }

    public List<SendMessage> route(String chatId, Player player, BotState botState, String text) {
        for (MessageCommandHandler handler : handlers) {
            if (handler.canHandle(text, botState)) {
                return handler.handle(chatId, player, text, botState);
            }
        }
        return List.of(menuMessage.getMenuMessage(chatId));
    }
}

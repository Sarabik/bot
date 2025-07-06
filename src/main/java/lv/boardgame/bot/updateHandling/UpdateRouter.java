package lv.boardgame.bot.updateHandling;

import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.directDataCallbackCommandHandlers.commands.GameSessionDeletedCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class UpdateRouter {

    private final List<UpdateHandler> handlers;

    private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

    @Autowired
    public UpdateRouter(List<UpdateHandler> handlers) {
        this.handlers = handlers;
    }

    public void route(Update update, TelegramLongPollingBot bot) {
        for (UpdateHandler handler : handlers) {
            if (handler.canHandle(update)) {
                handler.handle(update, bot);
                return;
            }
        }
        LOG.error("Failed to find a handler for the update");
    }
}

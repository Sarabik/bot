package lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.plainTextMessageCommandHandlers;

import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.updateHandling.updateMessageHandler.MessageCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class PlainTextMessageCommandHandler implements MessageCommandHandler {

    @Autowired
    private final PlainTextMessageCommandRouter plainTextMessageCommandRouter;

    public PlainTextMessageCommandHandler(PlainTextMessageCommandRouter plainTextMessageCommandRouter) {
        this.plainTextMessageCommandRouter = plainTextMessageCommandRouter;
    }

    @Override
    public boolean canHandle(String text, BotState botState) {
        return plainTextMessageCommandRouter.ifContainsKey(text);
    }

    @Override
    public List<SendMessage> handle(String chatId, Player player, String text, BotState botState) {
        return plainTextMessageCommandRouter.getCommand(text).execute(chatId, player, text);
    }
}

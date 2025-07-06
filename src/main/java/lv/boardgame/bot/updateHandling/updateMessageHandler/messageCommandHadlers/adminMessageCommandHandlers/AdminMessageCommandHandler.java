package lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.adminMessageCommandHandlers;

import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.updateHandling.updateMessageHandler.MessageCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class AdminMessageCommandHandler implements MessageCommandHandler {

    @Autowired
    AdminMessageCommandRouter adminMessageCommandRouter;

    @Override
    public boolean canHandle(String text, BotState botState) {
        return adminMessageCommandRouter.ifContainsKey(text.split(" ")[0]);
    }

    @Override
    public List<SendMessage> handle(String chatId, Player player, String text, BotState botState) {
        return adminMessageCommandRouter.getCommand(text.split(" ")[0]).execute(chatId, player, text);
    }
}

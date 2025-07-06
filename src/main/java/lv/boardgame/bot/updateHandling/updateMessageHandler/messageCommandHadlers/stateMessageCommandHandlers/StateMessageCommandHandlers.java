package lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.stateMessageCommandHandlers;

import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.updateHandling.updateMessageHandler.MessageCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

@Component
public class StateMessageCommandHandlers implements MessageCommandHandler {

    @Autowired
    StateMessageCommandRouter stateMessageCommandRouter;

    @Override
    public boolean canHandle(String text, BotState botState) {
        return (botState != null) && stateMessageCommandRouter.ifContainsKey(text);
    }

    @Override
    public List<SendMessage> handle(String chatId, Player player, String text, BotState botState) {
        return stateMessageCommandRouter.getCommand(botState.toString()).execute(chatId, player, text);
    }
}

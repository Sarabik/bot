package lv.boardgame.bot.updateHandling.updateMessageHandler;

import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.Player;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public interface MessageCommandHandler {
    boolean canHandle(String text, BotState botState);
    List<SendMessage> handle(String chatId, Player player, String text, BotState botState);
}

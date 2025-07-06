package lv.boardgame.bot.updateHandling.updateCallbackHandler;

import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.Player;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface CallbackCommandHandler {

    boolean canHandle(String data, BotState botState);
    List<SendMessage> handle(String chatIdString, Player player, String data, Message callbackQueryMessage, BotState botState);

}

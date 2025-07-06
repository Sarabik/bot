package lv.boardgame.bot.updateHandling;

import lv.boardgame.bot.model.Player;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface UpdateHandler {
    boolean canHandle(Update update);
    void handle(Update update, TelegramLongPollingBot bot);

    default Player getPlayer(String chatId, User user) {
        return new Player(chatId, user.getUserName(), user.getFirstName(), user.getLastName());
    }

    default void safeExecute(SendMessage message, TelegramLongPollingBot bot) {
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

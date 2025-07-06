package lv.boardgame.bot.updateHandling.updateMessageHandler;

import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.gameSessionConstructor.GameSessionConstructor;
import lv.boardgame.bot.updateHandling.UpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@Order(1)
public class UpdateMessageHandler implements UpdateHandler {

    @Autowired
    private MessageCommandRouter messageCommandRouter;

    @Autowired
    GameSessionConstructor gameSessionConstructor;

    @Override
    public boolean canHandle(Update update) {
        // check that: 1) update type is a message, 2) it is sent in a private chat, 3) text presents
        return update.hasMessage() && update.getMessage().getChat().isUserChat() && update.getMessage().hasText();
    }

    @Override
    public void handle(Update update, TelegramLongPollingBot bot) {
        Message receivedMessage = update.getMessage();
        String chatId = String.valueOf(receivedMessage.getChatId());
        Player player = getPlayer(chatId, receivedMessage.getFrom());
        String text = receivedMessage.getText();
        BotState botState = gameSessionConstructor.getBotState(player);

        List<SendMessage> messageList = messageCommandRouter.route(chatId, player, botState, text);
        messageList.forEach(message -> safeExecute(message, bot));
    }
}

package lv.boardgame.bot.updateHandling.updateCallbackHandler;

import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.directDataCallbackCommandHandlers.commands.GameSessionDeletedCallback;
import lv.boardgame.bot.model.BotState;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.gameSessionConstructor.GameSessionConstructor;
import lv.boardgame.bot.updateHandling.UpdateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
@Order(2)
public class UpdateCallbackHandler implements UpdateHandler {

    @Autowired
    private CallbackCommandRouter callbackCommandRouter;

    @Autowired
    GameSessionConstructor gameSessionConstructor;

    private static final Logger LOG = LoggerFactory.getLogger(GameSessionDeletedCallback.class);

    @Override
    public boolean canHandle(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    public void handle(Update update, TelegramLongPollingBot bot) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String data = callbackQuery.getData();
        int messageId = callbackQuery.getMessage().getMessageId();
        Message callbackQueryMessage = (Message) callbackQuery.getMessage();
        long chatId = callbackQuery.getMessage().getChatId();
        String chatIdString = String.valueOf(chatId);
        Player player = getPlayer(chatIdString, callbackQuery.getFrom());
        BotState botState = gameSessionConstructor.getBotState(player);

        List<SendMessage> messageList = callbackCommandRouter.route(chatIdString, player, botState, callbackQueryMessage, data);
        messageList.forEach(message -> safeExecute(message, bot));
        disableInlineKeyboardButtons(chatId, messageId, bot);
    }

    private void disableInlineKeyboardButtons(Long chatId, Integer messageId, TelegramLongPollingBot bot) {
        EditMessageReplyMarkup editMessageReplyMarkup = EditMessageReplyMarkup.builder()
                .chatId(String.valueOf(chatId))
                .messageId(messageId)
                .replyMarkup(null)
                .build();
        try {
            bot.execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            LOG.error(e.getMessage());
        }
    }
}

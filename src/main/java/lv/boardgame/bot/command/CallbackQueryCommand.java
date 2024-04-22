package lv.boardgame.bot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface CallbackQueryCommand {

	SendMessage execute(String chatId, String username, String data, Message message);

}

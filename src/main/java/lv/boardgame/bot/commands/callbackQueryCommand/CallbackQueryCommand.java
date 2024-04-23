package lv.boardgame.bot.commands.callbackQueryCommand;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface CallbackQueryCommand {

	List<SendMessage> execute(String chatId, String username, String data, Message message);

}

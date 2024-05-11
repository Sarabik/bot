package lv.boardgame.bot.commands.callbackQueryCommand;

import lv.boardgame.bot.model.Player;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

public interface CallbackQueryCommand {

	List<SendMessage> execute(String chatId, Player player, String data, Message message);

}

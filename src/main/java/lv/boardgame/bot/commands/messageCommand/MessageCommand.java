package lv.boardgame.bot.commands.messageCommand;

import lv.boardgame.bot.model.Player;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public interface MessageCommand {
	List<SendMessage> execute(String chatId, Player player, String receivedText);
}

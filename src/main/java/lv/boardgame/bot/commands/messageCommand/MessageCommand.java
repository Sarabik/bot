package lv.boardgame.bot.commands.messageCommand;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public interface MessageCommand {
	List<SendMessage> execute(String chatId, String username, String receivedText);
}

package lv.boardgame.bot.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public interface MessageCommand {
	List<SendMessage> execute(String chatId, String username, String receivedText);
}

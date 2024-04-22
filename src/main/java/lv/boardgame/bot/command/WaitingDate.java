package lv.boardgame.bot.command;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.messages.CreateTable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class WaitingDate implements Command {

	private CreateTable createTable;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		messageList.add(createTable.askForDate(chatId));
		return messageList;
	}
}

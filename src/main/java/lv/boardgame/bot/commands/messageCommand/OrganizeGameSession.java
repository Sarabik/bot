package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.messages.CreateTable;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class OrganizeGameSession implements MessageCommand {

	private GameSessionConstructor gameSessionConstructor;

	private CreateTable createTable;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		messageList.add(createTable.createTable(chatId));
		gameSessionConstructor.start(username);
		messageList.add(createTable.askForDate(chatId));
		return messageList;
	}
}

package lv.boardgame.bot.command;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.messages.CreateTable;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class WaitingComment implements MessageCommand {

	private GameSessionConstructor gameSessionConstructor;

	private CreateTable createTable;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.setComment(username, receivedText);
		messageList.add(createTable.savingTable(chatId, gameSessionConstructor.getGameSession(username)));
		gameSessionConstructor.clear(username);
		return messageList;
	}
}

package lv.boardgame.bot.command;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.messages.EditTable;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class LeaveGameSession implements Command {

	private GameSessionConstructor gameSessionConstructor;

	private EditTable editTable;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.clear(username);
		String str = "<b>Укажите от участия в какой игровой встрече вы хотели бы отписаться</b>";
		messageList.add(editTable.getCustomMessage(chatId, str));
		messageList.addAll(editTable.getAllTablesToLeave(chatId, username));
		return messageList;
	}
}

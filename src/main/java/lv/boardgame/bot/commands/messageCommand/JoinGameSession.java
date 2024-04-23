package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.messages.EditTable;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import static lv.boardgame.bot.messages.MessageUtil.*;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class JoinGameSession implements MessageCommand {

	private GameSessionConstructor gameSessionConstructor;

	private EditTable editTable;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.clear(username);
		String st = "<b>Укажите к какой игровой встрече вы хотели бы присоединиться</b>";
		messageList.add(getCustomMessage(chatId, st));
		messageList.addAll(editTable.getAllTablesToJoin(chatId, username));
		return messageList;
	}
}

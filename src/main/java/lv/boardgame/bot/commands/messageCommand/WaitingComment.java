package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.messages.CreateTableMessages;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class WaitingComment implements MessageCommand {

	private GameSessionConstructor gameSessionConstructor;

	private CreateTableMessages createTableMessages;

	private final static String NO_COMMENTS = "Нет комментариев";

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		if (!NO_COMMENTS.equals(receivedText)) {
			gameSessionConstructor.setComment(username, receivedText);
		}
		messageList.add(createTableMessages.savingTable(chatId, gameSessionConstructor.getGameSession(username)));
		gameSessionConstructor.clear(username);
		return messageList;
	}
}

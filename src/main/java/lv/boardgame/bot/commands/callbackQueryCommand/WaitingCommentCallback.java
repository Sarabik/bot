package lv.boardgame.bot.commands.callbackQueryCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.messages.CreateTable;
import lv.boardgame.bot.messages.EditTable;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class WaitingCommentCallback implements CallbackQueryCommand {

	private final CreateTable createTable;

	private final EditTable editTable;

	private final GameSessionConstructor gameSessionConstructor;

	private final static String NO_COMMENTS = "Нет комментариев";

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String data, final Message message) {
		List<SendMessage> messageList = new ArrayList<>();
		messageList.add(editTable.getCustomMessage(chatId, data));

		if (!NO_COMMENTS.equals(data)) {
			gameSessionConstructor.setComment(username, data);
		}
		messageList.add(createTable.savingTable(chatId, gameSessionConstructor.getGameSession(username)));
		gameSessionConstructor.clear(username);
		return messageList;
	}
}

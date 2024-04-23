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
public class WaitingTimeCallback implements CallbackQueryCommand {

	private final CreateTable createTable;

	private final EditTable editTable;

	private final GameSessionConstructor gameSessionConstructor;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String data, final Message message) {
		List<SendMessage> messageList = new ArrayList<>();
		messageList.add(editTable.getCustomMessage(chatId, data));

		gameSessionConstructor.setTime(username, data);
		messageList.add(createTable.askForPlace(chatId));
		return messageList;
	}
}

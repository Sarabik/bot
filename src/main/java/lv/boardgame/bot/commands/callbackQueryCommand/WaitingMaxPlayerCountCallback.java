package lv.boardgame.bot.commands.callbackQueryCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.messages.CreateTableMessages;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import static lv.boardgame.bot.messages.MessageUtil.*;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class WaitingMaxPlayerCountCallback implements CallbackQueryCommand {

	private final CreateTableMessages createTableMessages;

	private final GameSessionConstructor gameSessionConstructor;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String data, final Message message) {
		List<SendMessage> messageList = new ArrayList<>();
		messageList.add(getCustomMessage(chatId, data));

		gameSessionConstructor.setMaxPlayerCount(username, data);
		messageList.add(createTableMessages.askForComment(chatId));
		return messageList;
	}
}

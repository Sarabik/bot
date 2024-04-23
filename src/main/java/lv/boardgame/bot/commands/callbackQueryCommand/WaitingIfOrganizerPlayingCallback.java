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
public class WaitingIfOrganizerPlayingCallback implements CallbackQueryCommand {

	private final CreateTableMessages createTableMessages;

	private final GameSessionConstructor gameSessionConstructor;

	private static final String PLAYING = "Вы участвуете в игре сами";

	private static final String NOT_PLAYING = "Вы не участвуете в игре, а только ее проводите";

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String data, final Message message) {
		List<SendMessage> messageList = new ArrayList<>();
		if ("true".equals(data)) {
			messageList.add(getCustomMessage(chatId, PLAYING));
		} else {
			messageList.add(getCustomMessage(chatId, NOT_PLAYING));
		}

		gameSessionConstructor.setIfOrganizerPlaying(username, data);
		messageList.add(createTableMessages.askForMaxPlayerCount(chatId));
		return messageList;
	}
}

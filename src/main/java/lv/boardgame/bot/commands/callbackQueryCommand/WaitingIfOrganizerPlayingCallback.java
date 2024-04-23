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
public class WaitingIfOrganizerPlayingCallback implements CallbackQueryCommand {

	private final CreateTable createTable;

	private final EditTable editTable;

	private final GameSessionConstructor gameSessionConstructor;

	private static final String PLAYING = "Вы участвуете в игре сами";

	private static final String NOT_PLAYING = "Вы не участвуете в игре, а только ее проводите";

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String data, final Message message) {
		List<SendMessage> messageList = new ArrayList<>();
		if ("true".equals(data)) {
			messageList.add(editTable.getCustomMessage(chatId, PLAYING));
		} else {
			messageList.add(editTable.getCustomMessage(chatId, NOT_PLAYING));
		}

		gameSessionConstructor.setIfOrganizerPlaying(username, data);
		messageList.add(createTable.askForMaxPlayerCount(chatId));
		return messageList;
	}
}

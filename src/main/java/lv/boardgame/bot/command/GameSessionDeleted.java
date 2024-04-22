package lv.boardgame.bot.command;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.messages.EditTable;
import lv.boardgame.bot.model.GameSession;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@AllArgsConstructor
public class GameSessionDeleted implements CallbackQueryCommand {

	private EditTable editTable;

	@Override
	public SendMessage execute(
		final String chatId,
		final String username,
		final String data,
		final Message message
	) {
		String date = message.getEntities().get(1).getText();
		String organizer = message.getEntities().get(8).getText().substring(1);
		GameSession session = editTable.deleteTable(date, organizer);
		return editTable.getEditedSession(chatId, session);
	}
}

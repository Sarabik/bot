package lv.boardgame.bot.commands.callbackQueryCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.inlineKeyboard.DateInlineKeyboardMarkup;
import lv.boardgame.bot.inlineKeyboard.TimeInlineKeyboardMarkup;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import static lv.boardgame.bot.messages.MessageUtil.*;
import static lv.boardgame.bot.TextFinals.*;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class WaitingDateCallback implements CallbackQueryCommand {

	private TimeInlineKeyboardMarkup timeInlineKeyboardMarkup;

	private final GameSessionConstructor gameSessionConstructor;

	private DateInlineKeyboardMarkup dateInlineKeyboardMarkup;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String data, final Message message) {
		List<SendMessage> messageList = new ArrayList<>();
		messageList.add(getCustomMessage(chatId, data));

		if (CHOSE_DATE.equals(data)) {
			messageList.add(getCustomMessageWithMarkup(chatId, DATA, dateInlineKeyboardMarkup));
		} else {
			gameSessionConstructor.setDate(username, data);
			messageList.add(getCustomMessageWithMarkup(chatId, TIME, timeInlineKeyboardMarkup));
		}
		return messageList;
	}
}
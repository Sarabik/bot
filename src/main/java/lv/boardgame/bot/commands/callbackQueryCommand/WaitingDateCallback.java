package lv.boardgame.bot.commands.callbackQueryCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.inlineKeyboard.DateInlineKeyboardMarkup;
import lv.boardgame.bot.inlineKeyboard.TimeInlineKeyboardMarkup;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil.getStartList;
import static lv.boardgame.bot.messages.MessageUtil.*;
import static lv.boardgame.bot.TextFinals.*;

import java.util.List;

@Component
@Scope("prototype")
@AllArgsConstructor
public class WaitingDateCallback implements CallbackQueryCommand {

	private TimeInlineKeyboardMarkup timeInlineKeyboardMarkup;

	private final GameSessionConstructor gameSessionConstructor;

	private DateInlineKeyboardMarkup dateInlineKeyboardMarkup;

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String data, final Message message) {
		List<SendMessage> messageList = getStartList(chatId, data);

		if (CHOSE_DATE.equals(data)) {
			messageList.add(getCustomMessageWithMarkup(chatId, DATA, dateInlineKeyboardMarkup));
		} else {
			gameSessionConstructor.setDate(player, data);
			messageList.add(getCustomMessageWithMarkup(chatId, TIME, timeInlineKeyboardMarkup));
		}
		return messageList;
	}
}

package lv.boardgame.bot.commands.callbackQueryCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.inlineKeyboard.TimeInlineKeyboardMarkup;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import static lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil.getStartList;
import static lv.boardgame.bot.messages.MessageUtil.*;
import static lv.boardgame.bot.TextFinals.*;

import java.util.List;

@Component
@AllArgsConstructor
public class WaitingTimeCallback implements CallbackQueryCommand {

	private final GameSessionConstructor gameSessionConstructor;

	private final TimeInlineKeyboardMarkup timeInlineKeyboardMarkup;

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String data, final Message message) {
		List<SendMessage> messageList = getStartList(chatId, data);

		if (CHOSE_TIME.equals(data)) {
			messageList.add(getCustomMessageWithMarkup(chatId, TIME, timeInlineKeyboardMarkup));
		} else {
			gameSessionConstructor.setTime(player, data);
			messageList.add(getCustomMessage(chatId, PLACE));
		}

		return messageList;
	}
}

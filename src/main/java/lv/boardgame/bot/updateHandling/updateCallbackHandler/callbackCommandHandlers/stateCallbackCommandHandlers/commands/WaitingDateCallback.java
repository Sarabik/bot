package lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.stateCallbackCommandHandlers.commands;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.CallbackQueryCommand;
import lv.boardgame.bot.keyboards.DateInlineKeyboardMarkup;
import lv.boardgame.bot.keyboards.TimeInlineKeyboardMarkup;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.gameSessionConstructor.GameSessionConstructor;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static lv.boardgame.bot.constants.TextFinals.*;
import static lv.boardgame.bot.updateHandling.util.QueryUtil.getStartList;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessageWithMarkup;

@Component
@AllArgsConstructor
public class WaitingDateCallback implements CallbackQueryCommand {

	private TimeInlineKeyboardMarkup timeInlineKeyboardMarkup;

	private final GameSessionConstructor gameSessionConstructor;

	@Lookup
	protected DateInlineKeyboardMarkup getDateInlineKeyboardMarkup() {
		return null;
	}

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String data, final Message message) {
		List<SendMessage> messageList = getStartList(chatId, data);

		if (CHOSE_DATE.equals(data)) {
			messageList.add(getCustomMessageWithMarkup(chatId, DATA, getDateInlineKeyboardMarkup()));
		} else {
			gameSessionConstructor.setDate(player, data);
			messageList.add(getCustomMessageWithMarkup(chatId, TIME, timeInlineKeyboardMarkup));
		}
		return messageList;
	}
}

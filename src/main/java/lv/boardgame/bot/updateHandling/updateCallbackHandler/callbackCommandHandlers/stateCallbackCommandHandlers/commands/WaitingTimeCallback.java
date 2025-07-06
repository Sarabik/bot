package lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.stateCallbackCommandHandlers.commands;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.CallbackQueryCommand;
import lv.boardgame.bot.keyboards.TimeInlineKeyboardMarkup;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.gameSessionConstructor.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static lv.boardgame.bot.constants.TextFinals.*;
import static lv.boardgame.bot.updateHandling.util.QueryUtil.getStartList;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessage;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessageWithMarkup;

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

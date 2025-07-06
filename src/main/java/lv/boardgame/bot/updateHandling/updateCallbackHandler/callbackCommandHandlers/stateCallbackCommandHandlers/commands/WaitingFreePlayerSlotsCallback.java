package lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.stateCallbackCommandHandlers.commands;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.updateHandling.updateCallbackHandler.callbackCommandHandlers.CallbackQueryCommand;
import lv.boardgame.bot.keyboards.FreePlayerSlotsInlineKeyboardMarkup;
import lv.boardgame.bot.keyboards.NoCommentInlineKeyboardMarkup;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.gameSessionConstructor.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

import static lv.boardgame.bot.constants.TextFinals.*;
import static lv.boardgame.bot.updateHandling.util.QueryUtil.getStartList;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessageWithMarkup;

@Component
@AllArgsConstructor
public class WaitingFreePlayerSlotsCallback implements CallbackQueryCommand {

	private final NoCommentInlineKeyboardMarkup noCommentInlineKeyboardMarkup;

	private final FreePlayerSlotsInlineKeyboardMarkup freePlayerSlotsInlineKeyboardMarkup;

	private final GameSessionConstructor gameSessionConstructor;

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String data, final Message message) {
		List<SendMessage> messageList = getStartList(chatId, data);
		if (CHOSE_FREE_PLAYER_SLOTS.equals(data)) {
			messageList.add(getCustomMessageWithMarkup(chatId, FREE_PLAYER_SLOTS, freePlayerSlotsInlineKeyboardMarkup));
		} else {
			gameSessionConstructor.setFreePlayerSlots(player, data);
			messageList.add(getCustomMessageWithMarkup(chatId, WRITE_COMMENT, noCommentInlineKeyboardMarkup));
		}
		return messageList;
	}
}

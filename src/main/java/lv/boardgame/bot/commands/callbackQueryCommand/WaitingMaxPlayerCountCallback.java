package lv.boardgame.bot.commands.callbackQueryCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.inlineKeyboard.MaxPlayerCountInlineKeyboardMarkup;
import lv.boardgame.bot.inlineKeyboard.NoCommentInlineKeyboardMarkup;
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
public class WaitingMaxPlayerCountCallback implements CallbackQueryCommand {

	private final NoCommentInlineKeyboardMarkup noCommentInlineKeyboardMarkup;

	private final MaxPlayerCountInlineKeyboardMarkup maxPlayerCountInlineKeyboardMarkup;

	private final GameSessionConstructor gameSessionConstructor;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String data, final Message message) {
		List<SendMessage> messageList = getStartList(chatId, data);
		if (CHOSE_MAX_PLAYER_COUNT.equals(data)) {
			messageList.add(getCustomMessageWithMarkup(chatId, MAX_PLAYER_COUNT, maxPlayerCountInlineKeyboardMarkup));
		} else {
			gameSessionConstructor.setMaxPlayerCount(username, data);
			messageList.add(getCustomMessageWithMarkup(chatId, WRITE_COMMENT, noCommentInlineKeyboardMarkup));
		}
		return messageList;
	}
}

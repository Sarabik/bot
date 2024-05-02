package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil;
import lv.boardgame.bot.inlineKeyboard.DateInlineKeyboardMarkup;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import static lv.boardgame.bot.TextFinals.*;

import java.util.List;

import static lv.boardgame.bot.messages.MessageUtil.getCustomMessageWithMarkup;

@Component
@AllArgsConstructor
public class OrganizeGameSession implements MessageCommand {

	private GameSessionConstructor gameSessionConstructor;

	private DateInlineKeyboardMarkup dateInlineKeyboardMarkup;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		List<SendMessage> messageList = CallbackQueryUtil.getStartList(chatId, START);
		gameSessionConstructor.start(chatId, username);
		messageList.add(getCustomMessageWithMarkup(chatId, DATA, dateInlineKeyboardMarkup));
		return messageList;
	}
}

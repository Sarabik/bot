package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil;
import lv.boardgame.bot.inlineKeyboard.DateInlineKeyboardMarkup;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import static lv.boardgame.bot.TextFinals.*;

import java.util.List;

import static lv.boardgame.bot.messages.MessageUtil.getCustomMessageWithMarkup;

@Component
@AllArgsConstructor
public class OrganizeGameSession implements MessageCommand {

	private GameSessionConstructor gameSessionConstructor;

	@Lookup
	protected DateInlineKeyboardMarkup getDateInlineKeyboardMarkup() {
		return null;
	}

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String receivedText) {
		List<SendMessage> messageList = CallbackQueryUtil.getStartList(chatId, START);
		gameSessionConstructor.start(chatId, player);
		messageList.add(getCustomMessageWithMarkup(chatId, DATA, getDateInlineKeyboardMarkup()));
		return messageList;
	}
}

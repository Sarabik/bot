package lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.plainTextMessageCommandHandlers.commands;

import lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.MessageCommand;
import lombok.AllArgsConstructor;
import lv.boardgame.bot.keyboards.DateInlineKeyboardMarkup;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.gameSessionConstructor.GameSessionConstructor;
import lv.boardgame.bot.updateHandling.util.QueryUtil;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static lv.boardgame.bot.constants.TextFinals.DATA;
import static lv.boardgame.bot.constants.TextFinals.START;
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
		List<SendMessage> messageList = QueryUtil.getStartList(chatId, START);
		gameSessionConstructor.start(chatId, player);
		messageList.add(getCustomMessageWithMarkup(chatId, DATA, getDateInlineKeyboardMarkup()));
		return messageList;
	}
}

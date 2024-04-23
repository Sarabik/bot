package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.inlineKeyboard.IfOrganizerPlayingInlineKeyboardMarkup;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import static lv.boardgame.bot.TextFinals.*;

import java.util.ArrayList;
import java.util.List;

import static lv.boardgame.bot.messages.MessageUtil.getCustomMessageWithMarkup;

@Component
@AllArgsConstructor
public class WaitingGameName implements MessageCommand {

	private GameSessionConstructor gameSessionConstructor;

	private IfOrganizerPlayingInlineKeyboardMarkup ifOrganizerPlayingInlineKeyboardMarkup;

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.setGameName(username, receivedText);
		messageList.add(getCustomMessageWithMarkup(chatId, ORGANIZER_PLAYING, ifOrganizerPlayingInlineKeyboardMarkup));
		return messageList;
	}
}

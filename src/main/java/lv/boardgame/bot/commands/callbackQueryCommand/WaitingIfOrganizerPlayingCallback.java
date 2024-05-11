package lv.boardgame.bot.commands.callbackQueryCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.inlineKeyboard.MaxPlayerCountInlineKeyboardMarkup;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import static lv.boardgame.bot.messages.MessageUtil.*;
import static lv.boardgame.bot.TextFinals.*;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class WaitingIfOrganizerPlayingCallback implements CallbackQueryCommand {

	private MaxPlayerCountInlineKeyboardMarkup maxPlayerCountInlineKeyboardMarkup;

	private final GameSessionConstructor gameSessionConstructor;

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String data, final Message message) {
		List<SendMessage> messageList = new ArrayList<>();
		if ("true".equals(data)) {
			messageList.add(getCustomMessage(chatId, PLAYING));
		} else {
			messageList.add(getCustomMessage(chatId, NOT_PLAYING));
		}

		gameSessionConstructor.setIfOrganizerPlaying(player, data);
		messageList.add(getCustomMessageWithMarkup(chatId, MAX_PLAYER_COUNT, maxPlayerCountInlineKeyboardMarkup));
		return messageList;
	}
}

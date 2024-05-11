package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.inlineKeyboard.FreePlayerSlotsInlineKeyboardMarkup;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

import static lv.boardgame.bot.TextFinals.FREE_PLAYER_SLOTS;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessageWithMarkup;

@Component
@AllArgsConstructor
public class WaitingGameName implements MessageCommand {

	private GameSessionConstructor gameSessionConstructor;

	private FreePlayerSlotsInlineKeyboardMarkup freePlayerSlotsInlineKeyboardMarkup;

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.setGameName(player, receivedText);
		messageList.add(getCustomMessageWithMarkup(chatId, FREE_PLAYER_SLOTS, freePlayerSlotsInlineKeyboardMarkup));
		return messageList;
	}
}

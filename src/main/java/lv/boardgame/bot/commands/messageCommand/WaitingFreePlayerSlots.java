package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil;
import lv.boardgame.bot.model.Player;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static lv.boardgame.bot.TextFinals.CHOSE_FREE_PLAYER_SLOTS;

@Component
@AllArgsConstructor
public class WaitingFreePlayerSlots implements MessageCommand {

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String receivedText) {
		return CallbackQueryUtil.getStartList(chatId, CHOSE_FREE_PLAYER_SLOTS);
	}

}

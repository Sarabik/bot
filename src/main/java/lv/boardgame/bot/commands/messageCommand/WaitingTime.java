package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static lv.boardgame.bot.TextFinals.CHOSE_TIME;

@Component
@AllArgsConstructor
public class WaitingTime  implements MessageCommand {

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		return CallbackQueryUtil.getStartList(chatId, CHOSE_TIME);
	}
}

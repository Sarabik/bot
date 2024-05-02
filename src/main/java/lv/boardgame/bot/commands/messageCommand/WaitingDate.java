package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.commands.callbackQueryCommand.CallbackQueryUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import static lv.boardgame.bot.TextFinals.*;

import java.util.List;

@Component
@AllArgsConstructor
public class WaitingDate implements MessageCommand {

	@Override
	public List<SendMessage> execute(final String chatId, final String username, final String receivedText) {
		return CallbackQueryUtil.getStartList(chatId, CHOSE_DATE);
	}
}

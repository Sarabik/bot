package lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.stateMessageCommandHandlers.commands;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.updateHandling.updateMessageHandler.messageCommandHadlers.MessageCommand;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.updateHandling.util.QueryUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static lv.boardgame.bot.constants.TextFinals.CHOSE_DATE;

@Component
@AllArgsConstructor
public class WaitingDate implements MessageCommand {

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String receivedText) {
		return QueryUtil.getStartList(chatId, CHOSE_DATE);
	}
}

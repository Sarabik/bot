package lv.boardgame.bot.commands.messageCommand;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.model.Player;
import lv.boardgame.bot.mybot.GameSessionConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import static lv.boardgame.bot.TextFinals.*;

import java.util.ArrayList;
import java.util.List;

import static lv.boardgame.bot.messages.MessageUtil.getCustomMessage;

@Component
@AllArgsConstructor
public class WaitingPlace implements MessageCommand {

	private GameSessionConstructor gameSessionConstructor;

	@Override
	public List<SendMessage> execute(final String chatId, final Player player, final String receivedText) {
		List<SendMessage> messageList = new ArrayList<>();
		gameSessionConstructor.setPlace(player, receivedText);
		messageList.add(getCustomMessage(chatId, GAME_NAME));
		return messageList;
	}
}

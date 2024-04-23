package lv.boardgame.bot.messages;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.mybot.MenuReplyKeyboard;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import static lv.boardgame.bot.messages.MessageUtil.*;
import static lv.boardgame.bot.TextFinals.*;

@Component
@AllArgsConstructor
public class MenuMessage {

	private MenuReplyKeyboard menuReplyKeyboard;

	public SendMessage getMenuMessage(String chatIdString) {
		SendMessage message = getCustomMessage(chatIdString, MENU_MESSAGE);
		message.setReplyMarkup(menuReplyKeyboard);
		return message;
	}
}

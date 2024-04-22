package lv.boardgame.bot.messages;

import lombok.AllArgsConstructor;
import lv.boardgame.bot.mybot.MenuReplyKeyboard;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@AllArgsConstructor
public class MenuMessage {

	private MenuReplyKeyboard menuReplyKeyboard;

	public SendMessage getMenuMessage(String chatIdString) {
		return SendMessage.builder()
			.chatId(chatIdString)
			.parseMode("HTML")
			.text("<b>Для того чтобы</b>" + System.lineSeparator() +
				"  <i>1) присоединиться или отписаться от игровой встречи</i>" + System.lineSeparator() +
				"  <i>2) создать или отменить свою игровую встречу</i>" + System.lineSeparator() +
				"<b>используйте кнопки меню внизу</b>")
			.replyMarkup(menuReplyKeyboard)
			.build();
	}

}

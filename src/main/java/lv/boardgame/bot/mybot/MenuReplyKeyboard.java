package lv.boardgame.bot.mybot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

import static lv.boardgame.bot.TextFinals.*;

@Component
public class MenuReplyKeyboard extends ReplyKeyboardMarkup {

	private MenuReplyKeyboard() {
		this.setOneTimeKeyboard(false);
		this.setResizeKeyboard(true);
		this.setKeyboard(List.of(
			createButtonRow(List.of(ALL_GAME_SESSIONS)),
			createButtonRow(List.of(JOIN, LEAVE)),
			createButtonRow(List.of(ORGANIZE, DELETE))
			)
		);
	}

	private KeyboardRow createButtonRow(List<String> buttons) {
		KeyboardRow row = new KeyboardRow();
		buttons.forEach(button -> row.add(new KeyboardButton(button)));
		return row;
	}
}

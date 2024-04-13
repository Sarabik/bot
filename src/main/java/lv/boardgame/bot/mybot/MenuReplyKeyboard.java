package lv.boardgame.bot.mybot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class MenuReplyKeyboard extends ReplyKeyboardMarkup {

	private MenuReplyKeyboard() {
		this.setOneTimeKeyboard(false);
		this.setResizeKeyboard(true);
		this.setKeyboard(List.of(
			createButtonRow(List.of("Все игровые встречи")),
			createButtonRow(List.of("Присоединиться", "Отписаться")),
			createButtonRow(List.of("Организовать встречу", "Отменить встречу"))
			)
		);
	}

	private KeyboardRow createButtonRow(List<String> buttons) {
		KeyboardRow row = new KeyboardRow();
		buttons.forEach(button -> row.add(new KeyboardButton(button)));
		return row;
	}
}

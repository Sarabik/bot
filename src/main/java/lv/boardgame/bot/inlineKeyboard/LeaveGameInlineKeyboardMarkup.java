package lv.boardgame.bot.inlineKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class LeaveGameInlineKeyboardMarkup extends InlineKeyboardMarkup {

	public LeaveGameInlineKeyboardMarkup() {
		this.setKeyboard(keyboard());
	}

	private static List<List<InlineKeyboardButton>> keyboard() {
		InlineKeyboardButton button = InlineKeyboardButton.builder()
			.text("Отписаться")
			.callbackData("ВЫ ОТПИСАЛИСЬ ОТ ИГРОВОЙ ВСТРЕЧИ:")
			.build();
		return List.of(List.of(button));
	}
}

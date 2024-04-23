package lv.boardgame.bot.inlineKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static lv.boardgame.bot.TextFinals.LEAVE_SESSION;
import static lv.boardgame.bot.TextFinals.SESSION_LEAVED;

@Component
public class LeaveGameInlineKeyboardMarkup extends InlineKeyboardMarkup {

	public LeaveGameInlineKeyboardMarkup() {
		this.setKeyboard(keyboard());
	}

	private static List<List<InlineKeyboardButton>> keyboard() {
		InlineKeyboardButton button = InlineKeyboardButton.builder()
			.text(LEAVE_SESSION)
			.callbackData(SESSION_LEAVED)
			.build();
		return List.of(List.of(button));
	}
}

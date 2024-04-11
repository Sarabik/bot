package lv.boardgame.bot.inlineKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class IfOrganizerPlayingInlineKeyboardMarkup extends InlineKeyboardMarkup {

	public IfOrganizerPlayingInlineKeyboardMarkup() {
		this.setKeyboard(ifPlaying());
	}

	private static List<List<InlineKeyboardButton>> ifPlaying() {
		InlineKeyboardButton buttonYes = InlineKeyboardButton.builder()
			.text("Да, тоже буду играть")
			.callbackData("true")
			.build();
		InlineKeyboardButton buttonNo = InlineKeyboardButton.builder()
			.text("Нет, хочу провести игру для других")
			.callbackData("false")
			.build();
		return List.of(List.of(buttonYes), List.of(buttonNo));
	}
}

package lv.boardgame.bot.inlineKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static lv.boardgame.bot.TextFinals.I_WILL_NOT_PLAY;
import static lv.boardgame.bot.TextFinals.I_WILL_PLAY;

@Component
public class IfOrganizerPlayingInlineKeyboardMarkup extends InlineKeyboardMarkup {

	public IfOrganizerPlayingInlineKeyboardMarkup() {
		this.setKeyboard(ifPlaying());
	}

	private static List<List<InlineKeyboardButton>> ifPlaying() {
		InlineKeyboardButton buttonYes = InlineKeyboardButton.builder()
			.text(I_WILL_PLAY)
			.callbackData("true")
			.build();
		InlineKeyboardButton buttonNo = InlineKeyboardButton.builder()
			.text(I_WILL_NOT_PLAY)
			.callbackData("false")
			.build();
		return List.of(List.of(buttonYes), List.of(buttonNo));
	}
}

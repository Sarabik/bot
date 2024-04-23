package lv.boardgame.bot.inlineKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static lv.boardgame.bot.TextFinals.JOINED_SESSION;
import static lv.boardgame.bot.TextFinals.JOIN_SESSION;

@Component
public class JoinGameInlineKeyboardMarkup extends InlineKeyboardMarkup {

	public JoinGameInlineKeyboardMarkup() {
		this.setKeyboard(ifPlaying());
	}

	private static List<List<InlineKeyboardButton>> ifPlaying() {
		InlineKeyboardButton buttonYes = InlineKeyboardButton.builder()
			.text(JOIN_SESSION)
			.callbackData(JOINED_SESSION)
			.build();
		return List.of(List.of(buttonYes));
	}
}

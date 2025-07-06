package lv.boardgame.bot.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static lv.boardgame.bot.constants.TextFinals.DELETE_SESSION;
import static lv.boardgame.bot.constants.TextFinals.SESSION_DELETED;

@Component
public class DeleteGameInlineKeyboardMarkup extends InlineKeyboardMarkup {

	public DeleteGameInlineKeyboardMarkup() {
		this.setKeyboard(ifPlaying());
	}

	private static List<List<InlineKeyboardButton>> ifPlaying() {
		InlineKeyboardButton buttonYes = InlineKeyboardButton.builder()
			.text(DELETE_SESSION)
			.callbackData(SESSION_DELETED)
			.build();
		return List.of(List.of(buttonYes));
	}
}

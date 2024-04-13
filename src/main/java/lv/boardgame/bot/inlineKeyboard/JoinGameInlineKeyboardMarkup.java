package lv.boardgame.bot.inlineKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class JoinGameInlineKeyboardMarkup extends InlineKeyboardMarkup {

	public JoinGameInlineKeyboardMarkup() {
		this.setKeyboard(ifPlaying());
	}

	private static List<List<InlineKeyboardButton>> ifPlaying() {
		InlineKeyboardButton buttonYes = InlineKeyboardButton.builder()
			.text("Присоединиться к встрече")
			.callbackData("ВЫ ПРИСОЕДИНИЛИСЬ К ВСТРЕЧЕ:")
			.build();
		return List.of(List.of(buttonYes));
	}
}

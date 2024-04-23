package lv.boardgame.bot.inlineKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

import static lv.boardgame.bot.TextFinals.NO_COMMENTS;

@Component
public class NoCommentInlineKeyboardMarkup extends InlineKeyboardMarkup {
	public NoCommentInlineKeyboardMarkup() {
		this.setKeyboard(List.of(
			List.of(
				InlineKeyboardButton.builder()
				.text(NO_COMMENTS)
				.callbackData(NO_COMMENTS)
				.build())
			)
		);
	}
}

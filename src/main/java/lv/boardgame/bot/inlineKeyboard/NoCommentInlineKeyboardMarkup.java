package lv.boardgame.bot.inlineKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

@Component
public class NoCommentInlineKeyboardMarkup extends InlineKeyboardMarkup {
	public NoCommentInlineKeyboardMarkup() {
		this.setKeyboard(List.of(
			List.of(
				InlineKeyboardButton.builder()
				.text("Нет комментариев")
				.callbackData("Нет комментариев")
				.build())
			)
		);
	}
}

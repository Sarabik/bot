package lv.boardgame.bot.inlineKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class MaxPlayerCountInlineKeyboardMarkup extends InlineKeyboardMarkup {

	public MaxPlayerCountInlineKeyboardMarkup() {
		this.setKeyboard(getMaxPlayerCount());
	}

	private static List<List<InlineKeyboardButton>> getMaxPlayerCount() {
		List<List<InlineKeyboardButton>> listOfLists = new ArrayList<>();
		List<InlineKeyboardButton> list1 = new ArrayList<>();
		List<InlineKeyboardButton> list2 = new ArrayList<>();
		for (int i = 2; i <= 5; i++) {
			String str = String.valueOf(i);
			list1.add(InlineKeyboardButton.builder()
				.text(str)
				.callbackData(str)
				.build());
		}
		for (int i = 6; i <= 12; i++) {
			String str = String.valueOf(i);
			list2.add(InlineKeyboardButton.builder()
				.text(str)
				.callbackData(str)
				.build());
		}
		listOfLists.add(list1);
		listOfLists.add(list2);
		return listOfLists;
	}
}

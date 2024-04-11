package lv.boardgame.bot.inlineKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class TimeInlineKeyboardMarkup extends InlineKeyboardMarkup {

	private final static int TIMES_IN_ROW = 6;
	private final static LocalTime START_TIME = LocalTime.of(11, 0);

	public TimeInlineKeyboardMarkup() {
		this.setKeyboard(getTimes());
	}

	private static List<List<InlineKeyboardButton>> getTimes() {

		LocalTime time = START_TIME;
		List<InlineKeyboardButton> list = new ArrayList<>();
		for (int i = 0; i < 24; i++) {
			String str = time.format(DateTimeFormatter.ofPattern("HH:mm"));
			list.add(
				InlineKeyboardButton.builder()
					.text(str)
					.callbackData(str)
					.build()
			);
			time = time.plusMinutes(30);
		}

		List<List<InlineKeyboardButton>> listOfLists = new ArrayList<>();
		for (int i = 0; i < list.size(); i += TIMES_IN_ROW) {
			int endIndex = Math.min(i + TIMES_IN_ROW, list.size());
			List<InlineKeyboardButton> sublist = list.subList(i, endIndex);
			listOfLists.add(sublist);
		}
		return listOfLists;
	}
}

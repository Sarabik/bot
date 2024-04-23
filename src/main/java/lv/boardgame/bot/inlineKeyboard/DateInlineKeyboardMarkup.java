package lv.boardgame.bot.inlineKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static lv.boardgame.bot.TextFinals.CHOSE_DATE;
import static lv.boardgame.bot.TextFinals.DATE_FORMATTER;

@Component
public class DateInlineKeyboardMarkup extends InlineKeyboardMarkup {

	public DateInlineKeyboardMarkup() {
		this.setKeyboard(getDates());
	}

	private static List<List<InlineKeyboardButton>> getDates() {
		List<List<InlineKeyboardButton>> calendar = new ArrayList<>();
		LocalDate currentDate = LocalDate.now();

		LocalDate counter = LocalDate.now();
		List<String> days = List.of("ПН", "ВТ", "СР", "ЧТ", "ПН", "СБ", "ВС");
		List<InlineKeyboardButton> daysButtons = days.stream()
			.map(s -> InlineKeyboardButton.builder()
				.text(s)
				.callbackData(CHOSE_DATE)
				.build())
			.toList();
		calendar.add(daysButtons);

		/*first week*/
		int dayOfWeek = currentDate.getDayOfWeek().getValue();
		List<InlineKeyboardButton> firstWeek = new ArrayList<>();
		for (int i = 1; i < dayOfWeek; i++) {
			firstWeek.add(InlineKeyboardButton.builder()
				.text("-")
				.callbackData(CHOSE_DATE)
				.build());
		}
		for (int i = dayOfWeek; i <= 7; i++) {
			String text = String.valueOf(counter.getDayOfMonth());
			firstWeek.add(
				InlineKeyboardButton.builder()
					.text(text)
					.callbackData(counter.format(DATE_FORMATTER))
					.build()
			);
			counter = counter.plusDays(1);
		}
		calendar.add(firstWeek);

		/*other 4 weeks*/
		for (int i = 0; i < 4; i++) {
			List<InlineKeyboardButton> weekList = new ArrayList<>();
			for (int j = 1; j <= 7; j++) {
				String text = String.valueOf(counter.getDayOfMonth());
				weekList.add(
					InlineKeyboardButton.builder()
						.text(text)
						.callbackData(counter.format(DATE_FORMATTER))
						.build()
				);
				counter = counter.plusDays(1);
			}
			calendar.add(weekList);
		}
		return calendar;
	}
}

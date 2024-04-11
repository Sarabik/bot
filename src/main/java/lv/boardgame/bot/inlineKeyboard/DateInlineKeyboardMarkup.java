package lv.boardgame.bot.inlineKeyboard;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
				.callbackData("ignore")
				.build())
			.toList();
		calendar.add(daysButtons);

		/*first week*/
		int dayOfWeek = currentDate.getDayOfWeek().getValue();
		List<InlineKeyboardButton> firstWeek = new ArrayList<>();
		for (int i = 1; i < dayOfWeek; i++) {
			firstWeek.add(InlineKeyboardButton.builder()
				.text("-")
				.callbackData("ignore")
				.build());
		}
		for (int i = dayOfWeek; i <= 7; i++) {
			String text = String.valueOf(counter.getDayOfMonth());
			firstWeek.add(
				InlineKeyboardButton.builder()
					.text(text)
					.callbackData(counter.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
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
						.callbackData(counter.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
						.build()
				);
				counter = counter.plusDays(1);
			}
			calendar.add(weekList);
		}
		return calendar;
	}
}

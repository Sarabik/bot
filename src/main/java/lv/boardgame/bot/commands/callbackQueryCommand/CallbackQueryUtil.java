package lv.boardgame.bot.commands.callbackQueryCommand;

import lv.boardgame.bot.model.GameSession;
import lv.boardgame.bot.service.GameSessionService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lv.boardgame.bot.TextFinals.DATE_TIME_FORMATTER;
import static lv.boardgame.bot.messages.MessageUtil.getCustomMessage;

public class CallbackQueryUtil {

	public static GameSession getGameSession(Message message, GameSessionService gameSessionService) {
		String date = message.getEntities().get(1).getText();
		String place = message.getEntities().get(3).getText();
		String game = message.getEntities().get(5).getText();
		LocalDateTime dateTime = LocalDateTime.parse(date, DATE_TIME_FORMATTER);
		return gameSessionService.findGameSessionByDatePlaceAndGame(dateTime, place, game);
	}

	public static List<SendMessage> getStartList(String chatId, String data) {
		List<SendMessage> messageList = new ArrayList<>();
		messageList.add(getCustomMessage(chatId, data));
		return messageList;
	}

}

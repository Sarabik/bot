package lv.boardgame.bot.config;

import lv.boardgame.bot.mybot.BoardGameBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

	@Bean
	public BoardGameBot boardGameBot() {
		return new BoardGameBot();
	}

	@Bean
	public TelegramBotsApi telegramBotsApi(BoardGameBot boardGameBot) throws TelegramApiException {
		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		botsApi.registerBot(boardGameBot);
		return botsApi;
	}

}

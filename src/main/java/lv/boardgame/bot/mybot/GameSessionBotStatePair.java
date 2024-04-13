package lv.boardgame.bot.mybot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.boardgame.bot.model.GameSession;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class GameSessionBotStatePair {
	private GameSession gameSession = new GameSession();
	private BotState botState = BotState.START;
}

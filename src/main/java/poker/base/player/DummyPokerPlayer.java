package poker.base.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poker.base.Card;
import poker.base.game.GameState;

import java.util.Set;

public class DummyPokerPlayer extends BasePokerPlayer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public DummyPokerPlayer(int playerId, Set<Card> holeCards, int startingChips) {
        super(playerId, holeCards, startingChips);
    }

    @Override
    public GameState playBettingRound(GameState gameState) {
        String message;
        int bet = 0;
        if (gameState.isBigBlindTurn()) {
            bet = gameState.getBigBlind();
            gameState = placeMarginalBet(gameState, bet);
            gameState = gameState.withBigBlindTurn(false);
            gameState = gameState.withLittleBlindTurn(true);
            message = "Bet big blind " + bet;
        } else if (gameState.isLittleBlindTurn()) {
            bet = gameState.getLittleBlind();
            gameState = placeMarginalBet(gameState, bet);
            gameState = gameState.withLittleBlindTurn(false);
            message = "Bet little blind " + bet;
        } else if (gameState.getPot() < 60) {
            bet = 10;
            gameState = placeMarginalBet(gameState, bet);
            message = "Bet " + bet;
        } else if (false) {

        } else {
            fold();
            message = "Folded";
        }
        printMessage(message);
        return gameState;
    }
}

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
        printMessage(String.format("Starting betting round -- pot: %s, player chips: %s, starting total player bet: %s",
                gameState.getPot(), getChips(), getBet()));

        int bet = 0;
        if (gameState.isBigBlindTurn()) {
            bet = gameState.getBigBlind();
            printMessage("Big blind");
            gameState = placeMarginalBet(gameState, bet);
            gameState = gameState.withBigBlindTurn(false);
            gameState = gameState.withLittleBlindTurn(true);
        } else if (gameState.isLittleBlindTurn()) {
            bet = gameState.getLittleBlind();
            printMessage("Little blind");
            gameState = placeMarginalBet(gameState, bet);
            gameState = gameState.withLittleBlindTurn(false);
        } else if (gameState.getPot() < 60 && gameState.getActivePlayers().size() > 2) {
            bet = 10;
            gameState = placeMarginalBet(gameState, bet);
        } else if (getBet() >= 10 && gameState.getCommunityCards().size() == 3) {
            setIsChecked(true);
        } else {
            setIsFolded(true);
        }
        return gameState;
    }
}

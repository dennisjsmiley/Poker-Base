package poker.base.player;

import poker.base.Card;
import poker.base.game.GameState;

import java.util.Set;

public class TestPokerPlayer extends BasePokerPlayer {

    public TestPokerPlayer(int playerId, Set<Card> holeCards, int startingChips) {
        super(playerId, holeCards, startingChips);
    }

    @Override
    public GameState playBettingRound(GameState gameState) {
        throw new UnsupportedOperationException();
    }
}

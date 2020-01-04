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
    public GameState playBettingRoundCustom(GameState state) {
        if (state.getPot() < 60 && state.getActivePlayers().size() > 2) {
            state = placeMarginalBet(10, state);
        } else if (getBet() > 10 && (state.getCommunityCards().size() >= 3 || state.getCommunityCards().size() == 0)) {
            setIsChecked(true);
        } else {
            state = setIsFolded(true, state);
        }
        return state;
    }
}

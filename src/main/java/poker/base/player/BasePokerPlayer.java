package poker.base.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poker.base.Card;
import poker.base.Hand;
import poker.base.game.GameState;
import poker.base.util.PokerUtil;

import java.util.List;
import java.util.Set;

public abstract class BasePokerPlayer implements PokerPlayer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private int playerId;
    private int chips;
    private Set<Card> holeCards;
    private boolean isFolded;
    private boolean isChecked;
    private int bet;
    private Hand bestHand;

    public BasePokerPlayer(int playerId, Set<Card> holeCards, int startingChips) {
        this.playerId = playerId;
        chips = startingChips;
        this.holeCards = holeCards;
        isFolded = false;
        isChecked = false;
        bestHand = null;
    }

    @Override
    public int getChips() {
        return chips;
    }

    @Override
    public Set<Card> getHoleCards() {
        return holeCards;
    }

    @Override
    public GameState placeMarginalBet(GameState gameState, int chips) {
        assert this.chips > chips;
        bet += chips;
        gameState = gameState.withPot(gameState.getPot() + chips);
        this.chips -= chips;

        printMessage("Place marginal bet: " + chips + ", total bet: " + bet);

        return gameState;
    }

    @Override
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public int addWinnings(int marginalChips) {
        this.chips += marginalChips;
        return this.chips;
    }

    @Override
    public abstract GameState playBettingRound(GameState gameState);

    @Override
    public boolean isFolded() {
        return isFolded;
    }

    @Override
    public void setIsFolded(boolean isFolded) {
        this.isFolded = isFolded;
        if (isFolded) {
            printMessage("Fold");
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
        if (isChecked) {
            printMessage("Check");
        }
    }

    @Override
    public int getBet() {
        return bet;
    }

    @Override
    public Hand determineBestHand(List<Card> communityCards) {
        assert communityCards.size() == 5;
        List<Hand> hands = Hand.getHands(holeCards, communityCards);
        bestHand = hands.get(0);
        return bestHand;
    }

    public Hand getBestHand() {
        assert bestHand != null;
        return bestHand;
    }

    @Override
    public int compareTo(PokerPlayer other) {
        assert bestHand != null && other.getBestHand() != null;
        return bestHand.compareTo(other.getBestHand());
    }

    @Override
    public String toString() {
        if (getBestHand() == null) {
            return String.format("playerId: %s, chips: %s, hole cards: %s", playerId, chips, PokerUtil.toCardShortCodes(holeCards));
        } else {
            return String.format("playerId: %s, chips: %s, best hand: %s", playerId, chips, getBestHand());
        }
    }

    protected void printMessage(String message) {
        logger.info("playerId: {}, message: {}", getPlayerId(), message);
    }
}

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
    public void setHoleCards(Set<Card> cards) {
        this.holeCards = cards;
    }

    @Override
    public Set<Card> getHoleCards() {
        return holeCards;
    }

    @Override
    public GameState placeMarginalBet(int chips, GameState state) {
        assert this.chips > chips;
        bet += chips;
        state = state.withPot(state.getPot() + chips);
        this.chips -= chips;
        state = state.withMinimumRequiredBet(bet);

        printMessage("Place marginal bet: " + chips + ", total bet: " + bet);

        return state;
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
    public GameState playBettingRound(GameState state) {
        printMessage(String.format("Starting betting round -- pot: %s, player chips: %s, starting total player bet: %s", state.getPot(), getChips(), getBet()));

        if (state.isBigBlindTurn()) {
            printMessage("Big blind");

            int bigBlind = state.getBigBlind();
            if (bigBlind > getChips()) {
                state = setIsFolded(true, state);
                return state;
            }

            state = placeMarginalBet(bigBlind, state);
            state = state.withBigBlindTurn(false);
            state = state.withLittleBlindTurn(true);
        } else if (state.isLittleBlindTurn()) {
            printMessage("Little blind");

            int littleBlind = state.getLittleBlind();
            if (littleBlind > getChips()) {
                state = setIsFolded(true, state);
                return state;
            }

            state = placeMarginalBet(littleBlind, state);
            state = state.withLittleBlindTurn(false);
        } else {
            state = playBettingRoundCustom(state);
        }
        return state;
    }

    protected abstract GameState playBettingRoundCustom(GameState gameState);

    @Override
    public boolean isFolded() {
        return isFolded;
    }

    @Override
    public GameState setIsFolded(boolean isFolded, GameState gameState) {
        this.isFolded = isFolded;
        if (isFolded) {
            gameState = gameState.withPot(gameState.getPot() + getBet());
            bet = 0;
            printMessage("Fold");
            return gameState;
        } else {
            return gameState;
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
    public void resetBet() {
        bet = 0;
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

    public void clearHand() {
        holeCards.clear();
        bestHand = null;
    }

    @Override
    public int compareTo(PokerPlayer other) {
        assert bestHand != null && other.getBestHand() != null;
        return bestHand.compareTo(other.getBestHand());
    }

    @Override
    public String toString() {
        if (getBestHand() == null) {
            return String.format("playerId: %s, chips: %s, bet: %s, hole cards: %s", playerId, chips, bet, PokerUtil.toCardShortCodes(holeCards));
        } else {
            return String.format("playerId: %s, chips: %s, bet: %s, best hand: %s, hole cards: %s", playerId, chips, bet, getBestHand(), holeCards);
        }
    }

    protected void printMessage(String message) {
        logger.info("playerId: {}, message: {}", getPlayerId(), message);
    }
}

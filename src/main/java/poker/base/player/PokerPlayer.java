package poker.base.player;

import poker.base.Card;
import poker.base.Hand;
import poker.base.game.GameState;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PokerPlayer extends Comparable<PokerPlayer> {

    int getPlayerId();

    int getChips();

    GameState placeMarginalBet(int chips, GameState gameState);

    int addWinnings(int chips);

    GameState playBettingRound(GameState gameState);

    boolean isFolded();

    GameState setIsFolded(boolean isFolded, GameState state);

    boolean isChecked();

    void setIsChecked(boolean isChecked);

    void resetBet();

    int getBet();

    Hand determineBestHand(List<Card> communityCards);

    Hand getBestHand();

    int compareTo(PokerPlayer other);

    void setHoleCards(Set<Card> cards);

    Set<Card> getHoleCards();

    void clearHand();

    String toString();
}

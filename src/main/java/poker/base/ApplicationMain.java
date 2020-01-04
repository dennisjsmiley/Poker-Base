package poker.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poker.base.game.GameState;
import poker.base.game.PokerGame;
import poker.base.handRanking.HandRanking;
import poker.base.player.DummyPokerPlayer;
import poker.base.player.PokerPlayer;

import java.util.*;

public class ApplicationMain {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) {
        doGame();
    }

    public static void rankHands(String[] args) {
        String handShortCodes1 = args[0];
        String handShortCodes2 = args[1];

        System.out.println("Comparing " + handShortCodes1 + " versus " + handShortCodes2);

        Hand hand1 = Hand.fromShortCodes(handShortCodes1);
        Hand hand2 = Hand.fromShortCodes(handShortCodes2);

        HandRanking handRanking1 = hand1.getHandRanking();
        HandRanking handRanking2 = hand2.getHandRanking();

        int comparison = handRanking1.compareTo(handRanking2);
        if (comparison > 0) {
            System.out.println("Hand " + handShortCodes1 + " wins");
        } else if (comparison < 0) {
            System.out.println("Hand " + handShortCodes2 + " wins");
        } else {
            System.out.println("Tie between both hands");
        }
    }

    public static void doGame() {
        PokerGame game = new PokerGame();

        int numPlayers = 5;
        Deck deck = new Deck();
        List<Card> communityCards = new ArrayList<>();
        Map<Integer, PokerPlayer> pokerPlayers = new HashMap<>();

        GameState startingState = GameState
                .builder()
                .deck(deck)
                .pot(0)
                .startingPlayerChips(100)
                .communityCards(communityCards)
                .pokerPlayers(pokerPlayers)
                .isBigBlindTurn(true)
                .bigBlind(10)
                .isLittleBlindTurn(false)
                .littleBlind(5)
                .minimumRequiredBet(10)
                .build();

        for (int playerId = 0; playerId < numPlayers; playerId++) {
            PokerPlayer player = new DummyPokerPlayer(playerId, new HashSet<>(), startingState.getStartingPlayerChips());
            pokerPlayers.put(playerId, player);
        }

        GameState nextState = game.runGame(startingState);

        logger.info("nextState: {}", nextState);
    }
}

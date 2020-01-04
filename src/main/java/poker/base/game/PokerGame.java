package poker.base.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poker.base.Card;
import poker.base.Deck;
import poker.base.Hand;
import poker.base.player.DummyPokerPlayer;
import poker.base.player.PokerPlayer;
import poker.base.util.PokerUtil;
import poker.base.util.Tuple;

import java.util.*;

public class PokerGame {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void runGame() {
        int numPlayers = 5;
        Deck deck = new Deck();
        List<Card> communityCards = new ArrayList<>();
        Map<Integer, PokerPlayer> pokerPlayers = new HashMap<>();

        GameState gameState = GameState
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
            Set<Card> holeCards = new HashSet<>(Arrays.asList(deck.draw(), deck.draw()));

            PokerPlayer player = new DummyPokerPlayer(playerId, holeCards, gameState.getStartingPlayerChips());
            pokerPlayers.put(playerId, player);

            logger.info("Player({})", player);
        }

        gameState = gameState.doBetting();

        if (gameState.isOnlyOneActivePlayer()) {
            List<PokerPlayer> winners = gameState.getActivePlayers();
            logger.info("winner: {}, winnings: {}", winners.get(0), gameState.getPot());
            return;
        }

        List<Card> flop = deck.getFlop();
        communityCards.addAll(flop);
        logger.info("flop: {}", PokerUtil.toCardShortCodes(flop));

        gameState = gameState.doBetting();

        Card turn = deck.draw();
        communityCards.add(turn);
        logger.info("turn: {}", turn.toShortCode());

        gameState = gameState.doBetting();

        Card river = deck.draw();
        communityCards.add(river);
        logger.info("river: {}", river.toShortCode());

        gameState = gameState.doBetting();

        List<PokerPlayer> winners = gameState.getWinners();

        int pot = gameState.getPot();

        logger.info("winners: {}, winnings per player: {}", winners, new Double(pot) / new Double(winners.size()));
    }






}

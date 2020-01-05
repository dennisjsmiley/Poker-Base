package poker.base.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poker.base.Card;
import poker.base.Deck;
import poker.base.player.PokerPlayer;
import poker.base.util.PokerUtil;

import java.util.*;

public class PokerGame {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public GameState runGame(GameState state) {
        Deck deck = state.getDeck();
        deck.shuffle();

        List<Card> communityCards = state.getCommunityCards();

        state.getPokerPlayers().forEach((playerId, player) -> {
                player.setHoleCards(new HashSet<>(Arrays.asList(deck.draw(), deck.draw())));
                logger.info("Player({})", player);
        });

        state = state.doBetting();

        if (state.isOnlyOneActivePlayer()) {
            List<PokerPlayer> winners = state.getActivePlayers();
            PokerPlayer winner = winners.get(0);

            logger.info("winner: {}, winnings: {}", winner, state.getPot());

            winner.addWinnings(state.getPot());
            state = state.setFreshState();

            return state;
        }

        List<Card> flop = deck.getFlop();
        communityCards.addAll(flop);
        logger.info("flop: {}", PokerUtil.toCardShortCodes(flop));

        state = state.doBetting();

        Card turn = deck.draw();
        communityCards.add(turn);
        logger.info("turn: {}", turn.toShortCode());

        state = state.doBetting();

        Card river = deck.draw();
        communityCards.add(river);
        logger.info("river: {}", river.toShortCode());

        state = state.doBetting();

        logger.info("community cards: {}", PokerUtil.toCardShortCodes(communityCards));

        List<PokerPlayer> winners = state.getWinners();

        int pot = state.getPot();
        int winningsPerWinner = (int) Math.round(new Double(pot) / new Double(winners.size()));

        logger.info("winners: {}, winnings per player: {}", winners, winningsPerWinner);

        winners.forEach(winner -> winner.addWinnings(winningsPerWinner));
        state = state.setFreshState();

        return state;
    }






}

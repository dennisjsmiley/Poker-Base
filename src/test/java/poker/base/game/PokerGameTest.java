package poker.base.game;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poker.base.Card;
import poker.base.Deck;
import poker.base.Hand;
import poker.base.enums.Rank;
import poker.base.enums.Suit;
import poker.base.player.PokerPlayer;
import poker.base.player.TestPokerPlayer;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class PokerGameTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final int pot = 100;

    private final int startingPlayerChips = 10;

    private final boolean isBigBlindTurn = false;
    
    private final int bigBlind = 2;
    
    private final boolean isLittleBlindTurn = false;
    
    private final int littleBlind = 1;

    private final int minimumRequiredBet = 2;

    private Deck getTestDeck1() {
        Deck deck = new Deck(Arrays.asList(
                new Card(Suit.DIAMONDS, Rank.SIX),
                new Card(Suit.CLUBS, Rank.TWO),
                new Card(Suit.HEARTS, Rank.SIX),
                new Card(Suit.HEARTS, Rank.FIVE),
                new Card(Suit.HEARTS, Rank.FOUR)
        ));
        return deck;
    }

    private Deck getTestDeck2() {
        Deck deck = new Deck(Arrays.asList(
                new Card(Suit.DIAMONDS, Rank.SEVEN),
                new Card(Suit.CLUBS, Rank.TWO),
                new Card(Suit.HEARTS, Rank.EIGHT),
                new Card(Suit.HEARTS, Rank.FIVE),
                new Card(Suit.HEARTS, Rank.FOUR)
        ));
        return deck;
    }


    private Map<Integer, PokerPlayer> getTestPokerPlayers1() {
        List<PokerPlayer> playerList = Arrays.asList(
                new TestPokerPlayer(0, new HashSet<>(Arrays.asList(
                        new Card(Suit.SPADES, Rank.SIX),
                        new Card(Suit.SPADES, Rank.THREE)
                )), startingPlayerChips),

                new TestPokerPlayer(1, new HashSet<>(Arrays.asList(
                        new Card(Suit.HEARTS, Rank.SEVEN),
                        new Card(Suit.CLUBS, Rank.NINE)
                )), startingPlayerChips),

                new TestPokerPlayer(2, new HashSet<>(Arrays.asList(
                        new Card(Suit.CLUBS, Rank.FOUR),
                        new Card(Suit.SPADES, Rank.JACK)
                )), startingPlayerChips)
        );

        Map<Integer, PokerPlayer> playerMap = new HashMap<>();
        playerList.forEach(player -> playerMap.put(player.getPlayerId(), player));

        return playerMap;
    }

    private Map<Integer, PokerPlayer> getTestPokerPlayers2() {
        List<PokerPlayer> playerList = Arrays.asList(
                new TestPokerPlayer(0, new HashSet<>(Arrays.asList(
                        new Card(Suit.SPADES, Rank.TWO),
                        new Card(Suit.SPADES, Rank.NINE)
                )), startingPlayerChips),

                new TestPokerPlayer(1, new HashSet<>(Arrays.asList(
                        new Card(Suit.HEARTS, Rank.TWO),
                        new Card(Suit.CLUBS, Rank.NINE)
                )), startingPlayerChips)
        );

        Map<Integer, PokerPlayer> playerMap = new HashMap<>();
        playerList.forEach(player -> playerMap.put(player.getPlayerId(), player));

        return playerMap;
    }

    public void testGetWinners(PokerGame game, GameState state, List<PokerPlayer> expectedWinners) {
        state.getPokerPlayers().forEach((playerId, player) -> player.determineBestHand(state.getCommunityCards()));

        List<PokerPlayer> actualWinners = game.getWinners(state);

        List<Hand> winningHands = new ArrayList<>();
        actualWinners.forEach(player -> winningHands.add(player.getBestHand()));

        List<Integer> winningPlayerIds = new ArrayList<>();
        actualWinners.forEach(player -> winningPlayerIds.add(player.getPlayerId()));

        logger.debug("community cards: {}, winning hands: {}, winning playerIds: {}",
                state.getCommunityCards()
                .stream()
                .map(card -> card.toShortCode())
                .collect(Collectors.toList()), winningHands, winningPlayerIds);

        assertEquals(expectedWinners, actualWinners);
    }

    @Test
    public void testGetWinners_noTie() {
        PokerGame game = new PokerGame();

        GameState state = GameState
                .builder()
                .deck(getTestDeck1())
                .pot(pot)
                .startingPlayerChips(startingPlayerChips)
                .communityCards(getTestDeck1().getCards().subList(0, 5))
                .pokerPlayers(getTestPokerPlayers1())
                .isBigBlindTurn(isBigBlindTurn)
                .bigBlind(bigBlind)
                .isLittleBlindTurn(isLittleBlindTurn)
                .littleBlind(littleBlind)
                .minimumRequiredBet(minimumRequiredBet)
                .build();

        testGetWinners(game, state, Arrays.asList(
                state.getPokerPlayers().get(0)
        ));
    }

    @Test
    public void testGetWinners_tie() {
        PokerGame game = new PokerGame();

        GameState state = GameState
                .builder()
                .deck(getTestDeck2())
                .pot(pot)
                .startingPlayerChips(startingPlayerChips)
                .communityCards(getTestDeck2().getCards().subList(0, 5))
                .pokerPlayers(getTestPokerPlayers2())
                .isBigBlindTurn(isBigBlindTurn)
                .bigBlind(bigBlind)
                .isLittleBlindTurn(isLittleBlindTurn)
                .littleBlind(littleBlind)
                .minimumRequiredBet(minimumRequiredBet)
                .build();

        testGetWinners(game, state, Arrays.asList(
                state.getPokerPlayers().get(0),
                state.getPokerPlayers().get(1)
        ));
    }
}

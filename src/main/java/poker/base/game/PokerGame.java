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
        GameState gameState = GameState
                .builder()
                .deck(deck)
                .pot(0)
                .startingPlayerChips(100)
                .communityCards(communityCards)
                .pokerPlayers(new HashMap<>())
                .isBigBlindTurn(true)
                .bigBlind(10)
                .isLittleBlindTurn(false)
                .littleBlind(5)
                .build();

        Map<Integer, PokerPlayer> pokerPlayers = new HashMap<>();
        for (int playerId = 0; playerId < numPlayers; playerId++) {
            Set<Card> holeCards = new HashSet<>();
            holeCards.addAll(Arrays.asList(deck.draw(), deck.draw()));

            pokerPlayers.put(playerId, new DummyPokerPlayer(playerId, holeCards, gameState.getStartingPlayerChips()));

            logger.info("playerId: {}, hold cards: {}", playerId, PokerUtil.toCardShortCodes(holeCards));
        }
        gameState = gameState.withPokerPlayers(pokerPlayers);

        int pot = 0;
        int bigBlindPlayerId = 0;
        for (int playerId = 0; playerId < numPlayers; playerId++) {
            PokerPlayer player = pokerPlayers.get(playerId);
            gameState = player.playBettingRound(gameState);
        }

        if (gameState.isOnlyOneActivePlayer()) {
            List<PokerPlayer> winners = gameState.getActivePlayers();
            logger.info("winner: {}", winners.get(0));
            return;
        }

        List<Card> flop = deck.getFlop();
        communityCards.addAll(flop);
        logger.info("flop: {}", PokerUtil.toCardShortCodes(flop));

        gameState = doBetting(gameState);

        Card turn = deck.draw();
        communityCards.add(turn);
        logger.info("turn: {}", turn.toShortCode());

        gameState = doBetting(gameState);

        Card river = deck.draw();
        communityCards.add(river);
        logger.info("river: {}", river.toShortCode());

        gameState = doBetting(gameState);

        List<PokerPlayer> winners = getWinners(gameState);

        for (Map.Entry<Integer, PokerPlayer> playerEntry : pokerPlayers.entrySet()) {
            PokerPlayer player = playerEntry.getValue();
            pot += player.getBet();
        }

        logger.info("winners: {}", winners);
    }

    public List<PokerPlayer> getWinners(GameState gameState) {
        if (!gameState.isActivePlayers()) {
            return new ArrayList<>();
        }

        Map<Integer, PokerPlayer> pokerPlayers = gameState.getActivePlayersMap();

        List<Tuple<Integer, Hand>> playerHandTuples = new ArrayList<>();
        pokerPlayers.forEach((playerId, player) -> {
            playerHandTuples.add(new Tuple<>(playerId, player.getBestHand()));
        });

        if (playerHandTuples.size() == 1) {
            return Arrays.asList(pokerPlayers.get(playerHandTuples.get(0).getX()));
        }

        Collections.sort(playerHandTuples, (t1, t2) -> - t1.getY().compareTo(t2.getY()));
        Hand winningHand = playerHandTuples.get(0).getY();

        List<PokerPlayer> winners = new ArrayList<>();
        pokerPlayers.forEach((playerId, player) -> {
            Hand playerBestHand = player.getBestHand();

            logger.debug("getWinners -- playerId: {}, hand: {} ({}), winning hand: {} ({}), comparison: {}",
                    playerId,
                    playerBestHand,
                    playerBestHand.getHandRanking().asEnum(),
                    winningHand,
                    winningHand.getHandRanking().asEnum(),
                    player.getBestHand().compareTo(winningHand)
            );
            
            if (playerBestHand.compareTo(winningHand) == 0) {
                winners.add(player);
            }
        });

        return winners;
    }

    public GameState doBetting(GameState gameState) {
        Map<Integer, PokerPlayer> pokerPlayers = gameState.getPokerPlayers();
        while (isStillBetting(pokerPlayers)) {
            for (int playerId = 0; playerId < pokerPlayers.size(); playerId++) {
                PokerPlayer player = pokerPlayers.get(playerId);
                gameState = player.playBettingRound(gameState);
            }
        }
        return gameState;
    }

    public boolean isStillBetting(Map<Integer, PokerPlayer> pokerPlayers) {
        int numStillBetting = 0;
        for (Map.Entry<Integer, PokerPlayer> pokerPlayerEntry : pokerPlayers.entrySet()) {
            PokerPlayer player = pokerPlayerEntry.getValue();
            if (!player.isFolded()) {
                numStillBetting ++;
            }
        }
        return numStillBetting > 1;
    }

}

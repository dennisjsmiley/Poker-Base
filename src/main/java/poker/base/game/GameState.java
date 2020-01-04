package poker.base.game;

import lombok.*;
import lombok.experimental.Wither;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poker.base.Card;
import poker.base.Deck;
import poker.base.Hand;
import poker.base.player.PokerPlayer;
import poker.base.util.Tuple;

import java.util.*;

@Data
@Builder
@AllArgsConstructor
@ToString
public class GameState {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Wither
    private final Deck deck;
    @Wither
    private final int pot;
    @Wither
    private final int startingPlayerChips;
    @Wither
    private final List<Card> communityCards;
    @Wither
    private final Map<Integer, PokerPlayer> pokerPlayers;
    @Wither
    private final boolean isBigBlindTurn;
    @Wither
    private final int bigBlind;
    @Wither
    private final boolean isLittleBlindTurn;
    @Wither
    private final int littleBlind;
    @Wither
    private final int minimumRequiredBet;

    public List<PokerPlayer> getOtherPokerPlayersList(int targetPlayerId) {
        List<PokerPlayer> pokerPlayersList = new ArrayList<>();
        pokerPlayers.forEach((playerId, player) -> {
            if (playerId != targetPlayerId) {
                pokerPlayersList.add(player);
            }
        });
        return pokerPlayersList;
    }

    public List<PokerPlayer> getActivePlayers() {
        List<PokerPlayer> activePlayers = new ArrayList<>();
        pokerPlayers.forEach((playerId, player) -> {
            if (!player.isFolded()) {
                activePlayers.add(player);
            }
        });
        return activePlayers;
    }

    public Map<Integer, PokerPlayer> getActivePlayersMap() {
        Map<Integer, PokerPlayer> activePlayers = new HashMap<>();
        getActivePlayers().forEach(player -> activePlayers.put(player.getPlayerId(), player));
        return activePlayers;
    }

    public boolean isActivePlayers() {
        return getActivePlayers().size() > 0;
    }

    public boolean isOnlyOneActivePlayer() {
        return getActivePlayers().size() == 1;
    }

    public boolean isPlayersStillBetting() {
        int numStillBetting = 0;
        for (Map.Entry<Integer, PokerPlayer> pokerPlayerEntry : pokerPlayers.entrySet()) {
            PokerPlayer player = pokerPlayerEntry.getValue();
            if (!player.isFolded() && !player.isChecked()) {
                numStillBetting ++;
            }
        }
        return numStillBetting > 1;
    }

    public GameState doBetting() {
        return doBetting(this);
    }

    private GameState doBetting(GameState gameState) {
        Map<Integer, PokerPlayer> pokerPlayers = gameState.getActivePlayersMap();
        while (gameState.isPlayersStillBetting()) {
            for (int playerId = 0; playerId < pokerPlayers.size(); playerId++) {
                PokerPlayer player = pokerPlayers.get(playerId);
                gameState = player.playBettingRound(gameState);

                logger.info("pot: {}", gameState.getPot());
            }
            pokerPlayers = gameState.getActivePlayersMap();
        }

        gameState.getPokerPlayers().forEach((playerId, player) -> player.setIsChecked(false));

        return gameState;
    }

    public List<PokerPlayer> getWinners() {
        if (!isActivePlayers()) {
            return new ArrayList<>();
        }

        Map<Integer, PokerPlayer> pokerPlayers = getActivePlayersMap();
        pokerPlayers.forEach((playerId, player) -> player.determineBestHand(getCommunityCards()));

        List<Tuple<Integer, Hand>> playerHandTuples = new ArrayList<>();
        pokerPlayers.forEach((playerId, player) -> {
            playerHandTuples.add(new Tuple<>(playerId, player.getBestHand()));
        });

        if (playerHandTuples.size() == 1) {
            return Arrays.asList(pokerPlayers.get(playerHandTuples.get(0).getX()));
        }

        Collections.sort(playerHandTuples, (t1, t2) -> t2.getY().compareTo(t1.getY()));
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
}

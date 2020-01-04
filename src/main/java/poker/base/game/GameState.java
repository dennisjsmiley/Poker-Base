package poker.base.game;

import lombok.*;
import lombok.experimental.Wither;
import poker.base.Card;
import poker.base.Deck;
import poker.base.player.PokerPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@ToString
public class GameState {
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
}

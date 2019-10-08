package poker.base.handRanking;

import poker.base.Card;
import poker.base.exception.StraightFlushNotSameSuit;

public class StraightFlush extends HandRanking {

    private final Card lowCard;
    private final Card highCard;

    public StraightFlush(Card lowCard, Card highCard) throws StraightFlushNotSameSuit {
        if (!lowCard.getSuit().equals(highCard.getSuit())) {
            throw new StraightFlushNotSameSuit();
        }
        this.lowCard = lowCard;
        this.highCard = highCard;
    }

    public Card getLowCard() {
        return lowCard;
    }

    public Card getHighCard() {
        return highCard;
    }
}

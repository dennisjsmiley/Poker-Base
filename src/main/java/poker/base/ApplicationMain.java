package poker.base;

import poker.base.handRanking.HandRanking;

public class ApplicationMain {

    public static void main(String[] args) {
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
}

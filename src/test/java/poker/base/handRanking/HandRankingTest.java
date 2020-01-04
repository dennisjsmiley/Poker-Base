package poker.base.handRanking;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poker.base.Hand;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HandRankingTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Object[][] tieBreakerTestCases = {
            // Tie Breakers

            // Royal Flush
            {"as,ks,qs,js,10s", "ad,kd,qd,jd,10d", 0},

            // Straight Flush
            {"10d,9d,8d,7d,6d", "js,10s,9s,8s,7s", -1},

            // Four of a Kind
            {"as,ac,ad,ah,js", "as,ac,ad,ah,ks", -1},
            {"as,ac,ad,ah,js", "js,jc,jd,jh,ks", 1},

            // Full House
            {"as,ac,ad,ks,qs", "js,jc,jd,ks,qs", 1},

            // Flush
            {"10d,9c,8h,7s,6c", "10h,9s,8s,7h,6d", 0},
            {"10d,9c,8h,7s,6c", "jh,10s,9s,8h,7d", -1},

            // Straight
            {"6c,5s,4d,3h,2c", "7s,6d,5c,4h,3c", -1},

            // Three of a Kind
            {"as,ac,ah,3d,2c", "ks,kc,kd,4d,3d", 1},

            // Two Pair
            {"as,ac,ks,kc,5h", "ks,kc,qs,qh,6h", 1},
            {"as,ac,ks,kc,5h", "as,ac,ks,kh,6h", -1},

            // Pair
            {"as,ac,kh,qd,js", "kh,kd,qs,jd,10h", 1},
            {"as,ac,qh,jd,9s", "as,ac,kh,qd,8s", 1},
            {"as,ac,qh,10s,9h", "as,ac,qh,jd,10s", 1},

            // High Card
            {"10d,8h,7s,5c,3d", "kd,8h,7s,5c,3d", -1}


    };

    @Test
    public void testHandRanking_compareTo_TieBreakers() {
        for (Object[] testCase : tieBreakerTestCases) {
            String shortCodes1 = (String) testCase[0];
            String shortCodes2 = (String) testCase[1];
            int expectedResult = (int) testCase[2];

            Hand hand1 = Hand.fromShortCodes(shortCodes1);
            HandRanking handRanking1 = hand1.getHandRanking();

            Hand hand2 = Hand.fromShortCodes(shortCodes2);
            HandRanking handRanking2 = hand2.getHandRanking();

            int actualResult = adjustResult(handRanking1.compareTo(handRanking2));

            logger.debug("hand1: {}\thand2: {}\texpectedResult: {}\tactualResult: {}\tsuccess: {}",
                    shortCodes1, shortCodes2, expectedResult, actualResult, expectedResult == actualResult);

            assertEquals(expectedResult, actualResult);
        }
    }

    @Test
    public void testHandRanking_compareTo_NoTies() {
        Map<poker.base.enums.HandRanking, HandRanking> handRankingMap = new HashMap<>();

        HandRanking handRanking;

        // Royal Flush
        handRanking = Hand.fromShortCodes("as,ks,qs,js,10s").getHandRanking();
        handRankingMap.put(handRanking.asEnum(), handRanking);

        // Straight Flush
        handRanking = Hand.fromShortCodes("10d,9d,8d,7d,6d").getHandRanking();
        handRankingMap.put(handRanking.asEnum(), handRanking);

        // Four of a Kind
        handRanking = Hand.fromShortCodes("as,ac,ad,ah,js").getHandRanking();
        handRankingMap.put(handRanking.asEnum(), handRanking);

        // Full House
        handRanking = Hand.fromShortCodes("as,ac,ad,ks,qs").getHandRanking();
        handRankingMap.put(handRanking.asEnum(), handRanking);

        // Flush
        handRanking = Hand.fromShortCodes("10d,9c,8h,7s,6c").getHandRanking();
        handRankingMap.put(handRanking.asEnum(), handRanking);

        // Straight
        handRanking = Hand.fromShortCodes("6c,5s,4d,3h,2c").getHandRanking();
        handRankingMap.put(handRanking.asEnum(), handRanking);

        // Three of a Kind
        handRanking = Hand.fromShortCodes("as,ac,ah,3d,2c").getHandRanking();
        handRankingMap.put(handRanking.asEnum(), handRanking);

        // Two Pair
        handRanking = Hand.fromShortCodes("as,ac,ks,kc,5h").getHandRanking();
        handRankingMap.put(handRanking.asEnum(), handRanking);

        // Pair
        handRanking = Hand.fromShortCodes("as,ac,kh,qd,js").getHandRanking();
        handRankingMap.put(handRanking.asEnum(), handRanking);

        // High Card
        handRanking = Hand.fromShortCodes("10d,8h,7s,5c,3d").getHandRanking();
        handRankingMap.put(handRanking.asEnum(), handRanking);

        for (poker.base.enums.HandRanking handRankingEnum1 : handRankingMap.keySet()) {
            for (poker.base.enums.HandRanking handRankingEnum2 : handRankingMap.keySet()) {
                HandRanking handRanking1 = handRankingMap.get(handRankingEnum1);
                HandRanking handRanking2 = handRankingMap.get(handRankingEnum2);

                int expected = handRankingEnum1.compareTo(handRankingEnum2);
                int actual = handRanking1.compareTo(handRanking2);

                logger.debug("handRanking1: {}, handRanking2: {}, expected: {}, actual: {}, success: {}",
                        handRankingEnum1, handRankingEnum2, expected, actual, expected == actual);

                assertEquals(expected, actual);
            }
        }



    }

    private int adjustResult(int rawResult) {
        // adjust result to be 1, 0, or -1
        int result;
        if (rawResult > 0) {
            result = 1;
        } else if (rawResult < 0) {
            result = -1;
        } else {
            result = 0;
        }
        return result;
    }
}

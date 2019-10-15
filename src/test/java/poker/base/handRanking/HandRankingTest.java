package poker.base.handRanking;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import poker.base.Hand;

import static org.junit.Assert.assertEquals;

public class HandRankingTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Object[][] testCases = {
            {"as,ks,qs,js,10s", "ad,kd,qd,jd,10d", 0},
    };

    @Test
    public void testHandRanking_compareTo() {
        for (Object[] testCase : testCases) {
            String shortCodes1 = (String) testCase[0];
            String shortCodes2 = (String) testCase[1];
            int expectedResult = (int) testCase[2];

            Hand hand1 = Hand.fromShortCodes(shortCodes1);
            HandRanking handRanking1 = hand1.getHandRanking();

            Hand hand2 = Hand.fromShortCodes(shortCodes2);
            HandRanking handRanking2 = hand2.getHandRanking();

            int actualResult = handRanking1.compareTo(handRanking2);

            // adjust result to be 1, 0, or -1 
            if (actualResult > 0) {
                actualResult = 1;
            } else if (actualResult < 0) {
                actualResult = -1;
            }

            logger.info("hand1: {}\thand2: {}\texpectedResult: {}\tactualResult: {}\tsuccess: {}",
                    shortCodes1, shortCodes2, expectedResult, actualResult, expectedResult == actualResult);

            assertEquals(expectedResult, actualResult);
        }
    }
}

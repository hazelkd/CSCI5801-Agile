import org.junit.Test;
import static org.junit.Assert.*;

public class TestVotingSystem {
    private VotingSystem votingSys;

    @Test
    Public void testCoinToss() {
        votingSys = new VotingSystem();

        int result = votingSys.coinToss(-5);
        assertEquals(result, -1);
        result = 10;

        result = votingSys.coinToss(0);
        assertEquals(result, -1);

        for (int i = 0; i < 26; i++) {
            result = votingSys.coinToss(13);
            assertTrue("Random Number too High", result < 13);
            assertTrue("Random Number too Low", result >= 0);
        }
    }

}
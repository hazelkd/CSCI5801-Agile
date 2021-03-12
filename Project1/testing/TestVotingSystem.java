import org.junit.Test;
import static org.junit.Assert.*;

public class TestVotingSystem {
    private VotingSystem votingSys;

    @Test
    public void testCoinToss() {
        votingSys = new VotingSystem();

        // Test with negetive number input
        int result = votingSys.coinToss(-5);
        assertEquals(result, -1);
        result = 10;

        // Test with 0 as input
        result = votingSys.coinToss(0);
        assertEquals(result, -1); 
        
        // ensure the return value is with in the bounds 
        for (int i = 0; i < 100; i++) {
            result = votingSys.coinToss(13);
            assertTrue("Random Number too High", result < 13);
            assertTrue("Random Number too Low", result >= 0);
        }
    }

}
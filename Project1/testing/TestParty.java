import org.junit.Test;
import static org.junit.Assert.*;

public class PartyTest {

    private Candidate candidate1;
    private Candidate candidate2;
    private Candidate candidate3;
    private Candidate candidate4;
    private Party bestParty;

    @Test
    public void testAddCandidate() {
        bestParty = new Party("P");
        candidate1 = new Candidate("name", "P");
        candidate2 = new Candidate("name", "P");
        candidate3 = null;
        candidate4 = new Candidate("name", "notP");

        // General Case
        bestParty.addCandidate(candidate1);
        bestParty.addCandidate(candidate2);
        assertEquals(bestParty.getCandidates().length(), 2); // add two regular candidate objects

        // Insert Null Case
        bestParty.addCandidate(candidate3);
        assertEquals(bestParty.getCandidates().length(), 2); // should NOT have added the null candidate

        // Insert Candidate without a matching party
        
    }

}
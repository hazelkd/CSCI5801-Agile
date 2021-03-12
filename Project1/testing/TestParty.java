import org.junit.Test;
import static org.junit.Assert.*;

public class TestParty {

    @Test
    public void testAddCandidate() {
        Candidate candidate1;
        Candidate candidate2;
        Party bestParty;

        bestParty = new Party("P");
        candidate1 = new Candidate("name", "P");
        candidate2 = new Candidate("name", "P");

        bestParty.addCandidate(candidate1);
        bestParty.addCandidate(candidate2);

        assertEquals(bestParty.getCandidates().size(), 2);
    }
}
import org.junit.Test;
import static org.junit.Assert.*;

public class PartyTest {

    private Candidate candidate1;
    private Candidate candidate2;
    private Party bestParty;

    @Test
    public void testAddCandidate() {
        bestParty = new Party("P");
        candidate1 = new Candidate("name", "P");
        candidate2 = new Candidate("name", "P");

        bestParty.addCandiate(candidate1);
        bestParty.addCandiate(candidate2);

        assertEquals(bestParty.getCandidates().length(), 2);
    }

}
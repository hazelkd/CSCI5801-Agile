import org.junit.Test;
import static org.junit.Assert.*;

public class TestCandidate {
    private Ballot ballot1;
    private Ballot ballot2;
    private Ballot ballot3;
    private Ballot ballot4 = null;
    private Candidate candidate;

    @Test
    public void testAddBallot() {
        //dont know if i should make an example ballot to test this?
        ballot1 = new Ballot();
        ballot2 = new Ballot();
        ballot3 = new Ballot();
        ballot1.setID(001);
        ballot2.setID(002);
        ballot3.setID(003);

        assertEquals(ballot1.getID(), 001);
        assertEquals(ballot2.getID(), 002);
        assertEquals(ballot3.getID(), 003);
        assertNull(ballot4);

        assertTrue(ballot1.getID() != 0);
        assertTrue(ballot2.getID() != 0);
        assertTrue(ballot3.getID() != 0);

        candidate = new Candidate("name", "p");
        candidate.addBallot(ballot1);
        candidate.addBallot(ballot2);

        assertEquals(candidate.getcBallots().get(0), ballot1);
        assertEquals(candidate.getcBallots().get(1), ballot2);
        assertEquals(candidate.getcNumBallots(), 2);

        candidate.addBallot(ballot4);
        assertEquals(candidate.getcBallots().get(0), ballot1);
        assertEquals(candidate.getcBallots().get(1), ballot2);
        assertEquals(candidate.getcNumBallots(), 2);

        candidate.addBallot(ballot3);
        assertEquals(candidate.getcBallots().get(0), ballot1);
        assertEquals(candidate.getcBallots().get(1), ballot2);
        assertEquals(candidate.getcBallots().get(2), ballot3);
        assertEquals(candidate.getcNumBallots(), 3);

    }
}
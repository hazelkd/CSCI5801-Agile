import org.junit.Test;

import jdk.jfr.Timestamp;

import static org.junit.Assert.*;

public class TestBallot {

    @Test
    public void testAdd() {
        String str = "Junit is working fine";
        assertEquals("Junit is working fine",str);
    }

    private Ballot ballot1;
    private Ballot ballot2;
    private Ballot ballot3;
    private Ballot ballot4 = null;
    private Candidate candidate;

    @Test
    public void testAddBallot() {
        //dont know if i should make an example ballot to test this?
        ballot1.setID(001);
        ballot2.setID(002);
        ballot3.setID(003);
        
        assertEquals(ballot1.getID(), 001);
        assertEquals(ballot2.getID(), 002);
        assertEquals(ballot3.getID(), 003);
        assertEquals(ballot4.getID(), NULL);

        assertTrue(ballot1.getID() != NULL);
        assertTrue(ballot2.getID() != NULL);
        assertTrue(ballot3.getID() != NULL);

        candidate.addBallot(ballot1);
        candidate.addBallot(ballot2);

        assertEquals(candidate.getcBallots(), ballot1, ballot2);
        assertEquals(candidate.getcNumBallots(), 2);

        candidate.addBallot(ballot4);
        assertEquals(candidate.getcBallots(), ballot1, ballot2);
        assertEquals(candidate.getcNumBallots(), 2);

        candidate.addBallot(ballot3);
        assertEquals(candidate.getcBallots(), ballot1, ballot2, ballot3);
        assertEquals(candidate.getcNumBallots(), 3);



    }

}
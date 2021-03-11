import org.junit.Test;

import jdk.jfr.Timestamp;

import org.junit.Test;

import jdk.jfr.Timestamp;

import static org.junit.Assert.*;

public class TestOPLElection {

    private OPLElection election;

    @Test

    //want to use OPL file with this one too but not sure how
    public void testAllocateByQuota() {

        election.setTotalNumSeats(3);
        election.setNumSeatsLeft(3);
        ArrayList<Party> party = {"D", "R", "I"};
        election.setParty(party);
        election.setTotalNumBallots(9);
        election.setQuota(election.getTotalNumBallots()/election.getTotalNumSeats());
        election.getParty.get(0).setpNumBallots(5);
        election.getParty.get(1).setpNumBallots(3);
        election.getParty.get(2).setpNumBallots(1);

        //checking that there is at least one party
        assertTrue(election.getParty().size() >= 1);
        //checking that quota is not zero
        assertTrue(election.getQuota() > 0);

        //checking results with file - still not positive about this
        election.allocateByQuota();
        //checking party results from allocate by quota
        assertEquals(election.getParty.get(0).getNumSeats, 1); //dont know right syntax
        assertEquals(election.getParty.get(1).getNumSeats, 1);
        assertEquals(election.getParty.get(2).getNumSeats, 0);

        assertEquals(election.getParty.get(0).getRemainder, 2);
        assertEquals(election.getParty.get(1).getRemainder, 0);
        assertEquals(election.getParty.get(2).getRemainder, 1);   

    }

    @Test

    public void testAllocateByRemainder() {
        election.setTotalNumSeats(3);
        election.setNumSeatsLeft(3);
        ArrayList<Party> party = {"D", "R", "I"};
        election.setParty(party);
        election.setTotalNumBallots(9);
        election.setQuota(election.getTotalNumBallots()/election.getTotalNumSeats());
        election.getParty.get(0).setpNumBallots(5);
        election.getParty.get(1).setpNumBallots(3);
        election.getParty.get(2).setpNumBallots(1);

        assertTrue(election.getParty.get(0).getRemainder != NULL);
        assertTrue(election.getParty.get(1).getRemainder != NULL);
        assertTrue(election.getParty.get(2).getRemainder != NULL);

        assertTrue(election.getTotalNumSeats() != 0);
        
        election.allocateByRemainder();

        assertEquals(election.getParty.get(0).getNumSeats, 2);
        assertEquals(election.getParty.get(1).getNumSeats, 1);
        assertEquals(election.getParty.get(2).getNumSeats, 0);
    }

}
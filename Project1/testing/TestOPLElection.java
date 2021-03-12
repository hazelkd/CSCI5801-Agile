import org.junit.AfterClass;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TestOPLElection {
    // for restoration
    public static InputStream systemIn = System.in;
    public static PrintStream systemOut = System.out;

    public ByteArrayOutputStream testOut;

    public void provideInput(String data) {
        testOut = new ByteArrayOutputStream();
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        System.setOut(new PrintStream(testOut));
    }

    public String getOutput() {
        return testOut.toString();
    }

    @AfterClass
    public static void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    // readOPLCSV tests
    /**
     * Test Cases:
     * 2nd line (numCandidates) is valid/invalid
     * 3rd line (candidate list) is valid/invalid (not in correct format)
     * Length of list in 3rd line does not match length indicated in 2nd line
     * 4th line (numSeats) is valid/invalid
     * 5th line (numVotes) is valid/invalid
     * Make sure no divide by 0 when calculating quota
     */

    @Test
    public void testReadOPLCSVValidInput(){
        String data = "OPLTest\n";
        provideInput(data);
        OPLElection sys = (OPLElection) VotingSystem.promptCSV();
        assert sys != null;
        sys.readOPLCSV();
        // check totalNumBallots totalNumSeats, numSeatsLeft, quota
        assertEquals("Incorrect totalNumBallots", 9, sys.getTotalNumBallots());
        assertEquals("Incorrect totalNumSeats", 3, sys.getTotalNumSeats());
        assertEquals("Incorrect numSeatsLeft", 3, sys.getNumSeatsLeft());
        assertEquals("Incorrect quota", (9/3), sys.getQuota());

        // creates party and candidate objects (makes sure that lists are right length)
        assertEquals("Incorrect number of Parties", 3, sys.getParty().size());
        assertEquals("Incorrect number of Candidates", 6, sys.getCandidates().size());
        //first candidate should be [Pike,D], first party is "D"
        assertEquals("Incorrect [Candidate, Party] format, for Candidate Object", "Pike",
                sys.getCandidates().get(0).getcName());
        assertEquals("Incorrect [Candidate, Party] format, for Party Object", "D",
                sys.getParty().get(0).getpName());
    }

    @Test
    public void testReadOPLCSVInvalidSecondLine(){
        String data = "OPLInvTest2\n";
        provideInput(data);
        OPLElection sys = (OPLElection) VotingSystem.promptCSV();
        if(sys != null){
            sys.readOPLCSV();
            // check totalNumBallots totalNumSeats, numSeatsLeft, quota
            // none should be set
            assertEquals("Incorrect totalNumBallots", 0, sys.getTotalNumBallots());
            assertEquals("Incorrect totalNumSeats", 0, sys.getTotalNumSeats());
            assertEquals("Incorrect numSeatsLeft", 0, sys.getNumSeatsLeft());
            assertEquals("Incorrect quota", 0, sys.getQuota());

            // creates party and candidate objects (makes sure that lists are right length)
            // none should be initialized
            assertNull("Incorrect number of Parties", sys.getParty());
            assertNull("Incorrect number of Candidates", sys.getCandidates());
        } else {
            assertNotNull("Testing data not present", sys);
        }
    }

    @Test
    public void testReadOPLCSVInvalidThirdLine() {
        String data = "OPLInvTest3\n";
        provideInput(data);
        OPLElection sys = (OPLElection) VotingSystem.promptCSV();
        if (sys != null) {
            sys.readOPLCSV();
            // check totalNumBallots totalNumSeats, numSeatsLeft, quota
            // none should be set
            assertEquals("Incorrect totalNumBallots", 0, sys.getTotalNumBallots());
            assertEquals("Incorrect totalNumSeats", 0, sys.getTotalNumSeats());
            assertEquals("Incorrect numSeatsLeft", 0, sys.getNumSeatsLeft());
            assertEquals("Incorrect quota", 0, sys.getQuota());

            // creates party and candidate objects (makes sure that lists are right length)
            // should not be initialized
            assertNull("Incorrect number of Parties", sys.getParty());
            assertNull("Incorrect number of Candidates", sys.getCandidates());
        } else {
            assertNotNull("Testing data not present", sys);
        }
    }

    @Test
    public void testReadOPLCSVCandidateListWrongLength() {
        String data = "OPLInvTest3Length\n";
        provideInput(data);
        OPLElection sys = (OPLElection) VotingSystem.promptCSV();
        if (sys != null) {
            sys.readOPLCSV();
            // check totalNumBallots totalNumSeats, numSeatsLeft, quota
            // none should be set
            assertEquals("Incorrect totalNumBallots", 0, sys.getTotalNumBallots());
            assertEquals("Incorrect totalNumSeats", 0, sys.getTotalNumSeats());
            assertEquals("Incorrect numSeatsLeft", 0, sys.getNumSeatsLeft());
            assertEquals("Incorrect quota", 0, sys.getQuota());

            // creates party and candidate objects (makes sure that lists are right length)
            // Both should be initialized -> size of candidate list != numCandidates
            assertNull("Incorrect number of Parties", sys.getParty());
            assertNull("Incorrect number of Candidates", sys.getCandidates());
        } else {
            assertNotNull("Testing data not present", sys);
        }
    }

    @Test
    public void testReadOPLCSVInvalidFourthLine(){ // numSeats
        String data = "OPLInvTest4\n";
        provideInput(data);
        OPLElection sys = (OPLElection) VotingSystem.promptCSV();
        if (sys != null) {
            sys.readOPLCSV();
            // check totalNumBallots totalNumSeats, numSeatsLeft, quota
            // none should be set
            assertEquals("Incorrect totalNumBallots", 0, sys.getTotalNumBallots());
            assertEquals("Incorrect totalNumSeats", 0, sys.getTotalNumSeats());
            assertEquals("Incorrect numSeatsLeft", 0, sys.getNumSeatsLeft());
            assertEquals("Incorrect quota", 0, sys.getQuota());

            // creates party and candidate objects (makes sure that lists are right length)
            // Both should be initialized, need to check names of first input
            assertEquals("Incorrect number of Parties", 3, sys.getParty().size());
            assertEquals("Incorrect number of Candidates", 6, sys.getCandidates().size());
            //first candidate should be [Pike,D], first party is "D"
            assertEquals("Incorrect [Candidate, Party] format, for Candidate Object", "Pike",
                    sys.getCandidates().get(0).getcName());
            assertEquals("Incorrect [Candidate, Party] format, for Party Object", "D",
                    sys.getParty().get(0).getpName());
        } else {
            assertNotNull("Testing data not present", sys);
        }
    }

    @Test
    public void testReadOPLCSVInvalidFifthLine(){ // numBallots
        String data = "OPLInvTest5\n";
        provideInput(data);
        OPLElection sys = (OPLElection) VotingSystem.promptCSV();
        if (sys != null) {
            sys.readOPLCSV();
            // check totalNumBallots totalNumSeats, numSeatsLeft, quota
            // totalNumSeats and numSeatsLeft should be set
            assertEquals("Incorrect totalNumBallots", 0, sys.getTotalNumBallots());
            assertEquals("Incorrect totalNumSeats", 3, sys.getTotalNumSeats());
            assertEquals("Incorrect numSeatsLeft", 3, sys.getNumSeatsLeft());
            assertEquals("Incorrect quota", 0, sys.getQuota());

            // creates party and candidate objects (makes sure that lists are right length)
            // Both should be initialized (and correct)
            assertEquals("Incorrect number of Parties", 3, sys.getParty().size());
            assertEquals("Incorrect number of Candidates", 6, sys.getCandidates().size());
            //first candidate should be [Pike,D], first party is "D"
            assertEquals("Incorrect [Candidate, Party] format, for Candidate Object", "Pike",
                    sys.getCandidates().get(0).getcName());
            assertEquals("Incorrect [Candidate, Party] format, for Party Object", "D",
                    sys.getParty().get(0).getpName());
        } else {
            assertNotNull("Testing data not present", sys);
        }
    }

    @Test
    public void testReadOPLCSVQuotaDivByZero(){
        String data = "OPLInvTestDivByZero\n";
        provideInput(data);
        OPLElection sys = (OPLElection) VotingSystem.promptCSV();
        if (sys != null) {
            sys.readOPLCSV();
            // check totalNumBallots totalNumSeats, numSeatsLeft, quota
            // totalNumSeats and numSeatsLeft should be set
            assertEquals("Incorrect totalNumBallots", 9, sys.getTotalNumBallots());
            assertEquals("Incorrect totalNumSeats", 0, sys.getTotalNumSeats());
            assertEquals("Incorrect numSeatsLeft", 0, sys.getNumSeatsLeft());
            assertEquals("Incorrect quota", 0, sys.getQuota());

            // creates party and candidate objects (makes sure that lists are right length)
            // Both should be initialized (and correct)
            assertEquals("Incorrect number of Parties", 3, sys.getParty().size());
            assertEquals("Incorrect number of Candidates", 6, sys.getCandidates().size());
            //first candidate should be [Pike,D], first party is "D"
            assertEquals("Incorrect [Candidate, Party] format, for Candidate Object", "Pike",
                    sys.getCandidates().get(0).getcName());
            assertEquals("Incorrect [Candidate, Party] format, for Party Object", "D",
                    sys.getParty().get(0).getpName());
        } else {
            assertNotNull("Testing data not present", sys);
        }
    }
    // end of readOPLCSV tests

    // readBallots tests
    /**
     * Test Cases:
     * Ballot is in incorrect format
     */

    @Test
    public void testReadBallotsValidInput(){
        String data = "OPLTest\n";
        provideInput(data);
        OPLElection sys = (OPLElection) VotingSystem.promptCSV();
        if (sys != null) {
            sys.readOPLCSV();
            // check totalNumBallots totalNumSeats, numSeatsLeft, quota
            // totalNumSeats and numSeatsLeft should be set
            assertEquals("Incorrect totalNumBallots", 9, sys.getTotalNumBallots());
            assertEquals("Incorrect totalNumSeats", 3, sys.getTotalNumSeats());
            assertEquals("Incorrect numSeatsLeft", 3, sys.getNumSeatsLeft());
            assertEquals("Incorrect quota", 3, sys.getQuota());

            // creates party and candidate objects (makes sure that lists are right length)
            // Both should be initialized (and correct)
            assertEquals("Incorrect number of Parties", 3, sys.getParty().size());
            assertEquals("Incorrect number of Candidates", 6, sys.getCandidates().size());
            //first candidate should be [Pike,D], first party is "D"
            assertEquals("Incorrect [Candidate, Party] format, for Candidate Object", "Pike",
                    sys.getCandidates().get(0).getcName());
            assertEquals("Incorrect [Candidate, Party] format, for Party Object", "D",
                    sys.getParty().get(0).getpName());

            sys.readBallots();
            // should create 9 ballots
            assertEquals("Incorrect number of ballots created", 9, sys.getTotalNumBallots());
            assertEquals("Incorrect number of ballots assigned", 9,
                            (sys.getCandidates().get(0).getcNumBallots() +
                            sys.getCandidates().get(1).getcNumBallots() +
                            sys.getCandidates().get(2).getcNumBallots() +
                            sys.getCandidates().get(3).getcNumBallots() +
                            sys.getCandidates().get(4).getcNumBallots() +
                            sys.getCandidates().get(5).getcNumBallots()));
        } else {
            assertNotNull("Testing data not present", sys);
        }
    }

    @Test
    public void testReadBallotsInvalidInput(){
        String data = "OPLTestInvalid\n";
        provideInput(data);
        OPLElection sys = (OPLElection) VotingSystem.promptCSV();
        if (sys != null) {
            sys.readOPLCSV();
            // check totalNumBallots totalNumSeats, numSeatsLeft, quota
            // totalNumSeats and numSeatsLeft should be set
            assertEquals("Incorrect totalNumBallots", 9, sys.getTotalNumBallots());
            assertEquals("Incorrect totalNumSeats", 0, sys.getTotalNumSeats());
            assertEquals("Incorrect numSeatsLeft", 0, sys.getNumSeatsLeft());
            assertEquals("Incorrect quota", 0, sys.getQuota());

            // creates party and candidate objects (makes sure that lists are right length)
            // Both should be initialized (and correct)
            assertEquals("Incorrect number of Parties", 3, sys.getParty().size());
            assertEquals("Incorrect number of Candidates", 6, sys.getCandidates().size());
            //first candidate should be [Pike,D], first party is "D"
            assertEquals("Incorrect [Candidate, Party] format, for Candidate Object", "Pike",
                    sys.getCandidates().get(0).getcName());
            assertEquals("Incorrect [Candidate, Party] format, for Party Object", "D",
                    sys.getParty().get(0).getpName());

            sys.readBallots();
            // should create 9 ballots
            assertEquals("Incorrect number of ballots created", 0, sys.getTotalNumBallots());
            assertEquals("Incorrect number of ballots assigned", 0,
                    (sys.getCandidates().get(0).getcNumBallots() +
                            sys.getCandidates().get(1).getcNumBallots() +
                            sys.getCandidates().get(2).getcNumBallots() +
                            sys.getCandidates().get(3).getcNumBallots() +
                            sys.getCandidates().get(4).getcNumBallots() +
                            sys.getCandidates().get(5).getcNumBallots() +
                            sys.getCandidates().get(6).getcNumBallots() +
                            sys.getCandidates().get(7).getcNumBallots() +
                            sys.getCandidates().get(8).getcNumBallots()));
        } else {
            assertNotNull("Testing data not present", sys);
        }
    }
    // end of readBallots tests

    // printToScreen tests
    // Test Cases: set all values manually and check that each value prints correctly
    @Test
    public void testPrintToScreen(){
        // need parties (pName, numSeats, candidates) and candidates (name) for printing
        // set up
        OPLElection sys = new OPLElection();
        Party p1 = new Party("D");
        p1.setNumSeats(3);
        // create candidates and put in ArrayList
        ArrayList<Candidate> c1 = new ArrayList<>();
        c1.add(new Candidate("name1", "D"));
        c1.add(new Candidate("name2", "D"));
        c1.add(new Candidate("name3", "D"));
        p1.setCandidates(c1);

        Party p2 = new Party("R");
        p2.setNumSeats(1);
        // create candidates and put in ArrayList
        ArrayList<Candidate> c2 = new ArrayList<>();
        c2.add(new Candidate("name1", "R"));
        p2.setCandidates(c2);

        // put parties in arrayList
        ArrayList<Party> p = new ArrayList<>(2);
        p.add(p1);
        p.add(p2);
        sys.setParty(p);

        // testing
        sys.printToScreen();
        String expectOut = "------------------------------\n";
        expectOut += "Election Results\n";
        expectOut += "------------------------------\n";
        expectOut += "D \n";
        expectOut += "  Number of Seats Won: 3\n";
        expectOut += "  Candidates filling seats: \n";
        expectOut += "     name1\n";
        expectOut += "     name2\n";
        expectOut += "     name3\n";
        expectOut += "------------------------------\n";
        expectOut += "R \n";
        expectOut += "  Number of Seats Won: 1\n";
        expectOut += "  Candidates filling seats: \n";
        expectOut += "     name1\n";
        expectOut += "------------------------------\n";

        assertEquals("Incorrect Output", expectOut, getOutput());
    }
    // end of printToScreen tests

    // writeToMediaFile tests
    // Test Cases: set all values manually and check that each value prints correctly
    @Test
    public void testWriteToMediaFile(){
        // set up
        OPLElection sys = new OPLElection();
        sys.setTotalNumBallots(15);
        Party p1 = new Party("D");
        p1.setNumSeats(3);
        // create candidates and put in ArrayList
        ArrayList<Candidate> c1 = new ArrayList<>();
        c1.add(new Candidate("name1", "D"));
        c1.add(new Candidate("name2", "D"));
        c1.add(new Candidate("name3", "D"));
        p1.setCandidates(c1);

        Party p2 = new Party("R");
        p2.setNumSeats(1);
        // create candidates and put in ArrayList
        ArrayList<Candidate> c2 = new ArrayList<>();
        c2.add(new Candidate("name1", "R"));
        p2.setCandidates(c2);

        // put parties in arrayList
        ArrayList<Party> p = new ArrayList<>(2);
        p.add(p1);
        p.add(p2);
        sys.setParty(p);

        // testing
        sys.writeToMediaFile();
        String expectOut = "Election Results\n";
        expectOut += "------------------------------\n";
        expectOut += "D\n";
        expectOut += "  Number of Seats Won: 3\n";
        expectOut += "  Candidates filling seats: \n";
        double percent = (((double) p1.getCandidates().get(0).getcNumBallots()) / (15)) * 100;
        expectOut += "     name1 with " + percent + "% of the vote\n";
        percent = (((double) p1.getCandidates().get(1).getcNumBallots()) / (15)) * 100;
        expectOut += "     name2 with " + percent + "% of the vote\n";
        percent = (((double) p1.getCandidates().get(2).getcNumBallots()) / (15)) * 100;
        expectOut += "     name3 with " + percent + "% of the vote\n";
        expectOut += "------------------------------\n";
        expectOut += "R\n";
        expectOut += "  Number of Seats Won: 1\n";
        expectOut += "  Candidates filling seats: \n";
        percent = (((double) p2.getCandidates().get(0).getcNumBallots()) / (15)) * 100;
        expectOut += "     name1 with " + percent + "% of the vote\n";

        assertEquals("Incorrect Output", expectOut, getOutput());
    }
    // end of writeToMediaFile tests

    // writeToAuditFile tests
    // Test Cases: set all values manually and check that each value prints correctly
    @Test
    public void testWriteToAuditFile(){
        // set up
        // need party (name, numSeats) and candidate (numBallots, name, ballots)
        // ballot (id, bCandidate, party)
        OPLElection sys = new OPLElection();
        sys.setTotalNumBallots(4);
        // one ballot to each candidate
        Party p1 = new Party("D");
        p1.setNumSeats(3);
        // create candidates and put in ArrayList
        ArrayList<Candidate> c1 = new ArrayList<>();
        c1.add(new Candidate("name1", "D"));
        c1.add(new Candidate("name2", "D"));
        c1.add(new Candidate("name3", "D"));
        p1.setCandidates(c1);

        Party p2 = new Party("R");
        p2.setNumSeats(1);
        // create candidates and put in ArrayList
        ArrayList<Candidate> c2 = new ArrayList<>();
        c2.add(new Candidate("name1", "R"));
        p2.setCandidates(c2);
      
        // put parties in arrayList
        ArrayList<Party> p = new ArrayList<>(2);
        p.add(p1);
        p.add(p2);
        sys.setParty(p);

        // testing
        sys.writeToAuditFile();
        String expectOut = "";
        assertEquals("Incorrect Output", expectOut, getOutput());
    }
    // end of writeToAuditFile tests
  
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
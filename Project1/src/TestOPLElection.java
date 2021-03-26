import org.junit.AfterClass;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TestOPLElection {
    private Candidate candidate1;
    private Candidate candidate2;
    private Candidate candidate3;
    private Candidate candidate4;
    private Candidate candidate5;
    private Candidate candidate6; 

    private Party bestParty;
    private Party okayestParty;
    private Party partyRock;

    private Ballot ballotA;
    private Ballot ballotB;
    private Ballot ballotC;
    private Ballot ballotD;
    private Ballot ballotE;
    private Ballot ballotF;
    private Ballot ballotG;
    private Ballot ballotH;
    private Ballot ballotI;
    private Ballot ballotJ;
    private Ballot ballotK;
    private Ballot ballotL;
    private Ballot ballotM;

    private OPLElection oplElection;

    
    // for restoration
    public static InputStream systemIn = System.in;
    public static PrintStream systemOut = System.out;

    public ByteArrayOutputStream testOut;
    public ByteArrayInputStream testIn;

    public void provideInput(String data) {
        testOut = new ByteArrayOutputStream();
        testIn = new ByteArrayInputStream(data.getBytes());
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
            assertEquals("Incorrect totalNumBallots", 0, sys.getTotalNumBallots());
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
            // no ballots should be created
            assertEquals("Incorrect number of ballots created", 9, sys.getTotalNumBallots());
            assertEquals("Incorrect number of ballots assigned", 0,
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
    // end of readBallots tests

    // printToScreen tests
    // Test Cases: set all values manually and check that each value prints correctly
    @Test
    public void testPrintToScreen(){
        // need parties (pName, numSeats, candidates) and candidates (name) for printing
        // set up
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

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
        expectOut += "D\n";
        expectOut += "  Number of Seats Won: 3\n";
        expectOut += "  Candidates filling seats: \n";
        expectOut += "     name1\n";
        expectOut += "     name2\n";
        expectOut += "     name3\n";
        expectOut += "------------------------------\n";
        expectOut += "R\n";
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
        try {
            sys.setMediaFile(new PrintWriter("OPLMediaTestFile.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open test file");
            return;
        }

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

        String mediaFile = null;
        try {
            mediaFile = Files.readString(Path.of("OPLMediaTestFile.txt"));
        } catch (IOException e) {
            System.out.println("Unable to read test file");
        }
        assertEquals("Incorrect Output", expectOut, mediaFile);
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
        try {
            sys.setAuditFile(new PrintWriter("OPLAuditTestFile.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open test file");
        }

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

        // create and assign ballots
        OPLBallot b0 = new OPLBallot(0, "D", c1.get(0));
        c1.get(0).addBallot(b0);
        OPLBallot b1 = new OPLBallot(1, "D", c1.get(1));
        c1.get(1).addBallot(b1);
        OPLBallot b2 = new OPLBallot(2, "D", c1.get(2));
        c1.get(2).addBallot(b2);
        OPLBallot b3 = new OPLBallot(3, "R", c2.get(0));
        c2.get(0).addBallot(b3);

        // testing
        sys.writeToAuditFile();
        String expectOut = "Party: D\n";
        expectOut += "  Number of Seats Won: 3\n";
        expectOut += "  Candidates filling seats: \n";
        double percent = (((double) p1.getCandidates().get(0).getcNumBallots()) / (4)) * 100;
        expectOut += "     name1 with " + percent + "% of the vote\n";
        expectOut += "     Ballots assigned to this candidate: \n";
        expectOut += "        0: name1, D\n";
        percent = (((double) p1.getCandidates().get(1).getcNumBallots()) / (4)) * 100;
        expectOut += "     name2 with " + percent + "% of the vote\n";
        expectOut += "     Ballots assigned to this candidate: \n";
        expectOut += "        1: name2, D\n";
        percent = (((double) p1.getCandidates().get(2).getcNumBallots()) / (4)) * 100;
        expectOut += "     name3 with " + percent + "% of the vote\n";
        expectOut += "     Ballots assigned to this candidate: \n";
        expectOut += "        2: name3, D\n";
        expectOut += "\n";
        expectOut += "Party: R\n";
        expectOut += "  Number of Seats Won: 1\n";
        expectOut += "  Candidates filling seats: \n";
        percent = (((double) p2.getCandidates().get(0).getcNumBallots()) / (4)) * 100;
        expectOut += "     name1 with " + percent + "% of the vote\n";
        expectOut += "     Ballots assigned to this candidate: \n";
        expectOut += "        3: name1, R\n";
        expectOut += "\n";

        String auditFile = null;
        try {
            auditFile = Files.readString(Path.of("OPLAuditTestFile.txt"));
        } catch (IOException e) {
            System.out.println("Unable to read test file");
        }
        assertEquals("Incorrect Output", expectOut, auditFile);
    }
    // end of writeToAuditFile tests
  
    private OPLElection election;

    @Test

    //want to use OPL file with this one too but not sure how
    public void testAllocateByQuota() {
        election = new OPLElection();
        election.setTotalNumSeats(3);
        election.setNumSeatsLeft(3);
        ArrayList<Party> party = new ArrayList<>();
        //{"D", "R", "I"};
        party.add(new Party("D"));
        party.add(new Party("R"));
        party.add(new Party("I"));
        election.setParty(party);
        election.setTotalNumBallots(9);
        election.setQuota(election.getTotalNumBallots()/election.getTotalNumSeats());
        election.getParty().get(0).setpNumBallots(5);
        election.getParty().get(1).setpNumBallots(3);
        election.getParty().get(2).setpNumBallots(1);

        //checking that there is at least one party
        assertTrue(election.getParty().size() >= 1);
        //checking that quota is not zero
        assertTrue(election.getQuota() > 0);

        //checking results with file - still not positive about this
        election.allocateByQuota();
        //checking party results from allocate by quota
        assertEquals(election.getParty().get(0).getNumSeats(), 1); //dont know right syntax
        assertEquals(election.getParty().get(1).getNumSeats(), 1);
        assertEquals(election.getParty().get(2).getNumSeats(), 0);

        assertEquals(election.getParty().get(0).getRemainder(), 2);
        assertEquals(election.getParty().get(1).getRemainder(), 0);
        assertEquals(election.getParty().get(2).getRemainder(), 1);

    }

    @Test
    public void testAllocateByRemainder() {
        election = new OPLElection();
        election.setTotalNumSeats(3);
        election.setNumSeatsLeft(3);
        ArrayList<Party> party = new ArrayList<>();
        // {"D", "R", "I"};
        party.add(new Party("D"));
        party.add(new Party("R"));
        party.add(new Party("I"));
        election.setParty(party);
        election.setTotalNumBallots(9);
        election.setQuota(election.getTotalNumBallots()/election.getTotalNumSeats());
        election.getParty().get(0).setpNumBallots(5);
        election.getParty().get(1).setpNumBallots(3);
        election.getParty().get(2).setpNumBallots(1);

        assertNotNull(election.getParty().get(0).getRemainder());
        assertNotNull(election.getParty().get(1).getRemainder());
        assertNotNull(election.getParty().get(2).getRemainder());

        assertTrue(election.getTotalNumSeats() != 0);

        election.allocateByQuota();
        election.allocateByRemainder();

        assertEquals(election.getParty().get(0).getNumSeats(), 2);
        assertEquals(election.getParty().get(1).getNumSeats(), 1);
        assertEquals(election.getParty().get(2).getNumSeats(), 0);
    }

    @Test
    public void testPartyNumBallots() {
        OPLElection election = new OPLElection();

        bestParty = new Party("B");
        okayestParty = new Party("O");
        partyRock = new Party("P");

        ArrayList<Party> parties = new ArrayList<Party>(3);
        parties.add(bestParty);
        parties.add(okayestParty);
        parties.add(partyRock);
        election.setParty(parties); // add parties to the election array of political parties


        // 13 ballots in total
        ballotA = new Ballot();
        ballotB = new Ballot();
        ballotC = new Ballot();
        ballotD = new Ballot();
        ballotE = new Ballot();
        ballotF = new Ballot();
        ballotG = new Ballot();
        ballotH = new Ballot();
        ballotI = new Ballot();
        ballotJ = new Ballot();
        ballotK = new Ballot();
        ballotL = new Ballot();
        ballotM = new Ballot();

        // create candidates and add ballots to candidates
        candidate1 = new Candidate("one", "B");
        candidate1.addBallot(ballotA);
        candidate1.addBallot(ballotB);
        candidate1.addBallot(ballotC);
        candidate1.addBallot(ballotD);

        candidate2 = new Candidate("one", "B");
        candidate2.addBallot(ballotH);
        candidate2.addBallot(ballotJ);

        candidate3 = new Candidate("one", "B");
        candidate3.addBallot(ballotI);

        candidate4 = new Candidate("one", "P");
        candidate4.addBallot(ballotK);
        candidate4.addBallot(ballotM);

        candidate5 = new Candidate("one", "P");
        candidate5.addBallot(ballotE);
        candidate5.addBallot(ballotF);
        candidate5.addBallot(ballotG);
        candidate5.addBallot(ballotL);


        candidate6 = new Candidate("one", "B"); // zero ballots

        // add candidates to parties
        bestParty.addCandidate(candidate6);
        bestParty.addCandidate(candidate2);
        bestParty.addCandidate(candidate3);
        bestParty.addCandidate(candidate1);

        partyRock.addCandidate(candidate4);
        partyRock.addCandidate(candidate5);


        election.partyNumBallots();
        assertEquals("Zero Candidates mean zero ballots", okayestParty.getpNumBallots(), 0); // no candidates = no ballots

        // test total num ballots in each party is correct
        assertEquals("BestParty had 7 ballots", bestParty.getpNumBallots(), 7);
        assertEquals("PartyRock had 6 ballots", partyRock.getpNumBallots(), 6);

        // test order candidates by popularity in party is correct
        ArrayList<Candidate> bestPartyOrder = new ArrayList<Candidate>();
        bestPartyOrder.add(candidate1);
        bestPartyOrder.add(candidate2);
        bestPartyOrder.add(candidate3);
        bestPartyOrder.add(candidate6);

        // begin test caseID#016 step #5
        assertEquals(bestPartyOrder.get(0), bestParty.getCandidates().get(0));
        assertEquals(bestPartyOrder.get(1), bestParty.getCandidates().get(1));
        assertEquals(bestPartyOrder.get(2), bestParty.getCandidates().get(2));
        assertEquals(bestPartyOrder.get(3), bestParty.getCandidates().get(3));
        // end test caseID#016 step #5

        ArrayList<Candidate> partyRockOrder = new ArrayList<Candidate>();
        partyRockOrder.add(candidate5);
        partyRockOrder.add(candidate4);

        // begin test caseID#016 step #6
        assertEquals(partyRockOrder.get(0), partyRock.getCandidates().get(0));
        assertEquals(partyRockOrder.get(1), partyRock.getCandidates().get(1));
        // end test caseID#016 step #6
    }


    @Test
    public void testOPLRunElection() {
        String dataCSV = "OPLTest\n";
        provideInput(dataCSV);
        oplElection = (OPLElection) VotingSystem.promptCSV();

        String dataAudit = "testAudit1\nY";
        provideInput(dataAudit);
        oplElection.promptAudit();

        String dataMedia = "testMedia1\nY";
        provideInput(dataMedia);
        oplElection.promptMedia();

        oplElection.runElection();

        // everything in VotingSystem was set correctly...
        assertEquals(6, oplElection.getCandidates().size());
        assertEquals("OPL", oplElection.getElectionType());
        assertEquals("OPLTest", oplElection.getCsvName());
        assertEquals(9, oplElection.getTotalNumBallots());

        // everything in OPLElection was correctly...
        assertEquals(3, oplElection.getTotalNumSeats());
        assertEquals(0, oplElection.getNumSeatsLeft());
        assertEquals(3, oplElection.getParty().size());
        assertEquals((9/3), oplElection.getQuota()); //numVotes/numOfSeats = quota

        // test the candidates, parties, and ballots were assigned and sorted correctly
        for(int i = 0; i< oplElection.getParty().size(); i++) {
            if (oplElection.getParty().get(i).equals("D")) {
                // party got all the candidates
                int numCandidates = oplElection.getParty().get(i).getCandidates().size();
                assertEquals(2, numCandidates);
                // party go the correct number of seats
                assertEquals(2, oplElection.getParty().get(i).getNumSeats());
                // party has the correct number of ballots
                assertEquals(5, oplElection.getParty().get(i).getpNumBallots());
            }
            if (oplElection.getParty().get(i).equals("R")) {
                // party got all the candidates
                int numCandidates = oplElection.getParty().get(i).getCandidates().size();
                assertEquals(2, numCandidates);
                // party go the correct number of seats
                assertEquals(1, oplElection.getParty().get(i).getNumSeats());
                // party has the correct number of ballots
                assertEquals(3, oplElection.getParty().get(i).getpNumBallots());
            }
            if (oplElection.getParty().get(i).equals("I")) {
                // party got all the candidates
                int numCandidates = oplElection.getParty().get(i).getCandidates().size();
                assertEquals(2, numCandidates);
                // party go the correct number of seats
                assertEquals(0, oplElection.getParty().get(i).getNumSeats());
                // party has the correct number of ballots
                assertEquals(1, oplElection.getParty().get(i).getpNumBallots());
            }
        }
    }

    // OPL System edge case tests
    @Test
    public void testOPLTiedHighestRemainder(){
        // tie between these two candidates, test how many times they are randomly given a seat
        int ferb = 0;
        int patrick = 0; 
        
        // run OPL election 500 times and record which candidate gets the last seat
        for(int i = 0; i < 500; i++) {
            String dataCSV = "OPLTiedHighestReminder\n";
            provideInput(dataCSV);
            oplElection = (OPLElection) VotingSystem.promptCSV();

            String dataAudit = "OPLAuditTiedR\nY";
            provideInput(dataAudit);
            oplElection.promptAudit();

            String dataMedia = "OPLMediaTiedR\nY";
            provideInput(dataMedia);
            oplElection.promptMedia();

            oplElection.runElection();

            // everything in VotingSystem was set correctly...
            assertEquals(4, oplElection.getCandidates().size());
            assertEquals("OPL", oplElection.getElectionType());
            assertEquals("OPLTiedHighestReminder", oplElection.getCsvName());
            assertEquals(12, oplElection.getTotalNumBallots());

            // everything in OPLElection was correctly...
            assertEquals(3, oplElection.getTotalNumSeats());
            assertEquals(0, oplElection.getNumSeatsLeft());
            assertEquals(2, oplElection.getParty().size());
            assertEquals((12/3), oplElection.getQuota()); //numVotes/numOfSeats = quota

            // test the candidates, parties, and ballots were assigned and sorted correctly
            for(int i = 0; i< oplElection.getParty().size(); i++) {
                if (oplElection.getParty().get(i).equals("D")) {
                    // party got all the candidates
                    int numCandidates = oplElection.getParty().get(i).getCandidates().size();
                    assertEquals(2, numCandidates);
                    // party go the correct number of seats
                    int numSeats = oplElection.getParty().get(i).getNumSeats();
                    assertTrue((numSeats == 2) || (numSeats == 1));
                    if (numSeats = 2) { ferb++; }
                    // party has the correct number of ballots
                    assertEquals(6, oplElection.getParty().get(i).getpNumBallots());
                }
                if (oplElection.getParty().get(i).equals("N")) {
                    // party got all the candidates
                    int numCandidates = oplElection.getParty().get(i).getCandidates().size();
                    assertEquals(2, numCandidates);
                    // party go the correct number of seats
                    int numSeats = oplElection.getParty().get(i).getNumSeats();
                    assertTrue((numSeats == 2) || (numSeats == 1));
                    if (numSeats = 2) { patrick++; }
                    // party has the correct number of ballots
                    assertEquals(6, oplElection.getParty().get(i).getpNumBallots());
                }
            }
        }
        // ensure that there is a 5% error 225-275
        assertTrue((225 < ferb) && (ferb < 275));
        assertTrue((225 < partick) && (patrick < 275));
    }

    @Test
    public void testOPLTieBetweenCandidates(){

    }
    // end of edge case tests
}
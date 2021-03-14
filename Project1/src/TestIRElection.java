import org.junit.AfterClass;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestIRElection {
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
  
    private Candidate candidate1;
    private Candidate candidate2;
    private Candidate candidate3;
    private Candidate candidate4;
    private IRElection election;
    private IRBallot ballot1;
    private IRBallot ballot2;
    private IRBallot ballot3;
    private IRBallot ballot4;
    private IRBallot ballot5;
    private IRBallot ballot6;

    @Test
    public void testFindMajority() {
        election = new IRElection();
        candidate1 = new Candidate("Rosen", "D");
        candidate2 = new Candidate("Kleinberg", "R");
        candidate3 = new Candidate("Chou"," I");
        candidate4 = new Candidate("Royce", "L");
        candidate1.setcNumBallots(3);
        candidate2.setcNumBallots(0);
        candidate3.setcNumBallots(2);
        candidate4.setcNumBallots(1);

        ArrayList<Candidate> temp = new ArrayList<Candidate>();
        temp.add(candidate1);
        temp.add(candidate2);
        temp.add(candidate3);
        temp.add(candidate4);
        ArrayList <Candidate> temp2 = new ArrayList<Candidate>();
        election.setCurrCandidates(temp);
        election.setEliminatedCandidates(temp2);
        election.setTotalNumBallots(6);

        assertEquals("Did not find null majority", election.findMajority(), null);

        candidate1.setcNumBallots(4);
        candidate3.setcNumBallots(1);

        assertEquals("Did not find real majority", election.findMajority().getcName(), "Rosen");

        temp.remove(candidate2);
        temp.remove(candidate3);
        temp.remove(candidate4);
        election.setCurrCandidates(temp);
        candidate1.setcNumBallots(3);

        assertEquals("Found majority with one candidate left even tho there is no majority", election.findMajority(), null);
        candidate1.setcNumBallots(4);
        assertEquals("Didn't find majority with one candidate left", election.findMajority().getcName(), "Rosen");

        //add tie?

    }

    @Test
    public void testFindLeastCand() {
        election = new IRElection();
        candidate1 = new Candidate("Rosen", "D");
        candidate2 = new Candidate("Kleinberg", "R");
        candidate3 = new Candidate("Chou"," I");
        candidate4 = new Candidate("Royce", "L");
        candidate1.setcNumBallots(3);
        candidate2.setcNumBallots(0);
        candidate3.setcNumBallots(2);
        candidate4.setcNumBallots(1);

        ArrayList<Candidate> temp = new ArrayList<Candidate>();
        temp.add(candidate1);
        temp.add(candidate2);
        temp.add(candidate3);
        temp.add(candidate4);
        ArrayList <Candidate> temp2 = new ArrayList<Candidate>();
        election.setCurrCandidates(temp);
        election.setEliminatedCandidates(temp2);
        election.setTotalNumBallots(6);

        assertEquals("Did not find correct least Candidate", election.findLeastCand().getcName(), "Kleinberg");

        temp2.add(candidate2);
        temp.remove(candidate2);
        election.setCurrCandidates(temp);
        election.setEliminatedCandidates(temp2);

        assertEquals("Did not find correct least Candidate", election.findLeastCand().getcName(), "Royce");

        temp2.add(candidate4);
        temp.remove(candidate4);
        election.setCurrCandidates(temp);
        election.setEliminatedCandidates(temp2);


        assertEquals("Did not find correct least Candidate", election.findLeastCand().getcName(), "Chou");

        temp2.add(candidate3);
        temp.remove(candidate3);
        election.setCurrCandidates(temp);
        election.setEliminatedCandidates(temp2);

        assertEquals("Did not find correct least Candidate", election.findLeastCand().getcName(), "Rosen");

        candidate1.setcNumBallots(4);
        candidate2.setcNumBallots(0);
        candidate3.setcNumBallots(0);
        candidate4.setcNumBallots(2);

        ArrayList<Candidate> temp3 = new ArrayList<Candidate>();
        temp3.add(candidate1);
        temp3.add(candidate2);
        temp3.add(candidate3);
        temp3.add(candidate4);
        ArrayList <Candidate> temp4 = new ArrayList<Candidate>();
        election.setCurrCandidates(temp3);
        election.setEliminatedCandidates(temp4);
        election.setTotalNumBallots(6);

        Candidate least = election.findLeastCand();
        assertTrue(least.getcName().equals("Kleinberg") || least.getcName().equals("Chou"));


    }

    @Test
    public void testRedistributeBallots() {
        election = new IRElection();
        candidate1 = new Candidate("Rosen", "D");
        candidate2 = new Candidate("Kleinberg", "R");
        candidate3 = new Candidate("Chou"," I");
        candidate4 = new Candidate("Royce", "L");
        ballot1 = new IRBallot(1, 4);
        ballot2 = new IRBallot(2, 4);
        ballot3 = new IRBallot(3, 4);
        ballot4 = new IRBallot(4, 4);
        ballot5 = new IRBallot(5, 4);
        ballot6 = new IRBallot(6, 4);

        ArrayList<Candidate> ranking1 = new ArrayList<Candidate>();
        ranking1.add(candidate1);
        ranking1.add(candidate4);
        ranking1.add(candidate2);
        ranking1.add(candidate3);

        ArrayList<Candidate> ranking2 = new ArrayList<Candidate>();
        ranking2.add(candidate1);
        ranking2.add(candidate3);

        ArrayList<Candidate> ranking3 = new ArrayList<Candidate>();
        ranking3.add(candidate1);
        ranking3.add(candidate2);
        ranking3.add(candidate3);

        ArrayList<Candidate> ranking4 = new ArrayList<Candidate>();
        ranking4.add(candidate3);
        ranking4.add(candidate2);
        ranking4.add(candidate1);
        ranking4.add(candidate4);

        ArrayList<Candidate> ranking5 = new ArrayList<Candidate>();
        ranking5.add(candidate3);
        ranking5.add(candidate4);

        ArrayList<Candidate> ranking6 = new ArrayList<Candidate>();
        ranking6.add(candidate4);

        ballot1.setRanking(ranking1);
        ballot2.setRanking(ranking2);
        ballot3.setRanking(ranking3);
        ballot4.setRanking(ranking4);
        ballot5.setRanking(ranking5);
        ballot6.setRanking(ranking6);

        ballot1.setNumCandidates(4);
        ballot2.setNumCandidates(2);
        ballot3.setNumCandidates(3);
        ballot4.setNumCandidates(4);
        ballot5.setNumCandidates(2);
        ballot6.setNumCandidates(1);

        candidate1.setcNumBallots(3);
        candidate2.setcNumBallots(0);
        candidate3.setcNumBallots(2);
        candidate4.setcNumBallots(1);

        candidate1.addBallot(ballot1);
        candidate1.addBallot(ballot2);
        candidate1.addBallot(ballot3);
        candidate3.addBallot(ballot4);
        candidate3.addBallot(ballot5);
        candidate4.addBallot(ballot6);

        ArrayList<Candidate> temp = new ArrayList<Candidate>();
        temp.add(candidate1);
        temp.add(candidate2);
        temp.add(candidate3);
        temp.add(candidate4);
        ArrayList <Candidate> temp2 = new ArrayList<Candidate>();
        election.setCurrCandidates(temp);
        election.setEliminatedCandidates(temp2);
        election.setTotalNumBallots(6);

        election.redistributeBallots();

        assertEquals("Did not add eliminated candidate to array", election.getEliminatedCandidates().size(), 1);
        assertEquals("Did not add correct eliminated candidate to array", election.getEliminatedCandidates().get(0).getcName(), "Kleinberg");
        assertEquals("Did not remove eliminated candidate from curr array", election.getCurrCandidates().size(), 3);
        assertEquals("Did not remove correct eliminated candidate from curr array", election.getCurrCandidates().contains(candidate2), false);

        election.redistributeBallots();

        assertEquals("Did not add eliminiated candidate to array", election.getEliminatedCandidates().size(), 2);
        assertEquals("Did not add correct eliminated candidate to array", election.getEliminatedCandidates().get(1).getcName(), "Royce");
        assertEquals("Distributed ballot when not needed", candidate4.getcBallots().contains(ballot6), true);
        assertEquals("Did not remove eliminated candidate from curr array", election.getCurrCandidates().size(), 2);
        assertEquals("Did not remove correct eliminated candidate from curr array", election.getCurrCandidates().contains(candidate4), false);

        election.redistributeBallots();

        assertEquals("Did not add eliminiated candidate to array", election.getEliminatedCandidates().size(), 3);
        assertEquals("Did not add correct eliminated candidate to array", election.getEliminatedCandidates().get(2).getcName(), "Chou");
        assertEquals("Distributed ballot when not needed", candidate3.getcBallots().contains(ballot5), true);
        assertEquals("Did not remove eliminated candidate from curr array", election.getCurrCandidates().size(), 1);
        assertEquals("Did not remove correct eliminated candidate from curr array", election.getCurrCandidates().contains(candidate3), false);
        assertEquals("Distributed ballot to candidate that is already eliminated", candidate2.getcBallots().contains(ballot4), false);
        assertEquals("Distributed ballot to candidate that is already eliminated", candidate4.getcBallots().contains(ballot4), false);
        assertEquals("Did not redisribute Ballot to correct Candididate", candidate1.getcBallots().contains(ballot4), true);

    }

    @Test
    public void testRunElection() {
        String dataCSV = "IRTest\n";
        provideInput(dataCSV);
        election = (IRElection) VotingSystem.promptCSV();

        String dataAudit = "testAudit1\nY";
        provideInput(dataAudit);
        election.promptAudit();

        String dataMedia = "testMedia1\nY";
        provideInput(dataMedia);
        election.promptMedia();

        election.runElection();

        assertEquals("IR", election.getElectionType());
        assertEquals("IRTest", election.getCsvName());
        assertEquals(6, election.getTotalNumBallots());

        assertEquals("Did not find correct winner", election.getCurrCandidates().get(0).getcName(), "Rosen");
        assertEquals("Did not have the correct number of ballots for winner", election.getCurrCandidates().get(0).getcNumBallots(), 4);
        assertEquals("Did not move losers to eliminated candidates", election.getEliminatedCandidates().size(), 3);
        assertEquals("Did not remove losers from curr candidates", election.getCurrCandidates().size(), 1);
        assertEquals("Did not move losers to eliminated candidates", election.getEliminatedCandidates().get(0).getcName(), "Kleinberg");
        assertEquals("Did not move losers to eliminated candidates", election.getEliminatedCandidates().get(1).getcName(), "Royce");
        assertEquals("Did not move losers to eliminated candidates", election.getEliminatedCandidates().get(2).getcName(), "Chou");
        assertEquals("Did not redistribute ballots correctly", election.getEliminatedCandidates().get(0).getcNumBallots(), 0);
        assertEquals("Did not redistribute ballots correctly", election.getEliminatedCandidates().get(1).getcNumBallots(), 1);
        assertEquals("Did not redistribute ballots correctly", election.getEliminatedCandidates().get(2).getcNumBallots(), 1);

    }

    // readIRCSV tests
    /**
     * Test Cases:
     * 2nd line (num candidates) is valid/invalid
     * 3rd line (candidate list) is valid/invalid (not in correct format)
     * Length of list in 3rd line does not match length indicated in 2nd line
     * 4th line (numVotes) is valid/invalid
     */
    @Test
    public void testReadIRCSVValidInput(){
        String data = "IRTest\n";
        provideInput(data);
        IRElection sys = (IRElection) VotingSystem.promptCSV();
        assert sys != null;
        sys.readIRCSV();
        // check totalNumBallots, candidates (size)
        assertEquals("Incorrect totalNumBallots", 6, sys.getTotalNumBallots());
        assertEquals("Incorrect number of Candidates", 4, sys.getCandidates().size());

        //check that the first candidate has right name/party: Rosen (D)
        assertEquals("Incorrect Candidate (Party) format", "Rosen",
                        sys.getCandidates().get(0).getcName());
        assertEquals("Incorrect Candidate (Party) format", "D",
                        sys.getCandidates().get(0).getcParty());
    }

    @Test
    public void testReadIRCSVInvalidSecondLine(){
        provideInput("IRInvTest2\n");
        IRElection sys = (IRElection) VotingSystem.promptCSV();
        if(sys != null){
            sys.readIRCSV();
            // check totalNumBallots, candidates (size) -> should be 0
            assertEquals("Incorrect totalNumBallots", 0, sys.getTotalNumBallots());
            assertNull("Incorrect number of Candidates", sys.getCandidates());
        } else {
            assertNotNull("Testing data not present", sys);
        }
    }

    @Test
    public void testReadIRCSVInvalidThirdLine(){
        provideInput("IRInvTest3\n");
        IRElection sys = (IRElection) VotingSystem.promptCSV();
        if(sys != null){
            sys.readIRCSV();
            // check totalNumBallots, candidates (size) -> should be 0
            assertEquals("Incorrect totalNumBallots", 0, sys.getTotalNumBallots());
            assertNull("Incorrect number of Candidates", sys.getCandidates());
        } else {
            assertNotNull("Testing data not present", sys);
        }
    }

    @Test
    public void testReadIRCSVInvalidFourthLine(){
        provideInput("IRInvTest4\n");
        IRElection sys = (IRElection) VotingSystem.promptCSV();
        if(sys != null){
            sys.readIRCSV();
            // check totalNumBallots, candidates (size)
            // totalNumBallots should be 0
            // candidates.size() = 0, currCandidates.size() = 4, eliminatedCandidates.size() = 0
            assertEquals("Incorrect totalNumBallots", 0, sys.getTotalNumBallots());
            assertEquals("Incorrect size for candidates ArrayList", 4, sys.getCandidates().size());
            assertEquals("Incorrect size for currCandidates ArrayList", 4,
                            sys.getCandidates().size());
            assertEquals("Incorrect size for eliminatedCandides ArrayList", 0,
                            sys.getEliminatedCandidates().size());
        } else {
            assertNotNull("Testing data not present", sys);
        }
    }
    // end of readIRCSV tests

    // readBallots tests
    /**
     * Test Cases:
     * Ballot is in incorrect format
     * Number of lines read does not match totalNumBallots
     */
    @Test
    public void testReadBallotsValidInput(){
        provideInput("IRTest\n");
        IRElection sys = (IRElection) VotingSystem.promptCSV();
        if(sys != null){
            sys.readIRCSV(); // assume that it works

            // should create 6 ballots
            sys.readBallots();
            assertEquals("Incorrect number of ballots created", 6, sys.getTotalNumBallots());
            assertEquals("Incorrect number of ballots assigned", 6,
                    (sys.getCandidates().get(0).getcNumBallots() +
                    sys.getCandidates().get(1).getcNumBallots() +
                    sys.getCandidates().get(2).getcNumBallots() +
                    sys.getCandidates().get(3).getcNumBallots()));
        } else {
            assertNotNull("Testing data not present", sys);
        }
    }

    @Test
    public void testReadBallotsInvalidInput(){
        provideInput("IRTestInvalid\n");
        IRElection sys = (IRElection) VotingSystem.promptCSV();
        if (sys != null) {
            sys.readIRCSV(); // assume that it works

            // no ballots should be created
            assertEquals("Incorrect number of ballots created", 6, sys.getTotalNumBallots());
            assertEquals("Incorrect number of ballots assigned", 0,
                    (sys.getCandidates().get(0).getcNumBallots() +
                    sys.getCandidates().get(1).getcNumBallots() +
                    sys.getCandidates().get(2).getcNumBallots() +
                    sys.getCandidates().get(3).getcNumBallots()));
        } else {
            assertNotNull("Testing data not present", sys);
        }
    }
    // end of readBallots tests

    // writeToMediaFile tests
    // Test Cases: set all values manually and check that each value prints correctly
    @Test
    public void testWriteToMediaFile(){
        // set up
        IRElection sys = new IRElection();
        try {
            sys.setMediaFile(new PrintWriter("IRMediaTestFile.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open test file");
            return;
        }

        // need candidates and num votes
        sys.setCurrCandidates(new ArrayList<Candidate>(1));
        sys.setEliminatedCandidates(new ArrayList<Candidate>(2));

        sys.setTotalNumBallots(6);

        Candidate c1 = new Candidate("name1", "D");
        c1.setcNumBallots(3);
        Candidate c2 = new Candidate("name2", "D");
        c2.setcNumBallots(2);
        Candidate c3 = new Candidate("name3", "R");
        c3.setcNumBallots(1);

        sys.getCurrCandidates().add(c1);
        sys.getEliminatedCandidates().add(c2);
        sys.getEliminatedCandidates().add(c3);

        // testing
        sys.writeToMediaFile();
        String expectOut = "Election Result\n";
        expectOut += "------------------------------\n";
        double percentage = (c1.getcNumBallots() / ((double)sys.getTotalNumBallots())) * 100;
        expectOut += "name1, D won with " + String.format("%.3f", percentage) + "% of the vote\n";
        expectOut += "Eliminated Candidates: \n";
        percentage = (c2.getcNumBallots() / ((double)sys.getTotalNumBallots())) * 100;
        expectOut += "name2, D had " + String.format("%.3f", percentage) + "% of the vote when they were eliminated\n";
        percentage = (c3.getcNumBallots() / ((double)sys.getTotalNumBallots())) * 100;
        expectOut += "name3, R had " + String.format("%.3f", percentage) + "% of the vote when they were eliminated\n";

        String mediaFile = null;
        try {
            mediaFile = Files.readString(Path.of("IRMediaTestFile.txt"));
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
        IRElection sys = new IRElection();
        try {
            sys.setAuditFile(new PrintWriter("IRAuditTestFile.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open test file");
            return;
        }

        // Candidates -> cNumBallots, Ballots, name, party
        // Ballots -> ID, Ranking, numCandidates
        sys.setCurrCandidates(new ArrayList<Candidate>(1));
        sys.setEliminatedCandidates(new ArrayList<Candidate>(2));

        sys.setTotalNumBallots(6);
        ArrayList<Candidate> rank1 = new ArrayList<>(3);
        ArrayList<Candidate> rank2 = new ArrayList<>();
        ArrayList<Candidate> rank3 = new ArrayList<>();

        IRBallot b1 = new IRBallot(1, 1);
        b1.setNumCandidates(1);
        IRBallot b2 = new IRBallot(2, 1);
        b2.setNumCandidates(1);
        IRBallot b3 = new IRBallot(3, 1);
        b3.setNumCandidates(1);
        IRBallot b4 = new IRBallot(4, 1);
        b4.setNumCandidates(1);
        IRBallot b5 = new IRBallot(5, 1);
        b5.setNumCandidates(1);
        IRBallot b6 = new IRBallot(6, 1);
        b6.setNumCandidates(1);

        Candidate c1 = new Candidate("name1", "D");
        c1.setcNumBallots(3);
        c1.getcBallots().add(b1);
        c1.getcBallots().add(b2);
        c1.getcBallots().add(b3);
        rank1.add(c1);
        b1.setRanking(rank1);
        b2.setRanking(rank1);
        b3.setRanking(rank1);

        Candidate c2 = new Candidate("name2", "D");
        c2.setcNumBallots(2);
        c2.getcBallots().add(b4);
        c2.getcBallots().add(b5);
        rank2.add(c2);
        b4.setRanking(rank2);
        b5.setRanking(rank2);

        Candidate c3 = new Candidate("name3", "R");
        c3.setcNumBallots(1);
        c3.getcBallots().add(b6);
        rank3.add(c3);
        b6.setRanking(rank3);

        sys.getCurrCandidates().add(c1);
        sys.getEliminatedCandidates().add(c2);
        sys.getEliminatedCandidates().add(c3);

        // testing
        sys.writeToAuditFile(c3); // will need multiple of these
        sys.writeToAuditFile();
        double percentage = (c3.getcNumBallots()/((double)sys.getTotalNumBallots())) * 100;
        String expectOut = "name3, R: eliminated with " + String.format("%.3f", percentage) + "% of the vote\n";
        expectOut += "Ballots assigned to name3:\n";
        expectOut += "    6 Ranking: 0: name3\n";
        expectOut += "----------------------------------------\n";
        percentage = ( c1.getcNumBallots() / ((double)sys.getTotalNumBallots())) * 100;
        expectOut += "name1, D: won with " + String.format("%.3f", percentage) + "% of the vote\n";
        expectOut += "Ballots assigned to name1:\n";
        expectOut += "    1 Ranking: 0: name1\n";
        expectOut += "    2 Ranking: 0: name1\n";
        expectOut += "    3 Ranking: 0: name1\n";
        expectOut += "Ballots not reassigned from eliminated candidates: \n";
        expectOut += "Ballots assigned to name2, D:\n";
        expectOut += "    4 Ranking: 0: name2\n";
        expectOut += "    5 Ranking: 0: name2\n";
        expectOut += "Ballots assigned to name3, R:\n";
        expectOut += "    6 Ranking: 0: name3\n";

        String auditFile = null;
        try {
            auditFile = Files.readString(Path.of("IRAuditTestFile.txt"));
        } catch (IOException e) {
            System.out.println("Unable to read test file");
        }
        assertEquals("Incorrect Output", expectOut, auditFile);
    }
    // end of writeToAuditFile tests

    // printToScreen tests
    // Test Cases: set all values manually and check that each value prints correctly
    @Test
    public void testPrintToScreen(){
        // set up
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        IRElection sys = new IRElection();
        ArrayList<Candidate> cand = new ArrayList<>();
        Candidate c1 = new Candidate("name1", "D");
        c1.setcNumBallots(3);
        cand.add(c1);
        sys.setCurrCandidates(cand);
        sys.setTotalNumBallots(6);

        // testing
        sys.printToScreen();
        String expectOut = "Election Results\n";
        expectOut += "------------------------------\n";
        double percentage = (3 / (double)6) * 100;
        expectOut += "name1, D won with " + String.format("%.3f", percentage) + "% of the vote\n";

        assertEquals("Incorrect Output", expectOut, getOutput());
    }
    // end of printToScreen tests
  }

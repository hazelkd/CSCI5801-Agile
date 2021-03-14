import jdk.jfr.Timestamp;

import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestParty {

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
        assertEquals(2, bestParty.getCandidates().size()); // add two regular candidate objects

        // Insert Null Case
        bestParty.addCandidate(candidate3);
        assertEquals(2, bestParty.getCandidates().size()); // should NOT have added the null candidate

        // Insert Candidate without a matching party
        bestParty.addCandidate(candidate4);
        assertEquals(2, bestParty.getCandidates().size()); // should NOT add the non party candidate
    }


    @Test
    public void testCalculateNumBallots() {
        bestParty = new Party("P"); // 1 candidate
        okayestParty = new Party("O"); // no candidates 
        partyRock = new Party("R"); //2 candidates

        candidate1 = new Candidate("name", "P");
        candidate2 = new Candidate("name", "P");
        candidate3 = new Candidate("name", "");

        ballotA = new Ballot();
        ballotB = new Ballot();
        ballotC = new Ballot();
        ballotD = new Ballot();
        ballotE = new Ballot();
        ballotF = new Ballot();
        ballotG = new Ballot();
        ballotH = new Ballot();
        ballotI = new Ballot();

        okayestParty.calculateNumBallots();
        assertEquals(0, okayestParty.getCandidates().size()); // no candidates in this party
        assertEquals(0, okayestParty.getpNumBallots()); // party with no candidates should have 0 ballots

        candidate1.addBallot(ballotA);
        candidate1.addBallot(ballotB);
        candidate1.addBallot(ballotC);
        candidate1.addBallot(ballotD);
        bestParty.addCandidate(candidate1);
        
        candidate2.addBallot(ballotE);
        candidate2.addBallot(ballotF);
        candidate2.addBallot(ballotG);
        candidate2.addBallot(ballotH);
        candidate2.addBallot(ballotI);
        bestParty.addCandidate(candidate2);

        bestParty.calculateNumBallots();
        assertEquals(9, bestParty.getpNumBallots()); // calculate numBallots with 2 candidates
    }

    @Test
    public void testSortCandidates() {
        bestParty = new Party("P");
        candidate1 = new Candidate("Rosen", "P");
        candidate2 = new Candidate("Kleinberg", "P");
        candidate3 = new Candidate("Chou", "P");
        candidate4 = new Candidate("Royce", "P");

        candidate1.setcNumBallots(3);
        candidate2.setcNumBallots(0);
        candidate3.setcNumBallots(2);
        candidate4.setcNumBallots(1);

        ArrayList<Candidate> temp = new ArrayList<Candidate>();
        temp.add(candidate1);
        temp.add(candidate2);
        temp.add(candidate3);
        temp.add(candidate4);

        bestParty.setCandidates(temp);

        bestParty.sortCandidates();

        assertEquals("Did not put greatest in first position", bestParty.getCandidates().get(0).getcName(), "Rosen");
        assertEquals("Did not put second greatest in second position", bestParty.getCandidates().get(1).getcName(), "Chou");
        assertEquals("Did not put third greatest in third position", bestParty.getCandidates().get(2).getcName(), "Royce");
        assertEquals("Did not put last in last position", bestParty.getCandidates().get(3).getcName(), "Kleinberg");

        candidate1.setcNumBallots(4);
        candidate2.setcNumBallots(0);
        candidate3.setcNumBallots(0);
        candidate4.setcNumBallots(2);

        ArrayList<Candidate> temp2 = new ArrayList<Candidate>();
        temp2.add(candidate1);
        temp2.add(candidate2);
        temp2.add(candidate3);
        temp2.add(candidate4);

        bestParty.setCandidates(temp2);

        bestParty.sortCandidates();

        assertEquals("Did not put greatest in first position", bestParty.getCandidates().get(0).getcName(), "Rosen");
        assertEquals("Did not put second greatest in second position", bestParty.getCandidates().get(1).getcName(), "Royce");
        assertTrue(bestParty.getCandidates().get(2).getcName().equals("Kleinberg") || bestParty.getCandidates().get(2).getcName().equals("Chou"));
        assertTrue(bestParty.getCandidates().get(3).getcName().equals("Kleinberg") || bestParty.getCandidates().get(3).getcName().equals("Chou"));

    }
}

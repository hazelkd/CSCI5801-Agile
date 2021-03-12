package junitfaq;
      
import jdk.jfr.Timestamp;

import org.junit.*;
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
    private Party partRock;

    private ballotA;
    private ballotB;
    private ballotC;
    private ballotD;
    private bollotE;
    private ballotF;
    private ballotG;
    private ballotH;
    private ballotI;

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
        assertEquals(2, bestParty.getCandidates().length()); // add two regular candidate objects

        // Insert Null Case
        bestParty.addCandidate(candidate3);
        assertEquals(2, bestParty.getCandidates().length()); // should NOT have added the null candidate

        // Insert Candidate without a matching party
        bestParty.addCandidate(candidate4);
        assertEquals(2, bestParty.getCandidates().length()); // should NOT add the non party candidate
    }


    @Test
    public void testCalculateNumBallots() {
        bestParty = new Party("P"); // 1 candidate
        okayestParty = new Party("O"); // no candidates 
        partRock = new Party("R"); //2 candidates

        candidate1 = new Candidate("name", "P");
        candidate2 = new Candidate("name", "P");
        candidate3 = new Candidate("name", "");

        ballotA = new Ballot();
        ballotB = new Ballot();
        ballotC = new Ballot();
        ballotD = new Ballot();
        bollotE = new Ballot();
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
        bestParty.addCandidate(candiate1);
        
        candiate2.addBallot(ballotE);
        candiate2.addBallot(ballotF);
        candiate2.addBallot(ballotG);
        candiate2.addBallot(ballotH);
        candiate2.addBallot(ballotI);
        bestParty.addCandidate(candidate2);

        bestParty.calculateNumBallots();
        assertEquals(9, bestParty.getpNumBallots()); // calculate numBallots with 2 candidates
    }

}
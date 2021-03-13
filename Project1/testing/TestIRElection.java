import org.junit.AfterClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TestIRElection {

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
    temp.remvoe(candidate3);
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

    assertEquals("Did not find correct least Candidate", election.findLeasCand().getcName(), "Royce");

    temp2.add(candidate4);
    temp.remove(candidate4);
    election.setCurrCandidates(temp);
    election.setEliminatedCandidates(temp2);


    assertEquals("Did not find correct least Candidate", election.findLeasCand().getcName(), "Chou");

    temp2.add(candidate3);
    temp.remove(candidate3);
    election.setCurrCandidates(temp);
    election.setEliminatedCandidates(temp2);

    assertEquals("Did not find correct least Candidate", election.findLeasCand().getcName(), "Rosen");

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
    assertEquals("Did not add correct eliminated candidate to array", election.getEliminatedCandidates().get(0).getName(), "Kleinberg");
    assertEquals("Did not remove eliminated candidate from curr array", election.getCurrCandidates().size(), 3);
    assertEquals("Did not remove correct eliminated candidate from curr array", election.getCurrCandidate().contains(candidate2), false);

    election.redistributeBallots();

    assertEquals("Did not add eliminiated candidate to array" election.getEliminatedCandidates().size(), 2);
    assertEquals("Did not add correct eliminated candidate to array", election.getEliminatedCandidates().get(1).getName(), "Royce");
    assertEquals("Distributed ballot when not needed", candidate4.getcBallots().contains(ballot6), true);
    assertEquals("Did not remove eliminated candidate from curr array", election.getCurrCandidates().size(), 2);
    assertEquals("Did not remove correct eliminated candidate from curr array", election.getCurrCandidate().contains(candidate4), false);

    election.redistributeBallots();

    assertEquals("Did not add eliminiated candidate to array" election.getEliminatedCandidates().size(), 3);
    assertEquals("Did not add correct eliminated candidate to array", election.getEliminatedCandidates().get(2).getName(), "Chou");
    assertEquals("Distributed ballot when not needed", candidate3.getcBallots().contains(ballot5), true);
    assertEquals("Did not remove eliminated candidate from curr array", election.getCurrCandidates().size(), 1);
    assertEquals("Did not remove correct eliminated candidate from curr array", election.getCurrCandidate().contains(candidate3), false);
    assertEquals("Distributed ballot to candidate that is already eliminated", candidate2.getcBallots().contains(ballot4), false);
    assertEquals("Distributed ballot to candidate that is already eliminated", candidate4.getcBallots().contains(ballot4), false)
    assertEquals("Did not redisribute Ballot to correct Candididate", candidate1.getcBallots().contains(ballot4), true);

  }

  @Test
  public void testRunElection() {
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

    election.runElection();

    assertEquals("Did not find correct winner", election.getCurrCandidate().get(0).getcName(), "Rosen");
    assertEquals("Did not move losers to eliminated candidates", election.getEliminatedCandidates().size(), 3);
    assertEquals("Did not move losers to eliminated candidates", election.getEliminatedCandidates().contains(candidate2), true);
    assertEquals("Did not move losers to eliminated candidates", election.getEliminatedCandidates().contains(candidate3), true);
    assertEquals("Did not move losers to eliminated candidates", election.getEliminatedCandidates().contains(candidate4), true);

  }

}

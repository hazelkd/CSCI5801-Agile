import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class TestIRElection {

  private Candidate candidate1;
  private Candidate candidate2;
  private Candidate candidate3;
  private Candidate candidate4;
  private IRElection election;

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

    assertEquals(election.findMajority(), null);

    candidate1.setcNumBallots(3);

    assertEquals(election.findMajority().getcName(), "Rosen");

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

    assertEquals(election.findLeastCand().getcName(), "Kleinberg");

  }
}

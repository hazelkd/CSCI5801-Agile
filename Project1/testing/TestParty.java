import org.junit.Test;
import static org.junit.Assert.*;

public class PartyTest {

    private Candidate candidate1;
    private Candidate candidate2;
    private Candidate candidate3;
    private Candidate candidate4;
    private Party bestParty;

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
        assertEquals(bestParty.getCandidates().length(), 2); // add two regular candidate objects

        // Insert Null Case
        bestParty.addCandidate(candidate3);
        assertEquals(bestParty.getCandidates().length(), 2); // should NOT have added the null candidate

        // Insert Candidate without a matching party

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

      assertEquals("Did not put greatest in first position", bestParty.getCandidates().get(0).getName(), "Rosen");
      assertEquals("Did not put second greatest in second position", bestParty.getCandidates().get(1).getName(), "Chou");
      assertEquals("Did not put third greatest in third position", bestParty.getCandidates().get(2).getName(), "Royce");
      assertEquals("Did not put last in last position", bestParty.getCandidates().get(3).getName(), "Kleinberg");

    }

}

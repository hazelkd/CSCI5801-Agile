import org.junit.AfterClass;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static org.junit.Assert.*;

public class TestPOElection {
  private POElection poElection;

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

  @Test
  public void testPORunElection1() {
      //set up input
      String dataCSV = "POTest\n";
      provideInput(dataCSV);
      poElection = (POElection) VotingSystem.promptCSV();
      if(poElection != null){

          poElection.runElection();

          // everything in VotingSystem was set correctly...
          assertEquals(6, poElection.getCandidates().size());
          assertEquals("PO", poElection.getElectionType());
          assertEquals("POTest", poElection.getCsvName());
          assertEquals(9, poElection.getTotalNumBallots());

          // test the candidates and ballots were assigned correctly
          assertEquals("Pike", poElection.getCandidates().get(0).getcName());
          assertEquals("Foster", poElection.getCandidates().get(1).getcName());
          assertEquals("Deutsch", poElection.getCandidates().get(2).getcName());
          assertEquals("Borg", poElection.getCandidates().get(3).getcName());
          assertEquals("Jones", poElection.getCandidates().get(4).getcName());
          assertEquals("Smith", poElection.getCandidates().get(5).getcName());

          assertEquals(3, poElection.getCandidates().get(0).getcBallots().size());
          assertEquals(2, poElection.getCandidates().get(1).getcBallots().size());
          assertEquals(0, poElection.getCandidates().get(2).getcBallots().size());
          assertEquals(2, poElection.getCandidates().get(3).getcBallots().size());
          assertEquals(1, poElection.getCandidates().get(4).getcBallots().size());
          assertEquals(1, poElection.getCandidates().get(5).getcBallots().size());


      }
      else {
          assertNotNull("Testing data not present", poElection);
      }

      // tear down
      File check = new File("testAudit1.txt");
      if(check.exists()) check.delete();
      check = new File("testMedia1.txt");
      if(check.exists()) check.delete();
  }

  @Test
  public void testPORunElection2() {
      //set up input
      String dataCSV = "POTest2\n";
      provideInput(dataCSV);
      poElection = (POElection) VotingSystem.promptCSV();
      if(poElection != null){

          poElection.runElection();

          // everything in VotingSystem was set correctly...
          assertEquals(4, poElection.getCandidates().size());
          assertEquals("PO", poElection.getElectionType());
          assertEquals("POTest2", poElection.getCsvName());
          assertEquals(12, poElection.getTotalNumBallots());

          // test the candidates and ballots were assigned correctly
          assertEquals("Spongebob", poElection.getCandidates().get(0).getcName());
          assertEquals("Ferb", poElection.getCandidates().get(1).getcName());
          assertEquals("PerryThePlatypus", poElection.getCandidates().get(2).getcName());
          assertEquals("Patrick", poElection.getCandidates().get(3).getcName());

          assertEquals(4, poElection.getCandidates().get(0).getcBallots().size());
          assertEquals(2, poElection.getCandidates().get(1).getcBallots().size());
          assertEquals(4, poElection.getCandidates().get(2).getcBallots().size());
          assertEquals(2, poElection.getCandidates().get(3).getcBallots().size());


      }
      else {
          assertNotNull("Testing data not present", poElection);
      }

      // tear down
      File check = new File("testAudit1.txt");
      if(check.exists()) check.delete();
      check = new File("testMedia1.txt");
      if(check.exists()) check.delete();
  }

  @Test
  public void testPORunElectionMultFiles1() {
      //set up input
      String dataCSV = "POTest\nY\nPOTestMultFiles1";
      provideInput(dataCSV);
      poElection = (POElection) VotingSystem.promptCSV();
      if(poElection != null){

          poElection.runElection();

          // everything in VotingSystem was set correctly...
          assertEquals(6, poElection.getCandidates().size());
          assertEquals("PO", poElection.getElectionType());
          assertEquals("POTestMultFiles1", poElection.getCsvName());
          assertEquals(18, poElection.getTotalNumBallots());

          // test the candidates and ballots were assigned correctly
          assertEquals("Pike", poElection.getCandidates().get(0).getcName());
          assertEquals("Foster", poElection.getCandidates().get(1).getcName());
          assertEquals("Deutsch", poElection.getCandidates().get(2).getcName());
          assertEquals("Borg", poElection.getCandidates().get(3).getcName());
          assertEquals("Jones", poElection.getCandidates().get(4).getcName());
          assertEquals("Smith", poElection.getCandidates().get(5).getcName());

          assertEquals(6, poElection.getCandidates().get(0).getcBallots().size());
          assertEquals(4, poElection.getCandidates().get(1).getcBallots().size());
          assertEquals(0, poElection.getCandidates().get(2).getcBallots().size());
          assertEquals(4, poElection.getCandidates().get(3).getcBallots().size());
          assertEquals(2, poElection.getCandidates().get(4).getcBallots().size());
          assertEquals(2, poElection.getCandidates().get(5).getcBallots().size());


      }
      else {
          assertNotNull("Testing data not present", poElection);
      }

      // tear down
      File check = new File("testAudit1.txt");
      if(check.exists()) check.delete();
      check = new File("testMedia1.txt");
      if(check.exists()) check.delete();
  }

}

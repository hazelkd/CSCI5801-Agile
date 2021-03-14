import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class TestVotingSystem {

    // for restoration
    public static InputStream systemIn = System.in;
    public static PrintStream systemOut = System.out;

    public ByteArrayOutputStream testOut;
    private VotingSystem votingSys;

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
  
    private VotingSystem system; 

    @Test
    public void testIRElec() {
        String data = "IRTest\n";
        provideInput(data);
        // copy of main code (with added input lines):
        VotingSystem system = VotingSystem.promptCSV();
        if(system != null){
            provideInput("d\n");
            system.promptAudit();
            provideInput("d\n");
            system.promptMedia();
            if (system.getElectionType().equals("OPL") && (system.mediaFile != null) && (system.auditFile != null)){
                OPLElection newOPL = (OPLElection) system;
                newOPL.runElection();
            }
            else if(system.getElectionType().equals("IR") && (system.mediaFile != null) && (system.auditFile != null)){
                IRElection newIR = (IRElection) system;
                newIR.runElection();
            }
            assertEquals(system.getCsvName(), "IRTest");

            assertEquals(system.getElectionType(), "IR");

            assertEquals(system.getTotalNumBallots(), 6);

            assertEquals("Rosen", system.getCandidates().get(0).getcName());
            assertEquals("Kleinberg", system.getCandidates().get(1).getcName());
            assertEquals("Chou", system.getCandidates().get(2).getcName());
            assertEquals("Royce", system.getCandidates().get(3).getcName());

            assertEquals(system.getCandidates().size(), 4);
        }
    }

    @Test
    public void testIRElecLong(){
        long startTime = System.currentTimeMillis();
        String data = "IRTestLong\n";
        provideInput(data);
        // copy of main code (with added input lines):
        VotingSystem system = VotingSystem.promptCSV();
        if(system != null){
            provideInput("d\n");
            system.promptAudit();
            provideInput("d\n");
            system.promptMedia();
            if (system.getElectionType().equals("OPL") && (system.mediaFile != null) && (system.auditFile != null)){
                OPLElection newOPL = (OPLElection) system;
                newOPL.runElection();
            }
            else if(system.getElectionType().equals("IR") && (system.mediaFile != null) && (system.auditFile != null)){
                IRElection newIR = (IRElection) system;
                newIR.runElection();
            }
        }
        long endTime = System.currentTimeMillis();
        // 480,000 milliseconds in 8 minutes
        assertTrue((endTime-startTime) < 480000);
    }

    @Test
    public void testOPLElec(){
        // copy of main code (with added input lines):
        provideInput("OPLTest\n");
        VotingSystem system = VotingSystem.promptCSV();
        if(system != null){
            provideInput("d\n");
            system.promptAudit();
            provideInput("d\n");
            system.promptMedia();
            if (system.getElectionType().equals("OPL") && (system.mediaFile != null) && (system.auditFile != null)){
                OPLElection newOPL = (OPLElection) system;
                newOPL.runElection();
            }
            else if(system.getElectionType().equals("IR") && (system.mediaFile != null) && (system.auditFile != null)){
                IRElection newIR = (IRElection) system;
                newIR.runElection();
            }
            assertEquals(system.getCsvName(), "OPLTest");

            assertEquals(system.getElectionType(), "OPL");

            assertEquals(system.getTotalNumBallots(), 9);

            //assertEquals(system.getCandidates(), "Pike", "Foster", "Deutsch", "Borg", "Jones", "Smith");
            assertEquals("Pike", system.getCandidates().get(0).getcName());
            assertEquals("Foster", system.getCandidates().get(1).getcName());
            assertEquals("Deutsch", system.getCandidates().get(2).getcName());
            assertEquals("Borg", system.getCandidates().get(3).getcName());
            assertEquals("Jones", system.getCandidates().get(4).getcName());
            assertEquals("Smith", system.getCandidates().get(5).getcName());

            assertEquals(system.getCandidates().size(), 6);

            //should I be checking OPL objects too?
        }
    }

    @Test
    public void testOPLElecLong(){
        long startTime = System.currentTimeMillis();
        // copy of main code (with added input lines):
        provideInput("OPLTestLong\n");
        VotingSystem system = VotingSystem.promptCSV();
        if(system != null){
            provideInput("d\n");
            system.promptAudit();
            provideInput("d\n");
            system.promptMedia();
            if (system.getElectionType().equals("OPL") && (system.mediaFile != null) && (system.auditFile != null)){
                OPLElection newOPL = (OPLElection) system;
                newOPL.runElection();
            }
            else if(system.getElectionType().equals("IR") && (system.mediaFile != null) && (system.auditFile != null)){
                IRElection newIR = (IRElection) system;
                newIR.runElection();
            }
        }
        long endTime = System.currentTimeMillis();
        // 480,000 milliseconds in 8 minutes
        assertTrue((endTime-startTime) < 480000);
    }
  
    @Test
    public void testCoinToss() {
        votingSys = new VotingSystem();

        // Test with negetive number input
        int result = votingSys.coinToss(-5);
        assertEquals(result, -1);
        result = 10;

        // Test with 0 as input
        result = votingSys.coinToss(0);
        assertEquals(result, -1); 
        
        // ensure the return value is with in the bounds 
        for (int i = 0; i < 100; i++) {
            result = votingSys.coinToss(13);
            assertTrue("Random Number too High", result < 13);
            assertTrue("Random Number too Low", result >= 0);
        }
    }

    // promptCSV tests
    /**
     * Test Cases:
     * User inputs correct name, file can be opened
     * User inputs incorrect name, file cannot be opened
     * First line is IR
     * First line is OPL
     * First line is not valid
     */
    @Test
    public void testPromptCSVFirstLineIR(){
        String data = "IRTest\n";
        provideInput(data);
        VotingSystem sys = VotingSystem.promptCSV();
        assertNotNull("Returned a Null object", sys);
        assertTrue("Did not return type of IRElection", sys instanceof IRElection);
        assertNotNull("CSV File not initialized", sys.getCsvFile());
        assertEquals("Did not set ElectionType as IRElection", "IR", sys.getElectionType());
    }

    @Test
    public void testPromptCSVFirstLineOPL(){
        String data = "OPLTest\n";
        provideInput(data);
        VotingSystem sys = VotingSystem.promptCSV();
        assertNotNull("Returned a Null object", sys);
        assertTrue("Did not return type of OPLElection", sys instanceof OPLElection);
        assertNotNull("CSV File not initialized", sys.getCsvFile());
        assertEquals("Did not set ElectionType as OPL", "OPL", sys.getElectionType());
    }

    @Test
    public void testPromptCSVFirstLineInvalid(){
        String data = "InvalidTest\n";
        provideInput(data);
        VotingSystem sys = VotingSystem.promptCSV();
        assertNull("Returned a non-null object", sys);
        String expectOut = "Please enter the name of your file (do not include .txt extension): \n" +
                "Invalid election type, exiting\n";
        assertEquals("Unexpected Output", expectOut, getOutput());
    }

    @Test
    public void testPromptCSVInvalidFile(){
        String data = "blah\n";
        provideInput(data);
        VotingSystem sys = VotingSystem.promptCSV();
        assertNull("Returned a non-null object", sys);
        String expectOut = "Please enter the name of your file (do not include .txt extension): \n"+
                                "blah.txt not found\n"+
                                "Please enter the name of your file (do not include .txt extension): \n"+
                                "blah.txt not found\n"+
                                "Unable to open file, exiting\n";
        assertEquals("Unexpected Output", expectOut, getOutput());
    }
    // end of promptCSV tests

    // promptAudit tests
    /**
     * Test Cases:
     * User inputs name and accepts
     * User inputs name and rejects
     * User inputs name and has invalid input
     * User uses default name
     * PrintWriter successfully opens file (tested with first 2 cases)
     * PrintWriter cannot open file (tested with 3rd case)
     */
    @Test
    public void testPromptAuditFirstInput(){
        String data = "testAudit1\nY";
        provideInput(data);
        VotingSystem sys = new VotingSystem();
        sys.promptAudit();
        assertNotNull("Audit File not initialized", sys.getAuditFile());
    }

    @Test
    public void testPromptAuditSecondInput(){
        String data = "testAudit1\nN\ntestAudit1";
        provideInput(data);
        VotingSystem sys = new VotingSystem();
        sys.promptAudit();
        assertNotNull("Audit File not initialized", sys.getAuditFile());
    }

    @Test
    public void testPromptAuditDefault(){
        String data = "D";
        provideInput(data);
        VotingSystem sys = new VotingSystem();
        sys.promptAudit();
        assertNotNull("Audit File not initialized", sys.getAuditFile());
    }

    @Test
    public void testPromptAuditInvalidInput(){
        String data = "help?";
        provideInput(data);
        VotingSystem sys = new VotingSystem();
        sys.promptAudit();
        assertNull("Audit File incorrectly initialized", sys.getAuditFile());
    }
    // end of promptAudit tests

    // promptMedia tests
    /**
     * Test Cases:
     * User inputs name and accepts
     * User inputs name and rejects
     * User inputs name and has invalid input
     * User uses default name
     * PrintWriter successfully opens file (tested with first 2 cases)
     * PrintWriter cannot open file (tested with 3rd case)
     */
    @Test
    public void testPromptMediaFirstInput(){
        String data = "testMedia1\nY";
        provideInput(data);
        VotingSystem sys = new VotingSystem();
        sys.promptMedia();
        assertNotNull("Media File not initialized", sys.getMediaFile());
    }

    @Test
    public void testPromptMediaSecondInput(){
        String data = "testMedia1\nN\ntestMedia1";
        provideInput(data);
        VotingSystem sys = new VotingSystem();
        sys.promptMedia();
        assertNotNull("Media File not initialized", sys.getMediaFile());
    }

    @Test
    public void testPromptMediaDefault(){
        String data = "D";
        provideInput(data);
        VotingSystem sys = new VotingSystem();
        sys.promptMedia();
        assertNotNull("Media File not initialized", sys.getMediaFile());
    }

    @Test
    public void testPromptMediaInvalidInput(){
        String data = "help?";
        provideInput(data);
        VotingSystem sys = new VotingSystem();
        sys.promptAudit();
        assertNull("Media File incorrectly initialized", sys.getMediaFile());
    }
    // end of promptMedia tests
}
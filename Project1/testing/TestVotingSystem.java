import org.junit.AfterClass;
import org.junit.Test;

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
     * User inputs name & accepts
     * User inputs name & rejects
     * User inputs name & has invalid input
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
     * User inputs name & accepts
     * User inputs name & rejects
     * User inputs name & has invalid input
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
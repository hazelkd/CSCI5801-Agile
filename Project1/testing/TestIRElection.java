import org.junit.AfterClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

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
        // check
    }

    @Test
    public void testReadIRCSVInvalidSecondLine(){

    }

    @Test
    public void testReadIRCSVInvalidThirdLine(){

    }

    @Test
    public void testReadIRCSVCandidateListWrongLength(){

    }

    @Test
    public void testReadIRCSVInvalidFourthLine(){

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

    }

    @Test
    public void testReadBallotsInvalidInput(){

    }
    // end of readBallots tests

    // writeToMediaFile tests
    // Test Cases: set all values manually and check that each value prints correctly
    @Test
    public void testWriteToMediaFile(){

    }
    // end of writeToMediaFile tests

    // writeToAuditFile tests
    // Test Cases: set all values manually and check that each value prints correctly
    @Test
    public void testWriteToAuditFile(){

    }
    // end of writeToAuditFile tests

    // printToScreen tests
    // Test Cases: set all values manually and check that each value prints correctly
    @Test
    public void testPrintToScreen(){

    }
    // end of printToScreen tests

}
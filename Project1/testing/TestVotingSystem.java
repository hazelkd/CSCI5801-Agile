import org.junit.Test;

import jdk.jfr.Timestamp;

import static org.junit.Assert.*;

public class TestVotingSystem {

    private VotingSystem system; 

    @Test

    public void testIRElec() {

        system.setElectionType("IR");
        system.main(String[5]); //also don't really know what to put here in main call 
        //need to be able to skip prompt CSV, etc.
        //need to specify that I want to read in IRElection file

        //should I check return of the functions?

        assertEquals(system.getCsvName(), "IRTest.txt");

        assertEquals(system.getElectionType(), "IR");

        assertEquals(system.getTotalNumBallots(), 6);

        assertEquals(system.getCandidates(), "Rosen", "Kleinberg", "Chou", "Royce");

        assertEquals(system.getCandidates().length, 4);
    }

    @Test

    public void testOPLElec(){
        system.setElectionType("OPL");
        system.main(String[5]); //also don't really know what to put here in main call 
        //need to be able to skip prompt CSV, etc.
        //need to specify that I want to read in IRElection file

        //should I check return of the functions?

        assertEquals(system.getCsvName(), "OPLTest.txt");

        assertEquals(system.getElectionType(), "OPL");

        assertEquals(system.getTotalNumBallots(), 9);

        assertEquals(system.getCandidates(), "Pike", "Foster", "Deutsch", "Borg", "Jones", "Smith");

        assertEquals(system.getCandidates().length, 6);

        //should I be checking OPL objects too?  
    }

}
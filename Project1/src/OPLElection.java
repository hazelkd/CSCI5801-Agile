// OLPElection
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class OPLElection extends VotingSystem{
    /* Driving force of the OPL Election class. It will call all of the functions for the algorithm of OPL voting. 
     * It will also close the media, audit, and CSV files at the end of the function, which will be the 
     * end of this election process.
     * @return -1 to indicate error, 0 if everything operated properly
     */
    public int runElection(){ 

        // read in the rest of the file & set totalNumBallots, totalNumSeats, numSeatsLeft
        // make/assign party objects and candidate objects
        readOPLCSV();
        // read/make/distribute ballots
        readBallots();
        // calculate quote
        setQuota();
        // allocate the first round of seats
        allocateByQuota();
        writeToAuditFile();
        // allocate by remainder
        if (numSeatsLeft != 0) {
            allocateByRemainder();
            writeToAuditFile();
        }
        //write to media
        writeToMediaFile();
        writeToAuditFile(); // write the winners to the audit file
        printToScreen();
        // close the media, audit, and CSV files
        this.getCsvFile().close() 

        this.getAuditFile().flush();
        this.getAuditFile().close();

        this.getMediaFile().flush();
        this.getMediaFile().close();

        // print to screen
        printToScreen();

        return 0; // when will it not return 0 because we never check if other stuff is working properly
    }
    public void readOPLCSV(){}
    public void readBallots(){}
    public void printToScreen(){}
    public void writeToMediaFile(){}
    public void writeToAuditFile(){}
    public void allocateByQuota(){}
    public void allocateByRemainder(){}

    /*
     * Iterate through the party array list object in this class. As it is iterating through, it will call the 
     * calculateNumBallots() function for each party, which will calculate the number of ballots that each party has. 
     * As it is iterating through the party list, it will also call sortCandidate() function, which will sort the 
     * candidates in each party, with the person who has received the most votes at the beginning of the array. 
     * This function will be called after all objects have been created and assigned.
     */
    public void partyNumBallots(){
        for (int i = 0; i < party.size(); i++) {
            party.get(i).calculateNumBallots(); //calculate the number of ballots for each aprty
            party.get(i).sortCandidates(); // order the candidates in the party 
        }
    }

    // Getters & Setters

    public int getTotalNumSeats() {
        return totalNumSeats;
    }

    public void setTotalNumSeats(int totalNumSeats) {
        this.totalNumSeats = totalNumSeats;
    }

    public int getNumSeatsLeft() {
        return numSeatsLeft;
    }

    public void setNumSeatsLeft(int numSeatsLeft) {
        this.numSeatsLeft = numSeatsLeft;
    }

    public ArrayList<Party> getParty() {
        return party;
    }

    public void setParty(ArrayList<Party> party) {
        this.party = party;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota() {
        this.quota = this.getTotalNumBallots()/totalNumSeats;
    }

    private int totalNumSeats;
    private int numSeatsLeft;
    private ArrayList<Party> party;
    private int quota;
}

// OLPElection
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class OPLElection extends VotingSystem{
    public int runElection(){ return 0; }
    public void readOPLCSV(){}
    public void readBallots(){}
    public void printToScreen(){}
    public void writeToMediaFile(){}
    public void writeToAuditFile(){}
    public void allocateByQuota(){}
    public void allocateByRemainder(){}
    public void partyNumBallots(){}

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

    public void setQuota(int quota) {
        this.quota = quota;
    }

    private int totalNumSeats;
    private int numSeatsLeft;
    private ArrayList<Party> party;
    private int quota;
}

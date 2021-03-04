// IRElection
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class IRElection extends VotingSystem{
    public int runElection(){}
    public void readIRCSV(){}
    public void readBallots(){}
    public Candidate findMajority(){}
    public Candidate findLeastCand(){}
    public void redistributeBallots(){}
    public void writeToMediaFile(){}
    public void writeToAuditFile(){}
    public void printToScreen(){}

    // Getters & Setters

    public ArrayList<Candidate> getCurrCandidates() {
        return currCandidates;
    }

    public void setCurrCandidates(ArrayList<Candidate> currCandidates) {
        this.currCandidates = currCandidates;
    }

    public ArrayList<Candidate> getEliminatedCandidates() {
        return eliminatedCandidates;
    }

    public void setEliminatedCandidates(ArrayList<Candidate> eliminatedCandidates) {
        this.eliminatedCandidates = eliminatedCandidates;
    }

    private ArrayList<Candidate> currCandidates;
    private ArrayList<Candidate> eliminatedCandidates;
}

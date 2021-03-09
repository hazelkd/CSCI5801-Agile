// Party
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class Party {
    //constructor
    public Party(String name){
        pName = name;
        candidates = new ArrayList<Candidate>();
    }
    public void sortCandidates(){}
    public void calculateNumBallots(){}
    public void addCandidate(Candidate c){}

    // Getters & Setters

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getpNumBallots() {
        return pNumBallots;
    }

    public void setpNumBallots(int pNumBallots) {
        this.pNumBallots = pNumBallots;
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }

    public int getRemainder() {
        return remainder;
    }

    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    private String pName;
    private int pNumBallots;
    private ArrayList<Candidate> candidates;
    private int remainder;
    private int numSeats;

}

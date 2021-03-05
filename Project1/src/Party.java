// Party
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class Party {
    /*
     * Constructor to initialize a Party Object
     */
    public Party(String partyName) {
        pName = partyName;
        pNumBallots = 0;
        candidates = new ArrayList<Candidate>();
        remainder = 0;
        numSeats = 0;
    }
     
    public void sortCandidates(){}

    /*
     * Calculate the total number of ballots for the party.
     * Set the class variable pNumBallots to this sum.
     */
    public void calculateNumBallots(){
        int numBallots;
        for (int i = 0; i < candidates.size(); i++) {
            numBallots += candidates[i].getcBallots().size(); // get length of the candidate[i]'s' ballot arrayList
        }
        pNumBallots = numBallots;
    }
    
    /*
     * Add individual candidates to the candidates ArrayList
     * @param c         candidate object, the new candidate to be added
     */
    public void addCandidate(Candidate c){
        if ((c != null) && (c.getcName() == pName)) { // ensure c is not null and has a matching party name
            candidates.add(c);
        }
    }

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

// Candidate
// A Candidate class to be used by all algorithms
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class Candidate {
    //constructor
    public Candidate(String name, String party){
        cName = name;
        cParty = party;
        cBallots = new ArrayList<>(); //will be trimmed after all ballots are added
    }

    /**
     * This function will be used to add individual ballots to the cBallot ArrayList.
     *
     * @param b Ballot object to be added to the ArrayList
     */
    public void addBallot(Ballot b){
        cBallots.add(b);
        setcNumBallots((getcNumBallots())+1); //is this where we do the counting?
        //do i have to set c ballots?
    }

    // Getters & Setters

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcParty() {
        return cParty;
    }

    public void setcParty(String cParty) {
        this.cParty = cParty;
    }

    public ArrayList<Ballot> getcBallots() {
        return cBallots;
    }

    public void setcBallots(ArrayList<Ballot> cBallots) {
        this.cBallots = cBallots;
    }

    public int getcNumBallots() {
        return cNumBallots;
    }

    public void setcNumBallots(int cNumBallots) {
        this.cNumBallots = cNumBallots;
    }

    private String cName;
    private String cParty;
    private ArrayList<Ballot> cBallots;
    private int cNumBallots;
}
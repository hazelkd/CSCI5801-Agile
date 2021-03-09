// Candidate
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class Candidate {
    //constructor
    public Candidate(String name, String party){
        cName = name;
        cParty = party;
        cBallots = new ArrayList<Ballot>(); //will be trimmed after all ballots are added
    }

    public void addBallot(Ballot b){}

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

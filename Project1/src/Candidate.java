// Candidate
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class Candidate {
    public void addBallot(Ballot b){}

    // Getters & Setters

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Party getcParty() {
        return cParty;
    }

    public void setcParty(Party cParty) {
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
    private Party cParty;
    private ArrayList<Ballot> cBallots;
    private int cNumBallots;
}

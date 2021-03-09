// Candidate
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class Candidate {
    public void addBallot(Ballot b){
        /*This function will be used to add individual ballots to the cBallot ArrayList. 
        It takes a Ballot object as a parameter and adds that ballot to the ArrayList.*/
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

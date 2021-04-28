// POBallot
// A Specific Ballot class to be used by the POElection class
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

public class POBallot extends Ballot{
    //constructor
    public POBallot(int nId, Candidate c){
        bCandidate = c;
        ID = nId;
    }
    private Candidate bCandidate;

    // Getters & Setters

    public Candidate getbCandidate() {
        return bCandidate;
    }

    public void setbCandidate(Candidate bCandidate) {
        this.bCandidate = bCandidate;
    }
}

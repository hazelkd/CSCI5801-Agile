// OPLBallot
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

public class OPLBallot extends Ballot{
    //constructor
    public OPLBallot(int nId, String party, Candidate c){
        bParty = party;
        bCandidate = c;
        ID = nId;
    }
    private String bParty;
    private Candidate bCandidate;

    // Getters & Setters

    public String getbParty() {
        return bParty;
    }

    public void setbParty(String bParty) {
        this.bParty = bParty;
    }

    public Candidate getbCandidate() {
        return bCandidate;
    }

    public void setbCandidate(Candidate bCandidate) {
        this.bCandidate = bCandidate;
    }
}

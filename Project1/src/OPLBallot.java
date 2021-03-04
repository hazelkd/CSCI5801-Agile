// OPLBallot
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

public class OPLBallot extends Ballot{
    private Party bParty;
    private Candidate bCandidate;

    // Getters & Setters

    public Party getbParty() {
        return bParty;
    }

    public void setbParty(Party bParty) {
        this.bParty = bParty;
    }

    public Candidate getbCandidate() {
        return bCandidate;
    }

    public void setbCandidate(Candidate bCandidate) {
        this.bCandidate = bCandidate;
    }
}

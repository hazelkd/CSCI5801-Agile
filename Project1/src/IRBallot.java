// IRBallot
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class IRBallot extends Ballot{
    //constructor
    public IRBallot(int nId, int numCandidates){
        ID = nId;
        ranking = new ArrayList<Candidate>(numCandidates);
        for(int i = 0; i < numCandidates; i++){
            ranking.add(null);
        } // elements will be reset as CSV file is read
        rankIndex = 1;
    }
    private ArrayList<Candidate> ranking;
    private int rankIndex;

    // Getters & Setters

    public ArrayList<Candidate> getRanking() {
        return ranking;
    }

    public void setRanking(ArrayList<Candidate> ranking) {
        this.ranking = ranking;
    }

    public int getRankIndex() {
        return rankIndex;
    }

    public void setRankIndex(int rankIndex) {
        this.rankIndex = rankIndex;
    }
}

// IRBallot
// A Specific Ballot class to be used by the IRElection class
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class IRBallot extends Ballot{
    //constructor
    public IRBallot(int nId, int numCandidates){
        ID = nId;
        ranking = new ArrayList<>(numCandidates);
        for(int i = 0; i < numCandidates; i++){
            ranking.add(null);
        } // elements will be reset as CSV file is read
        numCandidates = 0;
        rankIndex = 1;
    }
    private ArrayList<Candidate> ranking;
    private int rankIndex;
    private int numCandidates;

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

    public int getNumCandidates() {
        return numCandidates;
    }

    public void setNumCandidates(int numCandidates) {
        this.numCandidates = numCandidates;
    }
}

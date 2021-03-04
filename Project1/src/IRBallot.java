// IRBallot
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class IRBallot extends Ballot{
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

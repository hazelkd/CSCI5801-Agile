// Party
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;
import java.util.Collections


public class Party {

  /*
   * Description - This function will be used to sort the candidates ArrayList.
   It will access the number of ballots for each candidate in the ArrayList and
   put the candidate with the most ballots in the 0th position and the candidate
   with the least ballots in the last position. It will call the coinToss() to
   handle any ties.
   */
    public void sortCandidates(){
      int tieBreaker;
      //Use BubbleSort to sort the candidates array by cNumBallots
      for(int j = 0; j < candidates.size(); j++){
        for(int i = 0; i < candidates.size() - j -1; i++){
          //if a candidate with less votes is before a candidate with more votes, swap
          if(candidates.get(i).getcNumBallots() < candidates.get(i+1).getcNumBallots()){
            Collections.swap(candidates, i, i+1);
          }
          //if tie, coin toss
          else if(candidates.get(i).getcNumBallots() == candidates.get(i+1).getcNumBallots()){
            tieBreaker = coinToss(2);
            //If tieBreaker = 1, first candidate lost and you have to swap, otherwise do nothing
            if(tieBreaker){
              Collections.swap(candidates, i, i+1);
            }
          }
        }
      }
    } // sortCandidates

    public void calculateNumBallots(){}
    public void addCandidate(Candidate c){}

    // Getters & Setters

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getpNumBallots() {
        return pNumBallots;
    }

    public void setpNumBallots(int pNumBallots) {
        this.pNumBallots = pNumBallots;
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }

    public int getRemainder() {
        return remainder;
    }

    public void setRemainder(int remainder) {
        this.remainder = remainder;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    private String pName;
    private int pNumBallots;
    private ArrayList<Candidate> candidates;
    private int remainder;
    private int numSeats;

}

// Party
// A general party class to be used by any election classes that require it
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Party {
    //constructor
    public Party(String name){
        pName = name;
        candidates = new ArrayList<>();
    }

    /**
     * Description - This function will be used to sort the candidates ArrayList.
     * It will access the number of ballots for each candidate in the ArrayList and
     * put the candidate with the most ballots in the 0th position and the candidate
     * with the least ballots in the last position. It will call the coinToss() to
     * handle any ties.
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
            if(tieBreaker == 1){
              Collections.swap(candidates, i, i+1);
            }
          }
        }
      }
    } // sortCandidates

    /**
     * Calculate the total number of ballots for the party.
     * Set the class variable pNumBallots to this sum.
     */
    public void calculateNumBallots(){
        int numBallots = 0;
        for (int i = 0; i < candidates.size(); i++) {
            numBallots += candidates.get(i).getcBallots().size(); // get length of the candidate[i]'s' ballot arrayList
        }
        pNumBallots = numBallots;
    }
    
    /**
     * Add individual candidates to the candidates ArrayList
     *
     * @param c         candidate object, the new candidate to be added
     */
    public void addCandidate(Candidate c){
        if ((c != null) && (c.getcParty().equals(pName))) { // ensure c is not null and has a matching party name
            candidates.add(c);
        }
    }

    /**
     * Break any ties throughout either algorithm. It takes in the number of candidates involved in the tie,
     * and returns an integer representing the losing candidate. It will simulate 1000 coin tosses and take
     * the 1001 result to ensure randomness.
     *
     * @param numTied       an int, the number of objects with the same values
     * @return option       an int, the chosen
     */
    public int coinToss(int numTied){
        if (numTied <= 0) {
            return -1;
        }

        int option;
        Random rand = new Random();

        for (int i = 0; i < 1000; i ++) {
            option = rand.nextInt(numTied); //get a number [0, numTied) to correspond to the index of the choosen object
        }
        option = rand.nextInt(numTied); // 1001th random number
        return option;
    }

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

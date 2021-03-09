// IRElection
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class IRElection extends VotingSystem{

  /*
   * Description - This function is the driving force for the IR election class.
     It will call any necessary functions to run the IR algorithm.
   * @return -1 to indicate error, 0 if everything operated properly
   */
  public int runElection() {

    //Reading in the CSV file up to the Ballots
    //Sets totalNumBallots and fills the currCandidates and Candidates ArrayLists full of candidates
    this.readIRCSV()

    //This reads in all of the Ballots in the csvFile
    //It creates a ballot object for each line read, and sets the ranking array
    //It assigns each ballot to the respective Candidate's ballot cBallot ArrayList
    this.readBallots()

    //findMajority will return the Candidate with the Majority is there is one, and null if there isn't
    Candidate winner = this.findMajority();
    //this will be used the track the loser to call the writeToAuditFile(Candidate)
    Candidate loser;

    //This will loop while there is not a majority
    while(winner == null) {
      //If there is only one candidate left, they win by populairty
      if(currCandidates.size() == 1){
        winner = currCandidates.get(0);
      }

      //If there is more than 1 candiate left, you have to redistribute the least popular
      else{
        //This finds the least popluar candidate and writes their info to the audit file
        loser = findLeastCand();
        this.WriteToAuditFile(loser);

        //This function will call findLeastCand() and redistribute their ballots
        //It will also move this candidate from CurrCandidates into eliminatedCandidates
        this.redistributeBallots();

        //Now we see if there is a majority again
        winner = this.findMajority();
      }
    }

    //Eliminating all of the candidates that arent the winner
    for(int i=0; i<currCandidates.size(); i++){
      if(currCandidates.get(i) != winner){
        eliminatedCandidates.add(currCandidates.get(i));
        currCandidates.remove(currCandidates.get(i));
      }
    }

    //Finally, write all of the necessary info to each file
    this.writeToMediaFile();
    this.writeToAuditFile();
    this.printToScreen();

    csvFile.close();
    return 0;

  } // runElection

    public void readIRCSV(){}
    public void readBallots(){}

      /*
       * Description - This function will use the variables totalNumBallots and
       cNumBallots to calculate a percentage of the total vote each candidate has.
       Once the majority has been found, the function will return the Candidate
       object that corresponds to that majority.
       * @return This will return the Candidate with the majority of the votes if there is one, and null if not
       */
    public Candidate findMajority(){
      //Initialize the maxBallot varaible to the number of Ballots the first candidate has
      double maxBallot = currCandidates.get(0).getcNumBallots();
      double curr;

      //Initialize the maxCan variable to the first candidate
      Candidate maxCan = currCandidates.get(0);

      //Loop through all the candidates still in play
      for(int i=1; i < currCandidates.size(); i++){
        //Make curr equal to the number of ballots the curent candidate has
        curr = currCandidates.get(i).getcNumBallots();

        //If the current candidate has more ballots than the max, set the maxCan to the current candidiate
        //Set the new max to the number of ballots the current candidate has
        if(curr > maxBallot){
          maxCan = currCandidates.get(i);
          maxBallot = curr;
        }
      }

      //If the max candidate has a majority, return that candidate, otherwise return null
      if(max/this.getTotalNumBallots() > .50){
        return maxCan;
      }
      else{
        return null;
      }
    } // findMajority

    /*
     * Description - This function will loop through the currCandidates ArrayList
     and calculate the minimum number of votes a candidate has. While looping,
     if a tie for minimum number of votes is found, the coinToss() function is
     called to determine which candidate continues as the minimum. Once the
     ArrayList has been completely looped through, the Candidate that was
     determined to have the least amount of votes is returned.
     * @return This will return the candidate with the least amount of ballots
     */
    public Candidate findLeastCand(){
      //Initialize the min varaible to the number of Ballots the first candidate has
      int min = currCandidates.get(0).getcNumBallots();
      int curr;

      //Initialize the loser variable to the first candidate
      Candidate loser = currCandidates.get(0);
      int tieBreaker;

      //Loop through all the candidates still in play
      for(int i=1; i < currCandidates.size(); i++){

        //Make curr equal to the number of ballots of the current candidate in the array
        curr = currCandidates.get(i).getcNumBallots();

        //If the current candiate has less ballots than the min, set the varibles to new min
        if(curr < min){
          loser = currCandidates.get(i);
          min = curr;
        }
        //If they're equal, you have to call coinToss
        else if (curr == min){
          tieBreaker = this.coinToss(2);
          //If 1 is returned, then the curr lost, and you have to set the variables to the new min
          if(tieBreaker){
            loser = currCandidates.get(i);
            min = curr;
          }
        }
      }

      return loser; //Return the candidate with the least amount of ballots

    } // findLeastCand

    /*
     * Description - This function will call findLeastCand() to determine which
     candidate to move to from the currCandidates ArrayList to the eliminatedCandidates
     ArrayList. The function will then also use this returned candidate object to
     redistribute the ballots in the cBallots ArrayList. If the rankIndex of the
     ballot is less than the number of candidates (and not equal to -1) and the candidate
     at rankIndex is determined to still be in the currCandidates ArrayList, the ballot
     can be added to the cBallots ArrayList of the new candidate. If both of these
     conditions are not met, then the rankIndex will be set to -1 and the ballot will
     stay assigned to the eliminated candidate.
     */
    public void redistributeBallots(){
      //Find the candidate with the least amount of votes, remove them from currCandidats and add to eliminatedCandidates
      Candidate loser = this.findLeastCand();
      currCandidates.remove(currCandidates.indexOf(loser));
      eliminatedCandidates.add(loser);

      //Variables to make the loop less messy
      Ballot currBallot;
      ArrayList<Candidate> currBallotRanking;
      int currBallotRankIndex;
      bool AddedBallot;

      //Loop through all of the ballots in the losers Ballot ArrayList
      for(int i =0; i < loser.getcBallots().size(); i++){

        //Set all of the variables to the current Ballot information
        currBallot = loser.getcBallots().get(i);
        currBallotRanking = currBallot.getRanking();
        currBallotRankIndex = currBallot.getRankIndex();
        AddedBallot = false;

        //If the rankIndex is past the amount of candidates in the rank array, set to -1
        if(currBallotRankIndex >= currBallotRanking.size()){
          currBallot.setRankIndex(-1);
          currBallotRankIndex = -1;
        }

        //This while loop will go until either the ballot is allocated to another candidate, or we've run out of candidates in the rank array
        while(currBallotRankIndex != -1 || AddedBallot){

          //If the candidate at the current rank index is still in play, assign that ballot to the candidate
          if(currCandidates.contains(currBallotRanking.get(currBallotRankIndex) ) ){
            //if the rank index is past the size of the array, set to -1, otherwise increment
            if(currBallotRankIndex >= currBallotRanking.size()-1){
              currBallot.setRankIndex(-1);
            }
            else{
              currBallot.setRankIndex(currBallotRankIndex+1);
            }
            //Add ballot to candidate
            currBallotRanking.get(currBallotRankIndex).addBallot(currBallot);
            AddedBallot = true;
            //remove ballot from loser
            loser.getcBallots().remove(i);
            i--;
          }
          //Otherwise, we have to check the next candidate in the ranking array
          else{
            //Check if the rankIndex is bigger than the array, if it is, set to -1
            if(currBallotRankIndex >= currBallotRanking.size()-1){
              currBallot.setRankIndex(-1);
              currBallotRankIndex = -1;
            }
            //Otherwise, increment rankIndex
            else{
              currBallotRankIndex++;
              currBallot.setRankIndex(currBallotRankIndex);
            }
          }
        }
      }

    } // redistributeBallots

    public void writeToMediaFile(){}
    public void writeToAuditFile(){}
    public void printToScreen(){}

    // Getters & Setters

    public ArrayList<Candidate> getCurrCandidates() {
        return currCandidates;
    }

    public void setCurrCandidates(ArrayList<Candidate> currCandidates) {
        this.currCandidates = currCandidates;
    }

    public ArrayList<Candidate> getEliminatedCandidates() {
        return eliminatedCandidates;
    }

    public void setEliminatedCandidates(ArrayList<Candidate> eliminatedCandidates) {
        this.eliminatedCandidates = eliminatedCandidates;
    }


    private ArrayList<Candidate> currCandidates;
    private ArrayList<Candidate> eliminatedCandidates;
}

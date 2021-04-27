// IRElection
// The main class for running the IR election algorithm
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;
import java.util.*;  

public class IRElection extends VotingSystem{

    /**
    * Description - This function is the driving force for the IR election class.
    * It will call any necessary functions to run the IR algorithm.
    *
    * @return -1 to indicate error, 0 if everything operated properly
    */
    public int runElection() {

        //Reading in the CSV file up to the Ballots
        //Sets totalNumBallots and fills the currCandidates and Candidates ArrayLists full of candidates
        for (int i = 0; i < count; i++){
          this.readIRCSV(scannerNameList.get(i));

        //This reads in all of the Ballots in the csvFile
        //It creates a ballot object for each line read, and sets the ranking array
        //It assigns each ballot to the respective Candidate's ballot cBallot ArrayList
          this.readBallots(scannerNameList.get(i));
        }

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
            this.writeToAuditFile(loser);

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
            i = i-1;
          }
        }

        //Finally, write all of the necessary info to each file
        this.writeToMediaFile();
        this.writeToAuditFile();
        this.printToScreen();

        csvFile.close();
        return 0;

    } // runElection

    /**
     * This function will read lines 2-4 in a CSV file that has been determined to be an IR CSV file. It will set the
     * variable totalNumBallots and initialize the candidates ArrayList to be the size read from line 4. In addition,
     * it will create and add candidate objects to this candidate ArrayList.
     */
    public void readIRCSV(Scanner fileScanner){
        int numCandidates = 0;
        // read in numCandidates from file
        if(fileScanner.hasNextInt()){
            String sNumCandidates = fileScanner.nextLine();
            numCandidates = numCandidates + Integer.parseInt(sNumCandidates);
        }
        else {
            System.out.print("Incorrect File Format (numCandidates)\n");
            return;
        }

        // read in candidate,party list from file
        String candidateLine = null;
        if(fileScanner.hasNext()){
            candidateLine = fileScanner.nextLine();
        }
        else {
            System.out.print("Incorrect File Format (candidate list)\n");
            return;
        }

        // parse line
        // format: name (p), name (p) ...
        // add to both candidates and currCandidates
        candidates = new ArrayList<>(numCandidates);
        currCandidates = new ArrayList<>(numCandidates);

        // will not be used in this function, but needs to be initialized
        eliminatedCandidates = new ArrayList<>(numCandidates);

        if(candidateLine == null){
            System.out.print("Invalid Third Line\n");
            return;
        }
        String[] parsed = candidateLine.split(",");

        // each index of parsed will contain: name (party)
        for(int i = 0; i < parsed.length; i++){
            parsed[i] = parsed[i].strip();
            String[] info = parsed[i].split(" ");

            // remove () from party
            info[1] = info[1].substring(1,2);

            // creating and adding candidate object to lists
            Candidate c = new Candidate(info[0], info[1]);
            candidates.add(c);
            currCandidates.add(c);
        }

        // read numVotes from file
        if(fileScanner.hasNextInt()){
          //want to do plus equals here 
            totalNumBallots = totalNumBallots + Integer.parseInt(fileScanner.nextLine());
        }
        else {
            System.out.print("Incorrect File Format (numBallots)\n");
            return;
        }
    } // readIRCSV
  
    /**
     * This function will read the majority of the input CSV file. For each line of input, an IRBallot object is
     * created with a unique ID, the ranking ArrayList is initialized to the size of the candidate's ArrayList, and
     * the rankIndex is set to 1. This function will then loop through the ballot’s ranking of the candidates and
     * place each candidate in the appropriate spot in the ranking ArrayList. Once the ballot is initialized, this
     * function will then assign the ballot to the first choice candidate
     */
    public void readBallots(Scanner fileScanner){
        int index = 0;
        while(fileScanner.hasNextLine()){
            String ballot = fileScanner.nextLine();

            // create ballot
            IRBallot b = new IRBallot(index, candidates.size());

            // loop through string and place candidates at correct index in ranking
            int commasEncountered = 0;
            for(int i = 0; i < ballot.length(); i++){
                char current = ballot.charAt(i);
                if(current == ','){
                    commasEncountered++;
                }
                else {
                    // convert char to int (by default gives ASCII number)
                    int rank = current-48-1; // 48 is the ASCII for 0 (-1 for 0 indexing)

                    // get correct candidate, then place in correct ranking spot
                    Candidate c = candidates.get(commasEncountered);
                    b.getRanking().set(rank, c);
                    b.setNumCandidates(b.getNumCandidates()+1);
                }
            }

            // look at first index of ranking and assign ballot to candidate
            Candidate check = b.getRanking().get(0);
            candidates.get(candidates.indexOf(check)).addBallot(b);
            index++;
        }
    } // readBallots

    /**
     * This function will use the variables totalNumBallots and
     * cNumBallots to calculate a percentage of the total vote each candidate has.
     * Once the majority has been found, the function will return the Candidate
     * object that corresponds to that majority.
     *
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
      if(maxBallot/this.getTotalNumBallots() > .50){
        return maxCan;
      }
      else{
        return null;
      }
    } // findMajority

    /**
     * Description - This function will loop through the currCandidates ArrayList
     * and calculate the minimum number of votes a candidate has. While looping,
     * if a tie for minimum number of votes is found, the coinToss() function is
     * called to determine which candidate continues as the minimum. Once the
     * ArrayList has been completely looped through, the Candidate that was
     * determined to have the least amount of votes is returned.
     *
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
          if(tieBreaker == 1){
            loser = currCandidates.get(i);
            min = curr;
          }
        }
      }

      return loser; //Return the candidate with the least amount of ballots

    } // findLeastCand

    /**
     * Description - This function will call findLeastCand() to determine which
     * candidate to move to from the currCandidates ArrayList to the eliminatedCandidates
     * ArrayList. The function will then also use this returned candidate object to
     * redistribute the ballots in the cBallots ArrayList. If the rankIndex of the
     * ballot is less than the number of candidates (and not equal to -1) and the candidate
     * at rankIndex is determined to still be in the currCandidates ArrayList, the ballot
     * can be added to the cBallots ArrayList of the new candidate. If both of these
     * conditions are not met, then the rankIndex will be set to -1 and the ballot will
     * stay assigned to the eliminated candidate.
     */
    public void redistributeBallots(){
      //Find the candidate with the least amount of votes, remove them from currCandidats and add to eliminatedCandidates
      Candidate loser = this.findLeastCand();
      currCandidates.remove(currCandidates.indexOf(loser));
      eliminatedCandidates.add(loser);

      //Variables to make the loop less messy
      IRBallot currBallot;
      ArrayList<Candidate> currBallotRanking;
      int currBallotRankIndex;
      boolean AddedBallot;
      int loserNumBallots = loser.getcBallots().size();
      int assignedBallots = 0;

      //Loop through all of the ballots in the losers Ballot ArrayList
      for(int i =0; i < loserNumBallots; i++){

        //Set all of the variables to the current Ballot information
        currBallot = (IRBallot) loser.getcBallots().get(i - assignedBallots);
        currBallotRanking = currBallot.getRanking();
        currBallotRankIndex = currBallot.getRankIndex();
        AddedBallot = false;

        //If the rankIndex is past the amount of candidates in the rank array, set to -1
        if(currBallotRankIndex >= currBallot.getNumCandidates()){
          currBallot.setRankIndex(-1);
          currBallotRankIndex = -1;
        }

        //This while loop will go until either the ballot is allocated to another candidate, or we've run out of candidates in the rank array
        while(currBallotRankIndex != -1 && !AddedBallot){

          //If the candidate at the current rank index is still in play, assign that ballot to the candidate
          if(currCandidates.contains(currBallotRanking.get(currBallotRankIndex) ) ){
            //if the rank index is past the size of the array, set to -1, otherwise increment
            if(currBallotRankIndex >= currBallot.getNumCandidates()-1){
              currBallot.setRankIndex(-1);
            }
            else{
              currBallot.setRankIndex(currBallotRankIndex+1);
            }
            //Add ballot to candidate
            currBallotRanking.get(currBallotRankIndex).addBallot(currBallot);
            AddedBallot = true;
            //remove ballot from loser
            loser.getcBallots().remove(i - assignedBallots);
            loser.setcNumBallots(loser.getcNumBallots() - 1);
            assignedBallots++;
            // i--;
          }
          //Otherwise, we have to check the next candidate in the ranking array
          else{
            //Check if the rankIndex is bigger than the array, if it is, set to -1
            if(currBallotRankIndex >= currBallot.getNumCandidates()-1){
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

    /**
     * This function will write some election information to the specified media file at the end of the election.
     * This information will include the winner of the election, the candidates and what percentage of votes went
     * to each candidate.
     */
    public void writeToMediaFile(){
        mediaFile.print("Election Result\n");
        mediaFile.print("------------------------------\n");
        // print winner + percentage of votes
        mediaFile.print(currCandidates.get(0).getcName()+", "+currCandidates.get(0).getcParty());
        double percentage = (currCandidates.get(0).getcNumBallots() / ((double)totalNumBallots)) * 100;
        mediaFile.print(" won with "+String.format("%.3f", percentage)+"% of the vote\n");

        // print rest of candidates + percentage of vote
        mediaFile.print("Eliminated Candidates: \n");
        for (Candidate c : eliminatedCandidates) {
            percentage = (c.getcNumBallots() / ((double)totalNumBallots)) * 100;
            mediaFile.print(c.getcName()+", "+c.getcParty()+" had "+String.format("%.3f", percentage)+"% of the vote when they were eliminated\n");
        }
        mediaFile.close();
    } // writeToMediaFile
  
    /**
     * This function will write all final election information to the specified audit file. This information will
     * include the basic election data, election results, as was put in the media file, as well as unique ballot IDs,
     * and a breakdown of how the ballots were distributed at the end of the analysis.
     */
    public void writeToAuditFile(){
        // print winner + percentage of votes
        double percentage = (currCandidates.get(0).getcNumBallots()/((double)totalNumBallots)) * 100;
        auditFile.print(currCandidates.get(0).getcName()+", "+currCandidates.get(0).getcParty()+": won with "+String.format("%.3f", percentage)+"% of the vote\n");
        auditFile.print("Ballots assigned to "+currCandidates.get(0).getcName()+":\n");

        // print all ballots assigned to winner
        for(Ballot b : currCandidates.get(0).getcBallots()){
            IRBallot b1 = (IRBallot) b;
            auditFile.print("    "+b1.getID()+" Ranking: ");
            for(int i = 0; i < b1.getNumCandidates(); i++){
                auditFile.print(i+": "+b1.getRanking().get(i).getcName());
                if(i != (b1.getNumCandidates()-1)){
                    auditFile.print(", ");
                } else {
                    auditFile.print("\n");
                }
            }
        }

        // print the rest of the candidates
        // print all ballots not reassigned from these candidates
        auditFile.print("Ballots not reassigned from eliminated candidates: \n");
        for (Candidate c : eliminatedCandidates) {
            auditFile.print("Ballots assigned to "+c.getcName()+", "+c.getcParty()+":\n");
            for(Ballot b : c.getcBallots()){
                IRBallot b1 = (IRBallot) b;
                auditFile.print("    "+b1.getID()+" Ranking: ");
                for(int i = 0; i < b1.getNumCandidates(); i++){
                    auditFile.print(i+": "+b1.getRanking().get(i).getcName());
                    if(i != (b1.getNumCandidates()-1)){
                        auditFile.print(", ");
                    } else {
                        auditFile.print("\n");
                    }
                }
            }
        }
        auditFile.close();
    } // writeToAuditFile
  
    /**
     * This function will write to the specified audit file the candidate, the percentage of votes, and the ballots
     * that the input candidate has before the candidate is eliminated. It is assumed that this function will be called
     * when it is determined that a candidate will be eliminated, but before the ballots assigned to this candidate are
     * redistributed.
     *
     * @param c Candidate to be eliminated from the IR election process
     */
    public void writeToAuditFile(Candidate c){
        // print candidate name+party and percentage of votes
        double percentage = (c.getcNumBallots()/((double)totalNumBallots)) * 100;
        auditFile.print(c.getcName()+", "+c.getcParty()+": eliminated with "+String.format("%.3f", percentage)+"% of the vote\n");
        auditFile.print("Ballots assigned to "+c.getcName()+":\n");

        // print all ballots assigned to candidate
        for(Ballot b : c.getcBallots()){
            IRBallot b1 = (IRBallot) b;
            auditFile.print("    "+b1.getID()+" Ranking: ");
            for(int i = 0; i < b1.getNumCandidates(); i++){
                auditFile.print(i+": "+b1.getRanking().get(i).getcName());
                if(i != (b1.getNumCandidates()-1)){
                    auditFile.print(", ");
                } else {
                    auditFile.print("\n");
                }
            }
        }
        auditFile.print("----------------------------------------\n");
    } // writeToAuditFile
    
    /**
     * This function will print information to the screen at the end of the program, giving the general election
     * info to the user. This information will include the candidate that won and the percentage of votes that
     * they received.
     */
    public void printToScreen(){
        System.out.print("Election Results\n");
        System.out.print("------------------------------\n");
        // print winner + percentage of votes
        System.out.print(currCandidates.get(0).getcName()+", "+currCandidates.get(0).getcParty());
        double percentage = (currCandidates.get(0).getcNumBallots() / ((double)totalNumBallots)) * 100;
        System.out.print(" won with "+String.format("%.3f", percentage)+"% of the vote\n");
    } // printToScreen


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

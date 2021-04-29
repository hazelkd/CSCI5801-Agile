// POElection
// The main class for running the PO election algorithm
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class POElection extends VotingSystem{
  /**
   * Driving force of the PO Election class. It will call all of the functions for the algorithm of PO voting.
   * For right now, it just reads in the ballots and does nothing with them.
   *
   * @return 0
   */
   public int runElection(){

     for (int i = 0; i < count; i++){
         this.readPOCSV(scannerNameList.get(i));
     // read/make/distribute ballots
         this.readBallots(scannerNameList.get(i));
     }

     System.out.print("PO ballots read in\n");

     return 0;
   }


   /**
    * This function will read lines 2-4 in a CSV file that has been determined to be an PO CSV file. It will set the
    * variable totalNumBallots and initialize the candidates ArrayList to be the size read from line 4. In addition,
    * it will create and add candidate objects to this candidate ArrayList.
    */
   public void readPOCSV(Scanner fileScanner){
     int numCandidates;
     // read in numCandidates from file
     if(fileScanner.hasNextInt()){
         String sNumCandidates = fileScanner.nextLine();
         numCandidates = Integer.parseInt(sNumCandidates);
     }
     else {
         System.out.print("Incorrect File Format (numCandidates)\n");
         return;
     }

     // read in candidate,party list from file
     String candidateLine = "";
     if(fileScanner.hasNext()){
         candidateLine = fileScanner.nextLine();
     }
     else {
         System.out.print("Incorrect File Format (candidate list)\n");
         return;
     }

     // parse line
     // format: [Candidate,Party],[Candidate,Party],...
     if(candidates == null){
       candidates = new ArrayList<>(numCandidates);

       // parse line -> array will switch between candidate and party
       String[] parsed = candidateLine.split(",");
       //removes all brackets
       for(int i = 0; i < parsed.length; i++){
           if(i%2 == 0){
               parsed[i] = parsed[i].substring(1);
           } else {
               parsed[i] = parsed[i].substring(0,1);
           }
       }

       // create candidate objects
       for(int i = 0; i < parsed.length; i = i + 2){
           candidates.add(new Candidate(parsed[i], parsed[i+1]));
       }
     }

     // read numVotes from file
     if(fileScanner.hasNextInt()){
         totalNumBallots = totalNumBallots + Integer.parseInt(fileScanner.nextLine());
     }
     else {
         System.out.print("Incorrect File Format (numBallots)\n");
         return;
     }
   } // readPOCSV


   /**
    * This function will create ballot objects for each individual ballot that is read in from the CSV. The function
    * will read one ballot at a time, create a ballot object for each, and will assign them a unique ID number in
    * the object, which will be stored in the ID object for each ballot.
    * It will also distribute each ballot that is touched to their respective candidate that they voted for, which
    * will be stored in the cBallots object in the candidate class.
    */
   public void readBallots(Scanner fileScanner){
       int index = 0;
       while(fileScanner.hasNextLine()){
           String[] ballot = fileScanner.nextLine().split(",");
           // length of ballot ^ will determine which candidate it is voting for
           // ie: length of 3 -> voting for third candidate
           Candidate c = candidates.get(ballot.length-1);
           c.addBallot(new POBallot(index, c));
           index++;
       }
   } // readBallots


}

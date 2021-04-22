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

     readPOCSV();

     readBallots();

     return 0;
   }


   /**
    * This function will read lines 2-4 in a CSV file that has been determined to be an PO CSV file. It will set the
    * variable totalNumBallots and initialize the candidates ArrayList to be the size read from line 4. In addition,
    * it will create and add candidate objects to this candidate ArrayList.
    */
   public void readPOCSV(){
       int numCandidates;
       // read in numCandidates from file
       if(csvFile.hasNextInt()){
           String sNumCandidates = csvFile.nextLine();
           numCandidates = Integer.parseInt(sNumCandidates);
       }
       else {
           System.out.print("Incorrect File Format (numCandidates)\n");
           return;
       }

       // read in candidate,party list from file
       String candidateLine = null;
       if(csvFile.hasNext()){
           candidateLine = csvFile.nextLine();
       }
       else {
           System.out.print("Incorrect File Format (candidate list)\n");
           return;
       }

       // parse line
       // format: name (p), name (p) ...
       // add to both candidates and currCandidates
       candidates = new ArrayList<>(numCandidates);

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
       }

       // read numVotes from file
       if(csvFile.hasNextInt()){
           totalNumBallots = Integer.parseInt(csvFile.nextLine());
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
   public void readBallots(){
       int index = 0;
       while(csvFile.hasNextLine()){
           String[] ballot = csvFile.nextLine().split(",");
           // length of ballot ^ will determine which candidate it is voting for
           // ie: length of 3 -> voting for third candidate
           Candidate c = candidates.get(ballot.length-1);
           c.addBallot(new POBallot(index, c));
           index++;
       }
   } // readBallots


}

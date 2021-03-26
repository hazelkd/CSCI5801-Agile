// OLPElection
// The main class for running the OPL election algorithm
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class OPLElection extends VotingSystem{
    /**
     * Driving force of the OPL Election class. It will call all of the functions for the algorithm of OPL voting.
     * It will also close the media, audit, and CSV files at the end of the function, which will be the 
     * end of this election process.
     *
     * @return -1 to indicate error, 0 if everything operated properly
     */
    public int runElection(){ 

        // read in the rest of the file & set totalNumBallots, totalNumSeats, numSeatsLeft
        // make/assign party objects and candidate objects
        readOPLCSV();
        // read/make/distribute ballots
        readBallots();

        // calculate number of seats for each party
        partyNumBallots();

        // allocate the first round of seats
        allocateByQuota();

        // allocate by remainder
        if (numSeatsLeft != 0) {
            allocateByRemainder();
        }

        //write to media
        writeToMediaFile();
        writeToAuditFile(); // write the winners to the audit file
        printToScreen();

        // close CSV file
        csvFile.close();

        return 0;
    }

    /**
     * This function will read in the first part of the OPL CSV file. It will set the variables totalNumBallots,
     * totalNumSeats, and numSeatsLeft that are in the OPL Election class. The function will read this information
     * directly from the CSV, and numSeatsLeft will not be read but will be set equal to totalNumSeats initially.
     * It will also set the quota for the election, which is calculated by dividing the total number of votes by the
     * number of seats in the election. This result will be put in the quota variable in the OPL Election class.
     * The function will also create the party objects for each party that is read in from the CSV. As each party is
     * read, an object will be created until there are no more parties/candidates to read in. This will also put each
     * party into the party array list in this class.
     * Additionally, this function will create candidate objects and assign them to their respective parties. It will
     * read in the candidate name from the CSV, and assign it to the party class that is specified.
     */
    public void readOPLCSV(){
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
        String candidateLine = "";
        if(csvFile.hasNext()){
            candidateLine = csvFile.nextLine();
        }
        else {
            System.out.print("Incorrect File Format (candidate list)\n");
            return;
        }

        // parse line
        // format: [Candidate,Party],[Candidate,Party],...
        candidates = new ArrayList<>(numCandidates);
        party = new ArrayList<>(); // trimToSize at end

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

        // create party and candidate objects
        ArrayList<String> parties = new ArrayList<>();
        for(int i = 0; i < parsed.length; i = i + 2){
            if(!parties.contains(parsed[i + 1])) { // party has not already been encountered
                parties.add(parsed[i + 1]);
                party.add(new Party(parsed[i + 1])); // add new party
            }
            candidates.add(new Candidate(parsed[i], parsed[i+1]));
        }

        // assign candidates to parties
        for (Candidate candidate : candidates) {
            String p = candidate.getcParty();
            Party addTo = party.get(parties.indexOf(p));
            addTo.addCandidate(candidate);
        }

        // read numSeats from file
        if(csvFile.hasNextInt()){
            totalNumSeats = Integer.parseInt(csvFile.nextLine());
            numSeatsLeft = totalNumSeats;
        }
        else {
            System.out.print("Incorrect File Format (numSeats)\n");
            return;
        }

        // read numVotes from file
        if(csvFile.hasNextInt()){
            totalNumBallots = Integer.parseInt(csvFile.nextLine());
        }
        else {
            System.out.print("Incorrect File Format (numBallots)\n");
            return;
        }

        // set quota
        quota = totalNumBallots/totalNumSeats;

        //trim party list
        party.trimToSize();

    } // readOPLCSV

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
            c.addBallot(new OPLBallot(index, c.getcParty(), c));
            index++;
        }
    } // readBallots

    /**
     * This function will print information to the screen at the end of the program, giving the general election
     * info to the user. This information will include the party, the number of seats that each party was given,
     * and which candidates got the seats for each party.
     */
    public void printToScreen(){
        System.out.print("------------------------------\n");
        System.out.print("Election Results\n");
        // for each party, print out name, numSeats, and the first x candidates in the candidate arrayList
        for (Party value : party) {
            System.out.print("------------------------------\n");
            System.out.print(value.getpName()+"\n");
            System.out.print("  Number of Seats Won: " + value.getNumSeats() + "\n");
            System.out.print("  Candidates filling seats: \n");
            for (int j = 0; j < value.getNumSeats(); j++) {
                System.out.print("     " + value.getCandidates().get(j).getcName() + "\n");
            }
        }
        System.out.print("------------------------------\n");
    }

    /**
     * This function will write some election information to the specified media file at the end of the election.
     * This information will include the winner(s) of the election, the candidates and what percentage of votes went
     * to each candidate. In this case, there are no specified winners, so it will store information for each party
     * that obtained seats.
     */
    public void writeToMediaFile(){
        mediaFile.print("Election Results\n");
        // for each party, print out name, numSeats, and the first x candidates in the candidate arrayList
        for (Party value : party) {
            mediaFile.print("------------------------------\n");
            mediaFile.print(value.getpName()+"\n");
            mediaFile.print("  Number of Seats Won: " + value.getNumSeats()+"\n");
            mediaFile.print("  Candidates filling seats: \n");
            for (int j = 0; j < value.getNumSeats(); j++) {
                double percent = (((double) value.getCandidates().get(j).getcNumBallots()) / (totalNumBallots)) * 100;
                mediaFile.print("     " + value.getCandidates().get(j).getcName() + " with " + percent + "% of the vote\n");
            }
        }
        mediaFile.close();
    }

    /**
     * This function will write all of election information to the specified audit file at the end of the election. 
     * This information will include the basic election data, election results, as was put in the media file, as well 
     * as unique ballot IDs, and a breakdown of how the ballots were distributed at each step of the analysis.
     */
    public void writeToAuditFile(){
        // for each party, print out name, numSeats, and the first x candidates (and any candidate info) in the candidate arrayList
        for (Party value : party) {
            auditFile.print("Party: "+value.getpName()+"\n");
            auditFile.print("  Number of Seats Won: " + value.getNumSeats()+"\n");
            auditFile.print("  Candidates filling seats: \n");
            for (int j = 0; j < value.getNumSeats(); j++) {
                double percent = (((double) value.getCandidates().get(j).getcNumBallots()) / (totalNumBallots)) * 100;
                auditFile.print("     " + value.getCandidates().get(j).getcName() + " with " + percent + "% of the vote\n");
                auditFile.print("     Ballots assigned to this candidate: \n");
                // loop through ballots
                ArrayList<Ballot> ballots = value.getCandidates().get(j).getcBallots();
                for (Ballot ballot : ballots) {
                    OPLBallot b = (OPLBallot) ballot;
                    auditFile.print("        " + b.getID() + ": " + b.getbCandidate().getcName() + ", " + b.getbParty()+"\n");
                }
            }
            if(value.getNumSeats() < value.getCandidates().size()){
                auditFile.print("  Candidates not filling seats: \n");
                for (int j = value.getNumSeats(); j < value.getCandidates().size(); j++){
                    double percent = (((double) value.getCandidates().get(j).getcNumBallots()) / (totalNumBallots)) * 100;
                    auditFile.print("     " + value.getCandidates().get(j).getcName() + " with " + percent + "% of the vote\n");
                    auditFile.print("     Ballots assigned to this candidate: \n");
                    // loop through ballots
                    ArrayList<Ballot> ballots = value.getCandidates().get(j).getcBallots();
                    for (Ballot ballot : ballots) {
                        OPLBallot b = (OPLBallot) ballot;
                        auditFile.print("        " + b.getID() + ": " + b.getbCandidate().getcName() + ", " + b.getbParty()+"\n");
                    }
                    auditFile.print("\n");
                }
            } else {
                auditFile.print("\n");
            }
        }
        auditFile.close();
    }

    /**
     * This function will be used to allocate the first round of seats to each party.
     * It will loop through each party in the party ArrayList.
     * It will access the numBallots class variable for each party and divide it by the quota.
     * It will take the quotient from this calculation and set the numSeats class variable for the party
     * and it will take the remainder of this calculation and set the remainder class variable for the party.
     */
    public void allocateByQuota(){
        int size = party.size();

        for(int i = 0; i < size; i++) {
            int numB = (party.get(i)).getpNumBallots();
            int seatsByQuota = numB/quota;
            int remain = numB%quota;
            (party.get(i)).setNumSeats(seatsByQuota);
            (party.get(i)).setRemainder(remain);
            numSeatsLeft = numSeatsLeft - seatsByQuota;
        }
    }

    /**
     * This function will allocate the second round of seats for each party based on the remaining votes
     * contained in the remainder object for each party in the election.
     * Remaining seats will be allocated based on the parties that have the highest remainder.
     * The party with the highest remainder will be given a seat first, and then it moves down the list until
     * there are no seats left to be allocated.
     */
    public void allocateByRemainder(){
        // Need to add coin toss if two remainders are equal

        int size = party.size();
        int numSeats = numSeatsLeft;

        ArrayList<Party> highestRemain = new ArrayList<>();

        int filledSeats = 0;

        while (filledSeats < numSeats ){

            int topIndex = 0;   //first party is set as the maximum initially

            for (int j = 0; j < size; j++) {
                if(highestRemain.contains(party.get(topIndex))){    //if current party is already in highestRemain, increment topIndex
                    topIndex++;
                }
                if(highestRemain.contains(party.get(j))){   //if current party is in highest remain, increment current party index
                    j++;
                }
                if (party.get(j).getRemainder()> party.get(topIndex).getRemainder()){   //if current party has greater remainder than curr topIndex
                    topIndex = j;
                }
                if (filledSeats == numSeats - 1){
                    if (party.get(j).getRemainder() == party.get(topIndex).getRemainder()){
                        int result = coinToss(2);
                        if (result == 0){
                            topIndex = j;
                        }
                    }
                }
            }
            highestRemain.add(filledSeats, party.get(topIndex));    //add max remainder seat that was decided from this iteration
            party.get(topIndex).setNumSeats(party.get(topIndex).getNumSeats() + 1);
            filledSeats++;   //move on to allocating next seat
        }
        numSeatsLeft = numSeats - filledSeats;
    }

    /**
     * Iterate through the party array list object in this class. As it is iterating through, it will call the 
     * calculateNumBallots() function for each party, which will calculate the number of ballots that each party has. 
     * As it is iterating through the party list, it will also call sortCandidate() function, which will sort the 
     * candidates in each party, with the person who has received the most votes at the beginning of the array. 
     * This function will be called after all objects have been created and assigned.
     */
    public void partyNumBallots(){
        for (int i = 0; i < party.size(); i++) {
            party.get(i).calculateNumBallots(); //calculate the number of ballots for each party
            party.get(i).sortCandidates(); // order the candidates in the party 
        }
    }

    // Getters & Setters

    public int getTotalNumSeats() {
        return totalNumSeats;
    }

    public void setTotalNumSeats(int totalNumSeats) {
        this.totalNumSeats = totalNumSeats;
    }

    public int getNumSeatsLeft() {
        return numSeatsLeft;
    }

    public void setNumSeatsLeft(int numSeatsLeft) {
        this.numSeatsLeft = numSeatsLeft;
    }

    public ArrayList<Party> getParty() {
        return party;
    }

    public void setParty(ArrayList<Party> party) {
        this.party = party;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    private int totalNumSeats;
    private int numSeatsLeft;
    private ArrayList<Party> party;
    private int quota;
}

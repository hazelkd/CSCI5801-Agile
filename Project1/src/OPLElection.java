// OLPElection
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class OPLElection extends VotingSystem{
    /* Driving force of the OPL Election class. It will call all of the functions for the algorithm of OPL voting. 
     * It will also close the media, audit, and CSV files at the end of the function, which will be the 
     * end of this election process.
     * @return -1 to indicate error, 0 if everything operated properly
     */
    public int runElection(){ 

        // read in the rest of the file & set totalNumBallots, totalNumSeats, numSeatsLeft
        // make/assign party objects and candidate objects
        readOPLCSV();
        // read/make/distribute ballots
        readBallots();
 
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
        csvFile.close() 

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
            System.out.println("Incorrect File Format (numCandidates)");
            return;
        }

        // read in candidate,party list from file
        String candidateLine = null;
        if(csvFile.hasNext()){
            candidateLine = csvFile.nextLine();
        }
        else {
            System.out.println("Incorrect File Format (candidate list)");
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
                parsed[i] = parsed[i].substring(0,2);
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
            System.out.println("Incorrect File Format (numSeats)");
            return;
        }

        // read numVotes from file
        if(csvFile.hasNextInt()){
            totalNumBallots = Integer.parseInt(csvFile.nextLine());
        }
        else {
            System.out.println("Incorrect File Format (numBallots)");
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
        System.out.println("------------------------------");
        System.out.println("Election Results");
        // for each party, print out name, numSeats, and the first x candidates in the candidate arrayList
        for (Party value : party) {
            System.out.println("------------------------------");
            System.out.println(value.getpName());
            System.out.println("  Number of Seats Won: " + value.getNumSeats());
            System.out.println("  Candidates filling seats: ");
            for (int j = 0; j < value.getNumSeats(); j++) {
                System.out.println("     " + value.getCandidates().get(j).getcName());
            }
        }
        System.out.println("------------------------------");
    }

    /**
     * This function will write some election information to the specified media file at the end of the election.
     * This information will include the winner(s) of the election, the candidates and what percentage of votes went
     * to each candidate. In this case, there are no specified winners, so it will store information for each party
     * that obtained seats.
     */
    public void writeToMediaFile(){
        mediaFile.println("Election Results");
        // for each party, print out name, numSeats, and the first x candidates in the candidate arrayList
        for (Party value : party) {
            mediaFile.println("------------------------------");
            mediaFile.println(value.getpName());
            mediaFile.println("  Number of Seats Won: " + value.getNumSeats());
            mediaFile.println("  Candidates filling seats: ");
            for (int j = 0; j < value.getNumSeats(); j++) {
                double percent = (((double) value.getCandidates().get(j).getcNumBallots()) / (totalNumBallots)) * 100;
                mediaFile.println("     " + value.getCandidates().get(j).getcName() + " with " + percent + "% of the vote");
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
            auditFile.println(value.getpName());
            auditFile.println("  Number of Seats Won: " + value.getNumSeats());
            auditFile.println("  Candidates filling seats: ");
            for (int j = 0; j < value.getNumSeats(); j++) {
                double percent = (((double) value.getCandidates().get(j).getcNumBallots()) / (totalNumBallots)) * 100;
                auditFile.println("     " + value.getCandidates().get(j).getcName() + " with " + percent + "% of the vote");
                auditFile.println("     Ballots assigned to this candidate: ");
                // loop through ballots
                ArrayList<Ballot> ballots = value.getCandidates().get(j).getcBallots();
                for (Ballot ballot : ballots) {
                    OPLBallot b = (OPLBallot) ballot;
                    auditFile.println("        " + b.getID() + ": " + b.getbCandidate().getcName() + ", " + b.getbParty());
                }

            }
        }
        auditFile.close();
    }
    public void allocateByQuota(){}
    public void allocateByRemainder(){}

    /*
     * Iterate through the party array list object in this class. As it is iterating through, it will call the 
     * calculateNumBallots() function for each party, which will calculate the number of ballots that each party has. 
     * As it is iterating through the party list, it will also call sortCandidate() function, which will sort the 
     * candidates in each party, with the person who has received the most votes at the beginning of the array. 
     * This function will be called after all objects have been created and assigned.
     */
    public void partyNumBallots(){
        for (int i = 0; i < party.size(); i++) {
            party.get(i).calculateNumBallots(); //calculate the number of ballots for each aprty
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

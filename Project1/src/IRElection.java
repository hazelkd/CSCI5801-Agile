// IRElection
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class IRElection extends VotingSystem{
    public int runElection(){ return 0; }

    /**
     * This function will read lines 2-4 in a CSV file that has been determined to be an IR CSV file. It will set the
     * variable totalNumBallots and initialize the candidates ArrayList to be the size read from line 4. In addition,
     * it will create and add candidate objects to this candidate ArrayList.
     * FORMAT:
     * IR -> already read
     * NumCandidates
     * Name (Party), Name (Party), ...
     * NumBallots
     * 1,2,3,4 ... (ballot)
     * ...
     */
    public void readIRCSV(){
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
        }

        // parse line
        // format: name (p), name (p) ...
        // add to both candidates and currCandidates
        candidates = new ArrayList<>(numCandidates);
        currCandidates = new ArrayList<>(numCandidates);
        String[] parsed = candidateLine.split(",");

        // each index of parsed will contain: name (party)
        for(int i = 0; i < parsed.length; i++){
            String[] info = parsed[i].split(" ");

            // remove () from party
            info[1].substring(1,2);

            // creating and adding candidate object to lists
            Candidate c = new Candidate(info[0], info[1]);
            candidates.add(c);
            currCandidates.add(c);
        }

        // read numVotes from file
        if(csvFile.hasNextInt()){
            totalNumBallots = Integer.parseInt(csvFile.nextLine());
        }
        else {
            System.out.println("Incorrect File Format (numBallots)");
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
    public void readBallots(){
        int index = 0;
        while(csvFile.hasNextLine()){
            String ballot = csvFile.nextLine();

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

                }
            }

            // look at first index of ranking and assign ballot to candidate
            Candidate check = b.getRanking().get(0);
            candidates.get(candidates.indexOf(check)).addBallot(b);
        }
    } // readBallots

    public Candidate findMajority(){ return null; }
    public Candidate findLeastCand(){ return null; }
    public void redistributeBallots(){}

    /**
     * This function will write some election information to the specified media file at the end of the election.
     * This information will include the winner of the election, the candidates and what percentage of votes went
     * to each candidate.
     */
    public void writeToMediaFile(){
        mediaFile.println("Election Results");
        mediaFile.println("------------------------------");
        // print winner + percentage of votes
        mediaFile.print(currCandidates.get(0).getcName()+", "+currCandidates.get(0).getcParty());
        double percentage = currCandidates.get(0).getcNumBallots() / ((double)totalNumBallots);
        mediaFile.println(" won with "+percentage+"% of the vote");

        // print rest of candidates + percentage of vote
        mediaFile.println("Eliminated Candidates: ");
        for (Candidate c : eliminatedCandidates) {
            percentage = c.getcNumBallots() / ((double)totalNumBallots);
            mediaFile.println(c.getcName()+", "+c.getcParty()+" had "+percentage+"% of the vote");
        }
    } // writeToMediaFile

    /**
     * This function will write all final election information to the specified audit file. This information will
     * include the basic election data, election results, as was put in the media file, as well as unique ballot IDs,
     * and a breakdown of how the ballots were distributed at the end of the analysis.
     */
    public void writeToAuditFile(){
        // print winner + percentage of votes
        double percentage = currCandidates.get(0).getcNumBallots()/((double)totalNumBallots);
        auditFile.println(currCandidates.get(0).getcName()+", "+currCandidates.get(0).getcParty()+": won with "+percentage+"% of the vote");
        auditFile.println("Ballots assigned to "+currCandidates.get(0).getcName()+":");

        // print all ballots assigned to winner
        for(Ballot b : currCandidates.get(0).getcBallots()){
            IRBallot b1 = (IRBallot) b;
            auditFile.print("    "+b1.getID()+" Ranking: ");
            for(int i = 0; i < b1.getRanking().size(); i++){
                auditFile.print(i+": "+b1.getRanking().get(i).getcName());
                if(i != (b1.getRanking().size()-1)){
                    auditFile.print(", ");
                } else {
                    auditFile.println();
                }
            }
        }

        // print the rest of the candidates
        // print all ballots not reassigned from these candidates
        auditFile.println("Ballots not reassigned from eliminated candidates: ");
        for (Candidate c : eliminatedCandidates) {
            auditFile.println("Ballots assigned to "+c.getcName()+", "+c.getcParty()+":");
            for(Ballot b : c.getcBallots()){
                IRBallot b1 = (IRBallot) b;
                auditFile.print("    "+b1.getID()+" Ranking: ");
                for(int i = 0; i < b1.getRanking().size(); i++){
                    auditFile.print(i+": "+b1.getRanking().get(i).getcName());
                    if(i != (b1.getRanking().size()-1)){
                        auditFile.print(", ");
                    } else {
                        auditFile.println();
                    }
                }
            }
        }

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
        double percentage = c.getcNumBallots()/((double)totalNumBallots);
        auditFile.println(c.getcName()+", "+c.getcParty()+": eliminated with "+percentage+"% of the vote");
        auditFile.println("Ballots assigned to "+c.getcName()+":");

        // print all ballots assigned to candidate
        for(Ballot b : c.getcBallots()){
            IRBallot b1 = (IRBallot) b;
            auditFile.print("    "+b1.getID()+" Ranking: ");
            for(int i = 0; i < b1.getRanking().size(); i++){
                auditFile.print(i+": "+b1.getRanking().get(i).getcName());
                if(i != (b1.getRanking().size()-1)){
                    auditFile.print(", ");
                } else {
                    auditFile.println();
                }
            }
        }
        auditFile.println("----------------------------------------");
    }

    /**
     * This function will print information to the screen at the end of the program, giving the general election
     * info to the user. This information will include the candidate that won and the percentage of votes that
     * they received.
     */
    public void printToScreen(){
        // print winner + percentage of votes
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

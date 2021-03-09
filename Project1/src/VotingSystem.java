// VotingSystem
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson
// VotingSystem
// The main class for running the entire system
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class VotingSystem {
    /**
     * This function will prompt the user for the name of the CSV containing the election information. It will then
     * read in the first line of the file and create a new instance of either OPLElection or IRElection based on that
     * line. Once this instance has been created, the class variables csvName, csvFile, and electionType will be set
     *
     * @return sys A VotingSystem instance with csvName, csvFile, and electionType set
     */
    public static VotingSystem promptCSV(){
        VotingSystem sys = null;
        String name = null;
        boolean flag = false;
        Scanner csvFile = null;
        String firstLine;

        Scanner fromUser = new Scanner(System.in);

        // try input twice
        for(int i = 0; i < 2; i++){
            flag = false;
            System.out.println("Please enter the name of your file (do not include .txt extension): ");
            name = fromUser.nextLine();
            String namePath = "testing/" + name + ".txt"; // change for final version: name + ".txt"
            try {
                csvFile = new Scanner(new File(namePath));
                flag = true;
            } catch (Exception e) {
                System.out.println(name  + ".txt not found");
            }
            if(flag){ break; } // if file read successfully on first try
        }
        if(!flag){
            System.out.println("Unable to open file, exiting");
            return null;
        }

        if(csvFile.hasNextLine()){
            firstLine = csvFile.nextLine();
        } else {
            System.out.println("Improper file format, exiting");
            return null;
        }

        // check if OLP or IR
        if(firstLine.equals("OPL")){
            sys = new OPLElection();
            sys.setCsvName(name);
            sys.setCsvFile(csvFile);
            sys.setElectionType(firstLine);
        }
        else if(firstLine.equals("IR")) {
            sys = new IRElection();
            sys.setCsvName(name);
            sys.setCsvFile(csvFile);
            sys.setElectionType(firstLine);
        }
        else {
            System.out.println("Invalid election type, exiting");
            return null;
        }
        return sys; // need to check that sys is not null in calling method
    } // promptCSV

    /**
     * This function will prompt the user for the desired audit file name. If one is not provided, the function will
     * generate a unique name. It will then create the file and open it for writing. It then sets the class variable
     * auditFile to the PrintWriter to be accessed and written to throughout the algorithm.
     */
    public void promptAudit(){
        String name = null;
        Scanner fromUser = new Scanner(System.in);
        System.out.println("What do you want the name of the audit file to be?");
        System.out.println("Do not include any extensions, the system will produce a .txt file");
        System.out.println("If you wish to use the default naming conventions, enter default");
        name = fromUser.nextLine();
        if(!name.equals("default")){
            System.out.println("You have entered: "+name+"\n Is this correct? (Y/N)");
            // check Y/N -> prompt accordingly
            String response = fromUser.nextLine();
            if(response.equals("Y") || response.equals("y")){
                System.out.println("Proceeding with input name");
            }
            else if(response.equals("N") || response.equals("n")){
                System.out.println("Enter the correct name: ");
                name = fromUser.nextLine();
            }
            else {
                System.out.println("Invalid entry, proceeding with first input name");
            }
            name += ".txt";
        }
        else {
            System.out.println("Using default");
            name = "default.txt"; // need to figure out actual conventions
        }
        try {
            auditFile = new PrintWriter(new File(name));
        } catch (Exception e) {
            System.out.println("Error opening file, exiting");
            return; // need to check that var is set in calling method
        }
        System.out.println();
        return;
    } // promptAudit

    /**
     * This function will prompt the user for the desired media file name. If one is not provided, the function will
     * generate a unique name. It will then create the file and open it for writing. It then sets the class variable
     * mediaFile to the PrintScanner to be accessed and written to throughout the algorithm.
     */
    public void promptMedia(){
        String name = null;
        Scanner fromUser = new Scanner(System.in);
        System.out.println("What do you want the name of the media file to be?");
        System.out.println("Do not include any extensions, the system will produce a .txt file");
        System.out.println("If you wish to use the default naming conventions, enter default");
        name = fromUser.nextLine();
        if(!name.equals("default")){
            System.out.println("You have entered: "+name+"\n Is this correct? (Y/N)");
            // check Y/N -> prompt accordingly
            String response = fromUser.nextLine();
            if(response.equals("Y") || response.equals("y")){
                System.out.println("Proceeding with input name");
            }
            else if(response.equals("N") || response.equals("n")){
                System.out.println("Enter the correct name: ");
                name = fromUser.nextLine();
            }
            else {
                System.out.println("Invalid entry, proceeding with first input name");
            }
            name += ".txt";
        }
        else {
            System.out.println("Using default");
            name = "default.txt"; // need to figure out actual conventions
        }
        try {
            mediaFile = new PrintWriter(new File(name));
        } catch (Exception e) {
            System.out.println("Error opening file, exiting");
            return; // need to check that var is set in calling method
        }
        System.out.println();
        return;
    } // promptMedia

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

    public static void main(String[] args){
//        VotingSystem sys = promptCSV();
//        System.out.println(sys == null);
//        if(sys != null){
//            System.out.println(sys.getCsvName());
//            System.out.println(sys.getElectionType());
//            sys.promptAudit();
//            sys.promptMedia();
//            System.out.println(sys.getAuditFile());
//            System.out.println(sys.getMediaFile());
//            if(sys.getElectionType().equals("OPL")){
//                OPLElection oplSys = (OPLElection) sys;
//                oplSys.readOPLCSV();
//                System.out.println("Checking that all vars are set correctly: ");
//                System.out.println("totalNumBallots: "+oplSys.getTotalNumBallots());
//                System.out.println("totalNumSeats: "+oplSys.getTotalNumSeats());
//                System.out.println("numSeatsLeft: "+oplSys.getNumSeatsLeft());
//                System.out.println("quota: "+oplSys.getQuota());
//                System.out.println("NumCandidates: "+oplSys.getCandidates().size());
//                System.out.println("Candidates: ");
//                ArrayList<Candidate> c = oplSys.getCandidates();
//                for(int i = 0; i < c.size(); i++){
//                    System.out.println("Name: "+c.get(i).getcName()+", Party: "+c.get(i).getcParty());
//                }
//                System.out.println("Parties: ");
//                ArrayList<Party> p = oplSys.getParty();
//                for(int i = 0; i < p.size(); i++){
//                    System.out.println(p.get(i).getpName()+": ");
//                    System.out.println("numCandidates: "+p.get(i).getCandidates().size());
//                    for(int j = 0; j<p.get(i).getCandidates().size(); j++){
//                        System.out.println("  - "+p.get(i).getCandidates().get(j).getcName());
//                    }
//                }
//                oplSys.readBallots();
//            }
//            else if(sys.getElectionType().equals("IR")){
//                IRElection irSys = (IRElection) sys;
//            }
//        }
//        ArrayList<Candidate> candidates = new ArrayList<>(4);
//        // Rosen (D), Kleinberg (R), Chou (I), Royce (L)
//        candidates.add(new Candidate("Rosen", "D"));
//        candidates.add(new Candidate("Kleinberg", "R"));
//        candidates.add(new Candidate("Chou", "I"));
//        candidates.add(new Candidate("Royce", "L"));
//        IRBallot b = new IRBallot(0, candidates.size());
//        String ballot = "1,3,4,2";
//        int commasEncountered = 0;
//        for(int i = 0; i < ballot.length(); i++){
//            char current = ballot.charAt(i);
//            if(current == ',') {
//                commasEncountered++;
//            } else {
//                int rank = current-48-1; // 48 is the ASCII for 0
//                Candidate c = candidates.get(commasEncountered);
//                b.getRanking().set(rank, c);
//            }
//        }
//
//        System.out.println(b.getRankIndex());
//        for(int i = 0; i < b.getRanking().size(); i++){
//            System.out.println(b.getRanking().get(i).getcName());
//            System.out.println(b.getRanking().get(i).getcParty());
//        }
        VotingSystem electionT = promptCSV();
        electionT.promptAudit();
        electionT.promptMedia();
        if (electionT.getElectionType().equals("OPL")){
            OPLElection newOPL = new OPLElection();
            newOPL.runElection();
        }
        else if(electionT.getElectionType().equals("IR")){
            IRElection newIR = new IRElection();
            newIR.runElection();
        }
        //else? need to do something if it's neither?
    }

    // Getters & Setters

    public PrintWriter getAuditFile() {
        return auditFile;
    }

    public void setAuditFile(PrintWriter auditFile) {
        this.auditFile = auditFile;
    }

    public PrintWriter getMediaFile() {
        return mediaFile;
    }

    public void setMediaFile(PrintWriter mediaFile) {
        this.mediaFile = mediaFile;
    }

    public String getCsvName() {
        return csvName;
    }

    public void setCsvName(String csvName) {
        this.csvName = csvName;
    }

    public Scanner getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(Scanner csvFile) {
        this.csvFile = csvFile;
    }

    public ArrayList<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(ArrayList<Candidate> candidates) {
        this.candidates = candidates;
    }

    public int getTotalNumBallots() {
        return totalNumBallots;
    }

    public void setTotalNumBallots(int totalNumBallots) {
        this.totalNumBallots = totalNumBallots;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStopTime() {
        return stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public String getElectionType() {
        return electionType;
    }

    public void setElectionType(String electionType) {
        this.electionType = electionType;
    }

    protected PrintWriter auditFile;
    protected PrintWriter mediaFile;
    protected String csvName;
    protected Scanner csvFile;
    protected ArrayList<Candidate> candidates;
    protected int totalNumBallots;
    protected long startTime;
    protected long stopTime;
    protected String electionType;

}
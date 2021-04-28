// VotingSystem
// The main class for running the entire system
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson
import java.lang.reflect.Array;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.*;  

public class VotingSystem {
    /**
     * This function will prompt the user for the name of the CSV containing the election information. It will then
     * read in the first line of the file and create a new instance of OPLElection, IRElection, or POElection based on that
     * line. Once this instance has been created, the class variables csvName, csvFile, and electionType will be set
     *
     * @return sys A VotingSystem instance with csvName, csvFile, and electionType set
     */
    public static VotingSystem promptCSV(){
        VotingSystem sys = null;
        String name = null;
        boolean flag = false;
        boolean fileStatus = true;
        boolean invalidFlag = false;
        String firstLine = null;
        String statusKey = null;
        Scanner csvFile = null;
        ArrayList<Scanner> scannerList = new ArrayList<>();

        int count = 0;

        Scanner fromUser = new Scanner(System.in);

        //keep looping while the user is entering files
        while(fileStatus){
        // try input twice
            for(int i = 0; i < 2; i++){
                flag = false;
                System.out.print("Please enter the name of your file (do not include .csv extension): \n");
                if(fromUser.hasNextLine()){
                    name = fromUser.nextLine();
                }
                String namePath = name + ".csv";
                try {
                    csvFile = new Scanner(new File(namePath));
                    flag = true;
                } catch (Exception e) {
                    System.out.print(name  + ".csv not found\n");
                }
                if(flag){ break; } // if file read successfully on first try
            }
        if(!flag){
            System.out.print("Unable to open file, exiting\n");
            invalidFlag = true;
            return null; 
        }

        // TODO: FIX so that all strings are accepted
        // Done?
        if (!invalidFlag){
            if(csvFile.hasNextLine()){
                firstLine = csvFile.nextLine();
                if(firstLine.length() == 2){
                    char[] test = new char[2];
                    test[0] = firstLine.charAt(0);
                    test[1] = firstLine.charAt(1);
                    firstLine = new String(test);
                } else if(firstLine.length() == 3) {
                    char[] test = new char[3];
                    test[0] = firstLine.charAt(0);
                    test[1] = firstLine.charAt(1);
                    test[2] = firstLine.charAt(2);
                    firstLine = new String(test);
                }
            } else {
                System.out.print("Improper file format, exiting\n");
                invalidFlag = true;
                return null;
            }

            // check if OPL or IR
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
                System.out.print("Invalid election type, exiting\n");
                invalidFlag = true;
            }
        }
        //asking if user has another file 
        if (invalidFlag){
            System.out.print("The file you previously entered was invalid. You may still input another file if desired.\n");
        }
        else if(firstLine.equals("PO")) {
            sys = new POElection();
            sys.setCsvName(name);
            sys.setCsvFile(csvFile);
            sys.setElectionType(firstLine);
        }
        else {
            scannerList.add(csvFile);
            count++;
        }
        System.out.print("Do you have another file to input? If yes, press Y/y, otherwise press any other key.\n");
                if(fromUser.hasNextLine()){
                    statusKey = fromUser.nextLine();
                    if (statusKey.equals("Y") || statusKey.equals("y")){
                        fileStatus = true;
                    }
                    else {
                            fileStatus = false;
                    }
                } else {
                    break;
                }
        }
        if(sys != null){
            sys.setScannerNameList(scannerList);
            sys.setCount(count);
        }
        return sys; // need to check that sys is not null in calling method
    } // promptCSV

    /**
     * This function will prompt the user for the desired audit file name. If one is not provided, the function will
     * generate a unique name. It will then create the file and open it for writing. It then sets the class variable
     * auditFile to the PrintWriter to be accessed and written to throughout the algorithm.
     */
    public void promptAudit(){
        String name = "";
        Scanner fromUser = new Scanner(System.in);
        System.out.print("What do you want the name of the audit file to be?\n");
        System.out.print("Do not include any extensions, the system will produce a .txt file\n");
        System.out.print("If you wish to use the default naming conventions, enter default (D)\n");
        if(fromUser.hasNext()){
            name = fromUser.nextLine();
        }
        if((!name.equals("default")) && (!name.equals("D")) && (!name.equals("d")) && (!name.equals("Default"))){
            System.out.print("You have entered: "+name+"\n Is this correct? (Y/N)\n");
            // check Y/N -> prompt accordingly
            String response = "";
            if(fromUser.hasNextLine()){
                response = fromUser.nextLine();
            }
            if(response.equals("Y") || response.equals("y")){
                System.out.print("Proceeding with input name\n");
            }
            else if(response.equals("N") || response.equals("n")){
                System.out.print("Enter the correct name: \n");
                name = fromUser.nextLine();
            }
            else {
                System.out.print("Invalid entry, proceeding with first input name\n");
            }
            name += ".txt";
        }
        else {
            System.out.print("Using default: " + csvName + "AuditFile.txt\n");
            name = csvName + "AuditFile.txt"; // need to figure out actual conventions
        }
        try {
            auditFile = new PrintWriter(new File(name));
        } catch (Exception e) {
            System.out.print("Error opening file, exiting\n");
            return; // need to check that var is set in calling method
        }
        System.out.print("\n");
    } // promptAudit

    /**
     * This function will prompt the user for the desired media file name. If one is not provided, the function will
     * generate a unique name. It will then create the file and open it for writing. It then sets the class variable
     * mediaFile to the PrintScanner to be accessed and written to throughout the algorithm.
     */
    public void promptMedia(){
        String name = null;
        Scanner fromUser = new Scanner(System.in);
        System.out.print("What do you want the name of the media file to be?\n");
        System.out.print("Do not include any extensions, the system will produce a .txt file\n");
        System.out.print("If you wish to use the default naming conventions, enter default (D)\n");
        name = fromUser.nextLine();
        if((!name.equals("default")) && (!name.equals("D")) && (!name.equals("d")) && (!name.equals("Default"))){
            System.out.print("You have entered: "+name+"\n Is this correct? (Y/N)\n");
            // check Y/N -> prompt accordingly
            String response = fromUser.nextLine();
            if(response.equals("Y") || response.equals("y")){
                System.out.print("Proceeding with input name\n");
            }
            else if(response.equals("N") || response.equals("n")){
                System.out.print("Enter the correct name: \n");
                name = fromUser.nextLine();
            }
            else {
                System.out.print("Invalid entry, proceeding with first input name\n");
            }
            name += ".txt";
        }
        else {
            System.out.print("Using default: " + csvName + "MediaFile.txt\n");
            name = csvName + "MediaFile.txt"; // need to figure out actual conventions
        }
        try {
            mediaFile = new PrintWriter(new File(name));
        } catch (Exception e) {
            System.out.print("Error opening file, exiting\n");
            return; // need to check that var is set in calling method
        }
        System.out.print("\n");
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
        VotingSystem electionT = promptCSV();
        if(electionT != null){
            electionT.promptAudit();
            electionT.promptMedia();
            if (electionT.getElectionType().equals("OPL") && (electionT.mediaFile != null) && (electionT.auditFile != null)){
                OPLElection newOPL = (OPLElection) electionT;
                newOPL.runElection();
            }
            else if(electionT.getElectionType().equals("IR") && (electionT.mediaFile != null) && (electionT.auditFile != null)){
                IRElection newIR = (IRElection) electionT;
                newIR.runElection();
            }
            else if(electionT.getElectionType().equals("PO") && (electionT.mediaFile != null) && (electionT.auditFile != null)){
                POElection newPO = (POElection) electionT;
                newPO.runElection();
            }
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

    public ArrayList<Scanner> getScannerNameList(){
        return this.scannerNameList;
    }

    public void setScannerNameList(ArrayList<Scanner> list){
        this.scannerNameList = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
    protected ArrayList<Scanner> scannerNameList;
    protected int count;

}

// VotingSystem
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class VotingSystem {
    /*
     * Description - pull from SDD
     * @param paramName description of parameter
     * @return returnName description of return
     */
    public void promptAudit(){

    } // promptAudit

    public void promptMedia(){}
    public void promptCSV(){}

    /*
     * Break any ties throughout either algorithm. It takes in the number of candidates involved in the tie, 
     * and returns an integer representing the losing candidate. It will simulate 1000 coin tosses and take 
     * the 1001 result to ensure randomness.
     * @param numTied       an int, the number of objects with the same values
     * @return option       an int, the chosen 
     */
    public int coinToss(int numTied){
        if (numTied == 0) {
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

    public static void main(String[] args){}

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

    private PrintWriter auditFile;
    private PrintWriter mediaFile;
    private String csvName;
    private Scanner csvFile;
    private ArrayList<Candidate> candidates;
    private int totalNumBallots;
    private long startTime;
    private long stopTime;
    private String electionType;

}
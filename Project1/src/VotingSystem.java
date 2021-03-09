// VotingSystem
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

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
    public int coinToss(int numTied){
        return 0;
    }
    public static void main(String[] args){
        VotingSystem electionT = new VotingSystem();
        electionT.promptAudit();
        electionT.promptMedia();
        electionT.promptCSV();
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
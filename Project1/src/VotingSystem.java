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
    public static void main(String[] args){}

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
// OLPElection
// DESCRIPTION OF CODE
// Eileen Campbell, Hazel Dunn, Olivia Hansen, Maranda Donaldson

import java.util.ArrayList;

public class OPLElection extends VotingSystem{
    public int runElection(){ return 0; }
    public void readOPLCSV(){}
    public void readBallots(){}
    public void printToScreen(){}
    public void writeToMediaFile(){}
    public void writeToAuditFile(){}

    public void allocateByQuota(){
        /*This function will be used to allocate the first round of seats to each party. 
        It will loop through each party in the party ArrayList. 
        It will access the numBallots class variable for each party and divide it by the quota. 
        It will take the quotient from this calculation and set the numSeats class variable for the party 
        and it will take the remainder of this calculation and set the remainder class variable for the party.*/

        int size = party.size();

        for(int i = 0; i < size; i++) {
            int numB = (party.get(i)).getpNumBallots();
            int seatsByQuota = numB/quota;
            int remain = numB%quota;
            (party.get(i)).setNumSeats(seatsByQuota);
            (party.get(i)).setRemainder(remain);
        }
    }

    public void allocateByRemainder(){
        /*This function will allocate the second round of seats for each party based on the remaining votes 
        contained in the remainder object for each party in the election. 
        Remaining seats will be allocated based on the parties that have the highest remainder. 
        The party with the highest remainder will be given a seat first, and then it moves down the list until 
        there are no seats left to be allocated.*/

        int size = party.size();
        int numSeats = numSeatsLeft;

        ArrayList<Party> highestRemain = new ArrayList<Party>();

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
            }
            highestRemain.add(filledSeats, party.get(topIndex));    //add max remainder seat that was decided from this iteration
            filledSeats++;   //move on to allocating next seat
        }
    }

    public void partyNumBallots(){}

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

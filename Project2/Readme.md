# Summary
This system’s primary purpose is to analyze an election’s results based on ballot data from an inputted CSV file. It then runs the appropriate voting system based on the election type in data from the file, which in this case is either an Instant Runoff election or Open Party Listing election. The analysis produces election results that are shown to the user and stored in external files for later use.

Minor changes were made to the design of the system, the Design Document has been updated accordingly.

##Running the System:
*All instructions are for running through the terminal/command prompt*
### Normal Usage
- The following files are necessary to run the system:
  - Ballot.java
  - Candidate.java
  - IRBallot.java
  - IRElection.java
  - OPLBallot.java
  - OPLElection.java
  - Party.java
  - VotingSystem.java
- To run the system, run the following commands:
  - javac VotingSystem.java
  - java VotingSystem
- Then follow prompts and enter information as needed

### Testing
- All .java and .csv files from the src and testing directories must be in the working directory
- The .jar files located in the testing directory must also be in the working directory
- To run all tests, execute the following commands:
  - On Windows:
    - javac -cp .;junit-4.12.jar;hamcrest-core-1.3.jar TestSuite.java
    - java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore TestSuite
  - On Linux/Mac:
    - javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar TestSuite.java
    - java -cp .:junit-4.12.jar:hamcrest-core-1.3.jar org.junit.runner.JUnitCore TestSuite

## Notes about Main Program:
+ Any input CSV file should not contain any extra commas, if there are extra commas on lines that should only contain one number, an error may occur

## Notes about Testing:
+ Use TestSuite to run all tests
+ All .csv files in testing folder are necessary for at least one test
+ .csv files may need to be copied over to another folder. (Into the project folder for IntelliJ, and into the src folder if running from the terminal)
+ Many extra .txt files will be generated and subsequently deleted during testing, if any happen to be left over, they can be deleted safely after the tests are done running
+ The requirement that the system should process 100,000 ballots in under 8 minutes is tested by the tests testOPLElecLong and testIRElecLong. These tests take in csv files with just over 100,000 ballot lines. The 8 minute limit checked inside of these tests but is also enforced by a JUnit timeout rule.

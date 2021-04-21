This system’s primary purpose is to analyze an election’s results based on ballot data from an inputted CSV file. It then runs the appropriate voting system based on the election type in data from the file, which in this case is either an Instant Runoff election or Open Party Listing election. The analysis produces election results that are shown to the user and stored in external files for later use.

Minor changes were made to the design of the system, the Design Document has been updated accordingly.

Notes about Main Program:
+ Any input CSV file should not contain any extra commas, if there are extra commas on lines that should only contain one number, an error may occur

Notes about Testing:
+ Use TestSuite to run all tests
+ All .csv files in testing folder are necessary for at least one test
+ .csv files may need to be copied over to another folder. (Into the project folder for IntelliJ, and into the src folder if running from the terminal)
+ Many extra .txt files will be generated and subsequently deleted during testing, if any happen to be left over, they can be deleted safely after the tests are done running
+ The requirement that the system should process 100,000 ballots in under 8 minutes is tested by the tests testOPLElecLong and testIRElecLong. These tests take in csv files with just over 100,000 ballot lines. The 8 minute limit checked inside of these tests but is also enforced by a JUnit timeout rule.

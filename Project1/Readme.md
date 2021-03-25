This system’s primary purpose is to analyze an election’s results based on ballot data from an inputted CSV file. It then runs the appropriate voting system based on the election type in data from the file, which in this case is either an Instant Runoff election or Open Party Listing election. The analysis produces election results that are shown to the user and stored in external files for later use. 

Notes about Main Program:
+ Any input CSV file should be stored as a .txt file

Notes about Testing:
+ Use TestSuite to run all tests
+ All .txt files in testing folder are necessary
+ .txt files may need to be copied over to another folder. (Into the project folder for IntelliJ, and into the src folder if running from the terminal)
+ Many extra .txt files will be generated during testing, all can be deleted safely after the tests are done running
+ The requirement that the system should process 100,000 ballots in under 8 minutes is tested by the tests testOPLElecLong and testIRElecLong. These tests take in csv files with just over 100,000 ballot lines. The 8 minute limit is enforced by a JUnit timeout rule.

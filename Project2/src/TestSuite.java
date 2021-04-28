import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestCandidate.class,
        TestIRElection.class,
        TestOPLElection.class,
        TestParty.class,
        TestVotingSystem.class,
        TestPOElection.class
})
public class TestSuite {
}

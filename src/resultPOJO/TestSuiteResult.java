package resultPOJO;

import java.util.ArrayList;

public class TestSuiteResult {
	int testSuiteId;
	ArrayList<TestCaseResult> testCaseResults;

	public int getTestSuiteId() {
		return testSuiteId;
	}

	public void setTestSuiteId(int testSuiteId) {
		this.testSuiteId = testSuiteId;
	}

	public ArrayList<TestCaseResult> getTestCaseResults() {
		return testCaseResults;
	}

	public void setTestCaseResults(ArrayList<TestCaseResult> testCaseResults) {
		this.testCaseResults = testCaseResults;
	}

}

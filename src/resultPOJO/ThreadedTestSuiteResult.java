package resultPOJO;

import java.util.ArrayList;

public class ThreadedTestSuiteResult {
	private int theadCount;
	int testSuiteId;
	ArrayList<TestSuiteResult> testSuiteResults = new ArrayList<>();

	public int getTestSuiteId() {
		return testSuiteId;
	}

	public void setTestSuiteId(int testSuiteId) {
		this.testSuiteId = testSuiteId;
	}

	public ArrayList<TestSuiteResult> getTestSuiteResults() {
		return testSuiteResults;
	}

	public void setTestSuiteResults(ArrayList<TestSuiteResult> testSuiteResults) {
		this.testSuiteResults = testSuiteResults;
	}

	public int getTheadCount() {
		return theadCount;
	}

	public void setTheadCount(int theadCount) {
		this.theadCount = theadCount;
	}

}

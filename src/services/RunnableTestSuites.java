package services;

import testCasePOJO.TestSuite;

interface Callback {
	void callback(); // would be in any signature
}

public class RunnableTestSuites implements Runnable {
	private TestSuite testSuite;
	Callback c;

	public RunnableTestSuites(TestSuite testSuite) {
		this.testSuite = testSuite;
	}

	public RunnableTestSuites(Callback c) {
		this.c = c;
		
	}

	@Override
	public void run() {
		TestSuiteServices services = new TestSuiteServices();
		try {
			String name = Thread.currentThread().getName();
			services.runTestSuite(this.testSuite, name);
			this.c.callback(); // callback
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}

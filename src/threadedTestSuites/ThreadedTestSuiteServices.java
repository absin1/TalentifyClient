/**
 * 
 */
package threadedTestSuites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import resultPOJO.TestCaseResult;
import resultPOJO.TestSuiteResult;
import services.TestSuiteServices;

/**
 * @author absin
 *
 */
public class ThreadedTestSuiteServices {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ThreadedTestSuiteServices().runTestSuiteThreads(2, 2);
	}

	public TestSuiteResult runTestSuiteThreads(int testSuiteId, int threads) {
		ExecutorService es = Executors.newCachedThreadPool();
		ArrayList<TestCaseResult> testCaseResults = new ArrayList<>();
		TestSuiteResult testSuiteResult = new TestSuiteResult();
		testSuiteResult.setTestSuiteId(testSuiteId);
		testSuiteResult.setTestCaseResults(testCaseResults);
		for (int i = 0; i < threads; i++)
			es.execute(new Runnable() {

				@Override
				public void run() {
					try {
						TestCaseResult caseResult = new TestCaseResult();
						caseResult.setThreadName(Thread.currentThread().getName());
						new TestSuiteServices().runTestSuite(new TestSuiteServices().getTestSuite(testSuiteId),
								caseResult);
						testSuiteResult.getTestCaseResults().add(caseResult);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
		es.shutdown();
		boolean finshed = false;
		try {
			finshed = es.awaitTermination(2, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (finshed)
			System.out.println("Done");
		for (TestCaseResult iterable_element : testSuiteResult.getTestCaseResults()) {
			System.out.println(iterable_element.getUrl());
			System.out.println(iterable_element.getStatus());
			System.out.println(iterable_element.getTimeTaken());
			System.out.println(iterable_element.getResponseBody());
		}
		return testSuiteResult;
	}

}

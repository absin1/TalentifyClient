/**
 * 
 */
package threadedTestSuites;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import resultPOJO.TestCaseResult;
import resultPOJO.TestSuiteResult;
import resultPOJO.ThreadedTestSuiteResult;
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

	public ThreadedTestSuiteResult runTestSuiteThreads(int testSuiteId, int threads) {
		ExecutorService es = Executors.newFixedThreadPool(threads);
		ArrayList<TestSuiteResult> testSuiteResults = new ArrayList<>();
		ThreadedTestSuiteResult threadedTestSuiteResult = new ThreadedTestSuiteResult();
		threadedTestSuiteResult.setTestSuiteResults(testSuiteResults);
		threadedTestSuiteResult.setTestSuiteId(testSuiteId);
		threadedTestSuiteResult.setTheadCount(threads);
		for (int i = 0; i < threads; i++)
			es.execute(new Runnable() {

				@Override
				public void run() {
					try {
						Thread.sleep(100);
						ArrayList<TestCaseResult> testCaseResults = new ArrayList<>();
						TestSuiteResult testSuiteResult = new TestSuiteResult();
						testSuiteResult.setThreadName(Thread.currentThread().getName());
						testSuiteResult.setTestSuiteId(testSuiteId);
						testSuiteResult.setTestCaseResults(testCaseResults);
						new TestSuiteServices().runTestSuite(new TestSuiteServices().getTestSuite(testSuiteId),
								testSuiteResult);
						threadedTestSuiteResult.getTestSuiteResults().add(testSuiteResult);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
		es.shutdown();
		boolean finshed = false;
		try {
			finshed = es.awaitTermination(10, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (finshed)
			System.out.println("Done");
		System.out.println("<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>");
		System.out.println(new Gson().toJson(threadedTestSuiteResult));
		return threadedTestSuiteResult;
	}

}

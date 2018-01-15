package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import resultPOJO.TestSuiteResult;
import resultPOJO.ThreadedTestSuiteResult;
import threadedTestSuites.ThreadedTestSuiteServices;

/**
 * Servlet implementation class ThreadTestSuites
 */
@WebServlet("/ThreadTestSuites")
public class ThreadTestSuites extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ThreadTestSuites() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int testSuiteId = Integer.parseInt(request.getParameter("testSuiteId"));
		int threads = Integer.parseInt(request.getParameter("threads"));
		ThreadedTestSuiteResult threadedTestSuiteResult = new ThreadedTestSuiteServices()
				.runTestSuiteThreads(testSuiteId, threads);
		response.getWriter().append(new Gson().toJson(threadedTestSuiteResult));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

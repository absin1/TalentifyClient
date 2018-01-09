package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import com.google.gson.Gson;

import resultPOJO.TestSuiteResult;
import services.TestSuiteServices;
import testCasePOJO.TestSuite;

/**
 * Servlet implementation class RunTestSuite
 */
@WebServlet("/RunTestSuite")
public class RunTestSuite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RunTestSuite() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		TestSuiteServices services = new TestSuiteServices();
		Integer testSuiteId = Integer.parseInt(request.getParameter("testSuiteId"));
		TestSuiteResult testSuiteResult = new TestSuiteResult();
		try {
			testSuiteResult = services.runTestSuiteById(testSuiteId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().append(new Gson().toJson(testSuiteResult));
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

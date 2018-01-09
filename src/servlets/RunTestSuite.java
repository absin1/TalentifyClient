package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

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
		String testSuiteId = request.getParameter("testSuiteId");
		TestSuite testSuite = null;
		try {
			testSuite = services.getTestSuite(Integer.parseInt(testSuiteId));
		} catch (NumberFormatException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			services.runTestSuite(testSuite);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

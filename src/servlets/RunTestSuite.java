package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import services.RunnableTestSuites;
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
		int threads = Integer.parseInt(request.getParameter("threads"));
		TestSuite testSuite = null;
		try {
			testSuite = services.getTestSuite(Integer.parseInt(testSuiteId));
		} catch (NumberFormatException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!testSuite.isThreadable())
			threads = 1;
		for (int i = 0; i < threads; i++) {
			(new Thread(new RunnableTestSuites(testSuite))).start();
		}
		appendResponse(response, testSuite);
	}

	public void appendResponse(HttpServletResponse response, TestSuite testSuite) throws IOException {
		for (String iterable_element : testSuite.getResponse().keySet()) {
			response.getWriter().append("For thread with name:  >>>>>>>>>>>>>>>" + iterable_element
					+ " the response recorded was \n \n \n");
			response.getWriter().append(testSuite.getResponse().get(iterable_element));

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

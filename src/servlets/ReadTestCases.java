package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import XMLCreation.XMLServices;
import testCasePOJO.TestCase;

/**
 * Servlet implementation class ReadTestCases
 */
@WebServlet("/ReadTestCases")
public class ReadTestCases extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReadTestCases() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		JSONArray responseArray = new JSONArray();
		XMLServices creationServices = new XMLServices();
		int maxId = creationServices.getMaxId();
		List<TestCase> testCases = new ArrayList<>();
		for (int i = 1; i <= maxId; i++) {
			TestCase testCase = null;
			try {
				testCase = creationServices.readRequestXML(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (testCase != null)
				testCases.add(testCase);
		}
		for (TestCase testCase : testCases) {
			JSONObject testCaseObject = creationServices.jsonify(testCase);
			responseArray.put(testCaseObject);
		}
		response.getWriter().append(responseArray.toString());
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

package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import XMLCreation.XMLServices;

/**
 * Servlet implementation class CreateXML
 */
@WebServlet("/CreateXML")
public class CreateXML extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateXML() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String body = request.getParameter("body");
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String baseURL = request.getParameter("baseURL");
		String relativeURL = request.getParameter("relativeURL");
		Integer testCaseId = new XMLServices().createXML(body, name, type, baseURL, relativeURL);
		response.getWriter().append(testCaseId.toString());
		/*
		 * System.out.println(request.getParameter("data"));
		 * response.getWriter().append("asasas");
		 */
	}

}

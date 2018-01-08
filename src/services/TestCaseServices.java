package services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import XMLCreation.XMLServices;
import resonsePOJO.Response;
import testCasePOJO.TestCase;

public class TestCaseServices {
	private final String USER_AGENT = "Internal";

	public String runTestCaseById(int id) throws Exception {
		XMLServices xmlServices = new XMLServices();
		TestCase testCaseRequest = xmlServices.readRequestXML(id);
		Response testCaseResponse = runTestCase(testCaseRequest);
		xmlServices.writeResponseXML(testCaseResponse);
		return xmlServices.jsonify(testCaseResponse);
	}

	public Response runTestCase(TestCase testCaseRequest) throws Exception {
		Response testCaseResponse = new Response();
		long startTime = System.currentTimeMillis();
		switch (testCaseRequest.getType()) {
		case "POST":
			sendPost(testCaseRequest, testCaseResponse);
			break;
		case "GET":
			sendGet(testCaseRequest, testCaseResponse);
			break;
		case "PUT":
			sendPut(testCaseRequest, testCaseResponse);
			break;

		default:
			System.out.println("This request method " + testCaseRequest.getType() + " has no implementation till now");
			break;
		}
		long endTime = System.currentTimeMillis();
		testCaseResponse.setTimeTaken(endTime - startTime);
		testCaseResponse.setTestCaseId(testCaseRequest.getId());
		testCaseResponse.setTestCaseName(testCaseRequest.getName());
		return testCaseResponse;
	}

	// HTTP GET request
	public void sendGet(TestCase testCaseRequest, Response testCaseResponse) throws Exception {

		String url = "http://localhost:8080/t2c/user/174/complex";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		testCaseResponse.setResponseBody(response.toString());
		testCaseResponse.setStatus(responseCode);
	}

	public void sendPost(TestCase testCaseRequest, Response testCaseResponse) throws Exception {
		URL obj = new URL(testCaseRequest.getFullURL());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(testCaseRequest.getBody());
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + testCaseRequest.getFullURL());
		System.out.println("Post parameters : " + testCaseRequest.getBody());
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		testCaseResponse.setResponseBody(response.toString());
		testCaseResponse.setStatus(responseCode);
	}

	public void sendPut(TestCase testCaseRequest, Response testCaseResponse) throws Exception {

		URL obj = new URL(testCaseRequest.getFullURL());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("PUT");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// Send put request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(testCaseRequest.getBody());
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'PUT' request to URL : " + testCaseRequest.getFullURL());
		System.out.println("Post parameters : " + testCaseRequest.getBody());
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		testCaseResponse.setResponseBody(response.toString());
		testCaseResponse.setStatus(responseCode);
	}
}

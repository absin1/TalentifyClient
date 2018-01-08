package services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.json.JSONArray;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import contants.TalentifyClientsConstants;
import testCasePOJO.Evaluator;
import testCasePOJO.MapElements;
import testCasePOJO.PossibleRuntimeVariables;
import testCasePOJO.RuntimeConstants;
import testCasePOJO.RuntimeVariables;
import testCasePOJO.TestCase;
import testCasePOJO.TestSuite;

public class TestSuiteServices {
	public void runTestSuite(TestSuite testSuite, String threadName) throws Exception {
		HashMap<String, String> runtimes = getRuntimeVariablesConstants(testSuite);
		HashMap<String, String> response = new HashMap<>();
		String runTestCaseOutput = "";
		for (TestCase testCase : testSuite.getTestCase()) {
			runTestCaseOutput = runTestCase(testCase, runtimes);
		}
		response.put(threadName, runTestCaseOutput);
		testSuite.setResponse(response);
	}

	public HashMap<String, String> getRuntimeVariablesConstants(TestSuite testSuite) {
		HashMap<String, String> runtimes = new HashMap<>();
		RuntimeConstants runtimeConstant = testSuite.getRuntimeConstant();
		RuntimeVariables runtimeVariable = testSuite.getRuntimeVariable();
		for (MapElements iterable_element : runtimeConstant.getConstants()) {
			runtimes.put(iterable_element.key, iterable_element.value);
		}
		for (MapElements iterable_element : runtimeVariable.getVars()) {
			runtimes.put(iterable_element.key, new PossibleRuntimeVariables().executeVariable(iterable_element.value));
		}
		return runtimes;
	}

	private String runTestCase(TestCase testCase, HashMap<String, String> runtimes) throws Exception {
		String output = "";
		long startTime = System.currentTimeMillis();
		String response = null;
		switch (testCase.getType()) {
		case "POST":
			response = sendPost(testCase, runtimes, output);
			break;
		case "GET":
			response = sendGet(testCase, output);
			break;
		case "PUT":
			response = sendPut(testCase, runtimes, output);
			break;

		default:
			System.out.println("This request method " + testCase.getType() + " has no implementation till now");
			break;
		}
		evaluate(testCase, response, runtimes, output);
		long endTime = System.currentTimeMillis();
		output += "\n The time taken by the test suite on this particular thread was " + (endTime - startTime)
				+ " milliseconds";
		return output;
	}

	private void evaluate(TestCase testCase, String response, HashMap<String, String> runtimes, String output) {
		JsonParser jsonParser = new JsonParser();
		JsonElement responseElement = jsonParser.parse(response);
		try {
			for (MapElements evaluators : testCase.getEvaluator().getMapElementKeys()) {
				String key = evaluators.getKey();
				String expectedValue = runtimes.get(evaluators.getValue());
				String responseValue = responseElement.getAsJsonObject().get(key).getAsString();
				System.out.println("The expected value " + expectedValue + " of key " + key + " matches response value "
						+ responseValue);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();

		for (Map.Entry<String, String> entry : params.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			result.append("&");
		}

		String resultString = result.toString();
		return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
	}

	private TestSuite createResetPasswordTestSuite() {

		TestSuite resetPasswordSuite = new TestSuite();

		ArrayList<MapElements> resetPasswordSuiteConstants = new ArrayList<>();
		MapElements e1 = new MapElements("userId", "777");
		MapElements e2 = new MapElements("authenticationToken", "rRC3AsbENP0DukY1LZ4W");
		MapElements e3 = new MapElements("email", "abhinav1@istarindia.com");
		resetPasswordSuiteConstants.add(e1);
		resetPasswordSuiteConstants.add(e2);
		resetPasswordSuiteConstants.add(e3);
		RuntimeConstants constants = new RuntimeConstants();
		constants.setConstants(resetPasswordSuiteConstants);
		RuntimeVariables runtimeVariable = new RuntimeVariables();
		ArrayList<MapElements> vars = new ArrayList<>();
		MapElements e4 = (new MapElements("password", PossibleRuntimeVariables.randomString));
		vars.add(e4);
		runtimeVariable.setVars(vars);

		ArrayList<TestCase> testCases = new ArrayList<>();

		TestCase resetPassword = createResetPasswordTestCase();

		TestCase login = createLogintestCase();

		testCases.add(resetPassword);
		testCases.add(login);
		resetPasswordSuite.setTestCase(testCases);
		resetPasswordSuite.setRuntimeConstant(constants);
		resetPasswordSuite.setRuntimeVariable(runtimeVariable);
		return resetPasswordSuite;
	}

	public TestCase createLogintestCase() {
		TestCase login = new TestCase();
		Evaluator loginEvaluator = new Evaluator();
		ArrayList<MapElements> mapElementKeys = new ArrayList<>();
		mapElementKeys.add(new MapElements("id", "userId"));
		mapElementKeys.add(new MapElements("authenticationToken", "authenticationToken"));
		mapElementKeys.add(new MapElements("email", "email"));
		loginEvaluator.setMapElementKeys(mapElementKeys);
		ArrayList<MapElements> bodyKeys = new ArrayList<>();
		bodyKeys.add(new MapElements("email", "email"));
		bodyKeys.add(new MapElements("password", "password"));

		login.setUrl("http://localhost:8080/t2c/auth/login");
		login.setBodyKeys(bodyKeys);
		login.setBodyType("keyValue");
		login.setType("POST");
		login.setEvaluator(loginEvaluator);

		return login;
	}

	public TestCase createResetPasswordTestCase() {
		TestCase resetPassword = new TestCase();
		ArrayList<MapElements> bodyKeys = new ArrayList<>();
		bodyKeys.add(new MapElements("userId", "userId"));
		bodyKeys.add(new MapElements("password", "password"));

		resetPassword.setBodyKeys(bodyKeys);
		resetPassword.setBodyType("keyValue");
		resetPassword.setUrl("http://localhost:8080/t2c/user/password/reset");
		resetPassword.setType("PUT");
		// resetPassword.setEvaluator(resetPasswordEvaluator);
		return resetPassword;
	}

	// HTTP GET request
	public String sendGet(TestCase testCase, String output) throws Exception {

		String url = testCase.getUrl();

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		// con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		output += "\nSending 'POST' request to URL : " + testCase.getUrl();
		output += "\nResponse Code : " + responseCode;
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		output += "\nResponse is : " + response.toString();
		return response.toString();

	}

	public String sendPost(TestCase testCase, HashMap<String, String> runtimes, String output) throws Exception {
		URL obj = new URL(testCase.getUrl());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// add reuqest header
		con.setRequestMethod("POST");
		// con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		String body = getBody(testCase, runtimes);
		wr.writeBytes(body);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + testCase.getUrl());
		System.out.println("Post parameters : " + body);
		System.out.println("Response Code : " + responseCode);
		output += "\nSending 'POST' request to URL : " + testCase.getUrl();
		output += "\nPost parameters : " + body;
		output += "\nResponse Code : " + responseCode;
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		output += "\nResponse is : " + response.toString();
		return response.toString();
	}

	public String getBody(TestCase testCase, HashMap<String, String> runtimes) throws UnsupportedEncodingException {
		String resultString = null;
		if (testCase.getBodyType().equalsIgnoreCase("keyValue")) {
			StringBuilder result = new StringBuilder();
			for (MapElements entry : testCase.getBodyKeys()) {
				result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
				result.append("=");
				result.append(URLEncoder.encode(runtimes.get(entry.getKey()), "UTF-8"));
				result.append("&");
			}
			resultString = result.toString();
		} else {
			resultString = testCase.getBodyType();
		}
		return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
	}

	public String sendPut(TestCase testCase, HashMap<String, String> runtimes, String output) throws Exception {

		URL obj = new URL(testCase.getUrl());
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("PUT");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// Send put request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		String body = getBody(testCase, runtimes);
		wr.writeBytes(body);
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'PUT' request to URL : " + testCase.getUrl());
		System.out.println("Post parameters : " + body);
		System.out.println("Response Code : " + responseCode);
		output += "\nSending 'POST' request to URL : " + testCase.getUrl();
		output += "\nPost parameters : " + body;
		output += "\nResponse Code : " + responseCode;
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());
		output += "\nResponse is : " + response.toString();
		return response.toString();

	}

	public TestSuite getTestSuite(int testSuiteId) throws JAXBException {
		TestSuite suite = new TestSuite();
		TestSuiteServices services = new TestSuiteServices();
		JSONArray testSuiteJsonArray = new JSONArray();
		File testSuiteFolder = new File(TalentifyClientsConstants.TestSuitePath);
		JAXBContext context = JAXBContext.newInstance(TestSuite.class);
		for (File testSuiteFile : testSuiteFolder.listFiles()) {
			if (testSuiteFile.getName().equalsIgnoreCase(testSuiteId + ".xml")) {
				Unmarshaller unmarshaller = context.createUnmarshaller();
				suite = (TestSuite) unmarshaller.unmarshal(testSuiteFile);
			}
		}
		return suite;
	}
}

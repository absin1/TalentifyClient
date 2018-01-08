package XMLCreation;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import contants.TalentifyClientsConstants;
import resonsePOJO.Response;
import services.TestSuiteServices;
import testCasePOJO.TestCase;
import testCasePOJO.TestCaseURL;
import testCasePOJO.TestSuite;

public class XMLServices {

	public int createXML(String body, String name, String type, String baseURL, String relativeURL) {

		TestCase request = new TestCase();
		request.setBody(body);
		int id = getMaxId() + 1;
		request.setId(id);
		request.setName(name);
		request.setType(type);
		TestCaseURL url = new TestCaseURL();
		url.setBaseURL(baseURL);
		url.setRelativeURL(relativeURL);
		request.setUrl(url);
		writeRequestXML(request, id);
		return id;
	}

	private void writeRequestXML(TestCase request, int id) {
		try {

			File file = new File(contants.TalentifyClientsConstants.RequestXMLPath + id + ".xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(TestCase.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(request, file);
			jaxbMarshaller.marshal(request, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public int getMaxId() {
		int maxID = 0;
		File folder = new File(contants.TalentifyClientsConstants.RequestXMLPath);
		File[] listFiles = folder.listFiles();
		for (File XMLFile : listFiles) {
			int parseInt = 0;
			try {
				parseInt = Integer.parseInt(XMLFile.getName().replaceAll(".xml", ""));
			} catch (Exception e) {
				System.err.println("Dirty file in TestCase XML folder");
				e.printStackTrace();
			}
			if (parseInt > maxID)
				maxID = parseInt;
		}
		return maxID;
	}

	public TestCase readRequestXML(int id) throws JAXBException {
		File file = new File(contants.TalentifyClientsConstants.RequestXMLPath + id + ".xml");
		JAXBContext context = JAXBContext.newInstance(TestCase.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		TestCase testCase = (TestCase) unmarshaller.unmarshal(file);
		return testCase;
	}

	public JSONObject jsonify(TestCase testCase) {
		JSONObject testCaseObject = new JSONObject();
		testCaseObject.put("id", testCase.getId());
		testCaseObject.put("type", testCase.getType());
		testCaseObject.put("baseURL", testCase.getUrl().getBaseURL());
		testCaseObject.put("relativeURL", testCase.getUrl().getRelativeURL());
		testCaseObject.put("name", testCase.getName());
		testCaseObject.put("body", testCase.getBody());
		return testCaseObject;
	}

	public String jsonify(Response testCaseResponse) {
		JSONObject testCaseResponseJsonObject = new JSONObject();
		testCaseResponseJsonObject.put("testCaseId", testCaseResponse.getTestCaseId());
		testCaseResponseJsonObject.put("testCaseName", testCaseResponse.getTestCaseName());
		testCaseResponseJsonObject.put("status", testCaseResponse.getStatus());
		testCaseResponseJsonObject.put("timeTaken", testCaseResponse.getTimeTaken());
		testCaseResponseJsonObject.put("responseBody", testCaseResponse.getResponseBody());
		return testCaseResponseJsonObject.toString();
	}

	public void updateXML(int id, String body, String name, String type, String baseURL, String relativeURL) {
		TestCase testCaseObject = null;
		try {
			testCaseObject = readRequestXML(id);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (testCaseObject != null) {
			testCaseObject.setBody(body);
			testCaseObject.setName(name);
			testCaseObject.setType(type);
			TestCaseURL url = testCaseObject.getUrl();
			url.setBaseURL(baseURL);
			url.setRelativeURL(relativeURL);
			testCaseObject.setUrl(url);
		}
		writeRequestXML(testCaseObject, id);
	}

	public void writeResponseXML(Response testCaseResponse) {
		try {

			File file = new File(
					contants.TalentifyClientsConstants.ResponseXMLPath + testCaseResponse.getTestCaseId() + ".xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(testCaseResponse, file);
			jaxbMarshaller.marshal(testCaseResponse, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public JSONArray readTestSuites() throws JAXBException, JSONException, UnsupportedEncodingException {
		TestSuiteServices services = new TestSuiteServices();
		JSONArray testSuiteJsonArray = new JSONArray();
		File testSuiteFolder = new File(TalentifyClientsConstants.TestSuitePath);
		JAXBContext context = JAXBContext.newInstance(TestSuite.class);
		for (File testSuiteFile : testSuiteFolder.listFiles()) {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			TestSuite suite = (TestSuite) unmarshaller.unmarshal(testSuiteFile);
			JSONObject testSuiteJsonObject = new JSONObject();
			testSuiteJsonObject.put("id", suite.getId());
			testSuiteJsonObject.put("name", suite.getName());
			JSONArray testCasesJsons = new JSONArray();
			for (TestCase testCase : suite.getTestCase()) {
				JSONObject testCaseJsonObject = new JSONObject();
				testCaseJsonObject.put("name", testCase.getName());
				testCaseJsonObject.put("URL", testCase.getUrl());
				testCaseJsonObject.put("body",
						services.getBody(testCase, services.getRuntimeVariablesConstants(suite)));
				testCasesJsons.put(testCaseJsonObject);
			}
			testSuiteJsonObject.put("testCases", testCasesJsons);
			testSuiteJsonArray.put(testSuiteJsonObject);
		}
		return testSuiteJsonArray;
	}

}

package XMLCreation;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import contants.TalentifyClientsConstants;
import services.TestSuiteServices;
import testCasePOJO.TestCase;
import testCasePOJO.TestSuite;

public class XMLServices {

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

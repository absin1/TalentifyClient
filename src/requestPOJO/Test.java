package requestPOJO;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import XMLCreation.XMLServices;

public class Test {
	private static String XMLPath = "C:\\\\Users\\\\absin\\\\Downloads\\\\";

	public static void main(String[] args) {
		// These 2 methods create the XML condifguration file for the Test Case
		// loginPOST();
		// resetPasswordPUT();
		// The next method performs an HTTP call using the configuration file. It will
		// take the filename of xml.
		try {
			factory("request2.xml");
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void factory(String path) throws JAXBException {
		File file = new File(XMLPath + path);
		JAXBContext context = JAXBContext.newInstance(Request.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		Request testCase = (Request) unmarshaller.unmarshal(file);
		switch (testCase.getType()) {
		case "POST":
			try {
				new services.Test().sendPost(testCase.getUrl().getBaseURL() + testCase.getUrl().getRelativeURL(),
						testCase.getBody());
			} catch (Exception e) {
				System.out.println("Failed");
			}
			break;
		case "GET":
			try {
				new services.Test().sendGet();
			} catch (Exception e) {
				System.out.println("Failed");
			}
			break;
		case "PUT":
			try {
				new services.Test().sendPut(testCase.getUrl().getBaseURL() + testCase.getUrl().getRelativeURL(),
						testCase.getBody());
			} catch (Exception e) {
				System.out.println("Failed");
			}
			break;

		default:
			System.out.println("This request method " + testCase.getType() + " has no implementation till now");
			break;
		}
	}

	private static void resetPasswordPUT() {
		Request request = new Request();
		String body = "userId=777&password=test123";

		request.setBody(body);
		int id = 2;
		request.setId(id);
		String name = "login";
		request.setName(name);
		String type = "PUT";
		request.setType(type);
		RequestURL url = new RequestURL();
		String baseURL = "http://localhost:8080/";
		url.setBaseURL(baseURL);
		String relativeURL = "t2c/user/password/reset";
		url.setRelativeURL(relativeURL);
		request.setUrl(url);
		try {

			File file = new File("C:\\Users\\absin\\Downloads\\request2.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Request.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(request, file);
			jaxbMarshaller.marshal(request, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static void loginPOST() {

		Request request = new Request();
		String body = "email=abhinav1@istarindia.com&password=test123";
		request.setBody(body);
		int id = 1;
		request.setId(id);
		String name = "login";
		request.setName(name);
		String type = "POST";
		request.setType(type);
		RequestURL url = new RequestURL();
		String baseURL = "http://localhost:8080/";
		url.setBaseURL(baseURL);
		String relativeURL = "t2c/auth/login";
		url.setRelativeURL(relativeURL);
		request.setUrl(url);
		XMLServices xml = new XMLServices();
		//xml.createXML(body, id, name, type, url);
		try {

			File file = new File("C:\\Users\\absin\\Downloads\\request1.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Request.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(request, file);
			jaxbMarshaller.marshal(request, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
}

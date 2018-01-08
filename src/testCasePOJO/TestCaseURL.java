/**
 * 
 */
package testCasePOJO;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author absin
 *
 */
@XmlRootElement(name = "url")
public class TestCaseURL {
	private String baseURL;
	private String relativeURL;

	public TestCaseURL() {
		
	}

	public TestCaseURL(String baseURL, String relativeURL) {
		super();
		this.baseURL = baseURL;
		this.relativeURL = relativeURL;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public String getRelativeURL() {
		return relativeURL;
	}

	public void setRelativeURL(String relativeURL) {
		this.relativeURL = relativeURL;
	}

}

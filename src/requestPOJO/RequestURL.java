/**
 * 
 */
package requestPOJO;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author absin
 *
 */
@XmlRootElement(name = "url")
public class RequestURL {
	private String baseURL;
	private String relativeURL;

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

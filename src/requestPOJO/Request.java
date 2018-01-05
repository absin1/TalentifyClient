/**
 * 
 */
package requestPOJO;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class will act as the root class for the custom XML object which will
 * capture any kind of HTTP request being made to talentify
 * 
 * @author absin
 *
 */
@XmlRootElement(name = "HTTPRequest")
public class Request {
	private String name;
	private int id;
	private String type;
	private String body;
	private RequestURL url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public RequestURL getUrl() {
		return url;
	}

	public void setUrl(RequestURL url) {
		this.url = url;
	}

	public String getFullURL() {
		return url.getBaseURL() + url.getRelativeURL();
	}
}

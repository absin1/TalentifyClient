package testCasePOJO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "KeyValue")
@XmlAccessorType(XmlAccessType.FIELD)
public class MapElements {
	public String key;
	public String value;

	private MapElements() {
	} // Required by JAXB

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public MapElements(String key, String value) {
		this.key = key;
		this.value = value;
	}
}
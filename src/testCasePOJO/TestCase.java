/**
 * 
 */
package testCasePOJO;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class will act as the root class for the custom XML object which will
 * capture any kind of HTTP request being made to talentify
 * 
 * @author absin
 *
 */
@XmlRootElement(name = "TestCase")
public class TestCase {
	private String name;
	private int id;
	private String type;
	private ArrayList<MapElements> bodyKeys;
	private String bodyType;
	private String url;
	private Evaluator evaluator;

	public Evaluator getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(Evaluator evaluator) {
		this.evaluator = evaluator;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<MapElements> getBodyKeys() {
		return bodyKeys;
	}

	public void setBodyKeys(ArrayList<MapElements> bodyKeys) {
		this.bodyKeys = bodyKeys;
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

}

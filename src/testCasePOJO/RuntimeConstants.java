package testCasePOJO;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RuntimeConstants {
	ArrayList<MapElements> constant = new ArrayList<>();

	public ArrayList<MapElements> getConstants() {
		return constant;
	}

	public void setConstants(ArrayList<MapElements> constants) {
		this.constant = constants;
	}
}

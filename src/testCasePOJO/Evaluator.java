package testCasePOJO;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Evaluator {
	private String type;
	ArrayList<MapElements> mapElementKey;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ArrayList<MapElements> getMapElementKeys() {
		return mapElementKey;
	}

	public void setMapElementKeys(ArrayList<MapElements> mapElementKeys) {
		this.mapElementKey = mapElementKeys;
	}
}

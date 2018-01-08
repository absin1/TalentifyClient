package testCasePOJO;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RuntimeVariables {
	ArrayList<MapElements> vars = new ArrayList<>();

	public ArrayList<MapElements> getVars() {
		return vars;
	}

	public void setVars(ArrayList<MapElements> vars) {
		this.vars = vars;
	}

}

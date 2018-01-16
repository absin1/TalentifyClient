package testCasePOJO;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseAddonConstants {
	ArrayList<MapElements> addonConstants = new ArrayList<>();
	String responseAddonConstantType;

	public String getResponseAddonConstantType() {
		return responseAddonConstantType;
	}

	public void setResponseAddonConstantType(String responseAddonConstantType) {
		this.responseAddonConstantType = responseAddonConstantType;
	}

	public ArrayList<MapElements> getAddonConstants() {
		return addonConstants;
	}

	public void setAddonConstants(ArrayList<MapElements> addonConstants) {
		this.addonConstants = addonConstants;
	}
}

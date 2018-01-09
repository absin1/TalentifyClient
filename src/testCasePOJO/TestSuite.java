package testCasePOJO;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestSuite {
	private int id;
	private boolean isThreadable;
	private ArrayList<TestCase> testCase;
	private RuntimeConstants runtimeConstant;
	private RuntimeVariables runtimeVariable;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<TestCase> getTestCase() {
		return testCase;
	}

	public void setTestCase(ArrayList<TestCase> testCase) {
		this.testCase = testCase;
	}

	public RuntimeConstants getRuntimeConstant() {
		return runtimeConstant;
	}

	public void setRuntimeConstant(RuntimeConstants runtimeConstant) {
		this.runtimeConstant = runtimeConstant;
	}

	public RuntimeVariables getRuntimeVariable() {
		return runtimeVariable;
	}

	public void setRuntimeVariable(RuntimeVariables runtimeVariable) {
		this.runtimeVariable = runtimeVariable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isThreadable() {
		return isThreadable;
	}

	public void setThreadable(boolean isThreadable) {
		this.isThreadable = isThreadable;
	}

}

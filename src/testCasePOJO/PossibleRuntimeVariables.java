package testCasePOJO;

import java.util.UUID;

public class PossibleRuntimeVariables {
	public static final String randomString = "$$RandomString$$";

	public String executeVariable(String value) {
		String exe = null;
		switch (value) {
		case PossibleRuntimeVariables.randomString:
			exe = UUID.randomUUID().toString();
			break;

		default:
			break;
		}
		return exe;
	}
}

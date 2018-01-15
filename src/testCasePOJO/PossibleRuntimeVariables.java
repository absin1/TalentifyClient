package testCasePOJO;

import java.util.UUID;

import com.github.javafaker.Faker;

public class PossibleRuntimeVariables {
	public static final String RanEmail = "$$RanEmail$$";
	public static final String RanAlphaNum = "$$RanAlphaNum$$";
	public static final String RanMobile = "$$RanMobile$$";
	public static final String RanName = "$$RanName$$";
	public static Faker faker = new Faker();

	public String executeVariable(String value) {
		String exe = null;
		switch (value) {
		case PossibleRuntimeVariables.RanEmail:
			exe = faker.internet().emailAddress();
			break;
		case PossibleRuntimeVariables.RanAlphaNum:
			exe = UUID.randomUUID().toString().split("-")[0];
			break;
		case PossibleRuntimeVariables.RanMobile:
			exe = faker.number().digits(10);
			break;
		case PossibleRuntimeVariables.RanName:
			exe = faker.name().firstName();
			break;

		default:
			break;
		}
		return exe;
	}
}

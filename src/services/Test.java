package services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

import com.github.javafaker.Faker;

public class Test {

	private final String USER_AGENT = "blah blah blah";

	public static void main(String[] args) throws Exception {

		// sendingTestRequests();
		// faker();
		String URL = "http://localhost:8080/t2c/user/$USERID$/complex";
		if (URL.contains("$")) {
			System.out.println("yes");
		}

		for (String string : URL.split("\\$")) {
			System.out.println(string);
		}
		String key = URL.split("\\$")[1];
		String url = URL.replaceAll("\\$" + key + "\\$", "777");
		System.out.println(url);

	}

	private static void faker() {
		Faker faker = new Faker();
		int i = 0;
		HashSet<String> emails = new HashSet<>();
		while (i < 100) {
			i++;
			System.out.println(faker.name().firstName());
		}
	}

	private static void sendingTestRequests() throws Exception {
		Test http = new Test();
		long now = System.currentTimeMillis();
		// System.out.println("Testing 1 - Send Http GET request");
		// http.sendGet();

		System.out.println("\nTesting 2 - Send Http POST request");
		// http.sendPost("http://localhost:8080/t2c/auth/login",
		// "email=abhinav1@istarindia.com&password=test123");
		// http.sendPost("http://localhost:8080/t2c/user/create",
		// "email=aj@demo.com&password=test&mobile=7899216009");
		http.sendPut("http://localhost:8080/t2c/user/password/reset", "userId=777&password=test123");
		// http.sendPost1();
		System.out.println((System.currentTimeMillis() - now) + " milliseconds were taken by this request");
	}

	// HTTP GET request
	public void sendGet() throws Exception {

		String url = "http://localhost:8080/t2c/user/174/complex";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		// System.out.println(response.toString());

	}

	public void sendPost(String url, String urlParameters) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

	}

	public void sendPut(String url, String urlParameters) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("PUT");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		// Send put request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'PUT' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

	}

	private void sendPut1(String url, String urlParameters) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("PUT");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

	}

}

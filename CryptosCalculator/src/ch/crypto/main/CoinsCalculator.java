package ch.crypto.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoinsCalculator {

	private static final DecimalFormat df = new DecimalFormat("0.00");
	private static final Pattern p = Pattern.compile("(\\d+(?:\\.\\d+)?)");
	
	public static void main(String[] args) {
		try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
			String output;
			double total = 0.0;
			while ((output = br.readLine()) != null) {
				double val = new CoinsCalculator().getValueOfCoin(output.split("=")[0], Integer.parseInt(output.split("=")[1]));
				System.out.println("The value of " + output.split("=")[0] + " : " + df.format(val) + " EUR");
				total += val;
			}

			System.out.println("\nThe total value of all Bob's investments : " + df.format(total) + " EUR");

		} catch (IOException | NumberFormatException e) {
			System.out.println("Exception occured in main method: " + e.getMessage());
		}
	}

	public double getValueOfCoin(String coin, int quantity) {
		HttpURLConnection conn = null;
		
		try {
			
			URL url = new URL("https://min-api.cryptocompare.com/data/price?fsym=" + coin + "&tsyms=EUR");
			conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
			}

			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);

			String output;
			while ((output = br.readLine()) != null) {
				Matcher m = p.matcher(output);
				String d = "";
				if(m.find()) {
					d = m.group();
				}
				return Double.parseDouble(d) * quantity;
			}

		} catch (IOException e) {
				System.out.println("Exception occured in getValueOfCoin method: " + e.getMessage());
		}  finally {
			conn.disconnect();
		}
		return 0.00;
	}

}

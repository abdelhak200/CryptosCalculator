package ch.crypto.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;

import org.junit.Test;

import ch.crypto.main.CoinsCalculator;

public class CoinsCalculatorTest {
	
	private CoinsCalculator underTest = new CoinsCalculator();

	@Test
	public void testGetValueOfCoin() throws IOException, InterruptedException {
		String coin = "BTC";
		int quantity = 10;
		double result = underTest.getValueOfCoin(coin, quantity);
		
		assertNotNull(result);
		
	}
	
	@Test
	public void testAPICallReturnStatusCode200() throws IOException, InterruptedException {
		
		HttpClient client = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=EUR")).build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		
		assertEquals(response.statusCode(),200);
		assertEquals(true, response.body().startsWith("{\"EUR\":"));
		
	}
	
	@Test(expected = IOException.class)
	public void testAPICallWithIOException() throws IOException, InterruptedException {

		HttpClient client = HttpClient.newBuilder().build();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://min-api.cryptocompare")).build();
		client.send(request, BodyHandlers.ofString());
	}
	
	@Test
    public void ensureThatJsonIsReturnedAsContentType() throws Exception {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=EUR")).build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        Optional<String> firstValue = response.headers().firstValue("Content-Type");
        
        String string = firstValue.get();
        assertEquals(string.startsWith("application/json"),true);
        assertEquals(true, response.body().startsWith("{\"EUR\":"));
    }


}

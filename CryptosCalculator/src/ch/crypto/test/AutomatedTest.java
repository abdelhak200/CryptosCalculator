package ch.crypto.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

 
public class AutomatedTest {
	
	private static final Pattern p = Pattern.compile("(\\d+(?:\\.\\d+)?)");
   
	@Test
	public void main(){
		
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		WebDriver driver=new ChromeDriver();

		driver.get("https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=EUR");
		
	
		WebElement tag = driver.findElement(By.tagName("pre"));
		
		Matcher m = p.matcher(tag.getText());
		String d = "";
		if(m.find()) {
			d = m.group();
		}
		
		Assert.assertNotNull(d);
		
	}
}
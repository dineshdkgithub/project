package Demo.demo;

import java.time.Duration;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class demo_project extends CMN {
	@Test(priority=1)
	public void Login() throws InterruptedException{
		//Open Driver
		chromedriver();
		driver.get("https://www.booking.com/flights");
	}
	@Test(dataProvider = excel,testName = "Search",priority=2)
	public void search_flight(Map mapdata) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String from="#basiclayout > div > div > div:nth-child(1) > div > div > div > div > div.SearchBoxHorizontalDefault-module__wrapper___oW4Vs > div:nth-child(2) > div > div > div > div > div.SegmentHorizontal-module__destination___a6iPJ > div > button:nth-child(1)";
		//From location
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(from))).click();
		WebElement fromfield =driver.findElement(By.xpath("//input[@placeholder='Airport or city']"));
		fromfield.sendKeys(Keys.BACK_SPACE);
		fromfield.sendKeys((String)mapdata.get("From Location"));
		//Select Airport
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='List-module__location___Yigjj']//b[contains(text(),'"+(String)mapdata.get("Location Code")+"')]"))).click();
		//To Location
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ShellButton-module__inner___dHha+']//span[contains(text(), 'Where to?')]"))).click();
		fromfield.sendKeys((String)mapdata.get("To Location"));
		Thread.sleep(3000);
	}
}

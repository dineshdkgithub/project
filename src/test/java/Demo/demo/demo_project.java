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
		try {
		String from="//button[@data-ui-name='input_location_from_segment_0']";
		//From location
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(from))).click();
		WebElement fromfield =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Airport or city']")));
		fromfield.sendKeys(Keys.BACK_SPACE);
		fromfield.sendKeys((String)mapdata.get("From Location"));
		//Select Airport
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='List-module__location___Yigjj']//b[contains(text(),'"+(String)mapdata.get("Location Code")+"')]"))).click();
		//To Location
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ShellButton-module__inner___dHha+']//span[contains(text(), 'Where to?')]"))).click();
		driver.findElement(By.xpath("//input[@placeholder='Airport or city']")).sendKeys((String)mapdata.get("To Location"));
		//Select To Location
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='List-module__location___Yigjj']//b[contains(text(),'"+(String)mapdata.get("To Location Code")+"')]"))).click();
		//Select Date
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-ui-name='button_date_segment_0']"))).click();
		//Select Depart date
		driver.findElement(By.xpath("//span[@data-date='"+(String)mapdata.get("Depart date")+"']")).click();//2024-08-03
		//Select Return date
		driver.findElement(By.xpath("//span[@data-date='"+(String)mapdata.get("Return date")+"']")).click();
		//Select adult
		driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy']")).click();
		String Adult=(String)mapdata.get("No Of Adult");
		while(true) {
			//Select Adult
			String adult=driver.findElement(By.xpath("//div[@data-ui-name='occupancy_adults']//span[@class='InputStepper-module__value___Hr63o']")).getText();
			if (adult.equals(Adult)) {
		        break;
		    } else {
		        // Click the next month button
		        driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy_adults_plus']")).click();
		    }
		}
//		String Child=(String)mapdata.get("No Of Child");
//		while(true) {
//			//Select Adult
//			String child=driver.findElement(By.xpath("//div[@data-ui-name='occupancy_children']//span[@class='InputStepper-module__value___Hr63o']")).getText();
//			if (child.equals(Child)) {
//		        break;
//		    } else {
//		        // Click the next month button
//		        driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy_children_plus']")).click();
//		    }
//		}
//		//Select
		//Submit
		driver.findElement(By.xpath("//button[@data-ui-name='button_search_submit']")).click();
		Thread.sleep(10000);
		//View flight details
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-describedby='flight-card-0']"))).click();
		
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}


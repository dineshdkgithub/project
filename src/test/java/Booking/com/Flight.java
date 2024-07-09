package Booking.com;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Flight extends CMN {
	@Test(priority=1)
	public void Login() throws InterruptedException{
		//Open Driver
		chromedriver();
		driver.get("https://www.booking.com/flights");
	}
	@Test(dataProvider = excel,testName = "Search",priority=2)
	public void Round_trip(Map mapdata) throws InterruptedException, IOException {
		String testcase =(String)mapdata.get("Test Case");
		test = extent.createTest("Round_trip","This is Round_trip Positive : "+testcase);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
		String from="//button[@data-ui-name='input_location_from_segment_0']";
		String to ="//button[@data-ui-name='input_location_to_segment_0']";
		//From location
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(from))).click();
		WebElement fromfield =wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Airport or city']")));
		fromfield.sendKeys(Keys.BACK_SPACE);
		fromfield.sendKeys((String)mapdata.get("From Location"));
		//Select Airport
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='List-module__location___Yigjj']//b[contains(text(),'"+(String)mapdata.get("Location Code")+"')]"))).click();
		//To Location
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(to))).click();
		driver.findElement(By.xpath("//input[@placeholder='Airport or city']")).sendKeys(Keys.BACK_SPACE);
		driver.findElement(By.xpath("//input[@placeholder='Airport or city']")).sendKeys((String)mapdata.get("To Location"));
		//Select To Location
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@class='List-module__location___Yigjj']//b[contains(text(),'"+(String)mapdata.get("To Location Code")+"')]"))).click();
		//Select Date
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-ui-name='button_date_segment_0']"))).click();
		String depart =(String)mapdata.get("Depart date");
		String returns =(String)mapdata.get("Return date");
        String larrow ="//button[@class='Actionable-module__root___VRKD+ Button-module__root___Q6nj1 Button-module__root--variant-tertiary___jSdz0 Button-module__root--icon-only___ojeRa Button-module__root--size-large___kP7cl Button-module__root--variant-tertiary-neutral___G8p0m Calendar-module__control___I7JT6 Calendar-module__control--prev___Mi8Du']";
        String rarrow ="//button[@class='Actionable-module__root___VRKD+ Button-module__root___Q6nj1 Button-module__root--variant-tertiary___jSdz0 Button-module__root--icon-only___ojeRa Button-module__root--size-large___kP7cl Button-module__root--variant-tertiary-neutral___G8p0m Calendar-module__control___I7JT6 Calendar-module__control--next___jXzpD']";
        // Select Depart date
        selectDate(driver, depart, larrow, rarrow);

        // Select Return date
        selectDate(driver, returns, larrow, rarrow);
        
		//Select adult
		driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy']")).click();
		String no_of_adult=(String)mapdata.get("No Of Adult");
		while(true) {
			//Select Adult
			String adult=driver.findElement(By.xpath("//div[@data-ui-name='occupancy_adults']//span[@class='InputStepper-module__value___Hr63o']")).getText();
			if (adult.equals(no_of_adult)) {
		        break;
		    } else {
		        // Click the next month button
		        driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy_adults_plus']")).click();
		    }
		}
		String no_of_child=(String)mapdata.get("No Of Child");
		while(true) {
			//Select Child
			String child=driver.findElement(By.xpath("//div[@data-ui-name='occupancy_children']//span[@class='InputStepper-module__value___Hr63o']")).getText();
			if (child.equals(no_of_child)) {
		        break;
		    }else if (Integer.parseInt(no_of_child) < Integer.parseInt(child)){
		    	driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy_children_minus']")).click();	
		    }else {
		        // Click the next month button
		        driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy_children_plus']")).click();
		        }
		    }
		for(int i=0;i< Integer.parseInt(no_of_child);i++) {
			driver.findElement(By.xpath("//select[@name='sr_occupancy_children_age_"+i+"']")).click();
			driver.findElement(By.xpath("//select[@name='sr_occupancy_children_age_"+i+"']/option[@value='"+(String)mapdata.get("Age Of Child_"+i+"")+"']")).click();
		}
		//Select
		//Submit
		driver.findElement(By.xpath("//button[@data-ui-name='button_search_submit']")).click();
		Thread.sleep(10000);
		//View flight details
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-describedby='flight-card-0']"))).click();
		Thread.sleep(2000);
		screenshotpath();
		driver.findElement(By.xpath("//div[contains(@class, 'DismissibleContainer-module__root___su8jr') and contains(@class, 'SheetContainer-module__header___PJvIW')]//button[@aria-label='Close']")).click();
		test.log(Status.PASS, MarkupHelper.createCodeBlock("Search Flight Pass".toString()));
		}catch(Exception e) {
			System.out.println(e);
			screenshotpath();
			test.log(Status.FAIL, MarkupHelper.createCodeBlock(e.toString()));
		}
	}
}

package Booking.com;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import Pageobject.*;
public class Flight extends CMN {
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	
	
	public void from_location(Map mapdata) throws InterruptedException {
		//From location
		wait.until(ExpectedConditions.visibilityOf(Search.from)).click();
		WebElement fromfield =wait.until(ExpectedConditions.visibilityOf(Search.fromfield));
		fromfield.sendKeys(Keys.BACK_SPACE);
		fromfield.sendKeys((String)mapdata.get("From Location"));
		//Select Airport
		String fromLocationCode = (String) mapdata.get("From Location Code");
		Thread.sleep(2000);
		clickLocationCode(driver, wait, fromLocationCode, "From");
	}
	
	public void to_location(Map mapdata) throws InterruptedException {
		//To Location
		wait.until(ExpectedConditions.visibilityOf(Search.to)).click();
		Search.fromfield.sendKeys(Keys.BACK_SPACE);
		Search.fromfield.sendKeys((String)mapdata.get("To Location"));
		//Select To Location
		String toLocationCode = (String) mapdata.get("To Location Code");
		Thread.sleep(2000);
        clickLocationCode(driver, wait, toLocationCode, "To");
		
	}
	
	@Test(dataProvider = excel,testName = "Search",priority=1)
	public void Round_trip(Map mapdata) throws InterruptedException, IOException {
		PageFactory.initElements(driver, Search.class);
		String testcase =(String)mapdata.get("Test Case");
		test = extent.createTest("Round_trip","This is Round_trip Positive : "+testcase);
		
		try {
		from_location(mapdata);
		to_location(mapdata);
		//Select Date
		wait.until(ExpectedConditions.visibilityOf(Search.select_date)).click();
		String depart =(String)mapdata.get("Depart date");
		String returns =(String)mapdata.get("Return date");
        String left_arrow ="//button[@class='Actionable-module__root___VRKD+ Button-module__root___Q6nj1 Button-module__root--variant-tertiary___jSdz0 Button-module__root--icon-only___ojeRa Button-module__root--size-large___kP7cl Button-module__root--variant-tertiary-neutral___G8p0m Calendar-module__control___I7JT6 Calendar-module__control--prev___Mi8Du']";
        String right_arrow ="//button[@class='Actionable-module__root___VRKD+ Button-module__root___Q6nj1 Button-module__root--variant-tertiary___jSdz0 Button-module__root--icon-only___ojeRa Button-module__root--size-large___kP7cl Button-module__root--variant-tertiary-neutral___G8p0m Calendar-module__control___I7JT6 Calendar-module__control--next___jXzpD']";
        try {
        // Select Depart date
        selectDate(driver, depart, left_arrow, right_arrow);
        // Select Return date
        selectDate(driver, returns, left_arrow, right_arrow);
        }catch(Exception e) {
        	driver.findElement(By.xpath("//div[text()='Round trip']")).click();
        }
		//Select adult
		driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy']")).click();
		String no_of_adult=(String)mapdata.get("No Of Adult");
		
		try {
		while(true) {
			//Select Adult
			String adult=driver.findElement(By.xpath("//div[@data-ui-name='occupancy_adults']//span[@class='InputStepper-module__value___Hr63o']")).getText();
			if (adult.equals(no_of_adult)||no_of_adult.isBlank()) {
		        break;
			} else if((Integer.parseInt(adult) > Integer.parseInt(no_of_adult))){
				// Click the Minus button
		        driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy_adults_minus']")).click();
			}else {
		        // Click the Plus button
		        driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy_adults_plus']")).click();
		    }
		}
		String no_of_child=(String)mapdata.get("No Of Child");
		while(true) {
			//Select Child
			String child=driver.findElement(By.xpath("//div[@data-ui-name='occupancy_children']//span[@class='InputStepper-module__value___Hr63o']")).getText();
			if (child.equals(no_of_child)||no_of_child.isBlank()) {
		        break;
		    }else if (Integer.parseInt(child) > Integer.parseInt(no_of_child)){
		    	driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy_children_minus']")).click();	
		    }else {
		        // Click the next month button
		        driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy_children_plus']")).click();
		        }
		    }
		//Select Child Age
		for(int i=0;i< Integer.parseInt(no_of_child);i++) {
			driver.findElement(By.xpath("//select[@name='sr_occupancy_children_age_"+i+"']")).click();
			driver.findElement(By.xpath("//select[@name='sr_occupancy_children_age_"+i+"']/option[@value='"+(String)mapdata.get("Age Of Child_"+i+"")+"']")).click();
		}
		}catch(Exception e) {
			driver.findElement(By.xpath("//div[text()='Round trip']")).click();
		}
		//Submit
		driver.findElement(By.xpath("//button[@data-ui-name='button_search_submit']")).click();
		Thread.sleep(2000);
		// Find elements matching the XPath
        boolean isElementVisible = driver.findElements(By.xpath("//div[@class='Frame-module__margin_4___3zoZe']")).size() > 0;
        List<WebElement> error = driver.findElements(By.xpath("//div[@class='ErrorMessage-module__wrapper___HuZk3 ErrorMessage-module__wrapperBottom___HmOJb']"));
        if (isElementVisible) {
		WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='Frame-module__margin_4___3zoZe']")));
			String actual_result =msg.getText();
			screenshotpath();
			test.log(Status.FAIL, MarkupHelper.createCodeBlock(actual_result.toString()));
        }else if(!error.isEmpty()){
        	screenshotpath();
			test.log(Status.FAIL, MarkupHelper.createCodeBlock("Error While Searching\nFlight Location Search Test Fail".toString()));
        }else {
		//View flight details
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@aria-describedby='flight-card-0']"))).click();
		Thread.sleep(2000);
		screenshotpath();
		driver.findElement(By.xpath("//div[contains(@class, 'DismissibleContainer-module__root___su8jr') and contains(@class, 'SheetContainer-module__header___PJvIW')]//button[@aria-label='Close']")).click();
		test.log(Status.PASS, MarkupHelper.createCodeBlock("Flight Location Search Test Pass".toString()));
        }
        }catch(Exception e) {
			System.out.println(e);
			screenshotpath();
			test.log(Status.FAIL, MarkupHelper.createCodeBlock(e.toString()));
		}
	}
}

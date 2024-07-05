package Booking.com;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	public void Search_flight(Map mapdata) throws InterruptedException, IOException {
		String testcase =(String)mapdata.get("Test Case");
		test = extent.createTest("Search_flight","This is Search flight Positive : "+testcase);
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
		String depart =(String)mapdata.get("Depart date");
		//date saprate
		LocalDate date = LocalDate.parse(depart, DateTimeFormatter.ISO_LOCAL_DATE);
		int year = date.getYear();
        String month = date.getMonth().name();  // Gets the month name in uppercase
        int day = date.getDayOfMonth();
        //Month
        String monthTitleCase = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();
        String verify=monthTitleCase+" "+year;
        System.out.println(year+monthTitleCase+day);
        while(true) {
        	String Month=driver.findElement(By.xpath("//div[contains(@class, 'Calendar-module__monthWrapper___snCld')]//h3[contains(@class, 'Text-module__root--variant-strong_1___0mtJ-') and contains(@class, 'Calendar-module__month___zJF7A')]")).getText();
        	if(verify.equals(Month)) {
        		break;	
        	}else {
        		
        	}
        }
        	
        //Select Depart date
		driver.findElement(By.xpath("//span[@data-date='"+depart+"']")).click();//2024-08-03
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
		String Child=(String)mapdata.get("No Of Child");
		while(true) {
			//Select Child
			String child=driver.findElement(By.xpath("//div[@data-ui-name='occupancy_children']//span[@class='InputStepper-module__value___Hr63o']")).getText();
			if (child.equals(Child)) {
		        break;
		    }else if (Integer.parseInt(Child) < Integer.parseInt(child)){
		    	
		    	driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy_children_minus']")).click();
		    	
		    }else {
		    }
		        // Click the next month button
		        driver.findElement(By.xpath("//button[@data-ui-name='button_occupancy_children_plus']")).click();
		    }
		for(int i=0;i< Integer.parseInt(Child);i++) {
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

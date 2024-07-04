package Demo_Project.demo_project;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Sales_Force extends Common_Files{
	@Test(dataProvider = excel,testName = "Login",priority=1)
	public void Login(Map mapdata) throws InterruptedException{
		chromedriver();
		//Login
		String user = (String)mapdata.get("Username");
		String password =(String)mapdata.get("Password");
		login(driver,user,password);
		//Contact
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String xpath = "//a[contains(@href, '/lightning/app/06mIS000001Gp07YAC')]";
		Common_Files.navigateToHyperlinkByXPath(driver, wait, xpath);
		
	}
	@Test(dataProvider = excel,testName = "Test Data",priority=2)
	public void Add_New_Contact(Map mapdata) throws InterruptedException, IOException{
		String testcase =(String)mapdata.get("Test Case");
		String salutation =(String)mapdata.get("Salutation");
		String first =(String)mapdata.get("First Name");
		String last =(String)mapdata.get("Last Name");
		String account = (String)mapdata.get("Account");
		String title= (String)mapdata.get("Title");
		 test = extent.createTest("Add_New_Contact","This is Add Contacts : "+testcase);
		try {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//New
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'New')]"))).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Salutations
        driver.findElement(By.xpath("//button[@data-value='--None--']")).click();
        driver.findElement(By.xpath("//lightning-base-combobox-item[@data-value='"+salutation+"']")).click();
        //First name
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='firstName']"))).sendKeys(first);
        //Last Name
        driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys(last);
        //Account
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search Accounts...']"))).sendKeys(account);
        Thread.sleep(2000);
        //Down Arrow and Enter
        downenter();
        //Title
        driver.findElement(By.xpath("//input[@name='Title']")).sendKeys(title);
        //Report to
        //driver.findElement(By.xpath("//input[@placeholder='Search Contacts...']")).click();
        Thread.sleep(1000);
        //downenter();
        //Description
        driver.findElement(By.xpath("//label[text()='Description']/following-sibling::div//textarea[@class='slds-textarea']")).sendKeys((String)mapdata.get("Description"));
        //Phone
        driver.findElement(By.xpath("//input[@name='Phone']")).sendKeys((String)mapdata.get("Phone"));
        //Email
        driver.findElement(By.xpath("//input[@name='Email']")).sendKeys((String)mapdata.get("Email"));
        //Mailing street
        driver.findElement(By.xpath("//textarea[@name='street']")).sendKeys((String)mapdata.get("Mailing street"));
        //Mailing city
        driver.findElement(By.xpath("//input[@name='city']")).sendKeys((String)mapdata.get("Mailing City"));
        //Postalcode
        driver.findElement(By.xpath("//input[@name='postalCode']")).sendKeys((String)mapdata.get("Postal code"));
        //Province
        driver.findElement(By.xpath("//input[@name='province']")).sendKeys((String)mapdata.get("State"));
        //Country
        driver.findElement(By.xpath("//input[@name='country']")).sendKeys((String)mapdata.get("Country"));
       // List<WebElement> warn =wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//header[@class='pageErrorHeader slds-popover__header']")));
        try{
        	String text =driver.findElement(By.xpath("//header[@class='pageErrorHeader slds-popover__header']")).getText();
        	System.out.println(text);
        	//close
        	driver.findElement(By.xpath("//button[@name='CancelEdit']")).click();
        	screenshotpath();
			test.log(Status.FAIL, MarkupHelper.createCodeBlock("Message : "+text.toString()));
        }catch(Exception e) {
        // save
        driver.findElement(By.xpath("//button[@name='SaveEdit']")).click();
        Thread.sleep(2000);
        List<WebElement> text =wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//header[@class='pageErrorHeader slds-popover__header']")));
        if(!text.isEmpty()) {
         String warn=text.get(0).getText();
       //close
     	driver.findElement(By.xpath("//button[@name='CancelEdit']")).click();
     	screenshotpath();
			test.log(Status.FAIL, MarkupHelper.createCodeBlock("Message : "+warn.toString()));
        }else {
        //Toast Message
        String actual_result= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='toastMessage slds-text-heading--small forceActionsText']"))).getText();
        //System.out.println(actual_result);
        screenshotbase64();
		 test.log(Status.PASS, MarkupHelper.createCodeBlock("Message : "+actual_result+"\n".toString()));
        }
		}
	}catch(Exception e){
		screenshotpath();
		test.log(Status.FAIL, MarkupHelper.createCodeBlock(e.toString()));
		
	                }
	   }
	package Demo.Project;

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
	}
}
}

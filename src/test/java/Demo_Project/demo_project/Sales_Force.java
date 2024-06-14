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
	@Test(dataProvider = excel,testName = "Test Data",priority=1)
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
		 test = extent.createTest("Add_New_Contact","This is Add Contacts : "+testcase);
		try {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//New
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'New')]"))).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //Salutations
        driver.findElement(By.xpath("//button[@data-value='--None--']")).click();
        driver.findElement(By.xpath("//lightning-base-combobox-item[@data-value='Mr.']")).click();
        //First name
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='firstName']"))).sendKeys("Dinesh");
        //Last Name
        driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("S");
        //Account
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Search Accounts...']"))).sendKeys("Sales");
        Thread.sleep(2000);
        //Down Arrow and Enter
        downenter();
        //Title
        driver.findElement(By.xpath("//input[@name='Title']")).sendKeys("Sales Force");
        //Report to
        //driver.findElement(By.xpath("//input[@placeholder='Search Contacts...']")).click();
        Thread.sleep(1000);
        //downenter();
        //Description
        driver.findElement(By.xpath("//label[text()='Description']/following-sibling::div//textarea[@class='slds-textarea']")).sendKeys("Good");
        //Phone
        driver.findElement(By.xpath("//input[@name='Phone']")).sendKeys("6379978010");
        //Email
        driver.findElement(By.xpath("//input[@name='Email']")).sendKeys("dinesh@gmail.com");
        //Mailing street
        driver.findElement(By.xpath("//textarea[@name='street']")).sendKeys("North street, Tambaram");
        //Mailing city
        driver.findElement(By.xpath("//input[@name='city']")).sendKeys("Chennai");
        //Postalcode
        driver.findElement(By.xpath("//input[@name='postalCode']")).sendKeys("600064");
        //Province
        driver.findElement(By.xpath("//input[@name='province']")).sendKeys("Tamil Nadu");
        //Country
        driver.findElement(By.xpath("//input[@name='country']")).sendKeys("India");
        List<WebElement> warn =wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@class='slds-icon slds-icon-text-warning slds-icon_small']")));
        if(!warn.isEmpty()) {
        	String text =driver.findElement(By.xpath("//header[@class='pageErrorHeader slds-popover__header']")).getText();
        	System.out.println(text);
        	//close
        	driver.findElement(By.xpath("//button[@name='CancelEdit']")).click();
        	screenshotpath();
			test.log(Status.FAIL, MarkupHelper.createCodeBlock("Message : "+text.toString()));
        }else {
        // save
        driver.findElement(By.xpath("//button[@name='SaveEdit']")).click();
        Thread.sleep(2000);
        //Toast Message
        String actual_result= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='toastMessage slds-text-heading--small forceActionsText']"))).getText();
        //System.out.println(actual_result);
        screenshotbase64();
		 test.log(Status.PASS, MarkupHelper.createCodeBlock("Message : "+actual_result+"\n".toString()));
		}
	}catch(Exception e){
		screenshotpath();
		test.log(Status.FAIL, MarkupHelper.createCodeBlock(e.toString()));
		
	                }
	   }
}

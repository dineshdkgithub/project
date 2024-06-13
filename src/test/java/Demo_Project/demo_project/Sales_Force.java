package Demo_Project.demo_project;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Sales_Force extends Common_Files{
	public static void main(String[] args) throws InterruptedException {
		chromedriver();
		driver.get("https://business-speed-8669.my.salesforce.com/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("dk6075790-gcxd@force.com");
		//Password
		driver.findElement(By.id("password")).sendKeys("Dk21072000");
		//From Location
		WebElement from =driver.findElement(By.id("Login"));
		from.click();
		//Contact
		String xpath = "//a[contains(@href, '/lightning/app/06mIS000001Gp07YAC')]";
		Common_Files.navigateToHyperlinkByXPath(driver, wait, xpath);
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
         
		}
	}



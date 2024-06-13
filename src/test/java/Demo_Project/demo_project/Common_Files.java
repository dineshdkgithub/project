package Demo_Project.demo_project;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Common_Files {
	public static  WebDriver driver;
	//ChromeDriver
	public static void chromedriver() {
        driver = new ChromeDriver();
		//Window Maximize
		driver.manage().window().maximize();
    }
	//EdgeDriver
	public static void edgedriver() {
	    // Initialize EdgeDriver
	     driver = new EdgeDriver();
	    
	    // Maximize the browser window
	    driver.manage().window().maximize();
	}
	//FireFoxDriver
	public  void firefoxdriver() {
	    // Initialize FirefoxDriver
	     driver = new FirefoxDriver();
	    
	    // Maximize the browser window
	    driver.manage().window().maximize();
	}

    public WebDriver getDriver() {
       
        return driver;
    }
    public static void navigateToHyperlinkByXPath(WebDriver driver, WebDriverWait wait, String xpath) {
        // Locate the hyperlink element by XPath
        WebElement hyperlink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        
        // Get the value of the href attribute
        String href = hyperlink.getAttribute("href");
        
        // Navigate to the URL
        driver.get(href);
    }
    public static void downenter() {
        Actions actions = new Actions(driver);
        
        // Perform "Actions down" (simulates pressing the down arrow key)
        actions.sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();

    }

}


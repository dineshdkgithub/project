package Demo_Project.demo_project;

import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.qameta.allure.Allure;

public class Common_Files {
	public static  WebDriver driver;
	protected static final String excel = "Demo";
	@DataProvider(name = excel)
    public static  Object[][]getdata(Method m)  throws IOException {
    	//System.out.println(" method name : " + m.getName());
    	String testcaseSheetName = ((Test) m.getAnnotation(Test.class)).testName();
        String filePath = "C:\\excel\\Demo.xlsx";
        FileInputStream fis = new FileInputStream(new File(filePath));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        int totalSheets = workbook.getNumberOfSheets();
        Object[][] data = null;
        for (int sheetIndex = 0; sheetIndex < totalSheets; sheetIndex++) {
	        XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
	        String sheetName = sheet.getSheetName();
	        if(!sheetName.equalsIgnoreCase(testcaseSheetName)) {
	        	continue;
	        }
	        int rows = sheet.getPhysicalNumberOfRows();
	        int cols = sheet.getRow(0).getLastCellNum();
	        //System.out.println("rows =  " + rows + " cols = " +cols);
	        DataFormatter formatter = new DataFormatter();
	        //System.out.println("Sheet Name: " + sheet.getSheetName());
	        //System.out.println("Sheet: " + sheetIndex);
	        data = new Object[cols-1][1];
	        Map<Integer, Map<String, Object>> initMap = new HashMap<>();
	        for(int i = 1; i < cols;i++) {
	        	Map<String, Object> colMap = new HashMap<String, Object>();
	        	initMap.put(i, colMap);
	        	data[i-1][0] = colMap;
	        }
	        int rowIndex = 0, colIndex = 0;
	        for (int i = 0; i < rows; i++) { 
	        	rowIndex = 0;
	            for (int j = 1; j < cols; j++) {
	            	Map<String, Object> colMap = initMap.get(j);
	            	String colName = formatter.formatCellValue(sheet.getRow(i).getCell(0));
	            	colMap.put(colName, formatter.formatCellValue(sheet.getRow(i).getCell(j)));
	                //System.out.println(colName + " >> " + colMap.get(colName));
	            }
	            colIndex++;
	        }
	        workbook.close();
	        fis.close();
	        return data;
	        }
return data;
        }
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
        actions.sendKeys(Keys.ENTER).build().perform();

    }
    public static void login(WebDriver driver, String user ,String password) {
    	driver.get("https://business-speed-8669.my.salesforce.com/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(user);
		//Password
		driver.findElement(By.id("password")).sendKeys(password);
		//From Location
		WebElement from =driver.findElement(By.id("Login"));
		from.click();
		//dk6075790-gcxd@force.com
		//Dk21072000
    }
  //ExtentReports
    public static ExtentReports extent;
    public static ExtentTest test;
    @BeforeSuite
    public void startReport() {
        // Get the current timestamp
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

        // Get the class name
        String className = this.getClass().getSimpleName();

        // Construct the report file name based on current time and class name
        String reportFileName = className + "_" + timeStamp + ".html";

        String reportDirectoryPath = "C:/Reports";
        File reportDirectory = new File(reportDirectoryPath);
        if (!reportDirectory.exists()) {
            reportDirectory.mkdirs(); // Create the directory if it does not exist
        }

        // Construct the full report file path
        String reportFilePath = reportDirectoryPath + "/" + reportFileName;

        // Create an ExtentSparkReporter with the dynamic file name
        ExtentSparkReporter reporter = new ExtentSparkReporter(reportFilePath);
  
        // Actual report
        extent = new ExtentReports();
        // To call reporter
        extent.attachReporter(reporter);
    }
	@AfterSuite
	public void Stoptest() {
		//ExtentReports extent ;
		extent.flush();
		driver.close();
	}
	public static String CaptureScreenshot() {
		TakesScreenshot takesScreenshot = (TakesScreenshot)driver;
		String base64code=takesScreenshot.getScreenshotAs(OutputType.BASE64);
		return base64code;
	}
	
	public static String captureScreenshotPath() throws IOException {
        String fileSeparator = System.getProperty("file.separator");
        String extentReportPath = "C:" + fileSeparator + "Reports";
        String screenshotPath = extentReportPath + fileSeparator + "screenshots";

        File directory = new File(screenshotPath);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it does not exist
        }

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String screenshotName = "screenshot_" + timeStamp + ".png";
        String screenshotFullPath = screenshotPath + fileSeparator + screenshotName;

        FileUtils.copyFile(src, new File(screenshotFullPath));
        return screenshotName; // Return the screenshot name instead of full path
    }
	//Hide Screenshot
		public void screenshotbase64() {
		String base64Code = CaptureScreenshot();
	    test.addScreenCaptureFromBase64String(base64Code);
	}
		//Review Screenshot
		public void screenshotpath() throws IOException {
	        String screenshotName = captureScreenshotPath();
	        String relativePath = "screenshots/" + screenshotName; // Relative path to be used in the report
	        test.addScreenCaptureFromPath(relativePath);
	    }
    
    public void allscreenshot(WebDriver driver, String actualresult) {
        ByteArrayInputStream screenshotStream = new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        Allure.addAttachment(actualresult, screenshotStream);
    }
}

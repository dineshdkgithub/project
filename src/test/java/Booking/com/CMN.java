package Booking.com;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
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


	public class CMN {
		//token : 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
		public static  WebDriver driver;
		protected static final String excel = "HRM";
		@DataProvider(name = excel)
	    public static  Object[][]getdata(Method m)  throws IOException {
	    	//System.out.println(" method name : " + m.getName());
	    	String testcaseSheetName = ((Test) m.getAnnotation(Test.class)).testName();
	        String filePath = "D:\\Data\\Testdata.xlsx";
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

	        String reportDirectoryPath = "D:/Reports";
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
	        String extentReportPath = "D:" + fileSeparator + "Reports";
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
	    //Select Data
	    public static void selectDate(WebDriver driver, String dateToSelect, String larrow, String rarrow) throws ParseException {
	        List<WebElement> leftarrow = driver.findElements(By.xpath(larrow));
	        List<WebElement> rightarrow = driver.findElements(By.xpath(rarrow));
	        //Change Date format
	        SimpleDateFormat oldFormat = new SimpleDateFormat("M/dd/yy");
	        SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
	        //
	            Date oldDate = oldFormat.parse(dateToSelect);
	            String newDateString = newFormat.format(oldDate);
	            System.out.println(newDateString);
	        boolean leftArrowClicked = false;

	        while (true) {
	            List<WebElement> Month = driver.findElements(By.xpath("//span[@data-date='" + newDateString + "']"));
	            if (!Month.isEmpty()) {
	                Month.get(0).click();
	                break;
	            } else if (!leftArrowClicked && !leftarrow.isEmpty()) {
	                leftarrow.get(0).click();
	                leftArrowClicked = true;
	            } else if (!rightarrow.isEmpty()) {
	                rightarrow.get(0).click();
	            } else {
	                System.out.println("Error while Selecting Date !!");
	                driver.findElement(By.xpath("//h1[@class='Text-module__root--variant-headline_1___Zs-em']")).click();
	                break;
	            }
	        }
	    }
	    //Select Location
	    public static void clickLocationCode(WebDriver driver, WebDriverWait wait, String locationCode, String locationType) {
	        String locationXpath;
	        if ("From".equals(locationType)) {
	            locationXpath = "//li[@class='List-module__location___Yigjj']//b[contains(text(),'" + locationCode + "')]";
	        } else if ("To".equals(locationType)) {
	            locationXpath = "//li[@class='List-module__location___Yigjj']//b[contains(text(),'" + locationCode + "')]";
	        } else {
	            throw new IllegalArgumentException("Invalid location type: " + locationType);
	        }

	        // Find elements matching the XPath
	        boolean isElementVisible = driver.findElements(By.xpath(locationXpath)).size() > 0;

	        if (isElementVisible) {
	            WebElement locationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locationXpath)));
	            locationElement.click();
	        } else {
	            driver.findElement(By.xpath("//div[text()='Round trip']")).click();
	        }
	    }

	}

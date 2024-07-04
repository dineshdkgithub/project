package Demo.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class CMN {
	//token : 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
	public static  WebDriver driver;
	protected static final String excel = "HRM";
	@DataProvider(name = excel)
    public static  Object[][]getdata(Method m)  throws IOException {
    	//System.out.println(" method name : " + m.getName());
    	String testcaseSheetName = ((Test) m.getAnnotation(Test.class)).testName();
        String filePath = "C:\\Excel\\HRM.xlsx";
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

    public static void downenter() {
        Actions actions = new Actions(driver);
        
        // Perform "Actions down" (simulates pressing the down arrow key)
        actions.sendKeys(Keys.DOWN).build().perform();
        actions.sendKeys(Keys.ENTER).build().perform();

    }
    
    public static void enter() {
        Actions actions = new Actions(driver);
        
        // Perform "Actions down" (simulates pressing the down arrow key)
        actions.sendKeys(Keys.DOWN).build().perform();
        actions.sendKeys(Keys.DOWN).build().perform();
        actions.sendKeys(Keys.ENTER).build().perform();

    }
    @AfterSuite
    public void close() {
    	//driver.close();
    }

}

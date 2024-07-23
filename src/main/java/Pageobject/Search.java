package Pageobject;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Search {
	
	@FindBy(xpath = "//button[@data-ui-name='input_location_from_segment_0']")
	public static WebElement from;
	
	@FindBy(xpath ="//input[@placeholder='Airport or city']")
	public static WebElement fromfield;
	
	@FindBy(xpath="//button[@data-ui-name='input_location_to_segment_0']")
	public static WebElement to;
	
	@FindBy(xpath="//button[@data-ui-name='button_date_segment_0']")
	public static WebElement select_date;
	
	public static String newdate(String newDateString) {
		return "//span[@data-date='" + newDateString + "']";
	}

}

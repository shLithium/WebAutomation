package Utilities;

import static Executor.Executor.OR;

import org.openqa.selenium.By;




public class Locator_Utils {
	
	
	public static By Locate(String object, String data, String locatorvalue) {
		By locator;
		if (locatorvalue != null && !locatorvalue.isEmpty()) {
			locator=Locator_Utils.locatorValue(object, locatorvalue);
			
		
		}else{
			locator=ObjectValue(object,OR.getProperty(object));
		
		}
		return locator;
	}
	
	public static By locatorValue(String locatorType, String value) { //utils function to be moved
		By by;
		switch (locatorType) {
		case "id":
			by = By.id(value);
			break;
		case "name":
			by = By.name(value);
			break;
		case "xpath":
			by = By.xpath(value);
			break;
		case "className":
			by = By.className(value);
			break;
				
		case "cssSelector":
			by = By.cssSelector(value);
			break;
			
		case "tagName":
			by = By.tagName(value);
			break;
		case "linkText":
			by = By.linkText(value);
			break;
		case "partialLinkText":
			by = By.partialLinkText(value);
			break;
		default:
			by = null;
			break;
		}
		return by;
	}
	
	
	public static By ObjectValue(String object, String objectvalue) {
		By by = null;
		if(object.contains("_id")){
			by = By.id(objectvalue);
		}
		if(object.contains("_name")){
			by = By.name(objectvalue);
		}
		if(object.contains("_xpath")){
			by = By.xpath(objectvalue);
			
		}
		if(object.contains("_className")){
			by = By.className(objectvalue);
		}
		if(object.contains("_css")){
			by = By.cssSelector(objectvalue);
		}
		if(object.contains("_tagName")){
			by = By.tagName(objectvalue);
		}
		if(object.contains("_linkText")){
			by = By.linkText(objectvalue);
		}
		if(object.contains("_partialLinkText")){
			by = By.partialLinkText(objectvalue);
		}
		return by;
	}
}

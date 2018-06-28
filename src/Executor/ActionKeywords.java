package Executor;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Executor.Executor;
import Functions.Login;
import Grid.GridExecutor;
import static Executor.Executor.OR;
import Utilities.Locator_Utils;
import Utilities.Log;

public class ActionKeywords {

	public static WebDriver driver;

	/***************** Basic HTML WEBELEMENT FUNCTIONS ***********************************/

	public static void Grid_Setup(String nodeURL, String browser,
			String platform) {

		try {

			Log.info("Setting Node Environment");
			DesiredCapabilities caps = new DesiredCapabilities();

			// Platform
			if (platform.equalsIgnoreCase("Win10"))
				caps.setPlatform(org.openqa.selenium.Platform.WIN10);
			if (platform.equalsIgnoreCase("MAC"))
				caps.setPlatform(org.openqa.selenium.Platform.MAC);
			if (platform.equalsIgnoreCase("Android"))
				caps.setPlatform(org.openqa.selenium.Platform.ANDROID);
			if (platform.equalsIgnoreCase("Win8_1"))
				caps.setPlatform(org.openqa.selenium.Platform.WIN8_1);
			if (platform.equalsIgnoreCase("Windows"))
				caps.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			if (platform.equalsIgnoreCase("Win8"))
				caps.setPlatform(org.openqa.selenium.Platform.WIN8);
			// Browsers
			if (browser.equalsIgnoreCase("Internet Explorer"))
				caps = DesiredCapabilities.internetExplorer();
			if (browser.equalsIgnoreCase("Safari"))
				caps = DesiredCapabilities.safari();
			if (browser.equalsIgnoreCase("Firefox"))
				caps = DesiredCapabilities.firefox();
			if (browser.equalsIgnoreCase("iPad"))
				caps = DesiredCapabilities.ipad();
			if (browser.equalsIgnoreCase("iPhone"))
				caps = DesiredCapabilities.iphone();
			if (browser.equalsIgnoreCase("HtmlUnit"))
				caps = DesiredCapabilities.htmlUnit();
			if (browser.equalsIgnoreCase("Edge"))
				caps = DesiredCapabilities.edge();
			if (browser.equalsIgnoreCase("Android"))
				caps = DesiredCapabilities.android();
			if (browser.equalsIgnoreCase("Chrome")) {
				caps = DesiredCapabilities.chrome();
				System.setProperty("webdriver.chrome.driver",
						"C://Java/uiautomation/libs/chromedriver.exe");
			}
			// System.out.println(nodeURL+"/wd/hub");
			driver = new RemoteWebDriver(new URL(nodeURL + "/wd/hub"), caps);

		} catch (Exception e) {
			Log.error("Setting node environment failed. Ensure that the node is registered to hub. "
					+ e.getMessage());
			GridExecutor.bResult = false;
		}

	}

	public static void Open_Browser(String data) {
		try {
			Log.info("Open Browser" + " - " + data);
			if (data.equals("Firefox")) {
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
			} else if (data.equals("IE")) {
				driver = new InternetExplorerDriver();
				driver.manage().window().maximize();
			} else if (data.equals("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						"./libs/chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().window().maximize();
			}
		} catch (Exception e) {
			Log.error("Not Able To Open Browser " + data + " - "
					+ e.getMessage());
			Executor.bResult = false;
		}
	}

	public static void Enter_URL(String object, String data,
			String LocatorValue, String TestStepdesc) {
		String Url = Login.get_baseurl().trim() + data.trim();
		try {
			Log.info("Enter URL" + " - " + Url);
			driver.get(Url);
		} catch (Exception e) {
			Log.error("Not able to Enter URL " + Url + " - " + e.getMessage());
			Executor.bResult = false;
		}
	}

	public static void Navigate_To(String object, String data,
			String LocatorValue, String TestStepdesc) {

		try {
			Log.info("Enter URL" + " - " + data);
			driver.get(data);
		} catch (Exception e) {
			Log.error("Not able to Enter URL " + data + " - " + e.getMessage());
			Executor.bResult = false;
		}
	}

	public static void Click_On(String object, String data,
			String LocatorValue, String TestStepdesc) {

		try {
			By locator = Locator_Utils.Locate(object, data, LocatorValue);
			Log.info(TestStepdesc + " - " + object + " - " + LocatorValue);
			Wait_till_elementToBeClickable(locator);
			driver.findElement(locator).click();
		} catch (Exception e) {
			Log.error("Not able to " + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}
	}

	public static void Enter_Value(String object, String data,
			String LocatorValue, String TestStepdesc) {
		try {
			Log.info(TestStepdesc + " - " + object + " - " + LocatorValue);
			By locator = Locator_Utils.Locate(object, data, LocatorValue);
			Wait_Element_Located(locator);
			driver.findElement(locator).sendKeys(data);
		} catch (Exception e) {
			Log.error("Not able to " + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}
	}

	public static void Edit_Value(String object, String data,
			String LocatorValue, String TestStepdesc) {
		try {
			Log.info(TestStepdesc + " - " + object + " - " + LocatorValue);
			By locator = Locator_Utils.Locate(object, data, LocatorValue);
			Wait_Element_Located(locator);
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(data);
		} catch (Exception e) {
			Log.error("Not able to" + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}
	}

	public static void Clear_Value(String object, String data,
			String LocatorValue, String TestStepdesc) {
		try {
			By locator = Locator_Utils.Locate(object, data, LocatorValue);
			Log.info(TestStepdesc + " - " + object + " - " + LocatorValue);
			Wait_Element_Located(locator);
			driver.findElement(locator).clear();
		} catch (Exception e) {
			Log.error("Not able to" + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}
	}

	public static void Select_Dropdown_Value(String object, String data,
			String LocatorValue, String TestStepdesc) {// to select value from
														// dropdown
		try {
			Log.info(TestStepdesc + " - " + object + " - " + LocatorValue);
			By locator = Locator_Utils.Locate(object, data, LocatorValue);
			Wait_Element_Located(locator);
			WebElement element = driver.findElement(locator);
			Select dropdown_value = new Select(element);
			dropdown_value.selectByVisibleText(data);
		} catch (Exception e) {
			Log.error("Not able to" + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}
	}

	public static void setClipboardData(String string) {
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard()
				.setContents(stringSelection, null);
	}

	public static void Browse_File(String object, String data,
			String LocatorValue, String TestStepdesc) {// browse file and select
		try {
			Log.info(TestStepdesc + " - " + object + " - " + LocatorValue
					+ " Data = " + data);
			By locator = Locator_Utils.Locate(object, data, LocatorValue);
			Wait_till_elementToBeClickable(locator);
			driver.findElement(locator).click();
			/* driver.findElement(locator).sendKeys(file path); */
			setClipboardData(data);
			// native key strokes for CTRL, V and ENTER keys
			Robot robot = new Robot();
			robot.delay(3000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_S);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_P);
			robot.keyPress(KeyEvent.VK_O);
			robot.keyPress(KeyEvent.VK_C);
			robot.keyRelease(KeyEvent.VK_P);
			robot.keyRelease(KeyEvent.VK_O);
			robot.keyRelease(KeyEvent.VK_C);
			robot.delay(50);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			Log.error("Not able to" + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}

	}

	public static void Input_FileURL(String object, String data,
			String LocatorValue, String TestStepdesc) {
		try {
			Log.info(TestStepdesc + " - " + object + " - " + LocatorValue
					+ " Data = " + data);
			By locator = Locator_Utils.Locate(object, data, LocatorValue);
			Wait_Element_Located(locator);
			driver.findElement(locator).sendKeys(data);

		} catch (Exception e) {
			Log.error("Not able to" + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}

	}

	public static void Verify_Text(String object, String data,
			String LocatorValue, String TestStepdesc) {// to verify strings and
														// messages are present
														// and equal
		try {
			Log.info(TestStepdesc + " - " + object + " - " + LocatorValue
					+ " Data = " + data);
			By locator = Locator_Utils.Locate(object, data, LocatorValue);
			WebElement element = driver.findElement(locator);
			String textpresent = element.getText();
			if (textpresent.contains(data)) {
				Executor.bResult = true;
			} else {
				Executor.bResult = false;
				Log.error(TestStepdesc + " - step failed");
			}

		} catch (Exception e) {
			Log.error("Not able to" + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}

	}

	public static void Close_Browser(String object, String data,
			String LocatorValue, String TestStepdesc) {
		try {
			Log.info(TestStepdesc);
			driver.quit();
		} catch (Exception e) {
			Log.error("Not able to" + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}
	}

	/***************** END -- Basic HTML WEBELEMENT FUNCTIONS ***********************************/

	/***************** Utility Selenium Functions ***********************************/

	public static void Switch_To_Active_Element() {
		driver.switchTo().activeElement();
	}

	public static void Wait(String object, String data, String LocatorValue,
			String TestStepdesc) { // implicitly wait till given time
		// long time = Long.parseLong(data);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public static void Delay(String object, String data, String LocatorValue,
			String TestStepdesc) { // implicitly wait till given time
		try {
			// long time = Long.parseLong(data);
			Robot robot = new Robot();
			// robot.delay((int) time);
			robot.delay(10);

		} catch (Exception e) {
			Log.error("Delay Failed..." + e.getMessage());

		}

	}

	public static void Wait_Element_Located(By locator) {
		Switch_To_Active_Element();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static void Wait_till_elementToBeClickable(By locator) {
		Switch_To_Active_Element();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static void Wait_textToBePresentIn_Element(By locator, String data) {
		Switch_To_Active_Element();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.textToBePresentInElementValue(locator,
				data));
	}

	public static void Wait_invisibilityOfElementLocated(By locator) {
		Switch_To_Active_Element();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	/***************** UDH Specific WEBELEMENT FUNCTIONS ***********************************/

	public static void UDH_Find_Select_Js_Dropdown(String object, String data,
			String LocatorValue, String TestStepdesc) {
		try {
			Log.info(TestStepdesc + " - " + object + " - " + LocatorValue
					+ " Data = " + data);
			By locator = Locator_Utils.Locate(object, data, LocatorValue);
			Wait_Element_Located(locator);
			driver.findElement(locator).click();

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(By
					.className("select2-selection")));

			List<WebElement> allRows = driver.findElements(By
					.xpath("//li[@class='select2-results__option']"));
			Iterator<WebElement> Rowiterator = allRows.iterator();

			// And iterate over them, getting the cells
			// Log.info("Number of Rows:"+allRows.size());
			while (Rowiterator.hasNext()) {
				WebElement row = Rowiterator.next();
				if (row.getText().contains(data)) {
					row.click();
					break;

				}
			}

		} catch (Exception e) {
			Log.error("Not able to" + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}

	}

	public static void UDH_Find_Select_Table_Record(String object, String data,
			String LocatorValue, String TestStepdesc) {
		try {
			Log.info(TestStepdesc + " - " + object + " - " + LocatorValue
					+ " Data = " + data);
			By locator = Locator_Utils.Locate(object, data, LocatorValue);
			Wait_Element_Located(locator);
			WebElement table = driver.findElement(locator);
			// Now get all the TR elements from the table
			List<WebElement> allRows = table.findElements(By.tagName("tr"));
			Iterator<WebElement> Rowiterator = allRows.iterator();

			// And iterate over them, getting the cells
			// Log.info("Number of Rows:"+allRows.size());
			while (Rowiterator.hasNext()) {
				WebElement row = Rowiterator.next();
				if (row.getText().contains(data)) {
					row.click();
					break;

				}
			}

		} catch (Exception e) {
			Log.error("Not able to" + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}
	}

	public static void UDH_Click_Menu(String object, String data,
			String LocatorValue, String TestStepdesc) {
		try {
			Log.info(TestStepdesc + " - " + object + " - " + LocatorValue
					+ " Data = " + data);
			By locator = Locator_Utils.Locate(object, data, LocatorValue);
			Wait_Element_Located(locator);
			WebElement li = driver.findElement(locator);
			// Now get all the TR elements from the table
			List<WebElement> allRows = li.findElements(By.tagName("li"));
			int count_li = allRows.size();
			System.out.println(count_li);
			Iterator<WebElement> Rowiterator = allRows.iterator();

			while (Rowiterator.hasNext()) {
				WebElement row = Rowiterator.next();
				if (row.getText().contains(data)) {
					row.click(); // .//*[@id='mCSB_1_container']/ul/li[2]/a
					break;

				} else {
					for (int i = 1; i <= count_li; i++) {
						Boolean result = check_element(By
								.xpath(".//*[@id='mCSB_1_container']/ul.//*[@id='mCSB_1_container']/ul[@class='menu dropdown']/li["
										+ i + "]/ul[@class='dropdown-menu']"));
						if (result == true) {

							WebElement ul_li = driver
									.findElement(By
											.xpath(".//*[@id='mCSB_1_container']/ul[@class='menu dropdown']/li["
													+ i
													+ "]/ul[@class='dropdown-menu']"));
							List<WebElement> ul_li_rows = ul_li.findElements(By
									.tagName("li"));
							int count_ul_li = ul_li_rows.size();
							System.out.println(count_ul_li);
							Iterator<WebElement> Rowiterator1 = ul_li_rows
									.iterator();

							while (Rowiterator1.hasNext()) {
								WebElement row1 = Rowiterator1.next();
								if (row1.getText().contains(data)) {
									row1.click();
									break;

								} else {
									for (int j = 1; j <= count_ul_li; j++) {

										Boolean result1 = check_element(By
												.xpath(".//*[@id='mCSB_1_container']/ul[@class='menu dropdown']/li["
														+ i
														+ "]/ul[@class='dropdown-menu']/li["
														+ j + "]"));
										if (result1 == true) {
											WebElement ul_li_ul = driver
													.findElement(By
															.xpath(".//*[@id='mCSB_1_container']/ul[@class='menu dropdown']/li["
																	+ i
																	+ "]/ul[@class='dropdown-menu']/li["
																	+ j + "]"));
											List<WebElement> ul_li_ul_rows = ul_li_ul
													.findElements(By
															.tagName("a"));
											Iterator<WebElement> Rowiterator2 = ul_li_ul_rows
													.iterator();
											while (Rowiterator2.hasNext()) {
												WebElement row2 = Rowiterator2
														.next();
												System.out.println(row2
														.getText());
												if (row2.getText().contains(
														data)) {
													row2.click();
													break;

												}
											}
										}
									}

								}

							}
						}
					}
				}
			}
		} catch (Exception e) {
			Log.error("Not able to" + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}

	}

	public static boolean check_element(By locator) {

		try {
			driver.findElement(locator);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}

	}

	public static String get_text(String object, String objectvalue) {

		try {
			By locator = Locator_Utils.locatorValue(object, objectvalue);
			WebElement TxtBoxContent = driver.findElement(locator);
			String text = TxtBoxContent.getText();
			Log.info(text);
			return text;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			Log.error("The data scrap failed for object - " + object
					+ "and object value" + objectvalue);
			return "N/A";
		}

	}

	// COD Automation Added by Swaichhik Karki
	public static boolean Exit_Intent(String object, String data,
			String LocatorValue, String TestStepdesc)
			throws InterruptedException {
		Thread.sleep(6000);
		try {
			Log.info(TestStepdesc);
			Robot robot = new Robot();
			robot.mouseMove(416, 397);

			int x, y;
			for (x = 500; x <= 1000; x++) {
				for (y = 0; y <= 500; y++) {
					robot.mouseMove(x, y);
					Executor.bResult = true;

				}
			}
			Log.info("Exit intent: Completed");

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			Log.error("Not able to" + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}
		return Executor.bResult;
	}

	public static boolean Switch_To_Frame(String object, String data,
			String LocatorValue, String TestStepdesc) {
		Log.info(TestStepdesc + " - " + " Data = ");
		// By locator=lu.locatorValue(locatorType, value);
		// WebElement iFrame= driver.findElement(locator);
		// int n= Integer.parseInt(data);
		driver.switchTo().frame(0);
		// WebElement iFrame= driver.findElement(By.tagName("iframe"));
		// driver.switchTo().frame(iFrame);
		return true;
	}

	public static boolean Switch_To_Default(String object, String data,
			String LocatorValue, String TestStepdesc) {
		Log.info(TestStepdesc + " - " + " Data = " + data);
		driver.switchTo().defaultContent();
		return true;
	}

	public static boolean Explicit_Wait(String object, String data,
			String LocatorValue, String TestStepdesc) {
		try {
			Log.info(TestStepdesc + " - " + " Data = ");
			// int n = Integer.parseInt(data);
			// driver.manage().timeouts().implicitlyWait(n, TimeUnit.SECONDS);
			Thread.sleep(10000);

		} catch (Exception e) {
			Log.error("Not able to" + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}
		return Executor.bResult;
	}

	// Screenshot
	public static boolean Take_Screenshot(String object, String data,
			String LocatorValue, String TestStepdesc) {
		Log.info(TestStepdesc + " - " + " Data = " + data);

		TakesScreenshot ts = (TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		try {
			String path = "./Screenshots/" + data + ".jpg";
			FileUtils.copyFile(src, new File(path));
			Executor.bResult = true;
		} catch (IOException e) {
			Log.error("Not able to" + TestStepdesc + " - " + e.getMessage());
			Executor.bResult = false;
		}
		return Executor.bResult;
	}

	public static boolean Verify_Client_Snippet(String object, String data,
			String LocatorValue, String TestStepdesc) {
		try {
			Log.info(TestStepdesc + " - " + " Data = " + data);
			// String bodyText =
			// driver.findElement(By.tagName("body")).getText();
			// Assert.assertTrue(bodyText.contains(value));

			/*
			 * 
			 * 
			 * }
			 */

			/*
			 * WebElement foo=driver.findElement(By.tagName("body"));
			 * List<WebElement> list = foo.findElements(By.tagName("script"));
			 * System.out.println(list);
			 */
			/*
			 * Iterator<WebElement> itr = list.iterator(); while(itr.hasNext()){
			 * WebElement row1 = itr.next();
			 * System.out.println(row1.getText().contains
			 * ("var cod_cartSubtotalAmt = parseFloat(0);")); }
			 */

		}

		/*
		 * boolean foo=driver.getPageSource().contains(value); if (foo== true){
		 * Log.info("Snippets Verified"); } else{
		 * Log.info("Snippets does not contain snippets"); } Executor.bResult=
		 * true; }
		 */
		catch (Exception e) {
			e.printStackTrace();
			Log.info("Take Screenshot Failed..." + e.getMessage());
			Executor.bResult = false;
		}
		return Executor.bResult;
	}

	public static boolean Cookie_Handeler_RepeatVisitor(String object,
			String data, String LocatorValue, String TestStepdesc) {
		try {
			Log.info(TestStepdesc + " - " + " Data = " + data);
			// Injecting cookie
			Cookie cm_stp = new Cookie("s_cod_cm_stp_911", "1", "/codadmin2/");
			Cookie visit_count = new Cookie(" s_cod_cm_visit_count_911", "21",
					"/codadmin2/");
			driver.manage().addCookie(cm_stp);
			driver.manage().addCookie(visit_count);
			// write cookie to a file and upload on next session
		} catch (Exception e) {
			Log.info("Cookie Injection Failed..." + e.getMessage());
			Executor.bResult = false;
		}
		return Executor.bResult;
	}

	// end of class
	// .//*[@id='mCSB_1_container']/ul/li[6]/ul[@class='dropdown-menu']/li[4]

	/***************** || JAVA URL class function || ***********************************/

	public static String get_Response_Code(String data) {
		String responseCode = null;
		try {
			// create URL object.
			URL url = new URL(data);
			// create connection to the URL.
			URLConnection connectURL = url.openConnection();

			HttpURLConnection con = null;

			if (connectURL instanceof HttpURLConnection) {
				// create HTTP connection to the URL object.
				con = (HttpURLConnection) url.openConnection();

				responseCode = Integer.toString(con.getResponseCode());
				// https://en.wikipedia.org/wiki/List_of_HTTP_status_codes
			} else {
				Log.info(url + " : given url is Not an HTTP URL.");
			}

		} catch (Exception e) {
			Log.error("Malformed URL. " + e.getMessage());
		}

		Log.info(responseCode);
		return responseCode;
	}

	// Get Response message
	public static String get_Response_Message(String data) {
		String responseMsg = null;
		try {
			// create URL object.
			URL url = new URL(data);
			// create connection to the URL.
			URLConnection connectURL = url.openConnection();

			HttpURLConnection con = null;

			if (connectURL instanceof HttpURLConnection) {
				// create HTTP connection to the URL object.
				con = (HttpURLConnection) url.openConnection();

				responseMsg = con.getResponseMessage();
			} else {
				Log.info(url + " : given url is Not an HTTP URL.");
			}

		} catch (Exception e) {
			Log.error("Malformed URL. " + e.getMessage());
		}

		Log.info(responseMsg);
		return responseMsg;
	}

}

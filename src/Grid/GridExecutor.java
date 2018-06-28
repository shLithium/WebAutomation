package Grid;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

import Executor.ActionKeywords;
import Functions.Fill_Form;
import Functions.Login;
//import config.Constants;

import Utilities.File_Reader;
import Utilities.Log;
//import config.Constants;

import Utilities.ExcelUtils;

public class GridExecutor {

	public static boolean bResult;
	public static Properties OR;
	public static Method method[];
	public static ActionKeywords actionKeywords;

	public static String TestCaseID;
	public static String TestDesc;
	public static String RunMode;
	public static String TestType;
	public static String Keyword;
	public static String ObjectOrLocator;
	public static String LocatorValue;
	public static String TestValue;
	public static String TestStepDesc;

	public GridExecutor() throws NoSuchMethodException, SecurityException {
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();
	}

	public void grid_execute_Actions() throws Exception {
		for (int k = 0; k < method.length; k++) {
			if (method[k].getName().equals(Keyword)) {
				method[k].invoke(Keyword, ObjectOrLocator, TestValue,
						LocatorValue, TestStepDesc);
				if (bResult != false) {
					break;
				} else {
					bResult = false;
					Log.info(Keyword + " - Keyword function not found!");
					ActionKeywords.Close_Browser("", "", "", "");
					break;
				}
			}
		}
	}

	public void Grid_execute_TestCases(String nodeURL, String browser,
			String platform) throws Exception {

		String filename = File_Reader.get_data("Initiator_filename",
				"./initiator_config.properties");

		ExcelUtils.setExcelFile("./" + filename);

		int TotalTestcases = ExcelUtils.getRowCount("Sheet1");
		// System.out.println(TotalTestcases);
		TestType = ExcelUtils.getCellData(0, 4, "Sheet1");

		for (int i = 1; i < TotalTestcases; i++) {
			bResult = true;
			TestCaseID = ExcelUtils.getCellData(i, 0, "Sheet1");
			TestDesc = ExcelUtils.getCellData(i, 1, "Sheet1");
			RunMode = ExcelUtils.getCellData(i, 3, "Sheet1");
			// System.out.println(TestCaseID);
			// System.out.println(TestDesc);

			if (RunMode.equals("Yes")) {
				Log.startTestCase(TestCaseID.concat(" : ").concat(TestDesc));
				String TestFilelocation = ExcelUtils
						.getCellData(i, 2, "Sheet1");

				// Initiates Test-case File
				ExcelUtils.setExcelFile(TestFilelocation);
				int TotalTestStep = ExcelUtils.getRowCount("Sheet1");

				for (int j = 1; j < TotalTestStep; j++) {

					TestStepDesc = ExcelUtils.getCellData(j, 0, "Sheet1");
					Keyword = ExcelUtils.getCellData(j, 1, "Sheet1");

					// ObjectOrLocator = "";
					// LocatorValue= "";

					ObjectOrLocator = ExcelUtils.getCellData(j, 2, "Sheet1")
							.trim();
					LocatorValue = ExcelUtils.getCellData(j, 3, "Sheet1")
							.trim();
					TestValue = ExcelUtils.getCellData(j, 4, "Sheet1").trim();

					if (Keyword.contains("Open_Browser")
							|| Keyword.contains("User_Login")
							|| Keyword.contains("UDHAdmin_Login")
							|| Keyword.contains("CODAdmin_Login")
							|| Keyword.contains("DEWPAdmin_Login")
							|| Keyword.contains("Enter_Testdata")
							|| Keyword.contains("Grid"))
						;
					{
						switch (Keyword) {
						case "User_Login":
							Login.User_Login();
							break;

						case "Open_Browser":
							actionKeywords.Open_Browser(TestValue);
							break;

						case "UDHAdmin_Login":
							Login.UDHAdmin_Login();
							break;

						case "CODAdmin_Login":
							Login.CODAdmin_Login();
							break;

						case "CODUser_Login":
							Login.CODAdmin_Login();
							break;

						case "DEWP_Login":
							Login.CODAdmin_Login();
							break;

						case "Enter_TestData":
							Fill_Form.Enter_TestData(TestFilelocation,
									TestValue);
							break;

						case "Grid":
							ActionKeywords.Grid_Setup(nodeURL, browser,
									platform);
							break;

						default:
							GridExecutor start = new GridExecutor();
							start.grid_execute_Actions();
							break;
						}
					}
					/*
					 * if(Keyword.contains("Select_Store")); { switch (Keyword)
					 * { case "Select_Store": SelectStore.Select_Store(); break;
					 * 
					 * default: execute_Actions(); break; } }
					 */

					if (bResult == false) {
						ExcelUtils.setCellData(TestFilelocation, "Fail", j, 5,
								"Sheet1");
						break;
					}
					if (bResult == true) {
						ExcelUtils.setCellData(TestFilelocation, "Pass", j, 5,
								"Sheet1");
					}
				} // test step for loop end
				ExcelUtils.setExcelFile("./Initiator_COD.xlsx");
				if (bResult == false) {
					ExcelUtils.setCellData("./Initiator_COD.xlsx", "Fail", i,
							4, "Sheet1");
					Log.endTestCase(TestCaseID);
					break;
				}
				if (bResult == true) {
					ExcelUtils.setCellData("./Initiator_COD.xlsx", "Pass", i,
							4, "Sheet1");
					Log.endTestCase(TestCaseID);
				}
			} // run mode if loop end

		}// initiator for loop end
	}// initiator for loop end

}// method closure


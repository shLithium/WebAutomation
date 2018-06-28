package Executor;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;

import Executor.ActionKeywords;
import Functions.Fill_Form;
import Functions.Login;
//import config.Constants;

import Utilities.File_Reader;
import Utilities.Log;
//import config.Constants;

import Utilities.ExcelUtils;
import Utilities.SendMail;

public class Executor {

	public static boolean bResult;
	public static Properties OR;
	public static Method method[];
	public static ActionKeywords actionKeywords;

	public static String TestCaseID;
	public static String TestDesc;
	public static String RunMode;

	public static String Keyword;
	public static String ObjectOrLocator;
	public static String LocatorValue;
	public static String TestValue;
	public static String TestStepDesc;

	public Executor() throws NoSuchMethodException, SecurityException {
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();
	}

	private static void execute_Actions() throws Exception {

		for (int k = 0; k < method.length; k++) {
			if (method[k].getName().equals(Keyword)) {
				method[k].invoke(Keyword, ObjectOrLocator, TestValue, LocatorValue, TestStepDesc);
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

	public static void main(String[] args) throws Exception {
		// Initiates Test Initiator File

		String filename = File_Reader.get_data("Initiator_filename", "./initiator_config.properties");
		// System.out.println(filename);
		// Clear log entries
		PrintWriter writer = new PrintWriter("./log/testlog1.log");
		writer.print("");
		writer.close();
		ExcelUtils.setExcelFile("./" + filename);
		PropertyConfigurator.configure("Log4j.properties");
		// To identity test mode from the initator file -- Generic, Scrapper
		String Testmode = ExcelUtils.getCellData(0, 3, "Sheet1");
		// System.out.println(Testmode);
		if (Testmode.contains("Scrapper")) {
			System.out.println(filename);
			System.out.println(Testmode);
			Scrapper.scrapper("./" + filename);

		} else {
			// String Path_OR = "./OR.txt";
			String repo_file = ExcelUtils.getCellData(0, 1, "Sheet1");
			// System.out.println(repo_file);
			String Path_OR = "./Object_Repositories/" + repo_file + ".txt";
			// System.out.println(Path_OR);
			FileInputStream fs = new FileInputStream(Path_OR);
			OR = new Properties(System.getProperties());
			OR.load(fs);
			Executor start = new Executor();
			start.execute_TestCases();
			// Send mail
			SendMail.mailsender(filename);
		}
	}

	private void execute_TestCases() throws Exception {
		int TotalTestcases = ExcelUtils.getRowCount("Sheet1");
		// System.out.println(TotalTestcases);

		for (int i = 1; i < TotalTestcases; i++) {
			bResult = true;
			TestCaseID = ExcelUtils.getCellData(i, 0, "Sheet1");
			TestDesc = ExcelUtils.getCellData(i, 1, "Sheet1");
			RunMode = ExcelUtils.getCellData(i, 3, "Sheet1");
			// System.out.println(TestCaseID);
			// System.out.println(TestDesc);
			if (RunMode.equals("Yes")) {
				Log.startTestCase(TestCaseID.concat(" : ").concat(TestDesc));
				String TestFilelocation = ExcelUtils.getCellData(i, 2, "Sheet1");

				// Initiates Test-case File
				ExcelUtils.setExcelFile(TestFilelocation);
				int TotalTestStep = ExcelUtils.getRowCount("Sheet1");

				for (int j = 1; j < TotalTestStep; j++) {

					TestStepDesc = ExcelUtils.getCellData(j, 0, "Sheet1");
					Keyword = ExcelUtils.getCellData(j, 1, "Sheet1");

					// ObjectOrLocator = "";
					// LocatorValue= "";

					ObjectOrLocator = ExcelUtils.getCellData(j, 2, "Sheet1").trim();
					LocatorValue = ExcelUtils.getCellData(j, 3, "Sheet1").trim();
					TestValue = ExcelUtils.getCellData(j, 4, "Sheet1").trim();

					if (Keyword.contains("User_Login") || Keyword.contains("UDHAdmin_Login")
							|| Keyword.contains("CODAdmin_Login") || Keyword.contains("DEWPAdmin_Login")
							|| Keyword.contains("Enter_Testdata"))
						;
					{
						switch (Keyword) {
						case "User_Login":
							Login.User_Login();
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
							Fill_Form.Enter_TestData(TestFilelocation, TestValue);
							break;

						default:
							execute_Actions();
							break;
						}
					}

					if (bResult == false) {
						ExcelUtils.setCellData(TestFilelocation, "Fail", j, 5, "Sheet1");
						break;
					}
					if (bResult == true) {
						ExcelUtils.setCellData(TestFilelocation, "Pass", j, 5, "Sheet1");
					}
				} // test step for loop end
				ExcelUtils.setExcelFile("./Initiator.xlsx");
				if (bResult == false) {
					ExcelUtils.setCellData("./Initiator.xlsx", "Fail", i, 4, "Sheet1");
					Log.endTestCase(TestCaseID);
					break;
				}
				if (bResult == true) {
					ExcelUtils.setCellData("./Initiator.xlsx", "Pass", i, 4, "Sheet1");
					Log.endTestCase(TestCaseID);
				}
			} // run mode if loop end

		} // initiator for loop end

	}// method closure

}

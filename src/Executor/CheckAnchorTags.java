package Executor;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;

import Executor.ActionKeywords;
import Utilities.ExcelUtils;

public class CheckAnchorTags {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static boolean bResult;
	public static String TestCaseID ;
	public static String TestDesc ; 
	public static String RunMode ;
	
	public static void checkanchortags(String initfilepath) throws Exception {
		
		ExcelUtils.setExcelFile(initfilepath);
		System.out.println("Reading from the Initiator file: "+initfilepath);
					
		int TotalTestcases = ExcelUtils.getRowCount("Sheet1");
//		System.out.println(TotalTestcases);
		
//		loop for test cases.
		for(int i=1;i<TotalTestcases;i++){
			
			bResult = true;
			TestCaseID = ExcelUtils.getCellData(i,0,"Sheet1");
					
			TestDesc = ExcelUtils.getCellData(i,1,"Sheet1"); 
			RunMode = ExcelUtils.getCellData(i,3,"Sheet1");

			
			if (RunMode.equals("Yes")){	
			
				String TestFilelocation = ExcelUtils.getCellData(i,2,"Sheet1");
				ExcelUtils.setExcelFile(TestFilelocation);
				System.out.println("Reading from the Test Case file: "+TestFilelocation);
				
//				open page on browser.
				ActionKeywords.Open_Browser("Firefox");
				
//				Count the number of rows
				int countrow1 = ExcelUtils.getRowCount("Sheet1");
				System.out.println("rows in "+TestFilelocation+": Sheet1: "+countrow1);
				
				if(countrow1>=1){
				
	//				loop for rows.
					for(int j = 1; j<countrow1; j++){
						
//						Read the URL given in row j 
						String ID = ExcelUtils.getCellData(j,0,"Sheet1");
						String URL = ExcelUtils.getCellData(j,1,"Sheet1");
						String locator = ExcelUtils.getCellData(j,2,"Sheet1");
						String locatorvalue = ExcelUtils.getCellData(j,3,"Sheet1");
						String locator2 = ExcelUtils.getCellData(j,4,"Sheet1");
						String value = ExcelUtils.getCellData(j,5,"Sheet1");
						
						ActionKeywords.Navigate_To("", URL,"", "");
						
						List<String> linklist = ActionKeywords.Find_All_Links(locator, locatorvalue, locator2, value);
						
						int index = 0;
						int sheet = 2;
						for(int k = 1; k <= linklist.size(); k++){
							
								ExcelUtils.setCellData(TestFilelocation, ID, j, 0, "Sheet"+sheet);
								ExcelUtils.setCellData(TestFilelocation, linklist.get(index), j, 0, "Sheet"+sheet);
								
								index++;
								sheet++;
						}	
					}
				}
				

				System.out.println("scrapping completed!");
				ActionKeywords.Close_Browser("","","","");
			}
		}
	}
}

				
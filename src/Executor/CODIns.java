package Executor;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebElement;

import Executor.ActionKeywords;
import Utilities.ExcelUtils;

public class CODIns {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static boolean bResult;
	public static String TestCaseID ;
	public static String TestDesc ; 
	public static String RunMode ;
	
	public static void CODtest(String initfilepath) throws Exception {
		
//		********************************************************************		
		
		String BrowserList[] = new String[]
				{
				"Firefox",
//				"Chrome",
//				"Safari", 
//				"IE", 
				};
		
//		********************************************************************	
		ExcelUtils.setExcelFile(initfilepath);
					
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
				
				System.out.println(TestFilelocation);
				
//				loop for different Browsers
				for(int browserno= BrowserList.length; browserno < BrowserList.length; browserno++) {
				
//				open page on browser.
				ActionKeywords.Open_Browser(BrowserList[browserno]);
				
//				Count the number of rows
				int countrow1 = ExcelUtils.getRowCount("Sheet1");
				System.out.println("rows in Sheet1: "+countrow1);
				
				if(countrow1>=1){
				
//					loop for rows.
					for(int j = 1; j<countrow1; j++){
						
//						Read the URL given in row j 
						String URL = ExcelUtils.getCellData(j,0,"Sheet1");				
					
					
						ActionKeywords.Navigate_To("", URL,"", "");
						
					
	//					count rows in sheet 2.
						int countrow2 = ExcelUtils.getRowCount("Sheet2");
						
						for(int k = 1; k < countrow2; k++){
							
	//						Get location of element.
							String testdesc = ExcelUtils.getCellData(k,0,"Sheet2");
							String teststep = ExcelUtils.getCellData(k,1,"Sheet2");
							String locatortype = ExcelUtils.getCellData(k,2,"Sheet2");
							String locatorvalue = ExcelUtils.getCellData(k,3,"Sheet2");
							String data = ExcelUtils.getCellData(k,4,"Sheet2");
							
							
							switch (teststep) {
							
							case "Enter_Value":
								ActionKeywords.Enter_Value(locatortype,data,locatorvalue,testdesc);
								break;
																
							case "Select_Dropdown_Value":
								ActionKeywords.Select_Dropdown_Value(locatortype,data,locatorvalue,testdesc);
								break;
								
							case "Click_On":
								ActionKeywords.Click_On(locatortype,data,locatorvalue,testdesc);
								break;
								
							case "get_Response_Code":
								String value=ActionKeywords.get_Response_Code(URL);
								break;	
								
							default:
								String abc=ActionKeywords.get_text(locatortype,locatorvalue);
								//column count
								int col=Integer.parseInt(data);
								ExcelUtils.setCellData(TestFilelocation, abc, j, col, "Sheet1");
								break;
							
										
							}
						}	
					}
				}
				
				
				System.out.println("scrapping completed!");
				ActionKeywords.Close_Browser("","","","");
				
				}
				
			}
		}
	}
}
	
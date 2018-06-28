package Executor;

import java.util.List;

import Utilities.ExcelUtils;

public class WebCrawler {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static boolean bResult;
	public static String TestCaseID ;
	public static String TestDesc ; 
	public static String RunMode ;
	
	public static void webCrawler(String initfilepath) throws Exception {
		
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
				
//				open browser.
				ActionKeywords.Open_Browser("Firefox");
				
				

				
				
	
	/**
//	check if sheet3 has URLs by counting the number of rows in the sheet.
	int rowInSheet3 = ExcelUtils.getRowCount("Sheet3");
	if (rowInSheet3 >=1 ) {
		
		List<String> coverList = null;
		
//		if sheet3 has URLs get the associate data 
		for(int m = 1; m < rowInSheet3; m++){
//			String mainID 			= ExcelUtils.getCellData(m, 0, "Sheet3");
			String mainURL 			= ExcelUtils.getCellData(m, 1, "Sheet3");
			String mainLocator 		= ExcelUtils.getCellData(m, 2, "Sheet3");
			String mainLocatorValue = ExcelUtils.getCellData(m, 3, "Sheet3");
			String secLocator 		= ExcelUtils.getCellData(m, 4, "Sheet3");
			String secValue 		= ExcelUtils.getCellData(m, 5, "Sheet3");
			String mainSearch 		= ExcelUtils.getCellData(m, 6, "Sheet3");
		
//			check if column 'Search' is set to Yes -- only perform the following for URLs set to Yes.
			if (mainSearch.equals("Yes")) {
				
//				check if the URL given in Sheet3 exists.
				String response = ActionKeywords.get_Response_Code(mainURL);
				int r= Integer.parseInt(response);
				
//				if the URL given in Sheet3 responds
				if (r<400){
//					open the URL given in sheet3 
					ActionKeywords.Navigate_To("", mainURL,"", "");
					
//					get the values in array from following method && set the array values in another array.
					coverList = ActionKeywords.Find_All_Links(mainURL, mainLocator, mainLocatorValue, secLocator, secValue);
					
					System.out.println("here");
				}
				else{
					ExcelUtils.setCellData(TestFilelocation, "URL not found", 1, 1, "Sheet1");
				}																				
			}
		}
		
//		extract the array values and set the cell data in sheet1
		System.out.println("here");
		System.out.println(coverList);
				
	}
	
**/	
			}
		}
	}
}
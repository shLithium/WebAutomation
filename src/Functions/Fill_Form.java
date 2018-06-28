package Functions;
import static Executor.Executor.OR;
import Utilities.ExcelUtils;
import Utilities.Log;
import Executor.ActionKeywords;
import Executor.Executor;

public class Fill_Form {
	public static void Enter_TestData(String Filelocation, String SheetName) throws Exception {
		try{
			ExcelUtils.setExcelFile(Filelocation);
			int TotalDataRow = ExcelUtils.getRowCount(SheetName);
			int TotalDataCol= ExcelUtils.getColCount(SheetName,1);
			
			System.out.println(TotalDataCol);
			
			for(int k=2;k<TotalDataRow;k++){
				Executor.bResult = true;
				//System.out.println(TestCaseID);
				//System.out.println(TestDesc);
				for(int l=0;l<=TotalDataCol;l++){
					
				//	String RunMode = ExcelUtils.getCellData(i+1,j,SheetName);
					String obj = ExcelUtils.getCellData(0,l,SheetName);
					String Keyword = ExcelUtils.getCellData(1,l,SheetName); 
					String data = ExcelUtils.getCellData(k,l,SheetName); 
				
					 if (!"".equals(Keyword)){	
					
						  Enter(Keyword,obj,data);
						
					 }else{
						 Log.error("Keyword Blank in testdata");
						 Executor.bResult = false;
					 }
				}
			}
		}catch(Exception e){
			Log.error("Not able to Enter data from file" + Filelocation + "and Sheet "+SheetName+" -" + e.getMessage());
			 Executor.bResult = false;
		}
	}
	
	public static void Enter(String Keyword,String obj, String data){
		 switch (Keyword) {
			case "Click_On":
				ActionKeywords.Click_On(obj, data, "", "Click on ");
				break;
			
			case "Enter_Value":
				ActionKeywords.Enter_Value(obj, data, "", "Enter Value");
				break;
				
			case "Enter_URL":
				ActionKeywords.Enter_URL("", data, "", "Enter form URL ");
				break;
				
			case "Select_Dropdown_Value":
				ActionKeywords.Select_Dropdown_Value(obj, data, "", "Enter Dropdown Value");
				break;	
				
		    default:
		    	break;
		  }
		
	}
}

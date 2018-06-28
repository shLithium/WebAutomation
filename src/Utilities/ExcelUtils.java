package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Executor.Executor;

public class ExcelUtils {
        private static XSSFSheet ExcelWSheet;
        private static XSSFWorkbook ExcelWBook;
        private static org.apache.poi.ss.usermodel.Cell Cell;
        private static XSSFRow Row;
        //private static XSSFRow Row;
        public static Executor bResult;
        public static FormulaEvaluator evaluator;
        
   
public static void setExcelFile(String Path) throws Exception {
    	try {
            FileInputStream ExcelFile = new FileInputStream(Path);
            ExcelWBook = new XSSFWorkbook(ExcelFile);
            evaluator  = ExcelWBook.getCreationHelper().createFormulaEvaluator();
        	
    	} catch (Exception e){
    		Log.error("Class Utils | Method setExcelFile | Exception desc : "+e.getMessage());
    		Executor.bResult = false;
        	}
    	}
    
    
   
public static String getCellData(int RowNum, int ColNum, String SheetName ) throws Exception{
        try{
        	String CellData = null;
        	ExcelWSheet = ExcelWBook.getSheet(SheetName);
           	Row=ExcelWSheet.getRow(RowNum);
			Cell =Row.getCell(ColNum);
			
			
			if ((Cell == null) || (Cell.equals("")) || (Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK)|| (Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN)){
				 Executor.bResult = true;
				 return"";
				
            }else if(Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC ){
            	//CellData=fmt.formatCellValue(Cell);
            	Cell.setCellType(Cell.CELL_TYPE_STRING);
            	//System.out.println("Numeric");
            	//Double data=Cell.getNumericCellValue();
            	//CellData = Double.toString(data);
            	CellData=Cell.getStringCellValue();
                Executor.bResult = true;
    			return CellData;
           	
			/*else if((Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA )&& (Cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC )){
				Cell.setCellType(Cell.CELL_TYPE_STRING);
				System.out.println("Numeric Formula");
				Double data=Cell.getNumericCellValue();
            	CellData = Double.toString(data);
                Executor.bResult = true;
    			return CellData;
    			*/
			}else{
				CellData=Cell.getStringCellValue();
				Executor.bResult = true;
				return CellData;
			}
		
           
         }catch (Exception e){
             Log.error("Class Utils | Method getCellData | Exception desc : "+e.getMessage());
             Executor.bResult = false;
             return"";
             }
         }
    

	public static int getRowCount(String SheetName){
		int iNumber=0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			iNumber=ExcelWSheet.getLastRowNum()+1;
		} catch (Exception e){
			Log.error("Class Utils | Method getRowCount | Exception desc : "+e.getMessage());
			Executor.bResult = false;
			}
		return iNumber;
		}
	
	public static int getColCount(String SheetName, int Rowno){
		int iNumber=0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			iNumber=ExcelWSheet.getRow(Rowno).getLastCellNum()-1;
		} catch (Exception e){
			Log.error("Class Utils | Method getColumnCount | Exception desc : "+e.getMessage());
			Executor.bResult = false;
			}
		return iNumber;
		}
	
	public static int getRowContains(String sTestCaseName, int colNum,String SheetName) throws Exception{
		int iRowNum=0;	
		try {
		    //ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int rowCount = ExcelUtils.getRowCount(SheetName);
			for (; iRowNum<rowCount; iRowNum++){
				if  (ExcelUtils.getCellData(iRowNum,colNum,SheetName).equalsIgnoreCase(sTestCaseName)){
					break;
				}
			}       			
		} catch (Exception e){
			Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
			Executor.bResult = false;
			}
		return iRowNum;
		}
	
	/*public static int getTestStepsCount(String SheetName, String sTestCaseID, int iTestCaseStart) throws Exception{
		try {
    		for(int i=iTestCaseStart;i<=ExcelUtils.getRowCount(SheetName);i++){
    			if(!sTestCaseID.equals(ExcelUtils.getCellData(i, "Constants.Col_TestCaseID", SheetName))){
    				int number = i;
    				return number;      				
    				}
    			}
    		ExcelWSheet = ExcelWBook.getSheet(SheetName);
    		int number=ExcelWSheet.getLastRowNum()+1;
    		return number;
		} catch (Exception e){
			//Log.error("Class Utils | Method getRowContains | Exception desc : "+e.getMessage());
			//DriverScript.bResult = false;
			return 0;
        }
	}*/
	
	@SuppressWarnings("static-access")
	public static void setCellData(String filepath,String Result,  int RowNum, int ColNum, String SheetName) throws Exception    {
           try{
        	   ExcelWSheet = ExcelWBook.getSheet(SheetName);
               Row  = ExcelWSheet.getRow(RowNum);
               Cell = Row.getCell(ColNum, Row.RETURN_BLANK_AS_NULL);
               if (Cell == null) {
            	   Cell = Row.createCell(ColNum);
            	   Cell.setCellValue(Result);
                } else {
                   Cell.setCellValue(Result);
                }
                // FileOutputStream fileOut = new FileOutputStream(Constants.Path_TestData);
                 FileOutputStream fileOut = new FileOutputStream(filepath);
                 ExcelWBook.write(fileOut);
                 //fileOut.flush();
                 fileOut.close();
                 ExcelWBook = new XSSFWorkbook(new FileInputStream(filepath));
             }catch(Exception e){
            	 Executor.bResult = false;
      
             }
        }

}
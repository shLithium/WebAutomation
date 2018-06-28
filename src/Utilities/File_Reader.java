package Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class File_Reader {
	
 static String  prop_value;
 
 
 public static String get_data(String propname, String filepath){
   
     try{
    	 
	   Properties prop=new Properties();
	   String url=filepath; // e.g resources\\config.properties
	   File file = new File(url);
	   FileInputStream read=new FileInputStream(file);
       prop.load(read);
       prop_value = prop.getProperty(propname);
   /*   if(prop_value.equals(null)){
       Assert.fail(propname+" Property value not found in the file! ("+url+")");
      }*/
    
  }catch (IOException e){
   System.err.format("Couldn't read property from File!"+e);
   
  }
  return prop_value;
 }
}
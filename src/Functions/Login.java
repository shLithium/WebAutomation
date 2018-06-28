package Functions;

import static Executor.Executor.OR;
import Executor.ActionKeywords;

public class  Login {
	static String Browser=OR.getProperty("Browser");
	static String Base_URL=OR.getProperty("Base_URL")+OR.getProperty("Base_Module");
	static String Base_Module=OR.getProperty("Base_Module");
	static String UserName=OR.getProperty("Login_UserName");
	static String Password=OR.getProperty("Login_Password");
	
	
	/*static String UserNameObjectValue=OR.getProperty("txtbx_UserName_id");
	static String PasswordObjectValue=OR.getProperty("txtbx_Password_id");
	
	static String LoginButton=OR.getProperty("btn_Login_xpath");*/
	public static String get_baseurl() {
		String Base_URL=OR.getProperty("Base_URL");
		return Base_URL;
	}
	
	public static void User_Login() {
		ActionKeywords.Open_Browser(Browser);
		ActionKeywords.Enter_URL("",Base_Module,"","Enter URL");
		ActionKeywords.Enter_Value("txtbx_UserName_id", UserName, "", "Entering User ID ");
		ActionKeywords.Enter_Value("txtbx_Password_id", Password, "", "Entering Password ");
		ActionKeywords.Click_On("btn_Login_xpath", "", "" , "Clicking On Login Button");
		
	}
	
	public static void UDHAdmin_Login() {
		ActionKeywords.Open_Browser(Browser);
		ActionKeywords.Enter_URL("",Base_Module,"","Enter URL");
		ActionKeywords.Enter_Value("txtbx_UserName_id", "growbydataadmin", "", "Entering User ID ");
		ActionKeywords.Enter_Value("txtbx_Password_id", "passwordSKY15", "", "Entering Password ");
		ActionKeywords.Click_On("btn_Login_xpath", "", "" , "Clicking On Login Button");
	}
	
	public static void CODAdmin_Login() {
		ActionKeywords.Open_Browser(Browser);
		//System.out.println(Browser);
		ActionKeywords.Enter_URL("",Base_Module,"","Enter URL");
		ActionKeywords.Enter_Value("txtbx_UserName_id", "hosher@exclusiveconcepts.com", "", "Entering User ID ");
		ActionKeywords.Enter_Value("txtbx_Password_id", "cod@977", "", "Entering Password ");
		ActionKeywords.Click_On("btn_Login_xpath", "", "" , "Clicking On Login Button");
		
	}
	
	public static void CODUser_Login() {
		ActionKeywords.Open_Browser(Browser);
		ActionKeywords.Enter_URL("",Base_Module,"","Enter URL");
		ActionKeywords.Enter_Value("txtbx_UserName_id", "demo@exclusiveconcepts.com", "", "Entering User ID ");
		ActionKeywords.Enter_Value("txtbx_Password_id", "demo", "", "Entering Password ");
		ActionKeywords.Click_On("btn_Login_xpath", "", "" , "Clicking On Login Button");
		
	}
	
	
	public static void DEWPAdmin_Login() {
		ActionKeywords.Open_Browser(Browser);
		ActionKeywords.Enter_URL("",Base_Module,"","Enter URL");
		ActionKeywords.Enter_Value("txtbx_UserName_id", "growbydataadmin", "", "Entering User ID ");
		ActionKeywords.Enter_Value("txtbx_Password_id", "passwordSKY15", "", "Entering Password ");
		ActionKeywords.Click_On("btn_Login_xpath", "", "" , "Clicking On Login Button");
		
	}

}

package com.parabank.ui.tests.pos;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.parabank.ui.PARABANK.POM.Login;
import com.parabank.ui.PARABANK.exceptions.CustomException;
import com.parabank.ui.PARABANK.exceptions.DataException;
import com.parabank.ui.base.BaseTest;

public class POS_Login_Test extends BaseTest{
	
	@Test(priority = 1,groups= {"mobile"})
	public void login_registerUser() throws IOException, DataException, CustomException
	{
		
		
		Login loginObj=createPage(Login.class);
		Map<String,String> testData = getTestData("login_registerUser");
		if(testData.size() !=0)
		{
			if(testData.get("CDPFunction") != null)
			{
				
				Map<String,Object> params = jsObj.queryJsonData(jsonPath,testData.get("CDPSetUpQuery"));
				if(!(getDriver() instanceof ChromeDriver))
				{
					throw new CustomException("Unable to proceed CDP with other drivers");
				}
				cdpObject.simulateIPhoneWithCDP((ChromeDriver)getDriver(), params);
			}
			String userRegStatusText = loginObj.registerUser(testData);
			softAssert.assertEquals(userRegStatusText, testData.get("successMsg"));
			loginObj.logoutUser();
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
	}
	
	@Test(priority = 2,enabled = true,groups = {"regression"})
	public void login_user() throws InterruptedException, IOException, DataException
	{
		Login loginObj=createPage(Login.class);
		Map<String,String> testData = getTestData("login_user");
		if(testData.size() !=0)
		{
			loginObj.loginUser(testData.get("userName"), testData.get("password"));
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
		
	}
	@Test(dataProvider = "testUsers",timeOut = 4000)
	public void checkUserFromDB(String user)
	{
		Login loginObj = createPage(Login.class);
		loginObj.loginUser(user, user);
		
		if(loginObj.isUserNotRegisterErrorMsg())
		{
			Map<String,String> userDate = new HashMap<>();
			userDate.put("firstName", user);
			userDate.put("lastName", user);
			userDate.put("address", user);
			userDate.put("city", user);
			userDate.put("state", user);
			userDate.put("zipCode", user);
			userDate.put("phone", user);
			userDate.put("ssn", user);
			userDate.put("userName", user);
			userDate.put("password", user);
			userDate.put("confirmPassword", user);
			loginObj.registerUser(userDate);
			
		}
	}
	@DataProvider(name="testUsers")
	public Object[][] getDBDataForUsers() throws SQLException
	{
	
		String selectQuery = "SELECT * from USER_DETAILS;";
		List<Map<String,Object>> users = sqlObj.executeSimpleSelectQuery(selectQuery);
		Object[][] userNames = new Object[users.size()][1];
		int rowIndex=0;
		for(Map<String,Object> row:users)
		{
			userNames[rowIndex][0]=row.get("USERNAME");
			System.out.println(userNames[rowIndex][0]);
			rowIndex++;
		}
		
		
		return userNames;
	}

}

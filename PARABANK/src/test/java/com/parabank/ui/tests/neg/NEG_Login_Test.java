package com.parabank.ui.tests.neg;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.parabank.ui.PARABANK.POM.Login;
import com.parabank.ui.PARABANK.exceptions.DataException;
import com.parabank.ui.base.BaseTest;

public class NEG_Login_Test extends BaseTest{
	
	@Test(groups = {"regression"})
	public void NEG_registerUserWithFirstNameBlank() throws IOException, DataException
	{
		Map<String,String> testData = getTestData("NEG_registerUserWithFirstNameBlank");
		if(testData.size() !=0)
		{
			List<String> errorMessage = createPage(Login.class).getRegisterUserErrorMessage(testData);
			softAssert.assertEquals(errorMessage.get(0), testData.get("errorMsg"));
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
	}
	
	@Test(groups = {"regression"})
	public void NEG_registerUserWithLastNameBlank() throws IOException, DataException
	{
		Map<String,String> testData = getTestData("NEG_registerUserWithLastNameBlank");
		if(testData.size() !=0)
		{
			List<String> errorMessage = createPage(Login.class).getRegisterUserErrorMessage(testData);
			softAssert.assertEquals(errorMessage.get(0), testData.get("errorMsg"));
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
	}
	
	@Test
	public void NEG_registerUserWithAddressBlank() throws IOException, DataException
	{
		Map<String,String> testData = getTestData("NEG_registerUserWithAddressBlank");
		if(testData.size() !=0)
		{
			List<String> errorMessage = createPage(Login.class).getRegisterUserErrorMessage(testData);
			softAssert.assertEquals(errorMessage.get(0), testData.get("errorMsg"));
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
	}
	@Test
	public void NEG_registerUserWithCityBlank() throws IOException, DataException
	{
		Map<String,String> testData = getTestData("NEG_registerUserWithCityBlank");
		if(testData.size() !=0)
		{
			List<String> errorMessage = createPage(Login.class).getRegisterUserErrorMessage(testData);
			softAssert.assertEquals(errorMessage.get(0), testData.get("errorMsg"));
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
	}
	
	@Test
	public void NEG_registerUserWithStateBlank() throws IOException, DataException
	{
		Map<String,String> testData = getTestData("NEG_registerUserWithStateBlank");
		if(testData.size() !=0)
		{
			List<String> errorMessage = createPage(Login.class).getRegisterUserErrorMessage(testData);
			softAssert.assertEquals(errorMessage.get(0), testData.get("errorMsg"));
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
	}
	
	@Test
	public void NEG_registerUserWithzipCodeBlank() throws IOException, DataException
	{
		Map<String,String> testData = getTestData("NEG_registerUserWithzipCodeBlank");
		if(testData.size() !=0)
		{
			List<String> errorMessage = createPage(Login.class).getRegisterUserErrorMessage(testData);
			softAssert.assertEquals(errorMessage.get(0), testData.get("errorMsg"));
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
	}
	
	@Test
	public void NEG_registerUserWithSSNBlank() throws IOException, DataException
	{
		Map<String,String> testData = getTestData("NEG_registerUserWithSSNBlank");
		if(testData.size() !=0)
		{
			List<String> errorMessage = createPage(Login.class).getRegisterUserErrorMessage(testData);
			softAssert.assertEquals(errorMessage.get(0), testData.get("errorMsg"));
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
	}
	
	@Test
	public void NEG_registerUserWithUserNameBlank() throws IOException, DataException
	{
		Map<String,String> testData = getTestData("NEG_registerUserWithUserNameBlank");
		if(testData.size() !=0)
		{
			List<String> errorMessage = createPage(Login.class).getRegisterUserErrorMessage(testData);
			softAssert.assertEquals(errorMessage.get(0), testData.get("errorMsg"));
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
	}
	
	@Test
	public void NEG_registerUserWithpasswordBlank() throws IOException, DataException
	{
		Map<String,String> testData = getTestData("NEG_registerUserWithpasswordBlank");
		if(testData.size() !=0)
		{
			List<String> errorMessage = createPage(Login.class).getRegisterUserErrorMessage(testData);
			softAssert.assertEquals(errorMessage.get(0), testData.get("errorMsg"));
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
	}
	
	@Test
	public void NEG_registerUserWithConfirmPasswordBlank() throws IOException, DataException
	{
		Map<String,String> testData = getTestData("NEG_registerUserWithConfirmPasswordBlank");
		if(testData.size() !=0)
		{
			List<String> errorMessage = createPage(Login.class).getRegisterUserErrorMessage(testData);
			softAssert.assertEquals(errorMessage.get(0), testData.get("errorMsg"));
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
	}
	
	@Test
	public void NEG_registerUserWithPasswordMismatch() throws IOException, DataException
	{
		Map<String,String> testData = getTestData("NEG_registerUserWithPasswordMismatch");
		if(testData.size() !=0)
		{
			List<String> errorMessage = createPage(Login.class).getRegisterUserErrorMessage(testData);
			softAssert.assertEquals(errorMessage.get(0), testData.get("errorMsg"));
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
	}
	
	@Test
	public void NEG_loginWithInvalidUser() throws IOException, DataException
	{
		Map<String,String> testData = getTestData("NEG_loginWithInvalidUser");
		if(testData.size() !=0)
		{
			Login loginObj = createPage(Login.class);
			loginObj.loginUser(testData.get("userName"), testData.get("password"));;
			softAssert.assertEquals(loginObj.getInvalidLoginUserErrorMsg(), testData.get("errorMsg"));
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
	}
	
	

}

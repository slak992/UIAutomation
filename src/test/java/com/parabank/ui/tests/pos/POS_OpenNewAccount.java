package com.parabank.ui.tests.pos;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.beust.jcommander.Parameter;
import com.parabank.ui.PARABANK.POM.AccountOverview;
import com.parabank.ui.PARABANK.POM.Admin;
import com.parabank.ui.PARABANK.POM.Home;
import com.parabank.ui.PARABANK.POM.Login;
import com.parabank.ui.PARABANK.POM.NewAccount;
import com.parabank.ui.PARABANK.exceptions.DataException;
import com.parabank.ui.base.BaseTest;


public class POS_OpenNewAccount extends BaseTest {
	
	@Test(priority = 1)
	public void verifyDefaultInitBalanceTest() throws IOException, DataException
	{
		Map<String,String> testData = getTestData("verifyDefaultInitBalanceTest");
		if(testData.size() !=0)
		{
			Home homeObj = createPage(Home.class);
			Admin admin = createPage(Admin.class);
			Login loginObj = createPage(Login.class);
			NewAccount newAccountObj = createPage(NewAccount.class);
			AccountOverview actOverview = createPage(AccountOverview.class);
			loginObj.loginUser(testData.get("userName"), testData.get("password"));
			homeObj.clickAdminPageLink();
			
			String minBalFromAdmin = String.format("%.2f", Double.parseDouble(admin.getMinBalance()));
			String iniBalFromAdmin = String.format("%.2f", Double.parseDouble(admin.getInitBalance()));
			admin.clearDatabase();
			homeObj.clickLogOutLink();
			loginObj.registerUser(testData);
			homeObj.clickOpenNewAccountLink();
			Map<String,String> acctDetails = newAccountObj.captureAccountDetails();
			testData.put("baseAcct", acctDetails.get("baseAcct"));
			testData.put("acctType", acctDetails.get("acctType"));
			homeObj.clickaccountsOverviewLink();
			Map<String,String> baseAcctDetails=actOverview.extractDetailsFromAccountOverviewTable(acctDetails.get("baseAcct"));
			String baseAcctBal = String.format("%.2f", Double.parseDouble(baseAcctDetails.get("Balance").replace("$", "")));
			softAssert.assertEquals(baseAcctBal, testData.get("defaultInitBal"));
			softAssert.assertEquals(iniBalFromAdmin,testData.get("defaultInitBal"));
			String acctMinBalFromNewActSrn = String.format("%.2f", Double.parseDouble(acctDetails.get("initMinAccountBal").replace("$", "")));
			String acctMinBalTestData = String.format("%.2f", Double.parseDouble(testData.get("defaultMinBal")));
			testData.put("baseAcct", acctDetails.get("baseAcct"));
			testData.put("acctType", acctDetails.get("acctType"));
			homeObj.clickOpenNewAccountLink();
			String newAcct = newAccountObj.openNewAccount(acctDetails.get("baseAcct"), acctDetails.get("acctType"));
			testData.put("createdAcct", newAcct);
			Map<String,String> acctOverviewAcctDetails =actOverview.extractAccountDetails();
			String minBalanceFromActOverview = String.format("%.2f", Double.parseDouble(acctOverviewAcctDetails.get("Balance").replace("$", "")));
			softAssert.assertEquals(acctMinBalFromNewActSrn, acctMinBalTestData);
			softAssert.assertEquals(minBalanceFromActOverview, acctMinBalTestData);
			softAssert.assertEquals(minBalFromAdmin,acctMinBalTestData);
			updateExcelFileDetails(testData);
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
	}
	
	@Test(priority = 2)
	public void verifyMinBalanceTest() throws DataException, IOException
	{
		Map<String,String> testData = getTestData("verifyMinBalanceTest");
		if(testData.size() !=0)
		{
			
			Home homeObj = createPage(Home.class);
			Admin adminObj = createPage(Admin.class);
			Login loginObj = createPage(Login.class);
			NewAccount newAccountObj = createPage(NewAccount.class);
			AccountOverview actOverview = createPage(AccountOverview.class);
			loginObj.loginUser(testData.get("userName"), testData.get("password"));
			homeObj.clickAdminPageLink();
			adminObj.setMinbal(testData.get("initMinBalance"));
			homeObj.clickOpenNewAccountLink();
			Map<String,String> acctDetails = newAccountObj.captureAccountDetails();
			testData.put("baseAcct", acctDetails.get("baseAcct"));
			testData.put("acctType", acctDetails.get("acctType"));
			String acctMinBalFromNewActSrn = String.format("%.2f", Double.parseDouble(acctDetails.get("initMinAccountBal").replace("$", "")));
			String acctMinBalFromTestData = String.format("%.2f", Double.parseDouble(testData.get("initMinBalance")));
			softAssert.assertEquals(acctMinBalFromNewActSrn, acctMinBalFromTestData);
			String newAcct = newAccountObj.openNewAccount(acctDetails.get("baseAcct"), acctDetails.get("acctType"));
			testData.put("createdAcct", newAcct);
			Map<String,String> acctOverviewAcctDetails =actOverview.extractAccountDetails();
			String minBalanceFromActOverview = String.format("%.2f", Double.parseDouble(acctOverviewAcctDetails.get("Balance").replace("$", "")));
			softAssert.assertEquals(acctMinBalFromNewActSrn, acctMinBalFromTestData);
			softAssert.assertEquals(minBalanceFromActOverview, acctMinBalFromTestData);
			homeObj.clickAdminPageLink();
			adminObj.setMinbal(testData.get("defaultMinBal"));
			updateExcelFileDetails(testData);
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
	}
	
	@Test
	public void verifyCustomiseInitialBalance() throws DataException, IOException
	{
		Map<String,String> testData = getTestData("verifyCustomiseInitialBalance");
		if(testData.size() !=0)
		{
			Home homeObj = createPage(Home.class);
			Admin admin = createPage(Admin.class);
			Login loginObj = createPage(Login.class);
			NewAccount newAccountObj = createPage(NewAccount.class);
			AccountOverview actOverview = createPage(AccountOverview.class);
			loginObj.loginUser(testData.get("userName"), testData.get("password"));
			homeObj.clickAdminPageLink();
			admin.setInitbal(testData.get("initBalance"));
			admin.clearDatabase();
			homeObj.clickLogOutLink();
			loginObj.registerUser(testData);
			homeObj.clickOpenNewAccountLink();
			Map<String,String> acctDetails = newAccountObj.captureAccountDetails();
			testData.put("baseAcct", acctDetails.get("baseAcct"));
			testData.put("acctType", acctDetails.get("acctType"));
			homeObj.clickaccountsOverviewLink();
			Map<String,String> baseAcctDetails=actOverview.extractDetailsFromAccountOverviewTable(acctDetails.get("baseAcct"));
			String baseAcctBal = String.format("%.2f", Double.parseDouble(baseAcctDetails.get("Balance").replace("$", "")));
			assertEquals(baseAcctBal, testData.get("initBalance"));
			homeObj.clickAdminPageLink();
			admin.setInitbal(testData.get("defaultInitBal"));
			updateExcelFileDetails(testData);
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
	}
	
	
	@Parameters({"BalanceTest"})
	@Test
	public void verifyOpeningSavingsAcct(String BalanceTest) throws DataException, IOException
	{
		System.out.println(BalanceTest);
		Map<String,String> testData = getTestData("verifyOpeningSavingsAcct");
		if(testData.size() !=0)
		{
			Home homeObj = createPage(Home.class);
			Login loginObj = createPage(Login.class);
			NewAccount newAccountObj = createPage(NewAccount.class);
			AccountOverview actOverview = createPage(AccountOverview.class);
			loginObj.loginUser(testData.get("userName"), testData.get("password"));
			
			homeObj.clickOpenNewAccountLink();
			String newAcct = newAccountObj.openNewAccount("NA", testData.get("acctType"));
			testData.put("createdAcct", newAcct);
			Map<String,String> acctOverviewAcctDetails =actOverview.extractAccountDetails();
			assertEquals(acctOverviewAcctDetails.get("Account Type"), testData.get("acctType"));
			updateExcelFileDetails(testData);
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
	}
	
	@Test
	public void verifyOpeningAccountWithOtherThanBaseAcct() throws DataException, IOException
	{
		Map<String,String> testData = getTestData("verifyOpeningAccountWithOtherThanBaseAcct");
		if(testData.size() !=0)
		{
			Home homeObj = createPage(Home.class);
			Login loginObj = createPage(Login.class);
			NewAccount newAccountObj = createPage(NewAccount.class);
			AccountOverview actOverview = createPage(AccountOverview.class);
			loginObj.loginUser(testData.get("userName"), testData.get("password"));
			
			homeObj.clickOpenNewAccountLink();
			Map<String,String> acctDetails = newAccountObj.captureAccountDetails();
			testData.put("baseAcct", acctDetails.get("baseAcct"));
			testData.put("acctType", acctDetails.get("acctType"));
			homeObj.clickOpenNewAccountLink();
			String newAcct = newAccountObj.openNewAccount("NA", "NA");
			testData.put("createdAcct", newAcct);
			homeObj.clickOpenNewAccountLink();
			String secAcct = newAccountObj.openNewAccount(newAcct, "NA");
			testData.put("secAcct", secAcct);
			Map<String,String> acctOverviewAcctDetails =actOverview.extractAccountDetails();
			String minBalanceFromActOverview = String.format("%.2f", Double.parseDouble(acctOverviewAcctDetails.get("Balance").replace("$", "")));
			assertEquals(minBalanceFromActOverview, testData.get("defaultMinBal"));
			updateExcelFileDetails(testData);
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
	}
	
	

}

package com.parabank.ui.tests.pos;

import java.io.IOException;
import java.util.Map;

import org.testng.annotations.Test;

import com.parabank.ui.PARABANK.POM.AccountOverview;
import com.parabank.ui.PARABANK.POM.Admin;
import com.parabank.ui.PARABANK.POM.Home;
import com.parabank.ui.PARABANK.POM.Login;
import com.parabank.ui.PARABANK.POM.NewAccount;
import com.parabank.ui.base.BaseTest;

public class POS_Account_Test extends BaseTest{
	
	@Test(priority = 1)
	public void account_OpenNewAccount() throws IOException
	{
		Map<String,String> testData = getTestData("account_OpenNewAccount");
		
		createPage(Login.class).loginUser(testData.get("userName"), testData.get("password"));
		createPage(Home.class).clickOpenNewAccountLink();
		String acctNumber = createPage(NewAccount.class).openNewAccount(testData.get("baseAcct"), testData.get("acctType"));
		testData.put("createdAcct", acctNumber);
		updateExcelFileDetails(testData);
		
	}
	@Test(dependsOnMethods = {"account_OpenNewAccount"})
	public void account_checkNewAccountDettails() throws IOException
	{
		Map<String,String> testData = getTestData("account_OpenNewAccount");
		AccountOverview acctOverviewObj =createPage(AccountOverview.class);
		Home homeObj = createPage(Home.class);
		createPage(Login.class).loginUser(testData.get("userName"), testData.get("password"));
		homeObj.clickaccountsOverviewLink();
		acctOverviewObj.selectAccount(testData.get("createdAcct"));
		Map<String,String> acctDetails=acctOverviewObj.extractTransactionDetails();
		softAssert.assertEquals(acctDetails.get("Account Type"), testData.get("acctType"));
		homeObj.clickAdminPageLink();
		String minBalance = createPage(Admin.class).getMinBalance();
		softAssert.assertEquals(acctDetails.get("Balance"), "$"+minBalance);
	}
	
	

}

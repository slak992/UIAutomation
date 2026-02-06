package com.parabank.ui.tests.pos;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.swing.text.DateFormatter;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.parabank.ui.PARABANK.POM.AccountOverview;
import com.parabank.ui.PARABANK.POM.Admin;
import com.parabank.ui.PARABANK.POM.FindTransactions;
import com.parabank.ui.PARABANK.POM.Home;
import com.parabank.ui.PARABANK.POM.Login;
import com.parabank.ui.PARABANK.POM.NewAccount;
import com.parabank.ui.PARABANK.POM.TransferFunds;
import com.parabank.ui.PARABANK.exceptions.CustomException;
import com.parabank.ui.PARABANK.exceptions.DataException;
import com.parabank.ui.base.BaseTest;


public class POS_Transaction extends BaseTest{
	@Test(priority = 1)
	public void findTransactionByDate(ITestContext context) throws IOException, DataException, CustomException
	{
		Map<String,String> testData = getTestData("findTransactionByDate");
		if(testData.size() !=0)
		{
			//Object initiation
			Login loginObj=createPage(Login.class);
			Home homeObj=createPage(Home.class);
			NewAccount newAcctObj=createPage(NewAccount.class);
			Admin adminObj = createPage(Admin.class);
			AccountOverview acctOverviewObj =createPage(AccountOverview.class);
			TransferFunds transferFundObj = createPage(TransferFunds.class);
			context.setAttribute("loginObj", loginObj);
			context.setAttribute("homeObj", loginObj);
			context.setAttribute("newAcctObj", loginObj);
			context.setAttribute("adminObj", loginObj);
			context.setAttribute("acctOverviewObj", loginObj);
			//Check if the user exist
				//if not exist then register
			loginObj.loginUser(testData.get("userName"), testData.get("password"));
			if(loginObj.isUserNotRegisterErrorMsg())
			{
				loginObj.registerUser(testData);
				
			}
			//Capturing the base account details
			homeObj.clickOpenNewAccountLink();
			Map<String,String> baseAcctDetails = newAcctObj.captureAccountDetails();
			context.setAttribute("baseAcct", baseAcctDetails.get("baseAcct"));
			//Deposit amount and capture the final base account bal
			homeObj.clickAdminPageLink();
			boolean status = adminObj.depositFund(baseAcctDetails.get("baseAcct"), testData.get("baseAcctDepositAmt"));
			if(!status)
			{
				throw new CustomException("ERROR!!Deposit failed!!");
			}
			homeObj.clickaccountsOverviewLink();
			acctOverviewObj.selectAccount(baseAcctDetails.get("baseAcct"));
			Map<String,String> baseAcctDetailsAcctOverview = acctOverviewObj.extractAccountDetails();
			context.setAttribute("baseAcctBal", baseAcctDetailsAcctOverview.get("Balance"));
			
			//open new account1
			homeObj.clickOpenNewAccountLink();
			String newAcct1=newAcctObj.openNewAccount(baseAcctDetails.get("baseAcct"), baseAcctDetails.get("acctType"));
			testData.put("createdAcct", newAcct1);
			//open new acct 2
			homeObj.clickOpenNewAccountLink();
			String newAcct2=newAcctObj.openNewAccount(baseAcctDetails.get("baseAcct"), baseAcctDetails.get("acctType"));
			testData.put("secAcct", newAcct2);
			//transfer 100 from base to new account1 
			homeObj.clickFundTransfersLink();
			transferFundObj.transferFund(context.getAttribute("baseAcct").toString(), newAcct1, testData.get("depositAmt1"));
			//trnasfer 50 from acct1 to acct2
			homeObj.clickFundTransfersLink();
			transferFundObj.transferFund(context.getAttribute("baseAcct").toString(), newAcct2, testData.get("depositAmt2"));
			//go to account overview for aact 1 get the record details with current date and amount
			homeObj.clickaccountsOverviewLink();
			acctOverviewObj.selectAccount(newAcct1);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
			String previousDate = formatter.format(LocalDate.now().minusDays(1));
			String currentDate = formatter.format(LocalDate.now());
			List<List<String>> tableData1 = acctOverviewObj.getTableAsList("Date",previousDate);
			System.out.println(tableData1);
			homeObj.clickFindTransactionsLink();
			FindTransactions transObj = createPage(FindTransactions.class);
			transObj.findTransactionByDate(newAcct1, currentDate);
			List<List<String>> tableTrans1 = transObj.getTableAsList();
			System.out.println(tableTrans1);
			Assert.assertEquals(tableData1, tableTrans1);
			//go to account overview for aact 2 get the record details with current date and amount
			homeObj.clickaccountsOverviewLink();
			acctOverviewObj.selectAccount(newAcct2);
			List<List<String>> tableData2 = acctOverviewObj.getTableAsList("Date",previousDate);
			System.out.println(tableData2);
			homeObj.clickFindTransactionsLink();
			transObj.findTransactionByDate(newAcct2, currentDate);
			List<List<String>> tableTrans2 = transObj.getTableAsList();
			System.out.println(tableTrans2);
			Assert.assertEquals(tableData2, tableTrans2);
			
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
		
	}

}

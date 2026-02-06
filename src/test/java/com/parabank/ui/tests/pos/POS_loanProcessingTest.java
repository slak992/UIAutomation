package com.parabank.ui.tests.pos;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.testng.annotations.Test;

import com.parabank.ui.PARABANK.POM.AccountOverview;
import com.parabank.ui.PARABANK.POM.Home;
import com.parabank.ui.PARABANK.POM.Loan;
import com.parabank.ui.PARABANK.POM.Login;
import com.parabank.ui.PARABANK.POM.NewAccount;
import com.parabank.ui.base.BaseTest;
import com.parabank.ui.base.RetryAnalyser;


public class POS_loanProcessingTest extends BaseTest {
	
	

	@Test(retryAnalyzer = RetryAnalyser.class)
	public void loanProcessing_request() throws IOException
	{
		Map<String,String> testData = getTestData("loanProcessing_request");
		AccountOverview acctObj = createPage(AccountOverview.class);
		Loan loanObj = createPage(Loan.class);
		createPage(Login.class).loginUser(testData.get("userName"), testData.get("password"));
		createPage(Home.class).clickOpenNewAccountLink();
		String newAcctNumber =createPage(NewAccount.class).openNewAccount(testData.get("baseAcct"), testData.get("acctType"));
		testData.put("createdAcct", newAcctNumber);
		
		//Before Loan Processing
		createPage(Home.class).clickaccountsOverviewLink();
		Map<String,String> loanBaseAcctDetailsBeforeLoan=acctObj.extractDetailsFromAccountOverviewTable(newAcctNumber);
		createPage(Home.class).clickRequestLoanLink();
		loanObj.applyForLoan(testData.get("loanAmt"), testData.get("downPayment"), testData.get("createdAcct"));
		Map<String,String> loanDetails = loanObj.getLoanAccountDetails();
		softAssert.assertEquals(loanDetails.get("date"), testData.get("loanProssedDate"));
		softAssert.assertEquals(loanDetails.get("status"), testData.get("expectedLoanStatus"));
		softAssert.assertEquals(loanDetails.get("successMsg"), testData.get("successMsg"));
		testData.put("loanAcct", loanDetails.get("loanAcctID"));
		
		Map<String,String> loanAcctDetails = acctObj.extractAccountDetails();
		assertEquals(loanAcctDetails.get("Account Type"),testData.get("loanAcctType") );
		assertEquals(loanAcctDetails.get("Balance"),"$"+testData.get("loanAmt")+".00" );
		assertEquals(loanAcctDetails.get("Available"),"$"+testData.get("loanAmt")+".00" );
		createPage(Home.class).clickaccountsOverviewLink();
		Map<String,String> loanBaseAcctDetailsAfterLoanProcessing=acctObj.extractDetailsFromAccountOverviewTable(newAcctNumber);
		double beforeLoanProcessing = Double.parseDouble(loanBaseAcctDetailsBeforeLoan.get("Balance").replace("$", ""));
		double expectedAccountBalance = beforeLoanProcessing - Double.parseDouble(testData.get("downPayment"));
		assertEquals(Double.parseDouble(loanBaseAcctDetailsAfterLoanProcessing.get("Balance").replace("$", "")),expectedAccountBalance);
		updateExcelFileDetails(testData);
		
		
	}

}

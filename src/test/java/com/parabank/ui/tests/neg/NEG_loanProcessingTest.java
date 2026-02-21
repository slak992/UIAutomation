package com.parabank.ui.tests.neg;

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

public class NEG_loanProcessingTest extends BaseTest {
	
	

	@Test(retryAnalyzer = RetryAnalyser.class)
	public void NEG_loanProcessing_request() throws IOException
	{
		Map<String,String> testData = getTestData("NEG_loanProcessing_request");
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
		Map<String,String> errorMsgDetails = loanObj.getErrorMsgDetails();
		
		assertEquals(errorMsgDetails.get("date"), testData.get("loanProssedDate"));
		assertEquals(errorMsgDetails.get("status"), testData.get("expectedLoanStatus"));
		assertEquals(errorMsgDetails.get("errorMsg"), testData.get("errorMsg"));
		createPage(Home.class).clickaccountsOverviewLink();
		Map<String,String> loanBaseAcctDetailsAfterLoanProcessing=acctObj.extractDetailsFromAccountOverviewTable(newAcctNumber);
		assertEquals(Double.parseDouble(loanBaseAcctDetailsAfterLoanProcessing.get("Balance").replace("$", "")),Double.parseDouble(loanBaseAcctDetailsBeforeLoan.get("Balance").replace("$", "")));
		updateExcelFileDetails(testData);
		
		
	}

}

package com.parabank.ui.tests.pos;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.parabank.ui.PARABANK.POM.AccountOverview;
import com.parabank.ui.PARABANK.POM.Admin;
import com.parabank.ui.PARABANK.POM.Home;
import com.parabank.ui.PARABANK.POM.Login;
import com.parabank.ui.PARABANK.POM.NewAccount;
import com.parabank.ui.PARABANK.POM.TransferFunds;
import com.parabank.ui.PARABANK.exceptions.CustomException;
import com.parabank.ui.PARABANK.exceptions.DataException;
import com.parabank.ui.base.BaseTest;
public class POS_TransferFunds extends BaseTest {

	@Test
	public void verifyTransferFunds() throws IOException, DataException, CustomException
	{
		Map<String,String> testData = getTestData("verifyTransferFunds");
		if(testData.size() !=0)
		{
			DecimalFormat dFormat = new DecimalFormat("0.00");
			
			Login loginObj=createPage(Login.class);
			Home homeObj=createPage(Home.class);
			NewAccount newAcctObj=createPage(NewAccount.class);
			Admin adminObj = createPage(Admin.class);
			AccountOverview acctOverviewObj =createPage(AccountOverview.class);
			TransferFunds transferFundObj = createPage(TransferFunds.class);
			loginObj.loginUser(testData.get("userName"), testData.get("password"));
			if(loginObj.isUserNotRegisterErrorMsg())
			{
				loginObj.registerUser(testData);
				
			}
			//capture minBal details
			homeObj.clickAdminPageLink();
			String minBal =adminObj.getMinBalance();
			
			homeObj.clickOpenNewAccountLink();
//		acctDetails.put('baseAcct', getDefaultValueFromDropDown(baseAcctDD));
//		acctDetails.put('acctType', getDefaultValueFromDropDown(acctTypeDD));
//		acctDetails.put('initMinAccountBal',
				
				
					
				
			Map<String,String> baseAcctDetails = newAcctObj.captureAccountDetails();
			homeObj.clickaccountsOverviewLink();
			acctOverviewObj.selectAccount(baseAcctDetails.get("baseAcct"));
			Map<String,String> baseAcctDetailsAcctOverview = acctOverviewObj.extractAccountDetails();
			baseAcctDetails.put("initialBalance", baseAcctDetailsAcctOverview.get("Balance"));
			homeObj.clickAdminPageLink();
			boolean status =adminObj.depositFund(baseAcctDetails.get("baseAcct"), testData.get("baseAcctDepositAmt"));
			Double baseAcctAfterDeposit = Double.parseDouble(baseAcctDetailsAcctOverview.get("Balance").replace("$", ""))+Double.parseDouble(testData.get("baseAcctDepositAmt"));
			baseAcctAfterDeposit = Math.round(baseAcctAfterDeposit*100)/100.0;
			if(!status)
			{
				throw new CustomException("ERROR!!Deposit failed!!");
			}
			baseAcctDetails.put("After first deposit", String.valueOf(baseAcctAfterDeposit));
			//create acct1
			homeObj.clickOpenNewAccountLink();
			String newAcct1=newAcctObj.openNewAccount(baseAcctDetails.get("baseAcct"), baseAcctDetails.get("acctType"));
			testData.put("createdAcct", newAcct1);
			//create acct2
			homeObj.clickOpenNewAccountLink();
			String newAcct2=newAcctObj.openNewAccount(baseAcctDetails.get("baseAcct"), baseAcctDetails.get("acctType"));
			testData.put("secAcct", newAcct2);
			Double BaseAcctAfterActCreations = baseAcctAfterDeposit- 
					(Double.parseDouble(minBal)*2);
			homeObj.clickFundTransfersLink();
			Double low = 20.00;
			Double high = 45.00;
			Double range = high-low;
			Double depositAmt1 = Math.round(((Math.random()*range)+low)*100)/100.0;
			testData.put("depositAmt1", String.valueOf(depositAmt1));
			Double depositAmt2 = Math.round(((Math.random()*range)+low)*100)/100.0;
			testData.put("depositAmt2", String.valueOf(depositAmt2));
			String successMsg1=transferFundObj.transferFund(baseAcctDetails.get("baseAcct"), newAcct1,String.valueOf(depositAmt1));
			String expectedSuccessMsg1 = testData.get("successMsg").replace("<amt>", String.format("%.2f",depositAmt1))
					.replace("<fromAct>",baseAcctDetails.get("baseAcct") )
					.replace("<toAcct>",newAcct1);
			Assert.assertEquals(successMsg1, expectedSuccessMsg1);
			Double baseAcctBalAfterT1 = BaseAcctAfterActCreations - depositAmt1;
			baseAcctBalAfterT1 = Math.round(baseAcctBalAfterT1*100)/100.0;
			homeObj.clickFundTransfersLink();
			String successMsg2=transferFundObj.transferFund(baseAcctDetails.get("baseAcct"), newAcct2,String.valueOf(depositAmt2));
			String expectedSuccessMsg2 = testData.get("successMsg").replace("<amt>", String.format("%.2f",depositAmt2))
					.replace("<fromAct>",baseAcctDetails.get("baseAcct") )
					.replace("<toAcct>",newAcct2);
			Assert.assertEquals(successMsg2, expectedSuccessMsg2);
			Double baseAcctBalAfterT2 = baseAcctBalAfterT1 - depositAmt2;
			homeObj.clickaccountsOverviewLink();
			acctOverviewObj.selectAccount(baseAcctDetails.get("baseAcct"));
			Double baseActOverview = Double.parseDouble(acctOverviewObj.extractAccountDetails().get("Balance").replace("$", "")) ;
			assertEquals(baseActOverview, baseAcctBalAfterT2);
			
			homeObj.clickaccountsOverviewLink();
			acctOverviewObj.selectAccount(newAcct1);
			Double newAcct1ActOverview = Double.parseDouble(acctOverviewObj.extractAccountDetails().get("Balance").replace("$", "")) ;
			
			Double finalBalAct1 =Double.parseDouble(minBal)+depositAmt1;
			finalBalAct1 = Math.round(finalBalAct1*100)/100.0;
			assertEquals(finalBalAct1, newAcct1ActOverview);
			
			homeObj.clickaccountsOverviewLink();
			acctOverviewObj.selectAccount(newAcct2);
			Double newAcct2ActOverview = Double.parseDouble(acctOverviewObj.extractAccountDetails().get("Balance").replace("$", "")) ;
			Double finalBalAct2 =Double.parseDouble(minBal)+depositAmt2;
			finalBalAct2=Math.round(finalBalAct2*100)/100.0;
			assertEquals(finalBalAct2, newAcct2ActOverview);
			updateExcelFileDetails(testData);
			
			
			
			
		}
		else
		{
			throw new DataException("ERROR:Test Data Not Found!!!") ;
		}
	}

}

package com.parabank.ui.PARABANK.POM;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.parabank.ui.PARABANK.helpers.ReuseableComponents;

public class Loan extends ReuseableComponents{
	private WebDriver driver;
	
	@FindBy(id="amount")
	private WebElement loanAmt;
	
	@FindBy(id="downPayment")
	private WebElement downPayment;
	
	@FindBy(id="fromAccountId")
	private WebElement fromAccountId;
	
	@FindBy(xpath="//input[@value='Apply Now']")
	private WebElement applyBtn;
	
	@FindBy(css="td#responseDate")
	private WebElement applyDate;
	
	@FindBy(css="td#loanStatus")
	private WebElement loanStatus;
	
	@FindBy(xpath="//div[@id='loanRequestApproved']/p[1]")
	private WebElement successMsg;
	
	@FindBy(id="newAccountId")
	private WebElement newAccountId;
	
	@FindBy(css="div#loanRequestDenied p")
	private WebElement errorMsg;
	
	public void applyForLoan(String amt,String downPaymentamt, String acctID)
	{
		loanAmt.sendKeys(amt);
		downPayment.sendKeys(downPaymentamt);
		selectValueFromDD(fromAccountId, "visibleText", acctID);
		applyBtn.click();
		
	}
	
	public Map<String,String> getLoanAccountDetails()
	{
		waitForElement(driver, applyDate, 3, "visibility of element");
		Map<String,String> res = new HashMap<>();
		res.put("date", applyDate.getText());
		res.put("status", loanStatus.getText());
		res.put("successMsg", successMsg.getText());
		res.put("loanAcctID", newAccountId.getText());
		newAccountId.click();
		return res;
	}
	
	public Map<String,String> getErrorMsgDetails()
	{
		waitForElement(driver, applyDate, 3, "visibility of element");
		Map<String,String> res = new HashMap<>();
		res.put("date", applyDate.getText());
		res.put("status", loanStatus.getText());
		res.put("errorMsg", errorMsg.getText());
		return res;
		
		
	}
	

}

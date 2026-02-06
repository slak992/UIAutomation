package com.parabank.ui.PARABANK.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.parabank.ui.PARABANK.helpers.ReuseableComponents;

public class TransferFunds extends ReuseableComponents{
	private WebDriver driver;
	@FindBy(id="amount")
	private WebElement amt;
	@FindBy(id="fromAccountId")
	private WebElement fromAccount;
	@FindBy(id="toAccountId")
	private WebElement toAccount;
	@FindBy(xpath="//input[@value='Transfer']")
	private WebElement transferBtn;
	@FindBy(xpath="//div[@id=\"showResult\"]//p")
	private WebElement result;
	
	public String transferFund(String fromAcct, String toAcct, String transferAmt)
	{
		waitForElement(driver,amt,2,"visibility of element");
		amt.sendKeys(transferAmt);
		selectValueFromDD( fromAccount, "value", fromAcct);
		selectValueFromDD( toAccount, "value", toAcct);
		transferBtn.submit();
		waitForElement(driver,result,4,"visibility of element");
		return result.getText();
	}
	

}

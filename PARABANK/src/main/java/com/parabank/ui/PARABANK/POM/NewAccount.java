package com.parabank.ui.PARABANK.POM;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.parabank.ui.PARABANK.helpers.ReuseableComponents;

public class NewAccount extends ReuseableComponents{
	private WebDriver driver;
	
	
	@FindBy(css="select#type")
	private WebElement acctTypeDD;
	
	@FindBy(css="select#fromAccountId")
	private WebElement baseAcctDD;
	
	@FindBy(css="input[type='button']")
	private WebElement openNewAcctBtn;
	
	@FindBy(css="a#newAccountId")
	private WebElement newAcctID;
	
	@FindBy(xpath="//select[@id='type']//following-sibling::p/b")
	private WebElement accountText;
	
	
	
	public String openNewAccount(String baseAcct, String acctType)
	{
		if(!acctType.equals("NA"))
		{
			Select acctTypeSelectObj = new Select(acctTypeDD);
			acctTypeSelectObj.selectByVisibleText(acctType);
		}
		
		if(!baseAcct.equals("NA"))
		{
			Select baseAcctObj = new Select(baseAcctDD);
			baseAcctObj.selectByVisibleText(baseAcct);
		}
		
		
		openNewAcctBtn.click();
		
		waitForElement(driver,newAcctID, 2, "visibility of element");
		
		String accountNmber = newAcctID.getText();	
		
		newAcctID.click();
		
		return accountNmber;
		
		
	}
	
	
	public Map<String,String> captureAccountDetails()
	{
		waitForElement(driver,baseAcctDD, 2, "visibility of element");
		Map<String,String> acctDetails = new HashMap<>();
		acctDetails.put("baseAcct", getDefaultValueFromDropDown(baseAcctDD));
		acctDetails.put("acctType", getDefaultValueFromDropDown(acctTypeDD));
		acctDetails.put("initMinAccountBal", extractDetailsUsingRegEx(accountText.getText(),"\\$\\d+\\.\\d{2}"));
		return acctDetails;
		
	}

}

package com.parabank.ui.PARABANK.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.parabank.ui.PARABANK.helpers.ReuseableComponents;

public class Home extends ReuseableComponents{
	private WebDriver driver;
	
	@FindBy(xpath="//a[contains(text(),'Open New Account')]")
	private WebElement openNewAccountLink;
	
	@FindBy(xpath="//a[contains(text(),'Accounts Overview')]")
	private WebElement accountsOverviewLink;
	
	@FindBy(xpath="//a[contains(text(),'Transfer Funds')]")
	private WebElement fundTransfersLink;
	
	@FindBy(xpath="//a[contains(text(),'Bill Pay')]")
	private WebElement billPayLink;
	
	@FindBy(xpath="//a[contains(text(),'Find Transactions')]")
	private WebElement findTransactionsLink;
	
	@FindBy(xpath="//a[contains(text(),'Update Contact Info')]")
	private WebElement updateContactInfoLink;
	
	@FindBy(xpath="//a[contains(text(),'Request Loan')]")
	private WebElement requestLoanLink;
	
	@FindBy(xpath="//a[contains(text(),'Log Out')]")
	private WebElement logOutLink;
	
	@FindBy(xpath="//a[contains(text(),'Admin Page')]")
	private WebElement adminPageLink;
	
	public void clickOpenNewAccountLink()
	{
		openNewAccountLink.click();
	}
	
	public void clickaccountsOverviewLink()
	{
		accountsOverviewLink.click();
	}
	
	public void clickFundTransfersLink()
	{
		fundTransfersLink.click();
	}
	
	public void clickBillPayLink()
	{
		billPayLink.click();
	}
	
	public void clickFindTransactionsLink()
	{
		findTransactionsLink.click();
	}
	
	public void clickUpdateContactInfoLink()
	{
		updateContactInfoLink.click();
	}
	
	public void clickRequestLoanLink()
	{
		requestLoanLink.click();
	}
	
	public void clickLogOutLink()
	{
		waitForElement(driver, logOutLink, 5, "visibility of element");
		logOutLink.click();
	}
	
	public void clickAdminPageLink()
	{
		adminPageLink.click();
	}
	
	

}

package com.parabank.ui.PARABANK.POM;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.parabank.ui.PARABANK.helpers.ReuseableComponents;

public class Admin extends ReuseableComponents{
	
	private WebDriver driver;
	@FindBy(xpath="//h3[contains(text(),\"Application Settings\")]/following-sibling::table")
	private WebElement appSettingTable;
	
	@FindBy(css="input#initialBalance")
	private WebElement initBalance;
	
	@FindBy(css="input#minimumBalance")
	private WebElement minBalance;
	
	@FindBy(xpath="//input[@value='Submit']")
	private WebElement submitBtn;
	
	@FindBy(xpath="//button[contains(text(),\"Initialize\")]")
	private WebElement initializeBtn;
	
	@FindBy(xpath="//button[contains(text(),\"Clean\")]")
	private WebElement cleanBtn;
	
	public String getMinBalance()
	{
		waitForElement(driver, minBalance, 3, "visibility of element");
		scrollIntoView(driver, minBalance);
		return minBalance.getAttribute("value");
	}
	public String getInitBalance()
	{
		waitForElement(driver, initBalance, 3, "visibility of element");
		scrollIntoView(driver, initBalance);
		return initBalance.getAttribute("value");
	}
	public void setInitbal(String val)
	{
		waitForElement(driver, initBalance, 5, "visibility of element");
		scrollIntoView(driver, initBalance);
		initBalance.clear();
		initBalance.sendKeys(val);
		submitBtn.submit();;
	}
	public void setMinbal(String val)
	{
		waitForElement(driver, minBalance, 5, "visibility of element");
		scrollIntoView(driver, minBalance);
		minBalance.clear();
		minBalance.sendKeys(val);
		submitBtn.submit();;
	}
	
	public void clearDatabase()
	{
		waitForElement(driver, cleanBtn, 5, "visibility of element");
		scrollIntoView(driver, cleanBtn);
		cleanBtn.click();
		waitForElement(driver, initializeBtn, 5, "visibility of element");
		initializeBtn.click();
	}
	@FindBy(xpath="//a[contains(text(),'OpenAPI')]")
	private WebElement openAPIEle;
	@FindBy(xpath="//span[contains(text(),'deposit')]//parent::a")
	private WebElement depositLink;
	@FindBy(xpath="//input[@placeholder='accountId']")
	private WebElement acctID;
	@FindBy(xpath="//input[@placeholder='amount']")
	private WebElement amount;
	@FindBy(xpath="//button[contains(text(),'Try it out')]")
	private WebElement tryItOutBtn;
	@FindBy(xpath="//button[contains(text(),'Execute')]")
	private WebElement executeBtn;
	@FindBy(xpath="//div[@class='loading']")
	private WebElement loadingSpinner;
	@FindBy (xpath="//tr[@class='response']//td[@class='response-col_status']")
	private WebElement resCode;
	public boolean depositFund(String acctNum, String amt)
	{
		String parentWindow = driver.getWindowHandle();
		openLinkInNewTab(driver,openAPIEle);
		Set<String> winHandles = driver.getWindowHandles();
		driver.switchTo().window(winHandles.stream().skip(1).findFirst().orElseThrow());
		waitForElement(driver, depositLink, 5, "visibility of element");
		depositLink.click();
		waitForElement(driver, tryItOutBtn, 5, "visibility of element");
		tryItOutBtn.click();
		waitForElement(driver, acctID, 5, "visibility of element");
		acctID.sendKeys(acctNum);
		amount.sendKeys(amt);
		executeBtn.click();
		waitForElement(driver, loadingSpinner, 5, "invisibility of element");
		if(resCode.getText().equals("200"))
		{
			driver.close();
			driver.switchTo().window(parentWindow);
			return true;
		}
		driver.close();
		driver.switchTo().window(parentWindow);
		return false;
	}
	

}

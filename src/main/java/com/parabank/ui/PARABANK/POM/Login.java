package com.parabank.ui.PARABANK.POM;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.parabank.ui.PARABANK.helpers.ReuseableComponents;

public class Login extends ReuseableComponents{
	private WebDriver driver;
	
	@FindBy(css="input[name='username']")
	private WebElement userName;
	
	@FindBy(css="input[name='password']")
	private WebElement password;
	
	@FindBy(css="input[class='button']")
	private WebElement loginButton;
	
	@FindBy(xpath="//a[contains(text(),'Register')]")
	private WebElement register;
	
	private By customerForm = By.id("customerForm");
	
	@FindBy(id="customer.firstName")
	private WebElement firstNametxtBox;
	
	@FindBy(id="customer.lastName")
	private WebElement lastNametxtBox;
	
	@FindBy(id="customer.address.street")
	private WebElement addresstxtBox;
	
	@FindBy(id="customer.address.city")
	private WebElement citytxtBox;
	
	@FindBy(id="customer.address.state")
	private WebElement statetxtBox;
	
	@FindBy(id="customer.address.zipCode")
	private WebElement zipCodetxtBox;
	
	@FindBy(id="customer.phoneNumber")
	private WebElement phoneNumbertxtBox;
	
	@FindBy(id="customer.ssn")
	private WebElement ssntxtBox;
	
	@FindBy(id="customer.username")
	private WebElement userNametxtBox;
	
	@FindBy(id="customer.password")
	private WebElement passwordtxtBox;
	
	@FindBy(id="repeatedPassword")
	private WebElement confirmtxtBox;
	
	@FindBy(xpath="//input[@value='Register']")
	private WebElement registerBtn;
	
	@FindBy(xpath="//span[contains(@id,'error')]")
	private List<WebElement> registerUserError;
	
	@FindBy(xpath="//a[contains(text(),'Log Out')]")
	private WebElement logout;
	
	private By successMessage = By.cssSelector("#rightPanel p");
	
	@FindBy(xpath="//p[@class='error']")
	private WebElement inValidLoginErrorMsg;
	
	
	public String registerUser(Map<String,String> userDetails)
	{
		waitForElement(driver,register,5,"visibility of element");
		register.click();
		waitForElement(driver,customerForm,5,"visibility of element");
		fillRegisterUserForm(userDetails);
		waitForElement(driver,successMessage,5,"element present");
		return this.driver.findElement(successMessage).getText();
		
	}
	public void fillRegisterUserForm(Map<String,String> userDetails)
	{
		firstNametxtBox.clear();
		firstNametxtBox.sendKeys(userDetails.get("firstName"));
		lastNametxtBox.clear();
		lastNametxtBox.sendKeys(userDetails.get("lastName"));
		addresstxtBox.clear();
		addresstxtBox.sendKeys(userDetails.get("address"));
		citytxtBox.clear();
		citytxtBox.sendKeys(userDetails.get("city"));
		statetxtBox.clear();
		statetxtBox.sendKeys(userDetails.get("state"));
		zipCodetxtBox.clear();
		zipCodetxtBox.sendKeys(userDetails.get("zipCode"));
		phoneNumbertxtBox.clear();
		phoneNumbertxtBox.sendKeys(userDetails.get("phone"));
		ssntxtBox.clear();
		ssntxtBox.sendKeys(userDetails.get("ssn"));
		userNametxtBox.clear();
		userNametxtBox.sendKeys(userDetails.get("userName"));
		passwordtxtBox.clear();
		passwordtxtBox.sendKeys(userDetails.get("password"));
		confirmtxtBox.clear();
		confirmtxtBox.sendKeys(userDetails.get("confirmPassword"));
		registerBtn.submit();
	}
	public List<String> getRegisterUserErrorMessage(Map<String,String> userDetails)
	{
		register.click();
		fillRegisterUserForm(userDetails);
		waitForElement(driver,registerUserError, 2, "visibility of all elements");
		return registerUserError.stream().map(ele ->ele.getText()).collect(Collectors.toList());
		
	}
	
	public void loginUser(String user, String pass)
	{
		userName.sendKeys(user);
		password.sendKeys(pass);
		loginButton.submit();
		
		
		
	}
	
	public void logoutUser()

	{
		logout.click();
		waitForElement(driver,loginButton,0, "visibility of element");
	}
	
	public String getInvalidLoginUserErrorMsg()
	{
		return inValidLoginErrorMsg.getText();
	}
	
	public boolean isUserNotRegisterErrorMsg()
	{
		try
		{
			if(getInvalidLoginUserErrorMsg().isEmpty())
			{
				return false;
			}
			else
			{
				return true;
			}
			
		}
		catch(NoSuchElementException e)
		{
			return false;
		}
	}
}

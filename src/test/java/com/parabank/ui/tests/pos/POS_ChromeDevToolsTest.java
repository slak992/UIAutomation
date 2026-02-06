package com.parabank.ui.tests.pos;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.function.Predicate;

import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.parabank.ui.PARABANK.POM.AccountOverview;
import com.parabank.ui.PARABANK.POM.Admin;
import com.parabank.ui.PARABANK.POM.FindTransactions;
import com.parabank.ui.PARABANK.POM.Home;
import com.parabank.ui.PARABANK.POM.Login;
import com.parabank.ui.base.BaseTest;

public class POS_ChromeDevToolsTest extends BaseTest{
	@Test
	public void setGeoLocation() throws StreamReadException, DatabindException, IOException, InterruptedException
	{
		String testURL = "https://www.google.com";
		String jsonPath = System.getProperty("user.dir")+"\\src\\test\\java\\resources\\configurations\\"+prop.getProperty("jsonFileName");
		Map<String,Object> params = jsObj.queryJsonData(jsonPath,"$.setGeolocationOverride");
		cdpObject.setGeolocationOverrideFunction((ChromeDriver)getDriver(), params);
		getDriver().get(testURL);
		WebElement searchTextBox =getDriver().findElement(By.xpath("//textarea[@class='gLFyf']"));
		searchTextBox.sendKeys("Hello Text");
		searchTextBox.sendKeys(Keys.ENTER);
		Thread.sleep(2000);
		
	}
	
	@Test
	public void account_checkNewAccountDettails() throws IOException, InterruptedException
	{
		cdpObject.tweekResponseCode((ChromeDriver)getDriver());
		AccountOverview acctOverviewObj =createPage(AccountOverview.class);
		Home homeObj = createPage(Home.class);
		createPage(Login.class).loginUser("demo1", "demo1");
		homeObj.clickaccountsOverviewLink();
		acctOverviewObj.selectAccount("13566");
		Thread.sleep(5000);
		
	}
	
	@Test
	public void alterUrl() throws InterruptedException
	{
		cdpObject.alterRequestUrl((ChromeDriver)getDriver());
		Home homeObj = createPage(Home.class);
		FindTransactions transObj = createPage(FindTransactions.class);
		createPage(Login.class).loginUser("demo1", "demo1");
		homeObj.clickFindTransactionsLink();
		transObj.findTransactionByID("13566", "14476");
		Thread.sleep(5000);
		
	}
	
	@Test
	public void logJSErrors() throws InterruptedException
	{
		cdpObject.logEntries((ChromeDriver)getDriver());
		Home homeObj = createPage(Home.class);
		FindTransactions transObj = createPage(FindTransactions.class);
		createPage(Login.class).loginUser("demo1", "demo1");
		homeObj.clickFindTransactionsLink();
		transObj.findTransactionByID("13566", "14476");
		Thread.sleep(5000);
		
	}
	
	@Test
	public void failedRequest() throws InterruptedException
	{
		cdpObject.getFailedRequestDetails((ChromeDriver)getDriver());
		Home homeObj = createPage(Home.class);
		FindTransactions transObj = createPage(FindTransactions.class);
		createPage(Login.class).loginUser("demo1", "demo1");
		homeObj.clickFindTransactionsLink();
		transObj.findTransactionByID("13566", "14476");
		Thread.sleep(5000);
		
	}
	
	@Test
	public void blockSpecificRequestCalls() throws InterruptedException
	{
		cdpObject.blockURL((ChromeDriver)getDriver());
		Home homeObj = createPage(Home.class);
		FindTransactions transObj = createPage(FindTransactions.class);
		createPage(Login.class).loginUser("demo1", "demo1");
		homeObj.clickFindTransactionsLink();
		transObj.findTransactionByID("13566", "14476");
		Thread.sleep(5000);
		
	}
	
	@Test
	public void emulateNetworkCondition() throws InterruptedException
	{
		cdpObject.emulateNetworkSpeed((ChromeDriver)getDriver());
		Home homeObj = createPage(Home.class);
		FindTransactions transObj = createPage(FindTransactions.class);
		createPage(Login.class).loginUser("demo1", "demo1");
		homeObj.clickFindTransactionsLink();
		transObj.findTransactionByID("13566", "14476");
		Thread.sleep(5000);
		
	}
	
	@Test
	public void basicAuth()
	{
		Predicate<URI> httpBinURIPredicate = uri -> uri.getHost().contains("httpbin.org");
		((HasAuthentication)getDriver()).register(httpBinURIPredicate, UsernameAndPassword.of("user", "passwd"));
		getDriver().get("https://httpbin.org/basic-auth/user/passwd");
	}
	
	
	

}

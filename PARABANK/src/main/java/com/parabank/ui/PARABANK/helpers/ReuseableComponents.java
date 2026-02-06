package com.parabank.ui.PARABANK.helpers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReuseableComponents {
	
	public void waitForElement(WebDriver driver, Object ele, int durations, String condition)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(durations));
				
		switch(condition)
		{
		case "visibility of element": wait.until(ele instanceof By ? 
				ExpectedConditions.visibilityOf(driver.findElement((By) ele)):
					ExpectedConditions.visibilityOf((WebElement) ele));
		break;
		case "element present":wait.until(ExpectedConditions.presenceOfElementLocated((By)ele));
		break;
		case "visibility of all elements":wait.until(ExpectedConditions.visibilityOfAllElements((List<WebElement>) ele));
		break;
		case "invisibility of element":wait.until(ExpectedConditions.invisibilityOf((WebElement)ele));
		break;
		default:System.out.printf("%s not implemented",condition);
		break;
		}
		
	}
	
	public void waitForPageLoad(WebDriver driver,int duration)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(duration));
		wait.until(webDriver -> ((JavascriptExecutor)webDriver).executeScript("return document.readyState").equals("complete"));
		
	}
	
	public void scrollIntoView(WebDriver driver, WebElement ele)
	{
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].scrollIntoView();", ele);
	}
	
	public void selectValueFromDD( WebElement ele, String selectBy, String val)
	{
		Select selectObj = new Select(ele);
		if(selectBy.equals("visibleText"))
		{
			selectObj.selectByContainsVisibleText(val);
		}
		else if(selectBy.equals("value"))
		{
			selectObj.selectByValue(val);
		}
		else if(selectBy.equals("index"))
		{
			selectObj.selectByIndex(Integer.parseInt(val));
		}
	}
	
	public String getDefaultValueFromDropDown(WebElement ele)
	{
		Select selectObj = new Select(ele);
		return selectObj.getFirstSelectedOption().getText();
	}
	
	public String extractDetailsUsingRegEx(String statement, String regEx)
	{
		Pattern pattern = Pattern.compile(regEx);
		Matcher match = pattern.matcher(statement);
		if(match.find())
		{
			String extractedValue = match.group();
			return extractedValue;
		}
		return "";
	}
	
	public void openLinkInNewTab(WebDriver driver, WebElement ele)
	{
		Actions action = new Actions(driver);
		action.keyDown(Keys.CONTROL).click(ele).keyUp(Keys.CONTROL).build().perform();
	}
	
	public WebElement fluentWait(WebDriver driver, By ele, int durationInSec)
	{
		Wait<WebDriver> fwait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(durationInSec)).
				pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
		fwait.until(d -> d.findElement(ele));
		
		return  fwait.until(new Function<WebDriver,WebElement>(){
			public WebElement apply(WebDriver driver)
			{
				return driver.findElement(ele);
			}
		});

	}
	
	public int checkConnectionStatus(String url) throws MalformedURLException, IOException
	{
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod("HEAD");
		conn.connect();
		return conn.getResponseCode();
	}

	

}

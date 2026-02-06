package com.parabank.ui.PARABANK.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.parabank.ui.PARABANK.helpers.ReuseableComponents;

public class FooterProducts extends ReuseableComponents{
	private WebDriver driver;
	
	@FindBy(xpath="//p[contains(text(),'QUICK LINKS')]")
	private WebElement quickLinkHeader;
	
	@FindBy(xpath="//*[contains(text(),'info@parasoft.com')]")
	private WebElement email;
	
	public void checkQuickLinkStatusCode() throws MalformedURLException, IOException, InterruptedException
	{
		waitForElement(driver, quickLinkHeader, 3, "visibility of element");
		scrollIntoView(driver, quickLinkHeader);
		Actions action = new Actions(driver);
//		List<WebElement> quickLinks =driver.findElements(with(By.tagName("a")).below(quickLinkHeader));
		// Find the header fresh within the method
	    WebElement anchor = driver.findElement(By.xpath("//p[contains(text(),'QUICK LINKS')]"));
	    
	    // Use the fresh WebElement instead of the @FindBy one
	    List<WebElement> allLinks = driver.findElements(
	        with(By.tagName("a"))
	        .below(anchor));
		String parentWindow = driver.getWindowHandle();
		List<String> skipLinkList = new ArrayList<String>(Arrays.asList("info@parasoft.com",
				"support@parasoft.com",
				"+1 888 305 0041","101 E. Huntington Drive"));
		for(WebElement quickLink:allLinks)
		{
			if(!skipLinkList.contains(quickLink.getText()))
			{
				System.out.println(quickLink.getText());
				waitForElement(driver, quickLink, 3, "visibility of element");
				String cntrlClick = Keys.chord(Keys.CONTROL,Keys.ENTER);
				quickLink.sendKeys(cntrlClick);
//				action.keyDown(Keys.CONTROL).click(quickLink).keyUp(Keys.CONTROL).build().perform();
//				action.click(quickLink).build().perform();
				Set<String> winHandles = driver.getWindowHandles();
//				Thread.sleep(2000);
				WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
				wait.until(ExpectedConditions.numberOfWindowsToBe(2));
				String childWin = new ArrayList<String>(winHandles).get(1);
				driver.switchTo().window(childWin);
				String url = driver.getCurrentUrl();
				int winStatusCode = checkConnectionStatus(url);
				System.out.println(winStatusCode);
				driver.close();
				driver.switchTo().window(parentWindow);
			}
			
		}
		
	}
	
	

}

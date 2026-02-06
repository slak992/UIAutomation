package com.practice.ui;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class AutomationPractise {
public static void main(String[] args)
{
	ChromeOptions option = new ChromeOptions();
	Map<String, Object> prefs = new HashMap<>();
	prefs.put("credentials_enable_service", false);
	prefs.put("profile.password_manager_enabled", false);
	prefs.put("profile.password_manager_leak_detection", false);
	option.setExperimentalOption("prefs", prefs);
	WebDriver driver = new ChromeDriver(option);
	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
	driver.manage().window().maximize();
	driver.get("https://rahulshettyacademy.com/AutomationPractice/");
	String countryName = "United States Minor Outlying Islands";
	WebElement dynamicDD = driver.findElement(By.id("autocomplete"));
	Actions action = new Actions(driver);
	action.sendKeys(dynamicDD, countryName.substring(0,3)).build().perform();
	WebElement ddList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@id='ui-id-1']")));
	boolean isCountryDisplayed=false;
	int index=0;
	int ddListLen = ddList.findElements(By.xpath(".//li")).size();
	while(index<ddListLen)
	{
		action.keyDown(Keys.ARROW_DOWN).keyUp(Keys.ARROW_DOWN).build().perform();
		if(dynamicDD.getAttribute("value").equalsIgnoreCase(countryName))
		{
			System.out.printf("Country %s found in the dropdown list",countryName);
			isCountryDisplayed = true;
			break;
		}
		index++;
	}
	if(!isCountryDisplayed)
	{
		System.out.println("Country Not Found!!");
	}
	
	driver.quit();
}
}

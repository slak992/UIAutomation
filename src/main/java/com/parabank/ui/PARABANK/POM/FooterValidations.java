package com.parabank.ui.PARABANK.POM;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.parabank.ui.PARABANK.helpers.ReuseableComponents;

public class FooterValidations extends ReuseableComponents{
	private WebDriver driver;
	
	@FindBy(id="footerPanel")
	private WebElement footerPanel;
	
	
	public void checkTheStatusOfQuickLinksInProductPage(String footerItem)
	{
		waitForElement(driver, footerPanel, 3, "visibility of element");
		scrollIntoView(driver, footerPanel);
		List<WebElement> footerItems = footerPanel.findElements(By.tagName("a"));
		WebElement productLink = footerItems.stream().filter(ele -> ele.getText().equalsIgnoreCase(footerItem)).findFirst().orElseThrow();
		productLink.click();
		waitForPageLoad(driver, 3);
	}

}

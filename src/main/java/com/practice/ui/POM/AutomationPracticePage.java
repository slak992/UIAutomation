package com.practice.ui.POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.parabank.ui.PARABANK.helpers.ReuseableComponents;

public class AutomationPracticePage extends ReuseableComponents{
	WebDriver driver;
	
	@FindBy(id="checkbox-example")
	private WebElement checkBox;
	
	@FindBy(id="checkBoxOption1")
	private WebElement checkBoxOption1;
	
	public boolean verifyCheckBox()
	{
		checkBoxOption1.click();
		if(!checkBoxOption1.isSelected())
		{
			return false;
		}
		return true;
	}
	public int getNumberOfCheckBoxes()
	{
		return checkBox.findElements(By.xpath(".//label")).size();
	}

}

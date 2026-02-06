package com.parabank.ui.PARABANK.POM;

import java.io.IOException;
import java.nio.file.Paths;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.parabank.ui.PARABANK.helpers.ReuseableComponents;

public class AutoITPractice extends ReuseableComponents{
	private WebDriver driver;
	@FindBy(id="file-upload")
	private WebElement chooseFileBtn;
	
	@FindBy(id="file-submit")
	private WebElement uploadBtn;
	
	public String uploadFile(String filePath) throws IOException, InterruptedException
	{
		waitForElement(driver, chooseFileBtn, 3, "visibility of element");
		Actions action = new Actions(driver);
		action.click(chooseFileBtn).build().perform();
//		((JavascriptExecutor)driver).executeScript("arguments[0].click();", chooseFileBtn);
//		chooseFileBtn.click();
		String autoItPath = Paths.get(System.getProperty("user.dir"),"externalFiles","emailAppWinCtrlScript.exe").toString();
		Thread.sleep(1000);
		Runtime.getRuntime().exec(new String[] {autoItPath,filePath});
		Thread.sleep(2000);
		waitForElement(driver, uploadBtn, 3, "visibility of element");
//		((JavascriptExecutor)driver).executeScript("arguments[0].click();", uploadBtn);
		action.click(uploadBtn).build().perform();
//		uploadBtn.click();
		waitForPageLoad(driver, 2);
		String fileUploadStatus = driver.findElement(By.tagName("h3")).getText();
		return fileUploadStatus;
		
	}
	
	

}

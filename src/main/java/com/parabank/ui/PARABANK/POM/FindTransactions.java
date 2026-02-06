package com.parabank.ui.PARABANK.POM;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.parabank.ui.PARABANK.helpers.ReuseableComponents;

import groovy.transform.Final;

public class FindTransactions extends ReuseableComponents{
	private WebDriver driver;
	
	@FindBy(id="accountId")
	private WebElement accountID;
	
	@FindBy(id="transactionDate")
	private WebElement transactionDate;
	
	@FindBy(id="findByDate")
	private WebElement findByDateBtn;
	
	@FindBy(id="transactionTable")
	private WebElement transactionTable;
	
	@FindBy(xpath="//tbody//tr")
	private List<WebElement> tableRows;
	
	@FindBy(id="transactionId")
	private WebElement transactionId;
	
	@FindBy(id="findById")
	private WebElement findByIdBtn;
	
	public void findTransactionByDate(String acctID,String date)
	{
		waitForElement(driver, accountID, 2, "visibility of element");
		selectValueFromDD(accountID,"value",acctID);
		transactionDate.sendKeys(date);
		findByDateBtn.click();
		waitForElement(driver, transactionTable, 2, "visibility of element");
		
		
	}
	public void findTransactionByID(String acctID,String transactionID)
	{
		waitForElement(driver, accountID, 2, "visibility of element");
		selectValueFromDD(accountID,"value",acctID);
		transactionId.sendKeys(transactionID);
		findByIdBtn.click();
	}
	public List<List<String>> getTableAsList()
	{
		List<List<String>> tableContent =tableRows.stream().map(ele -> 
		{
			List<String> rowData = new ArrayList<>();
			List<WebElement> colVlaue = ele.findElements(By.xpath(".//td"));
			for(WebElement col:colVlaue)
			{
				rowData.add(col.getText());
			}
			return rowData;
		}).collect(Collectors.toList());
		
		
		
		return tableContent;
		
	}
	
	
	

}

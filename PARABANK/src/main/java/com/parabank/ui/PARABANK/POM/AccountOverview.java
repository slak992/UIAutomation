package com.parabank.ui.PARABANK.POM;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RuntimeEnvironment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static org.openqa.selenium.support.locators.RelativeLocator.with;

import com.parabank.ui.PARABANK.helpers.ReuseableComponents;

public class AccountOverview extends ReuseableComponents {
	private WebDriver driver;
	

	@FindBy(xpath = "//div[@id='accountDetails']//tr")
	private List<WebElement> acctDetails;

	@FindBy(id = "transactionTable")
	private WebElement transactionTable;

	@FindBy(xpath = "//table[@id='transactionTable']/thead/tr/th")
	private List<WebElement> ttableHeader;

	@FindBy(xpath = "//table[@id='transactionTable']/tbody/tr")
	private List<WebElement> ttableBody;

	@FindBy(xpath = "//table[@id='accountTable']/thead/tr/th")
	private List<WebElement> acctTableHeader;

	@FindBy(xpath = "//table[@id='accountTable']/tbody/tr")
	private List<WebElement> acctTableBody;
	
	@FindBy(id="accountTable")
	private WebElement acctTable;

	public void selectAccount(String acctNumber) {
		int index = IntStream.range(1, acctTableHeader.size()+1).filter(i -> acctTableHeader.get(i - 1).getText().equals("Account"))
				.findFirst().orElse(-1);

		acctTableBody.stream().map(ele -> ele.findElement(By.xpath("./td[" + index + "]/a")))
				.filter(ele -> ele.getText().equals(acctNumber)).findFirst()
				.orElseThrow(() -> new NoSuchElementException("Account number:" + acctNumber + " Not found")).click();
	}

	public Map<String, String> extractAccountDetails() {
		Map<String, String> acctDetailsMap;
		acctDetailsMap = acctDetails.stream()
				.collect(Collectors.toMap(ele -> ele.findElement(By.xpath("./td[1]")).getText().replace(":", ""), // Key:
																													// String
						ele -> ele.findElement(By.xpath("./td[2]")).getText(), // Value: String
						(existing, replacement) -> existing // Merge function (prevents errors on duplicate keys)
				));
		return acctDetailsMap;

	}

	public Map<String, String> extractTransactionDetails() {
		Map<String, String> acctDetailsMap = extractAccountDetails();
		int index = IntStream.range(1, ttableHeader.size())
				.filter(i -> ttableHeader.get(i - 1).getText().equals("Date")).findFirst().orElse(-1);

		LocalDate date = LocalDate.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		LocalDate yesterday = date.minusDays(1);
		String expectedDate = yesterday.format(format);
		
		String actualDate = ttableBody.stream().map(ele -> ele.findElement(By.xpath("./td[" + index + "]")).getText())
				.filter(text -> text.equals(expectedDate)).findFirst().orElse("None");
		acctDetailsMap.put("Date", actualDate);
		return acctDetailsMap;

	}
	
	
	public Map<String,String> extractDetailsFromAccountOverviewTable(String acctNumber)
	{
		Map<String,String> acctDetails = new LinkedHashMap<>();
		int acctNumIndex = IntStream.range(1, acctTableHeader.size()+1).filter(i -> acctTableHeader.get(i-1).getText().equals("Account")).findFirst().orElse(-1);
		int balIndex = IntStream.range(1, acctTableHeader.size()+1).filter(i -> acctTableHeader.get(i-1).getText().equals("Balance*")).findFirst().orElse(-1);
		int availableAmtIndex = IntStream.range(1, acctTableHeader.size()+1).filter(i -> acctTableHeader.get(i-1).getText().equals("Available Amount")).findFirst().orElse(-1);
		WebElement accountEle =acctTableBody.stream().filter(ele->ele.findElement(By.xpath("./td["+acctNumIndex+"]")).getText().equals(acctNumber)).findFirst().orElseThrow(() -> new RuntimeException("Account: "+acctNumber+" not found"));
		acctDetails.put("acctNumber", acctNumber);
		acctDetails.put("Balance", accountEle.findElement(By.xpath("./td["+balIndex+"]")).getText());
		acctDetails.put("availableAmt", accountEle.findElement(By.xpath("./td["+availableAmtIndex+"]")).getText());
		return acctDetails;
		
	}
	
	public List<List<String>> getTableAsList(String filterType,String filterData)
	{
		waitForElement(driver, transactionTable, 2, "visibility of element");
		int filterColIndex = IntStream.range(1,ttableBody.size()).filter(i -> ttableHeader.get(i-1).getText().equals(filterType) )
				.findFirst().orElse(-1);
		
		List<WebElement> filterTableData = ttableBody.stream().filter(e->e.findElement(By.xpath("./td["+filterColIndex+"]")).getText().equals(filterData))
		.collect(Collectors.toList());
		List<List<String>> tableData = new ArrayList<>();
		for(WebElement eachRow:filterTableData)
		{
			List<String> row = new ArrayList<>();
			List<WebElement> cols = eachRow.findElements(By.tagName("td"));
			for(WebElement col:cols)
			{
				row.add(col.getText());
			}
			tableData.add(row);
		}
		
		return tableData;
		
	}
}

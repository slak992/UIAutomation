package com.parabank.ui.base;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.parabank.ui.PARABANK.resources.ExtentReportManager;


public class TestListner  implements ITestListener{
	static String reportPath = System.getProperty("user.dir")+BaseTest.prop.getProperty("extentReportPath");
	private static ExtentReports extend = ExtentReportManager.getExtentReportInstance(reportPath, BaseTest.getKlovReportVersion(),BaseTest.prop.getProperty("klovUrl"));
	private static ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
	@Override
	public void onTestStart(ITestResult result)
	{
		ExtentTest test = extend.createTest(result.getMethod().getMethodName());
		testThread.set(test);
	}
	
	@Override
	public void onTestSuccess(ITestResult result)
	{
		testThread.get().log(Status.PASS, "Test Case: "+result.getMethod().getMethodName()+" is PASSED.");
	}
	
	@Override
	public void onTestFailure(ITestResult result)
	{
		WebDriver driver = null;
		testThread.get().log(Status.FAIL, "Test Case: "+result.getMethod().getMethodName()+" is FAILED.");
		testThread.get().fail(result.getThrowable());
		Object instance = result.getInstance();
		if(instance instanceof BaseTest)
		{
			driver = ((BaseTest) instance).getDriver();
		}
		if(driver == null)
		{
			testThread.get().warning("Driver instance null, screenshot skipped.");
			return;
		}
		try
		{
			
			String base64Screenshot= ((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
			testThread.get().addScreenCaptureFromBase64String(base64Screenshot,"Failed screenshot");

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@Override
	public void onTestSkipped(ITestResult result)
	{
		String skippedMsg = result.getThrowable() != null ? result.getThrowable().getMessage():"No Reason";
		testThread.get().log(Status.SKIP, "Test Case: "+result.getMethod().getMethodName()+" is SKIPPED. and the reason is: "+skippedMsg);
	}
	
	@Override
	public void onFinish(ITestContext context)
	{
		if(extend != null)
		{
			extend.flush();
		}
	}
	
	
	

}

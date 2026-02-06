package com.parabank.ui.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.input.ReaderInputStream;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.parabank.ui.PARABANK.helpers.ChromeDevToolProtocol;
import com.parabank.ui.PARABANK.helpers.ExecelHelper;
import com.parabank.ui.PARABANK.helpers.JsonReader;
import com.parabank.ui.PARABANK.helpers.ReuseableComponents;
import com.parabank.ui.PARABANK.helpers.SQLHelper;
import com.parabank.ui.PARABANK.resources.ExtentReportManager;

public class BaseTest{
	private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();
	protected static Properties prop;
	protected List<Map<String,String>> excelData ;
	protected SoftAssert softAssert;
	protected  static ExecelHelper excelHelperObj;
	protected static SQLHelper sqlObj;
	protected String jsonPath;
	protected JsonReader jsObj;
//	private static ThreadLocal<ExecelHelper> excelHelper = new ThreadLocal<>();
	protected ExtentReportManager exManager;
	protected ChromeDevToolProtocol cdpObject;
	String downloadFilePath;
	static
	{
		try {
			initProp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public BaseTest()
//	{
//		
//		threadLocalDriver = new ThreadLocal<>();
//		
//		
//	}
	@BeforeSuite(alwaysRun = true)
	public void globalSetup() throws IOException
	{
		if(excelHelperObj == null)
		{
			excelHelperObj = new ExecelHelper();
		}
		
		this.exManager = new ExtentReportManager();
		
		sqlObj = new SQLHelper();
		connectDB();
		
		cdpObject= new ChromeDevToolProtocol();
		this.jsObj = new JsonReader();
		this.jsonPath = System.getProperty("user.dir")+"\\src\\test\\java\\resources\\configurations\\"+prop.getProperty("jsonFileName");
		
	}
	
	@AfterSuite(alwaysRun = true)
	public void tearDownSetup() throws SQLException, IOException, InterruptedException
	{
		sqlObj.closeConnection();
		if(prop.getProperty("downloadKlovReportFlag").equals("true"))
		{
			WebDriver driver = new ChromeDriver(getWebDriverOptions(new ChromeOptions()));
			threadLocalDriver.set(driver);
			getDriver().manage().window().maximize();
			getDriver().get(prop.getProperty("klovUrl"));
			Thread.sleep(1000);
			cdpObject.savePageAsPDF(getDriver(), downloadFilePath, prop.getProperty("klovProjectName"));
			getDriver().close();
		}
		
	}


//	public ExecelHelper getExecelHelper()
//	{
//		if(excelHelper==null)
//		{
//			excelHelper.set(new ExecelHelper());
//		}
//		return excelHelper.get();
//	}
	public WebDriver getDriver()
	{
		return threadLocalDriver.get();
	}
	
	
	@BeforeMethod(alwaysRun = true)
	public void webDriverSetUp() throws IOException
	{
		this.softAssert = new SoftAssert();
		String browserFromCmd = System.getProperty("browser");
		String url = System.getProperty("url");
		if(url == null)
		{
			url =prop.getProperty("url");
		}
		
		if(browserFromCmd == null)
		{
			browserFromCmd=prop.getProperty("browser");
		}
		
		if(browserFromCmd.equals("chrome"))
		{
			WebDriver driver = new ChromeDriver(getWebDriverOptions(new ChromeOptions()));
			threadLocalDriver.set(driver);
			getDriver().manage().window().maximize();
			getDriver().get(url);
		}
		else if(browserFromCmd.equals("edge"))
		{
			WebDriver driver = new EdgeDriver(getWebDriverOptions(new EdgeOptions()));
			threadLocalDriver.set(driver);
			getDriver().manage().window().maximize();
			getDriver().get(url);
//			try
//			{
//				connectDB();
//			}
//			catch(Exception ex)
//			{
//				ex.printStackTrace();
//			}
//			
		}
		
		
	}
	
	public <T extends ChromiumOptions<T>> T getWebDriverOptions(T options)
	{
		downloadFilePath = System.getProperty("user.dir")+File.separator+"externalFiles"+File.separator+"downloads";
		File dir = new File(downloadFilePath);
		if(!dir.exists()) dir.mkdirs();
		for(File f:dir.listFiles())
		{
			if(!f.isDirectory())f.delete();
		}
		// 1. Remove the "Chrome is being controlled by automated software" flag
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);

		// 2. Disable the "blink" features that websites use to detect WebDriver
		options.addArguments("--disable-blink-features=AutomationControlled");

		// 3. Set a realistic User-Agent (important for bypassing CAPTCHAs)
//		options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36");
		// Disable password manager & security popups
		options.addArguments("--disable-notifications");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-popup-blocking");
//		options.addArguments("user-data-dir=C:\\Users\\Sreelaksmi\\AppData\\Local\\Google\\Chrome\\User Data");
//		options.addArguments("profile-directory=Default");
//		options.addArguments("--remote-allow-origins=*");
//		options.addArguments("--no-sandbox");
//		options.addArguments("--disable-dev-shm-usage");
//		options.addArguments("--start-maximized");
		if(System.getProperty("headless") != null)
		{
			options.addArguments("--headless=new");
		}
		

		// Disable password manager
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("download.default_directory", downloadFilePath);
		prefs.put("download.prompt_for_download", false);
		prefs.put("plugins.always_open_pdf_externally", true);
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.password_manager_leak_detection", false);
		prefs.put("protocol_handler.excluded_schemes.mailto", true); // Example for mail links
		prefs.put("profile.default_content_setting_values.notifications", 2);

		options.setExperimentalOption("prefs", prefs);
		return options;
	}
	
	@AfterMethod(alwaysRun = true)
	public void tearDown()
	{
		try
		{
			if(softAssert != null)
			{
				softAssert.assertAll();
			}
		}
		finally
		{
			if(getDriver() != null)
			{
				getDriver().quit();
				threadLocalDriver.remove();
			}
		}
		
		
	}
	
	public <T> T createPage(Class<T> classObj)
	{
		try
		{
			T page = classObj.getDeclaredConstructor().newInstance();
			
			try
			{
				if(getDriver() == null)
				{
					throw new IllegalStateException("Driver is null. @BeforeMethod didn't run.");
				}
				java.lang.reflect.Field driverField = classObj.getDeclaredField("driver");
				driverField.setAccessible(true);
				driverField.set(page, getDriver());
			}
			catch(NoSuchFieldException e)
			{
				throw new RuntimeException("Field missing ",e);
			}
			PageFactory.initElements(getDriver(), page);
			return page;
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error while initiating class "+classObj.getSimpleName(),e);
		}
	}
	public static void initProp() throws IOException
	{
		String propertyFilePath = System.getProperty("user.dir")+"\\src\\test\\java\\resources\\configurations\\config.properties";
		prop = new Properties();
		FileInputStream propFile = new FileInputStream(new File(propertyFilePath));
		prop.load(propFile);
	}
	
	public Map<String,String> getTestData(String testName) throws IOException
	{
		excelData = this.excelHelperObj.readExcelData(System.getProperty("user.dir") + prop.getProperty("excelFilePath"), "UI");
		for(Map<String,String> eachData:excelData)
		{
			if(eachData.containsValue(testName))
			{
				return eachData;
			}
		}
		return new LinkedHashMap<>();
	}
	
	public void updateExcelFileDetails(Map<String,String> data) throws NumberFormatException, IOException
	{
		this.excelHelperObj.updateExcelData(data, System.getProperty("user.dir") + prop.getProperty("excelFilePath"), "UI",Integer.parseInt(data.get("#")));
	}
	
	public static String getKlovReportVersion()
	{
		Double newVersion = Double.parseDouble(prop.getProperty("klovVersion"))+0.01;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM_dd_yyyy_hh_mm_ss_a");
		String versionName = "Build_V"+String.valueOf(newVersion)+"_"+formatter.format(LocalDateTime.now());
		System.out.println(versionName);
		return versionName;
	}
	public void connectDB()
	{
		String dbPath = System.getProperty("user.dir")+"/src/test/java/resources/DB/"+prop.getProperty("dbName");
		sqlObj.getConnection(prop.getProperty("dbDriverName"), dbPath);
	}
	
	
	

}

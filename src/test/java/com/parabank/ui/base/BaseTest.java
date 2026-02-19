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
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chromium.ChromiumOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
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
import com.parabank.ui.PARABANK.POM.Login;


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
		preconditionCheck();
		
		cdpObject= new ChromeDevToolProtocol();
		this.jsObj = new JsonReader();
		this.jsonPath = System.getProperty("user.dir")+"/src/test/java/resources/configurations/"+prop.getProperty("jsonFileName");
		
	}
	
	@AfterSuite(alwaysRun = true)
	public void tearDownSetup() throws SQLException, IOException, InterruptedException
	{
		sqlObj.closeConnection();

		// Check if running in CI/CD environment (Azure DevOps)
		boolean isCIEnvironment = System.getenv("SYSTEM_TEAMFOUNDATIONCOLLECTIONURI") != null ||
		                          System.getenv("BUILD_REPOSITORY_URI") != null ||
		                          System.getenv("TF_BUILD") != null ||
		                          System.getenv("CI") != null;

		if(isCIEnvironment)
		{
			System.out.println("⚠️  Running in Azure DevOps Pipeline - Skipping Klov report generation");
			System.out.println("   TestNG reports will be published to Azure pipeline artifacts");
			System.out.println("   Klov reporting is available only in local execution");
		}
		else if(prop.getProperty("downloadKlovReportFlag").equals("true"))
		{
			try
			{
				generateKlovReport();
			}
			catch(Exception e)
			{
				System.out.println("⚠️  Failed to generate Klov report: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void generateKlovReport() throws IOException, InterruptedException
	{
		WebDriver driver = new ChromeDriver(getWebDriverOptions(new ChromeOptions()));
		threadLocalDriver.set(driver);
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("klovUrl"));

		// Use WebDriverWait instead of Thread.sleep() for better reliability
		try
		{
			WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

			cdpObject.savePageAsPDF(getDriver(), downloadFilePath, prop.getProperty("klovProjectName"));
			System.out.println("✓ Klov report generated successfully");
		}
		finally
		{
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

		// Detect if running in CI/CD environment (Azure DevOps)
		boolean isCIEnvironment = System.getenv("SYSTEM_TEAMFOUNDATIONCOLLECTIONURI") != null ||
		                          System.getenv("BUILD_REPOSITORY_URI") != null ||
		                          System.getenv("CI") != null ||
		                          System.getenv("TF_BUILD") != null ||
		                          System.getProperty("headless") != null;

		// 1. Remove the "Chrome is being controlled by automated software" flag
		options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);

		// 2. Disable the "blink" features that websites use to detect WebDriver
		options.addArguments("--disable-blink-features=AutomationControlled");

		// 3. Set a realistic User-Agent (important for bypassing CAPTCHAs)
		// options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/144.0.0.0 Safari/537.36");

		// 4. Disable password manager & security popups
		options.addArguments("--disable-notifications");
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-popup-blocking");

		// 5. Azure DevOps / CI/CD Pipeline Specific Options
		if(isCIEnvironment)
		{
			// Headless mode for CI/CD environments
			options.addArguments("--headless=new");

			// Disable GPU acceleration (important for CI/CD environments without GPU)
			options.addArguments("--disable-gpu");

			// Run in sandbox with restrictions for security
			options.addArguments("--no-sandbox");

			// Disable shared memory usage (prevents crashes in Docker/CI environments)
			options.addArguments("--disable-dev-shm-usage");

			// Additional CI/CD stability options
			options.addArguments("--disable-software-rasterizer");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-plugins");

			// Disable GPU compositing
			options.addArguments("--disable-gpu-compositing");

			// Reduce memory footprint
			options.addArguments("--disable-background-networking");
			options.addArguments("--disable-component-extensions-with-background-pages");
			options.addArguments("--disable-default-apps");
			options.addArguments("--disable-sync");

			// Set window size (important when running without display)
			options.addArguments("--window-size=1920,1080");

			System.out.println("🔵 Running in CI/CD Environment (Azure DevOps) - Headless Mode Enabled");
		}
		else
		{
			// Local development/debugging options
			// Uncomment below to maximize window in local execution
			// options.addArguments("--start-maximized");
			System.out.println("🟢 Running in Local Environment - Standard Mode");
		}

		// 6. Download preferences
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
		String propertyFilePath = System.getProperty("user.dir")+"/src/test/java/resources/configurations/config.properties";
		prop = new Properties();
		FileInputStream propFile = new FileInputStream(new File(propertyFilePath));
		prop.load(propFile);
	}
	
	public Map<String,String> getTestData(String testName) throws IOException
	{
		excelData = this.excelHelperObj.readExcelData(System.getProperty("user.dir") + prop.getProperty("excelFilePath"), "UI");
		for(Map<String,String> eachData:excelData)
		{
			System.out.println(eachData.get("testName"));
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

	public void preconditionCheck()  throws IOException
	{
		WebDriver preconditionDriver = new ChromeDriver(getWebDriverOptions(new ChromeOptions()));
		threadLocalDriver.set(preconditionDriver);
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		try
		{
			Login loginObj = createPage(Login.class);
			Map<String,String> testData = getTestData("POS_DB_CleanAndInitiate");

			loginObj.loginUser(testData.get("userName"), testData.get("password"));

			if(loginObj.isUserNotRegisterErrorMsg())
			{
				loginObj.registerUser(testData);
			}

			System.out.println("✓ Precondition Passed: User '" + testData.get("userName") + "' can login successfully");
		}
		finally
		{
			// Clear the precondition driver
			if(getDriver() != null)
			{
				getDriver().quit();
				threadLocalDriver.remove();
			}
		}


	}
	
	
	

}

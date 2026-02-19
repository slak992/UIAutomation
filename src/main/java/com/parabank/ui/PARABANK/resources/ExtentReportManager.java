package com.parabank.ui.PARABANK.resources;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentKlovReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
	public static ExtentReports extend;
	public  static ExtentReports getExtentReportInstance(String htmlReportPath,String buildVersion, String klovUrl)
	{
		if(extend == null)
		{
			ExtentSparkReporter report = new ExtentSparkReporter(htmlReportPath);
			report.config().setTheme(Theme.DARK);
			report.config().setDocumentTitle("PARABANK - UI Automation Report");
			report.config().setReportName("UI Automation");
			report.config().setTimeStampFormat("EEE,MMM,dd,yyyy hh:mm a '('zzz')'");
			report.config().setEncoding("utf-8");
			
			extend = new ExtentReports();
			extend.attachReporter(report);
			
			extend.setSystemInfo("Organization", "PARABANK");
			extend.setSystemInfo("Project", "Payment Systems");
			extend.setSystemInfo("Team", "UI Automation");
			boolean isCI = "true".equalsIgnoreCase(System.getenv("CI"));
			if(!isCI)
			{
				ExtentKlovReporter  klov = new ExtentKlovReporter("ParaBank-UI", buildVersion);

				// Connect to Klov (Docker maps port 80 to 8080 internally)
				klov.initKlovServerConnection(klovUrl);

				// Connect to MongoDB
				klov.initMongoDbConnection("localhost", 27017);

				extend.attachReporter(klov);
			}
			else {
				System.out.println("🔵 CI/CD run - Skipping MongoDB initialization");
			}
			

		}
		
		return extend;
		}
	
	

}

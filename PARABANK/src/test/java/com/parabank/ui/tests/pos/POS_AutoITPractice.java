package com.parabank.ui.tests.pos;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.parabank.ui.PARABANK.POM.AutoITPractice;
import com.parabank.ui.base.BaseTest;

public class POS_AutoITPractice extends BaseTest{
	@Test(enabled=false)
	public void uploadExtendReport() throws IOException, InterruptedException
	{
		getDriver().get("https://the-internet.herokuapp.com/upload");
		AutoITPractice autoITPOM = createPage(AutoITPractice.class);
		String reportFilePath = System.getProperty("user.dir")+File.separator+"test-output"+File.separator+"ExtendReport"
		+File.separator+"ExtentReport.html";
		String uploadStatus = autoITPOM.uploadFile(reportFilePath);
		Assert.assertEquals(uploadStatus, "File Uploaded!");
		
	}

}

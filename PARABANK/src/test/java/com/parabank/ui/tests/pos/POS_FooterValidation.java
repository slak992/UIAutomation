package com.parabank.ui.tests.pos;

import java.io.IOException;
import java.util.Map;

import org.testng.annotations.Test;

import com.parabank.ui.PARABANK.POM.FooterProducts;
import com.parabank.ui.PARABANK.POM.FooterValidations;
import com.parabank.ui.PARABANK.exceptions.DataException;
import com.parabank.ui.base.BaseTest;

public class POS_FooterValidation extends BaseTest{
	@Test
	public void testQuickLinkInsideProduct() throws IOException, DataException, InterruptedException
	{
		Map<String,String> testData = getTestData("testQuickLinkInsideProduct");
		if(testData.size()!=0)
		{
			
			FooterValidations footerObj = createPage(FooterValidations.class);
			FooterProducts footerProdObj = createPage(FooterProducts.class);
			footerObj.checkTheStatusOfQuickLinksInProductPage(testData.get("footerValidationItem"));
			footerProdObj.checkQuickLinkStatusCode();
		}
		else
		{
			throw new DataException("ERROR: Test Data not found!!!");
		}
	}

}

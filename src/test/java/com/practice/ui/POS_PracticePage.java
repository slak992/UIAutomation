package com.practice.ui;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.parabank.ui.base.BaseTest;
import com.practice.ui.POM.AutomationPracticePage;

public class POS_PracticePage extends BaseTest{
	@Test
	public void verifyCheckBox()
	{
		AutomationPracticePage automationPgObj = createPage(AutomationPracticePage.class);
		assertTrue(automationPgObj.verifyCheckBox());
		assertEquals(automationPgObj.getNumberOfCheckBoxes(),3);
		
	}
	

}

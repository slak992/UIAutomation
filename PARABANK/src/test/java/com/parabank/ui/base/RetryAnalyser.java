package com.parabank.ui.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

public class RetryAnalyser implements IRetryAnalyzer{
	private int count = 0;
	private static int MAXTRY=2;
	@Override
	public boolean retry(ITestResult result) {
		if(count<MAXTRY)
		{
			count++;
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}
	
}

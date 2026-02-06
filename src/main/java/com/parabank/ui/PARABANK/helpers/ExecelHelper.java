package com.parabank.ui.PARABANK.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExecelHelper {
	 DataFormatter formatter = new DataFormatter();
	 
	 

	public List<Map<String, String>> readExcelData(String excelFilePath, String sheetName) throws IOException {
		FileInputStream fisExcel = new FileInputStream(excelFilePath);

		XSSFWorkbook workbook = new XSSFWorkbook(fisExcel);
		XSSFSheet sheet = workbook.getSheet(sheetName);
		int totRowNum = sheet.getLastRowNum();
		Row header = sheet.getRow(0);
		
		int totColNum = header.getLastCellNum();
		List<Map<String, String>> testData = new ArrayList<>();
		
		FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		for (int rows = 1; rows <= totRowNum; rows++) {
			Map<String, String> testCaseData = new LinkedHashMap<>();
			Row currRow = sheet.getRow(rows);
			for (int cols = 0; cols < totColNum; cols++) {
				testCaseData.put(header.getCell(cols).getStringCellValue(),
						Optional.ofNullable(currRow.getCell(cols)).map(
								c ->{
									evaluator.evaluateInCell(c);
									return formatter.formatCellValue(c);
								}).orElse(""));

			}
			testData.add(testCaseData);
		}
		
		return testData;

	}
	
	public void updateExcelData(Map<String,String> data, String excelFilePath, String sheetName, int rowNum) throws IOException
	{
		FileInputStream fisExcel = new FileInputStream(excelFilePath);
		XSSFWorkbook workbook = new XSSFWorkbook(fisExcel);
		XSSFSheet sheet = workbook.getSheet(sheetName);
//		int totRowNum = sheet.getLastRowNum();
		Row header = sheet.getRow(0);
		FormulaEvaluator Setevaluator = workbook.getCreationHelper().createFormulaEvaluator();
		
		Row targetRow = sheet.getRow(rowNum);
		for(String key:data.keySet())
		{
			if(key.equals("createdAcct"))
			{
				System.out.println("Break");
			}
			System.out.println(key);
			Integer headerColIndex =IntStream.range(0, header.getLastCellNum()).filter(i -> formatter.formatCellValue(header.getCell(i)).equals(key)).findFirst().orElse(-1);
			Setevaluator.evaluateInCell(targetRow.getCell(headerColIndex));
			String targetCellVal = formatter.formatCellValue(targetRow.getCell(headerColIndex));
			System.out.println(targetCellVal);
			if(!data.get(key).isEmpty())
			{
				targetRow.getCell(headerColIndex).setCellValue(data.get(key));
			}
			
//			for(Cell cell:header)
//			{
//				String headerCellVal = formatter.formatCellValue(cell);
//				if(headerCellVal != null && headerCellVal.equals(key))
//				{
//					int colIndex = cell.getColumnIndex();
//					targetRow.getCell(colIndex).setCellValue(data.get(key));
//					break;
//				}
//			}
		}
		FileOutputStream fos = new FileOutputStream(excelFilePath);
		workbook.write(fos);
		workbook.close();
		
	}

	

	

}

package com.parabank.ui.PARABANK.helpers;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

public class JsonReader {
	
	public Map<String,Object> readJsonData(String jsonPath) throws StreamReadException, DatabindException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		File jsonFile = new File(jsonPath);
		Map<String,Object> jsonMapData = mapper.readValue(jsonFile,new TypeReference<Map<String,Object>>() {});
		return jsonMapData;
	}
	public Map<String, Object> queryJsonData(String filePath, String query) throws IOException
	{
		File jsonFile = new File(filePath);
		return JsonPath.read(jsonFile, query);
	}
	
	public static void main(String[] args) throws StreamReadException, DatabindException, IOException
	{
		JsonReader jsObj = new JsonReader();
		String jsonPath = System.getProperty("user.dir")+"\\src\\test\\java\\resources\\configurations\\cdpSpec.json";
		Map<String,Object> jsonMapData = jsObj.readJsonData(jsonPath);
//		System.out.println(jsonMapData.get("simulateIPhoneCDP"));
		String query = "$.simulateIPhoneCDP['iPhone SE']";
//		String query ="test[1]";
		Map<String,Object> params = jsObj.queryJsonData(jsonPath,query);
		System.out.println(params.get("Width"));
		
	}

}

package com.parabank.ui.PARABANK.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v141.emulation.Emulation;
import org.openqa.selenium.devtools.v141.page.Page;
import org.openqa.selenium.devtools.v142.network.model.ConnectionType;
import org.openqa.selenium.devtools.v143.fetch.Fetch;
import org.openqa.selenium.devtools.v143.fetch.Fetch.GetResponseBodyResponse;
import org.openqa.selenium.devtools.v143.network.model.Response;
import org.openqa.selenium.devtools.v143.fetch.model.HeaderEntry;
import org.openqa.selenium.devtools.v143.fetch.model.RequestPattern;
import org.openqa.selenium.devtools.v143.fetch.model.RequestStage;
import org.openqa.selenium.devtools.v143.log.Log;
import org.openqa.selenium.devtools.v143.network.Network;
import org.openqa.selenium.devtools.v143.network.model.BlockPattern;
import org.openqa.selenium.devtools.v143.network.model.ErrorReason;
import org.openqa.selenium.devtools.v143.network.model.NetworkConditions;
import org.openqa.selenium.devtools.v143.network.model.Request;





public class ChromeDevToolProtocol extends ReuseableComponents{
	
	
	public void savePageAsPDF(WebDriver driver,String pdfDirPath, String projectName) throws IOException, InterruptedException
	{
		driver.manage().window().maximize();
		waitForPageLoad(driver,5000);
		driver.findElement(By.xpath("//a[contains(text(),'"+projectName+"')]")).click();
		waitForPageLoad(driver,10000);
		Thread.sleep(5000);
		DevTools devtool = ((ChromeDriver)driver).getDevTools();
		devtool.createSession();
		devtool.send(Page.enable(Optional.empty()));
		Page.PrintToPDFResponse pdf= devtool.send(Page.printToPDF(
				Optional.of(false),  // landscape
                Optional.of(false),  // displayHeaderFooter
                Optional.of(true),   // printBackground
                Optional.of(1.0),    // scale
                Optional.empty(),    // paperWidth (in inches)
                Optional.empty(),    // paperHeight (in inches)
                Optional.empty(),    // marginTop
                Optional.empty(),    // marginBottom
                Optional.empty(),    // marginLeft
                Optional.empty(),    // marginRight
                Optional.of("1"),    // pageRanges -> ONLY page 1 ✅
                Optional.empty(),    // ignoreInvalidPageRanges
                Optional.empty(),    // headerTemplate
                Optional.empty(),    // footerTemplate
                Optional.empty(),    // preferCSSPageSize
                Optional.empty(),    // transferMode
                Optional.empty()     // generateTaggedPDF
                ));
		byte[] reportPDF = Base64.getDecoder().decode(pdf.getData());
		Path pdfPath = Paths.get(pdfDirPath, "performance_report.pdf");
		Files.write(pdfPath, reportPDF);
		devtool.close();
		
	}
	
	public void simulateIPhoneWithCDP(ChromeDriver driver,Map<String,Object> params) {
	    DevTools devTools = driver.getDevTools();
	    devTools.createSession();
	    

	    // Settings for iPhone 12 Pro
	    devTools.send(Emulation.setDeviceMetricsOverride(
	    	((Number) params.get("Width")).intValue(),        // width
	    	((Number) params.get("Height")).intValue(),
	    	((Number) params.get("Scale Factor")).intValue(),
	        true,       // mobile (boolean)
	        java.util.Optional.empty(), // scale
	        java.util.Optional.empty(), // screenWidth
	        java.util.Optional.empty(), // screenHeight
	        java.util.Optional.empty(), // screenOrientation
	        java.util.Optional.empty(), // viewport
	        java.util.Optional.empty(), // displayFeature
	        java.util.Optional.empty(), // devicePosture
	        java.util.Optional.empty()  // userAgent
, java.util.Optional.empty(), java.util.Optional.empty()
	    ));
	}
	public void setGeolocationOverrideFunction(ChromeDriver driver,Map<String,Object> params)
	{
		DevTools devTools = driver.getDevTools();
	    devTools.createSession();
	    
	    devTools.send(Emulation.setGeolocationOverride(Optional.of(((Number)params.get("latitude")).doubleValue()), 
	    		Optional.of(((Number)params.get("longitude")).doubleValue()), 
	    		Optional.of(((Number)params.get("accuracy")).intValue()), Optional.empty(), 
	    		Optional.empty(), Optional.empty(), Optional.empty()));
	}
	
	public void tweekResponseCode(ChromeDriver driver)
	{
		DevTools devTools = driver.getDevTools();
	    devTools.createSession();
//	    String targetUrl = "http://localhost:8080/parabank/services_proxy/bank/accounts/13566/transactions/month/All/type/All";
	    String targetUrl = "13566/transactions/month/All";
	    
//	    devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
	    
	    devTools.send(Fetch.enable(
	            Optional.of(List.of(new RequestPattern(
	                Optional.of("*13566/transactions/month/All*"), // Filter for your URL
	                Optional.empty(), 
	                Optional.of(RequestStage.RESPONSE) // CRITICAL: Only pause when response is ready
	            ))), 
	            Optional.of(false)
	        ));
	    devTools.addListener(Fetch.requestPaused(), request -> {
	    	String url = request.getRequest().getUrl();
	    	if(url.contains(targetUrl))
	    	{
	    		GetResponseBodyResponse resBody = devTools.send(
	    			    Fetch.getResponseBody(request.getRequestId())
	    			);

	    			String responseBody = resBody.getBase64Encoded()
	    			        ? new String(Base64.getDecoder().decode(resBody.getBody()), StandardCharsets.UTF_8)
	    			        : resBody.getBody();
	    			List<HeaderEntry> headers = List.of(
	    				    new HeaderEntry("Content-Type", "application/json")
	    				);
	    			String modifiedBody = responseBody.replaceFirst("\"Debit\"", "\"Credit\"");

	    			String encodedBody = Base64.getEncoder()
	    			        .encodeToString(modifiedBody.getBytes(StandardCharsets.UTF_8));

	    			devTools.send(Fetch.fulfillRequest(
	    			        request.getRequestId(),
	    			        200,
	    			        Optional.of(headers),      // ✅ headers added
	    			        Optional.empty(),
	    			        Optional.of(encodedBody),
	    			        Optional.of("OK")
	    			));



//	    		GetResponseBodyResponse resBody = devTools.send(Fetch.getResponseBody(request.getRequestId()));
//	    		String body = resBody.getBody();
//	    		byte[] bosyBytes = Base64.getDecoder().decode(body);
//	    		String actualString = new String(bosyBytes,StandardCharsets.UTF_8);
//	    		System.out.println(actualString);
////	    		String newResBody = actualString.replaceFirst("\"type\":\\s*\"Debit\"", "\"type\":\"Credit\"");
//	    		String newResBody = actualString.replaceFirst("\"amount\":100.00", "\"amount\":200.00");
////	    		String newResBody = body.replaceFirst("\"Debit\"", "\"Credit\"");
//	    		System.out.println(newResBody);
//	    		String finalString = Base64.getEncoder().encodeToString(newResBody.getBytes(StandardCharsets.UTF_8));
//	    		devTools.send(Fetch.fulfillRequest(request.getRequestId(), 200, Optional.empty(), Optional.empty(), 
//	    				Optional.of(finalString), Optional.of("OK")));
	    	}
	    	else
	    	{
	    		devTools.send(Fetch.continueRequest(request.getRequestId(),
	    				Optional.empty(), Optional.empty(), Optional.empty(), 
	    				Optional.empty(), Optional.empty()));
	    	}
	    });
	}
	
	
	
	public void alterRequestUrl(ChromeDriver driver) {
	    DevTools devTools = driver.getDevTools();
	    devTools.createSession();

	    // Optional but helpful
	    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), java.util.Optional.empty(), java.util.Optional.empty()));
	    devTools.send(Network.setCacheDisabled(true));

	    devTools.send(Fetch.enable(
	            Optional.of(List.of(new RequestPattern(
	                    Optional.of("*://*/*/services_proxy/bank/accounts/13566/transactions*"),
	                    Optional.empty(),
	                    Optional.of(RequestStage.REQUEST)
	            ))),
	            Optional.of(false)
	    ));

	    devTools.addListener(Fetch.requestPaused(), event -> {
	        var req = event.getRequest();
	        String originalUrl = req.getUrl();

	        if (originalUrl.contains("/services_proxy/bank/accounts/13566/transactions")) {

	            // Convert existing headers (keeps cookies/auth/etc.)
	            List<HeaderEntry> headers = req.getHeaders().entrySet().stream()
	                    .map(e -> new HeaderEntry(e.getKey(), String.valueOf(e.getValue())))
	                    .collect(Collectors.toCollection(ArrayList::new));

	            // If you really need Content-Type, override/add it (don’t wipe others)
	            headers.removeIf(h -> h.getName().equalsIgnoreCase("Content-Type"));
	            headers.add(new HeaderEntry("Content-Type", "application/json"));
	            headers.removeIf(h -> h.getName().equalsIgnoreCase("Accept"));
	            headers.add(new HeaderEntry("Accept", "application/json"));

	            // IMPORTANT: keep the same base context if your app uses /parabank/
//	            String newUrl = "http://localhost:8080/parabank/services/bank/transactions/14476";
	            String newUrl = "http://localhost:8080/parabank/services_proxy/bank/accounts/13677/transactions/fromDate/01-01-2026/toDate/01-31-2026?timeout=30000";

	            devTools.send(Fetch.continueRequest(
	                    event.getRequestId(),
	                    Optional.of(newUrl),
	                    Optional.of(req.getMethod()),         // keep method
	                    Optional.ofNullable(req.getPostData().orElse(null)), // keep body if any
	                    Optional.of(headers),
	                    Optional.empty()
	            ));
	        } else {
	            devTools.send(Fetch.continueRequest(
	                    event.getRequestId(),
	                    Optional.empty(),
	                    Optional.empty(),
	                    Optional.empty(),
	                    Optional.empty(),
	                    Optional.empty()
	            ));
	        }
	    });
	}
	
	public void logEntries(ChromeDriver driver)
	{
		DevTools devtool = driver.getDevTools();
		devtool.createSession();
		devtool.send(Log.enable());
		devtool.addListener(Log.entryAdded(), entry -> {
			System.out.println("=====Browser Log======");
			System.out.println("Level : "+entry.getLevel());
			System.out.println("Text  : "+entry.getText());
			System.out.println("Source: "+entry.getSource());
			System.out.println("url   : "+entry.getUrl().orElse(" "));
			System.out.println("==========================");
		});
	}
	
	public void getFailedRequestDetails(ChromeDriver driver)
	{
		DevTools devTool = driver.getDevTools();
		devTool.createSession();
		devTool.send(Fetch.enable(
	            Optional.of(List.of(new RequestPattern(
	                    Optional.of("*://*/*/services_proxy/bank/accounts/13566/transactions*"),
	                    Optional.empty(),
	                    Optional.of(RequestStage.REQUEST)
	            ))),
	            Optional.of(false)
	    ));
//        List<RequestPattern> params = Arrays.asList(new RequestPattern(java.util.Optional.of("*transactions*"), java.util.Optional.empty(), java.util.Optional.empty()));
        
//		devTool.send(Fetch.enable(Optional.of(params), Optional.empty()));
		devTool.addListener(Fetch.requestPaused(), event ->
		{
			devTool.send(Fetch.failRequest(event.getRequestId(), ErrorReason.FAILED));
		});
	}
	
	public void blockURL(ChromeDriver driver)
	{
		DevTools devTool = driver.getDevTools();
		devTool.createSession();
		devTool.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
		// block requests
		devTool.send(Network.setBlockedURLs(
			    Optional.of(List.of(
			    		new BlockPattern("http://localhost:8080/parabank/*.gif", true),
			    		new BlockPattern("http://localhost:8080/parabank/*.png", true),
			    		new BlockPattern("http://localhost:8080/parabank/*.jpg", true)
			    )), java.util.Optional.empty()
			));
	}
	
	public void emulateNetworkSpeed(ChromeDriver driver)
	{
		DevTools devTool = driver.getDevTools();
		devTool.createSession();
		devTool.send(Network.enable(Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()));
		
		NetworkConditions netCondition = new NetworkConditions("", (Number)2000,(Number)30000, (Number)40000, 
				java.util.Optional.of(org.openqa.selenium.devtools.v143.network.model.ConnectionType.CELLULAR3G), java.util.Optional.empty(), java.util.Optional.empty(), java.util.Optional.empty());
		
		devTool.send(Network.emulateNetworkConditionsByRule(false, List.of(netCondition)));
	}
	

}

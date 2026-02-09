# Klov Reporting Issues in Azure DevOps Pipeline - Analysis & Solutions

## 🔴 Problems Identified

### 1. **Network Accessibility Issue**
**Problem**: Klov server (`http://localhost:8082`) is running on your **local machine**, but Azure DevOps pipeline runs on **cloud-hosted agents** (Ubuntu VMs).

```java
// From BaseTest.java (line 89)
getDriver().get(prop.getProperty("klovUrl"));  // http://localhost:8082
```

**Why it fails**: The Azure DevOps agent cannot reach `localhost:8082` because:
- The Klov server is only accessible on your local machine's network
- Azure agents are isolated cloud instances with no access to your local network
- There's no network bridge or VPN connection between Azure and your local machine

### 2. **PDF Download Issue in Headless Mode**
**Problem**: The code tries to save a PDF using Chrome DevTools Protocol in headless mode:

```java
// From BaseTest.java (line 89-91)
if(prop.getProperty("downloadKlovReportFlag").equals("true")) {
    WebDriver driver = new ChromeDriver(getWebDriverOptions(new ChromeOptions()));
    cdpObject.savePageAsPDF(getDriver(), downloadFilePath, prop.getProperty("klovProjectName"));
}
```

**Why it fails in Azure**:
- Headless Chrome has limited PDF rendering capabilities
- No display server (Xvfb) to handle UI rendering
- CDP (Chrome DevTools Protocol) operations may timeout in CI/CD environments

### 3. **Thread.sleep(1000) - Not Production Ready**
```java
Thread.sleep(1000);  // Hard-coded wait - unpredictable in pipeline
```
This can cause flaky tests in slow environments.

---

## ✅ Solutions

### **Solution 1: Conditional Klov Reporting (RECOMMENDED)**

Disable Klov reporting in Azure pipeline and use TestNG/JUnit reports instead:

#### Step 1: Update `config.properties`

```ini
# Add a new property to control Klov reporting
downloadKlovReportFlag=false  # Set to 'false' for Azure pipeline
```

#### Step 2: Update `BaseTest.java` - Make Klov Optional

```java
@AfterSuite(alwaysRun = true)
public void tearDownSetup() throws SQLException, IOException, InterruptedException {
    sqlObj.closeConnection();
    
    // Check if running in CI/CD environment
    boolean isCIEnvironment = System.getenv("SYSTEM_TEAMFOUNDATIONCOLLECTIONURI") != null ||
                              System.getenv("BUILD_REPOSITORY_URI") != null ||
                              System.getenv("TF_BUILD") != null;
    
    // Only download Klov report if not in CI/CD and flag is enabled
    if(!isCIEnvironment && prop.getProperty("downloadKlovReportFlag").equals("true")) {
        uploadKlovReportToPipeline();
    } else if(isCIEnvironment) {
        System.out.println("⚠️  Skipping Klov report generation in CI/CD environment");
    }
}

private void uploadKlovReportToPipeline() throws IOException, InterruptedException {
    WebDriver driver = new ChromeDriver(getWebDriverOptions(new ChromeOptions()));
    threadLocalDriver.set(driver);
    getDriver().manage().window().maximize();
    getDriver().get(prop.getProperty("klovUrl"));
    
    // Use WebDriverWait instead of Thread.sleep
    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    
    cdpObject.savePageAsPDF(getDriver(), downloadFilePath, prop.getProperty("klovProjectName"));
    getDriver().close();
}
```

---

### **Solution 2: Deploy Klov Server in Azure (Docker Container)**

If you want Klov reports in Azure pipeline:

#### Step 1: Create `docker-compose.yml` for Klov in Azure

```yaml
version: '3.8'
services:
  klov:
    image: extent/klov:latest
    container_name: klov-server
    ports:
      - "8082:80"
    environment:
      - JAVA_OPTS=-Xmx1024m
    networks:
      - parabank-network
      
  parabank-app:
    image: parabank:latest
    container_name: parabank-app
    ports:
      - "8080:8080"
    networks:
      - parabank-network
    depends_on:
      - klov

networks:
  parabank-network:
    driver: bridge
```

#### Step 2: Update Azure Pipeline to Use Docker

```yaml
stages:
  - stage: Build_and_Test_with_Services
    displayName: 'Build, Start Services, and Run Tests'
    jobs:
      - job: Run_Tests_with_Services
        displayName: 'Run Tests with Klov'
        
        steps:
          # Start Docker services
          - script: |
              docker-compose -f docker-compose.yml up -d
              sleep 20  # Wait for services to start
              docker-compose -f docker-compose.yml ps
            displayName: 'Start Docker Services (Klov + ParaBank)'
          
          # Run tests
          - task: Maven@3
            inputs:
              goals: 'test'
              arguments: |
                -DsuiteXmlFile=testng.xml
                -Dheadless=true
                -DklovUrl=http://localhost:8082
            displayName: 'Run Tests'
          
          # Publish Klov reports
          - task: PublishBuildArtifacts@1
            inputs:
              pathToPublish: '$(System.DefaultWorkingDirectory)/test-output'
              artifactName: 'klov-reports-$(Build.BuildId)'
            condition: always()
          
          # Cleanup
          - script: docker-compose -f docker-compose.yml down
            condition: always()
            displayName: 'Cleanup Docker Services'
```

---

### **Solution 3: Parallel Reporting (Best Practice)**

Run both Klov (local) and TestNG (for Azure) simultaneously:

#### Update `BaseTest.java`

```java
@AfterSuite(alwaysRun = true)
public void tearDownSetup() throws SQLException, IOException, InterruptedException {
    sqlObj.closeConnection();
    
    boolean isCIEnvironment = System.getenv("TF_BUILD") != null;
    
    if(isCIEnvironment) {
        System.out.println("📊 Azure Pipeline Detected - TestNG reports will be published automatically");
        System.out.println("   Klov reporting skipped (available only in local execution)");
    } else {
        // Local execution - generate Klov reports
        if(prop.getProperty("downloadKlovReportFlag").equals("true")) {
            generateKlovReport();
        }
    }
}

private void generateKlovReport() throws IOException, InterruptedException {
    try {
        WebDriver driver = new ChromeDriver(getWebDriverOptions(new ChromeOptions()));
        threadLocalDriver.set(driver);
        getDriver().manage().window().maximize();
        getDriver().get(prop.getProperty("klovUrl"));
        
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        
        cdpObject.savePageAsPDF(getDriver(), downloadFilePath, prop.getProperty("klovProjectName"));
        System.out.println("✓ Klov report generated successfully");
    } catch(Exception e) {
        System.out.println("⚠️  Failed to generate Klov report: " + e.getMessage());
    } finally {
        if(getDriver() != null) {
            getDriver().quit();
            threadLocalDriver.remove();
        }
    }
}
```

---

## 📋 Implementation Checklist

### For Local Development (Keep using Klov)
- [ ] Keep `downloadKlovReportFlag=true` in local config
- [ ] Ensure Klov server is running: `docker run -it -p 8082:80 extent/klov:latest`

### For Azure Pipeline (Solution 1 - Recommended)
- [ ] Create a separate config for Azure: `config-azure.properties`
- [ ] Set `downloadKlovReportFlag=false` in Azure config
- [ ] Update pipeline to use: `-Dconfig=azure`
- [ ] Verify TestNG reports publish correctly

### For Azure Pipeline with Klov (Solution 2)
- [ ] Create docker-compose.yml with Klov service
- [ ] Update azure-pipelines.yml to start services
- [ ] Add wait condition for service health check
- [ ] Ensure network connectivity between containers

### For Both Local and Azure (Solution 3)
- [ ] Implement conditional logic based on environment detection
- [ ] Add proper error handling for Klov operations
- [ ] Use WebDriverWait instead of Thread.sleep()
- [ ] Add logging to track report generation

---

## 🎯 Recommended Approach

**Solution 1 (Conditional Reporting)** is recommended because:
1. ✅ **Simplest to implement** - No infrastructure changes needed
2. ✅ **Cost-effective** - No additional Docker images or services
3. ✅ **Reliable** - Azure pipeline uses TestNG reports (proven reliable)
4. ✅ **Flexible** - Local development still uses Klov (rich reporting)
5. ✅ **Maintainable** - Minimal configuration overhead

---

## 📊 Comparison Table

| Aspect | Solution 1 | Solution 2 | Solution 3 |
|--------|-----------|-----------|-----------|
| **Complexity** | Low | High | Medium |
| **Cost** | Free | Extra resources | Free |
| **Klov in Azure** | No | Yes | Yes |
| **Implementation Time** | 15 mins | 1-2 hours | 30 mins |
| **Maintenance** | Easy | Complex | Medium |
| **Recommended** | ✅ YES | For rich reports | For flexibility |

---

## 🔗 Next Steps

1. Choose your preferred solution
2. Update configuration and code
3. Test locally first
4. Deploy to Azure DevOps
5. Monitor pipeline execution
6. Adjust based on results

For further assistance, refer to:
- [Azure DevOps Complete Setup](./AZURE_DEVOPS_SETUP.md)
- [Docker Pipeline Guide](./DOCKER_PIPELINE_INDEX.md)
- [Optimization Guide](./OPTIMIZATION_GUIDE_PROFILES.md)


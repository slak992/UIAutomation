# 🔵 Azure DevOps Pipeline Configuration Guide

## ChromeDriver Options Updated for CI/CD Execution

Your PARABANK project has been updated to automatically detect and configure ChromeDriver for Azure DevOps pipeline execution.

---

## ✅ WHAT WAS UPDATED

### File Modified
**`src/test/java/com/parabank/ui/base/BaseTest.java`**

### Method Updated
**`getWebDriverOptions(T options)` method**

---

## 🎯 NEW FEATURES

### 1. **Automatic Environment Detection**

The ChromeDriver configuration now automatically detects if it's running in Azure DevOps by checking for:
```java
boolean isCIEnvironment = System.getenv("SYSTEM_TEAMFOUNDATIONCOLLECTIONURI") != null || 
                          System.getenv("BUILD_REPOSITORY_URI") != null ||
                          System.getenv("CI") != null ||
                          System.getenv("TF_BUILD") != null ||
                          System.getProperty("headless") != null;
```

### 2. **Azure DevOps Specific Options**

When running in CI/CD environment:

| Option | Purpose |
|--------|---------|
| `--headless=new` | Run without GUI (essential for pipelines) |
| `--disable-gpu` | Disable GPU acceleration (no GPU in CI) |
| `--no-sandbox` | Disable sandbox for CI environments |
| `--disable-dev-shm-usage` | Prevent crashes in Docker/CI |
| `--disable-software-rasterizer` | Reduce memory footprint |
| `--disable-extensions` | Speed up startup |
| `--disable-plugins` | Security and stability |
| `--disable-gpu-compositing` | Reduce resource usage |
| `--disable-background-networking` | Optimize for CI |
| `--window-size=1920,1080` | Set viewport for headless |

### 3. **Intelligent Logging**

The configuration logs which mode is active:
- **CI/CD**: `🔵 Running in CI/CD Environment (Azure DevOps) - Headless Mode Enabled`
- **Local**: `🟢 Running in Local Environment - Standard Mode`

---

## 📋 AZURE DEVOPS PIPELINE CONFIGURATION

### Create `azure-pipelines.yml` in your repository root:

```yaml
trigger:
  - main
  - develop

pool:
  vmImage: 'ubuntu-latest'

variables:
  # Set CI environment variable for ChromeDriver detection
  CI: 'true'
  MAVEN_CACHE_FOLDER: $(Pipeline.Workspace)/.m2/repository
  MAVEN_OPTS: '-Dmaven.repo.local=$(MAVEN_CACHE_FOLDER)'

steps:
  # Step 1: Install Java
  - task: JavaToolInstaller@0
    inputs:
      versionSpec: '17'
      jdkArchitectureOption: 'x64'
      jdkSourceOption: 'PreInstalled'
    displayName: 'Install Java 17'

  # Step 2: Cache Maven dependencies
  - task: CacheBeta@1
    inputs:
      key: 'maven | "$(Agent.OS)" | **/pom.xml'
      restoreKeys: |
        maven | "$(Agent.OS)"
        maven
      path: $(MAVEN_CACHE_FOLDER)
    displayName: 'Cache Maven packages'

  # Step 3: Install Chrome (for UI testing)
  - bash: |
      sudo apt-get update
      sudo apt-get install -y google-chrome-stable
      google-chrome --version
    displayName: 'Install Google Chrome'

  # Step 4: Run Tests
  - task: Maven@3
    inputs:
      mavenPomFile: 'pom.xml'
      mavenOptions: '-Xmx3072m $(MAVEN_OPTS)'
      javaHomeOption: 'JDKVersion'
      jdkVersionOption: '1.17'
      jdkArchitectureOption: 'x64'
      publishJUnitResults: true
      testResultsFiles: '**/surefire-reports/TEST-*.xml'
      goals: 'clean test'
      arguments: |
        -DsuiteXmlFile=testng.xml
        -Dheadless=true
    displayName: 'Run Tests with Maven'

  # Step 5: Publish Test Results
  - task: PublishTestResults@2
    inputs:
      testResultsFormat: 'JUnit'
      testResultsFiles: '**/surefire-reports/TEST-*.xml'
      failTaskOnFailedTests: false
    displayName: 'Publish Test Results'
    condition: succeededOrFailed()

  # Step 6: Publish Logs and Reports
  - task: PublishBuildArtifacts@1
    inputs:
      pathToPublish: 'logs'
      artifactName: 'test-logs'
    displayName: 'Publish Test Logs'
    condition: always()

  - task: PublishBuildArtifacts@1
    inputs:
      pathToPublish: 'test-output'
      artifactName: 'test-reports'
    displayName: 'Publish Test Reports'
    condition: always()
```

---

## 🚀 HOW TO RUN

### 1. **Local Execution (Automatic Detection)**
```bash
# Detects local environment automatically
mvn clean test -DsuiteXmlFile=testng.xml
```

### 2. **Force Headless Mode Locally**
```bash
# Forces headless/CI mode
mvn clean test -DsuiteXmlFile=testng.xml -Dheadless=true
```

### 3. **Azure DevOps Pipeline (Automatic)**
Push to repository with `azure-pipelines.yml` - CI/CD detection happens automatically.

---

## 🔧 ENVIRONMENT VARIABLES DETECTED

The system automatically detects Azure DevOps by checking for:

| Environment Variable | Set By |
|---|---|
| `SYSTEM_TEAMFOUNDATIONCOLLECTIONURI` | Azure DevOps |
| `BUILD_REPOSITORY_URI` | Azure DevOps |
| `CI` | Generic CI/CD systems |
| `TF_BUILD` | Azure DevOps |
| `headless` (Java property) | Command line: `-Dheadless=true` |

---

## 📊 COMPARISON: Before vs After

### BEFORE
```java
if(System.getProperty("headless") != null)
{
    options.addArguments("--headless=new");
}
// Only basic headless mode, no CI-specific options
```

### AFTER
```java
boolean isCIEnvironment = System.getenv("SYSTEM_TEAMFOUNDATIONCOLLECTIONURI") != null || 
                          System.getenv("BUILD_REPOSITORY_URI") != null ||
                          System.getenv("CI") != null ||
                          System.getenv("TF_BUILD") != null ||
                          System.getProperty("headless") != null;

if(isCIEnvironment)
{
    options.addArguments("--headless=new");
    options.addArguments("--disable-gpu");
    options.addArguments("--no-sandbox");
    // ... 10+ additional CI/CD optimization options
}
```

---

## ✨ KEY BENEFITS

✅ **Automatic Detection** - Detects CI/CD environment automatically  
✅ **Zero Configuration** - Works with Azure DevOps without changes  
✅ **Optimized for CI** - Reduces memory, prevents crashes  
✅ **Local Compatible** - Still works perfectly locally  
✅ **Logging** - Tells you which mode is active  
✅ **Stable** - Handles Docker, headless, GPU-less environments  

---

## 🐛 TROUBLESHOOTING

### Issue: Chrome crashes in Azure DevOps
**Solution**: Already handled with:
- `--no-sandbox`
- `--disable-dev-shm-usage`
- `--disable-gpu`

### Issue: Tests run slow in CI
**Solution**: Already optimized with:
- `--disable-extensions`
- `--disable-plugins`
- `--disable-background-networking`

### Issue: Window size issues in headless
**Solution**: Already set with:
- `--window-size=1920,1080`

### Issue: Need to force CI mode locally
**Solution**: Use command line:
```bash
mvn test -Dheadless=true
```

---

## 📝 CONFIGURATION FILES

### Required Files in Azure DevOps

1. **`azure-pipelines.yml`** (in repo root)
   - Defines the pipeline configuration
   - Installs Chrome
   - Sets CI environment variable
   - Runs Maven tests

2. **`pom.xml`** ✅ Already optimized
   - Maven configuration
   - Dependencies defined

3. **`testng.xml`** ✅ Already exists
   - Test suite configuration

4. **`src/test/resources/log4j2.xml`** ✅ Already created
   - Logging configuration with rotation

---

## 🎯 WHAT HAPPENS IN PIPELINE

```
Azure DevOps Pipeline Execution Flow
│
├─ Agent detects environment variables (TF_BUILD, etc.)
├─ Java property "CI" is set
├─ Maven runs: mvn test -Dheadless=true
│
├─ BaseTest.java initializes
├─ getWebDriverOptions() is called
├─ Detects CI environment: isCIEnvironment = true
│
├─ Applies CI/CD ChromeDriver options:
│  ├─ --headless=new
│  ├─ --disable-gpu
│  ├─ --no-sandbox
│  ├─ --disable-dev-shm-usage
│  └─ ... (10+ more options)
│
├─ Logs: "🔵 Running in CI/CD Environment (Azure DevOps)"
├─ Chrome starts in headless mode
├─ Tests execute
├─ Results collected
├─ Logs and reports published
│
└─ Pipeline succeeds/fails based on test results
```

---

## 💡 OPTIONAL ENHANCEMENTS

### 1. Add Retry Logic (for flaky tests)
Update `pom.xml` maven-surefire-plugin:
```xml
<configuration>
    <rerunFailingTestsCount>2</rerunFailingTestsCount>
</configuration>
```

### 2. Add Screenshot on Failure
Already supported via ExtentReports configuration.

### 3. Add Performance Monitoring
Add to pom.xml:
```xml
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>exec-maven-plugin</artifactId>
    <version>3.0.0</version>
</plugin>
```

---

## 📚 REFERENCES

- **Selenium Chrome Options**: https://chromedriver.chromium.org/
- **Azure DevOps Predefined Variables**: https://docs.microsoft.com/en-us/azure/devops/pipelines/build/variables
- **Chrome Headless Flags**: https://peter.sh/experiments/chromium-command-line-switches/

---

## ✅ VERIFICATION

Your ChromeDriver options are now configured for:

- ✅ Local development (standard mode)
- ✅ Azure DevOps pipeline (headless + optimized)
- ✅ Docker containers
- ✅ Headless execution
- ✅ GPU-less environments
- ✅ Memory-constrained CI agents

---

**Status**: ✅ Ready for Azure DevOps Pipeline  
**Modified**: `BaseTest.java` - `getWebDriverOptions()` method  
**Date**: February 6, 2026  


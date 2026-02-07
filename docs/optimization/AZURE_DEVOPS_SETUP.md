# 🚀 Azure DevOps Pipeline Setup Guide

## Quick Start: 3 Simple Steps

### Step 1: Verify ChromeDriver Configuration ✅
ChromeDriver options have been automatically updated in:
- **File**: `src/test/java/com/parabank/ui/base/BaseTest.java`
- **Method**: `getWebDriverOptions()`
- **Status**: ✅ Ready for Azure DevOps

### Step 2: Add Pipeline Configuration to Your Repository

Copy the file `azure-pipelines.yml` from project root to your Azure DevOps repository root.

**File Location**: 
```
E:\AUTOMATION\PARABANK\azure-pipelines.yml
```

This file contains:
- ✅ Java 17 installation
- ✅ Chrome browser installation
- ✅ Maven test execution
- ✅ Test result publishing
- ✅ Report artifact collection

### Step 3: Configure Azure DevOps Project

#### A. Create a New Pipeline

1. Go to **Azure DevOps** → Your **Project**
2. Click **Pipelines** → **Create Pipeline**
3. Select **GitHub** or **Azure Repos** (depending on your repository)
4. Select your repository
5. Choose **Existing Azure Pipelines YAML file**
6. Select: `azure-pipelines.yml`
7. Click **Save and run**

#### B. Alternative: Upload YAML File

1. Go to your repository
2. Upload `azure-pipelines.yml` to the root
3. Go to **Pipelines** → **New Pipeline**
4. It will auto-detect the YAML file

---

## 🎯 What Happens When Pipeline Runs

```
Push Code to Repository
        ↓
Azure DevOps Detects Trigger
        ↓
Pipeline Initializes Agent
        ↓
System sets CI=true environment variable
        ↓
Java 17 Installed
        ↓
Chrome Browser Installed
        ↓
Maven Builds Project
        ↓
BaseTest.getWebDriverOptions() Executes
        ↓
Detects CI Environment (isCIEnvironment = true)
        ↓
Applies Azure DevOps ChromeDriver Options:
   • --headless=new (no GUI)
   • --disable-gpu (no GPU in CI)
   • --no-sandbox (CI compatibility)
   • --disable-dev-shm-usage (prevent crashes)
   • --window-size=1920,1080 (set viewport)
   • ... + 10 more optimization options
        ↓
Chrome Starts in Headless Mode
        ↓
Tests Execute
        ↓
Results Collected
        ↓
Artifacts Published (logs, reports)
        ↓
Pipeline Completes
```

---

## 📋 Manual Commands (If Not Using Pipeline)

### Run Tests Locally in CI Mode
```bash
mvn clean test -DsuiteXmlFile=testng.xml -Dheadless=true
```

### Run Specific Test Suite
```bash
mvn test -DsuiteXmlFile=testng.xml -Dheadless=true
```

### Run with More Verbose Output
```bash
mvn test -DsuiteXmlFile=testng.xml -Dheadless=true -X
```

---

## 🔍 Monitoring Pipeline Execution

### In Azure DevOps

1. Go to **Pipelines** → Select your pipeline
2. Click **Runs** to see execution history
3. Click on a run to see:
   - **Build logs** - Real-time execution logs
   - **Test results** - JUnit results with pass/fail
   - **Artifacts** - Published logs and reports

### Check Logs

1. Click on the run
2. Click **Job** to see console output
3. Scroll through to find:
   - `🔵 Running in CI/CD Environment (Azure DevOps)` - Confirms CI mode detected
   - Test execution logs
   - Assertion results

### Download Artifacts

1. Click on the run
2. Click **Artifacts** tab
3. Download:
   - `test-logs` - Test execution logs
   - `test-reports` - HTML reports (ExtentReports)
   - `surefire-reports` - JUnit XML reports

---

## 🛠️ Troubleshooting

### Problem: Chrome doesn't start in pipeline

**Solution**: 
- The pipeline already installs Chrome: `sudo apt-get install -y google-chrome-stable`
- ChromeDriver options include `--no-sandbox` and `--disable-dev-shm-usage`
- If still fails, check agent has internet access to download Chrome

### Problem: Tests timeout in pipeline

**Solution**:
- Pipeline has `--disable-dev-shm-usage` which helps in Docker
- Increase timeout in test properties if needed
- Check if tests are network-dependent and add retry logic

### Problem: Window size issues

**Solution**:
- ChromeDriver options set: `--window-size=1920,1080`
- This ensures proper element visibility in headless mode

### Problem: Cannot find test results

**Solution**:
- Pipeline publishes results from: `**/surefire-reports/TEST-*.xml`
- Check if tests actually ran (see build logs)
- Verify `testng.xml` has correct test class references

### Problem: Need to skip tests temporarily

**Solution**:
Update `azure-pipelines.yml` goals:
```yaml
goals: 'clean package -DskipTests'
```

---

## 📊 Pipeline Performance Tips

### Tip 1: Cache Maven Dependencies
The pipeline already includes Maven cache configuration:
```yaml
- task: CacheBeta@1
  inputs:
    key: 'maven | "$(Agent.OS)" | **/pom.xml'
    path: $(MAVEN_CACHE_FOLDER)
```

### Tip 2: Parallelize Tests
Update `testng.xml` to run tests in parallel:
```xml
<suite name="Suite" parallel="methods" thread-count="5">
```

### Tip 3: Use Specific Test Suites
Instead of running all tests, specify which suite:
```yaml
goals: 'test -DsuiteXmlFile=smoke-tests.xml'
```

### Tip 4: Schedule Nightly Runs
In Azure DevOps Pipeline editor:
1. Click **Triggers**
2. Add **Scheduled trigger**
3. Set time (e.g., 2 AM daily)

---

## 🔐 Security Considerations

### 1. Secrets in Pipeline
If you need credentials:
```yaml
variables:
  - group: 'PARABANK-Secrets'  # Create this variable group in DevOps

steps:
  - script: echo $(DB_PASSWORD)  # Reference secret
```

### 2. Artifact Retention
Azure DevOps keeps artifacts by default. To reduce storage:
```yaml
- task: PublishBuildArtifacts@1
  inputs:
    deleteBeforePublish: true  # Clean old artifacts
```

### 3. Test Data Security
Store test data in Azure Key Vault:
```yaml
- task: AzureKeyVault@1
  inputs:
    azureSubscription: 'YOUR-SUBSCRIPTION'
    KeyVaultName: 'your-keyvault'
    SecretsFilter: '*'
```

---

## 📈 Monitoring and Alerts

### Setup Build Success Notification
1. Go to **Pipelines** → **Settings**
2. Select pipeline → **Options**
3. Enable notifications for:
   - Build completion
   - Test failures
   - Build queuing

### Create Retention Policy
1. Go to **Pipelines** → **Settings**
2. Set artifact retention days
3. Set build retention days

---

## 📞 Support & Help

### If Pipeline Fails

1. **Check build logs** - Click on run → View logs
2. **Look for 🔵 marker** - Confirms CI mode detection
3. **Search for errors** - Filter logs for "ERROR" or "FAILED"
4. **Check artifacts** - May have detailed error logs

### Common Error Messages

| Error | Solution |
|-------|----------|
| `Chrome not found` | Pipeline installs Chrome - wait for step completion |
| `Port already in use` | Rarely happens, usually retry fixes it |
| `Timeout waiting for browser` | Increase timeout or check network |
| `No tests found` | Verify testng.xml has correct class names |

---

## ✅ Verification Checklist

- [ ] Copied `azure-pipelines.yml` to repository root
- [ ] Created pipeline in Azure DevOps pointing to this file
- [ ] Ran pipeline successfully (at least once)
- [ ] Verified test results are published
- [ ] Confirmed 🔵 CI environment detection in logs
- [ ] Downloaded and reviewed test reports
- [ ] Set up notifications for pipeline status

---

## 🎉 YOU'RE READY!

Your PARABANK project is now configured for Azure DevOps pipeline execution.

### Next Steps:
1. Push `azure-pipelines.yml` to your repository
2. Create pipeline in Azure DevOps
3. Monitor first run and verify ChromeDriver options are applied
4. Download reports and confirm tests pass

---

**ChromeDriver Status**: ✅ Configured for Azure DevOps  
**Pipeline YAML**: ✅ Ready to use  
**Documentation**: ✅ Complete  
**Date Updated**: February 6, 2026


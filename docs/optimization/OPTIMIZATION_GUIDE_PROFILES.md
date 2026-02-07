# Maven Profiles Configuration Guide

This file demonstrates how to add profiles to your pom.xml for better test suite management.

## How to add profiles to pom.xml

Add this section AFTER the `<properties>` section and BEFORE `<dependencies>`:

```xml
<profiles>
    <!-- Smoke Tests Profile -->
    <profile>
        <id>smoke</id>
        <properties>
            <suiteXmlFile>smoke-tests.xml</suiteXmlFile>
        </properties>
    </profile>

    <!-- Full Regression Profile -->
    <profile>
        <id>regression</id>
        <properties>
            <suiteXmlFile>testng.xml</suiteXmlFile>
        </properties>
    </profile>

    <!-- Login Tests Only -->
    <profile>
        <id>login</id>
        <properties>
            <suiteXmlFile>login-tests.xml</suiteXmlFile>
        </properties>
    </profile>

    <!-- Account Tests Only -->
    <profile>
        <id>account</id>
        <properties>
            <suiteXmlFile>account-tests.xml</suiteXmlFile>
        </properties>
    </profile>

    <!-- Loan Processing Tests -->
    <profile>
        <id>loan</id>
        <properties>
            <suiteXmlFile>loan-tests.xml</suiteXmlFile>
        </properties>
    </profile>

    <!-- Negative Tests Only -->
    <profile>
        <id>negative</id>
        <properties>
            <suiteXmlFile>negative-tests.xml</suiteXmlFile>
        </properties>
    </profile>

    <!-- Fast Tests (Positive scenarios) -->
    <profile>
        <id>positive</id>
        <properties>
            <suiteXmlFile>positive-tests.xml</suiteXmlFile>
        </properties>
    </profile>
</profiles>
```

## How to use profiles

Run tests with specific profile:

```bash
# Default (all tests)
mvn test

# Smoke tests only
mvn test -Psmoke

# Regression tests
mvn test -Pregression

# Login tests
mvn test -Plogin

# Account tests
mvn test -Paccount

# Loan processing tests
mvn test -Ploan

# Negative tests
mvn test -Pnegative

# Positive tests
mvn test -Ppositive

# Multiple profiles
mvn test -Psmoke,regression
```

## Update application.properties

Create `src/test/resources/application.properties`:

```properties
# Test Environment
env=qa
browser=chrome
headless=false
timeout=30
implicit.wait=15
explicit.wait=20

# Application URLs
base.url=http://parabank.parasoft.com
api.base.url=http://parabank.parasoft.com/parabank/services/bank

# Report Configuration
extent.report.path=test-output/ExtentReport/ExtentReport.html
klov.url=http://localhost:8080

# Screenshots
take.screenshot=true
screenshot.on.failure=true
screenshot.path=test-output/screenshots
```

## Clean up Unnecessary Test XML Files

The following files can be archived or deleted (they appear to be examples/practice):
- AutomationPractise.xml
- IncludeExcludeExample.xml
- checkDBTestCase.xml
- packageLevelExecution.xml
- chromeDevProtocolTests.xml

Keep:
- testng.xml (main execution suite)
- Create specialized suites as needed (smoke-tests.xml, login-tests.xml, etc.)


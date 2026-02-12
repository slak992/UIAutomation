# Feature Files Implementation Guide

## 📋 Overview

This guide explains how to implement and use the Gherkin feature files created for the ParaBank automation test suite.

---

## 1️⃣ Feature Files Created

### Positive Test Feature Files (5 files)
Located: `src/test/resources/features/pos/`

1. **POS_Login.feature** - User registration and login scenarios
2. **POS_Account.feature** - Account management operations
3. **POS_TransferFunds.feature** - Fund transfer scenarios
4. **POS_LoanProcessing.feature** - Loan application and processing
5. **POS_Transaction.feature** - Transaction viewing and management

### Negative Test Feature Files (3 files)
Located: `src/test/resources/features/neg/`

1. **NEG_Login.feature** - Invalid registration and login attempts
2. **NEG_LoanProcessing.feature** - Invalid loan applications
3. **NEG_Account.feature** - Invalid account operations

---

## 2️⃣ File Organization

```
E:\AUTOMATION\PARABANK\
├── src/
│   └── test/
│       └── resources/
│           └── features/
│               ├── pos/
│               │   ├── POS_Login.feature
│               │   ├── POS_Account.feature
│               │   ├── POS_TransferFunds.feature
│               │   ├── POS_LoanProcessing.feature
│               │   └── POS_Transaction.feature
│               └── neg/
│                   ├── NEG_Login.feature
│                   ├── NEG_LoanProcessing.feature
│                   └── NEG_Account.feature
├── FEATURE_FILES_SUMMARY.md
└── FEATURE_FILES_QUICK_REFERENCE.md
```

---

## 3️⃣ Feature File Statistics

| Category | Count | Scenarios |
|----------|-------|-----------|
| Positive Feature Files | 5 | 21 |
| Negative Feature Files | 3 | 25 |
| **Total** | **8** | **46** |

---

## 4️⃣ Feature File Content

### Structure of Each Feature File

```gherkin
@Positive @CategoryTag
Feature: Clear feature description
  As a user role
  I want to perform some action
  So that expected benefit occurs

  Background:
    Given precondition 1
    And precondition 2

  @Tag1 @Tag2
  Scenario: Scenario title
    When user performs action
    Then expected result
    And additional assertion

  @DataDriven
  Scenario Outline: Template scenario
    When user enters "<value>"
    Then result is "<expected>"
    Examples:
      | value | expected |
      | val1  | result1  |
```

### Key Elements

1. **Feature Tag**: `@Positive` or `@Negative`
2. **Category Tags**: `@Login`, `@Account`, `@LoanProcessing`, etc.
3. **Background**: Shared setup for all scenarios
4. **Scenarios**: Test cases with Given-When-Then format
5. **Examples**: Data-driven test data

---

## 5️⃣ Positive Tests Overview

### POS_Login.feature (4 scenarios)
✅ User registration success  
✅ User login with valid credentials  
✅ Multi-user login (data-driven)  
✅ User logout  

### POS_Account.feature (4 scenarios)
✅ Open new account  
✅ Verify account details  
✅ View accounts overview  
✅ Verify default minimum balance  

### POS_TransferFunds.feature (4 scenarios)
✅ Transfer funds successfully  
✅ Verify source account balance after transfer  
✅ Verify destination account balance after transfer  
✅ Transfer with decimal amounts  

### POS_LoanProcessing.feature (5 scenarios)
✅ Apply for loan successfully  
✅ Loan account creation after application  
✅ Loan balance verification  
✅ Source account balance after loan  
✅ Loan account type verification  

### POS_Transaction.feature (4 scenarios)
✅ View transaction history  
✅ Find transactions by date range  
✅ Find transactions by amount  
✅ Verify transaction balance calculation  

---

## 6️⃣ Negative Tests Overview

### NEG_Login.feature (15 scenarios)
❌ Required field validations (7)  
  - First name blank
  - Last name blank
  - Address blank
  - City blank
  - State blank
  - Zip code blank
  - SSN blank

❌ Password validations (2)  
  - Password blank
  - Password mismatch

❌ Login validations (5)  
  - Invalid username
  - Invalid password
  - Both invalid
  - Blank username
  - Blank password

### NEG_LoanProcessing.feature (8 scenarios)
❌ Invalid loan amount  
❌ Insufficient funds  
❌ Down payment > loan amount  
❌ Blank loan amount  
❌ Blank down payment  
❌ Invalid account selection  
❌ Negative loan amount  
❌ No account created without successful application  

### NEG_Account.feature (6 scenarios)
❌ No base account selection  
❌ No account type selection  
❌ Mismatched account details  
❌ Duplicate account prevention  
❌ No account without valid selection  
❌ Negative balance prevention  

---

## 7️⃣ Tag-Based Execution

### By Test Type
```bash
# Positive tests only
mvn test -Dcucumber.options="--tags @Positive"

# Negative tests only
mvn test -Dcucumber.options="--tags @Negative"
```

### By Functionality
```bash
# Login tests
mvn test -Dcucumber.options="--tags @Login"

# Account tests
mvn test -Dcucumber.options="--tags @Account"

# Loan tests
mvn test -Dcucumber.options="--tags @LoanProcessing"

# Transaction tests
mvn test -Dcucumber.options="--tags @Transaction"

# Transfer tests
mvn test -Dcucumber.options="--tags @TransferFunds"
```

### By Test Category
```bash
# Regression tests
mvn test -Dcucumber.options="--tags @Regression"

# Mobile tests
mvn test -Dcucumber.options="--tags @Mobile"

# Data-driven tests
mvn test -Dcucumber.options="--tags @DataDriven"
```

### Combined Execution
```bash
# Positive regression tests
mvn test -Dcucumber.options="--tags @Positive and @Regression"

# Login tests excluding mobile
mvn test -Dcucumber.options="--tags @Login and not @Mobile"
```

---

## 8️⃣ Feature File to Test Class Mapping

| Feature File | Test Class | File Location |
|-------------|-----------|--------------|
| POS_Login.feature | POS_Login_Test.java | src/test/java/com/parabank/ui/tests/pos/ |
| POS_Account.feature | POS_Account_Test.java | src/test/java/com/parabank/ui/tests/pos/ |
| POS_TransferFunds.feature | POS_TransferFunds.java | src/test/java/com/parabank/ui/tests/pos/ |
| POS_LoanProcessing.feature | POS_loanProcessingTest.java | src/test/java/com/parabank/ui/tests/pos/ |
| POS_Transaction.feature | POS_Transaction.java | src/test/java/com/parabank/ui/tests/pos/ |
| NEG_Login.feature | NEG_Login_Test.java | src/test/java/com/parabank/ui/tests/neg/ |
| NEG_LoanProcessing.feature | NEG_loanProcessingTest.java | src/test/java/com/parabank/ui/tests/neg/ |
| NEG_Account.feature | POS_Account_Test.java | src/test/java/com/parabank/ui/tests/pos/ |

---

## 9️⃣ Running Feature Files

### Execute All Features
```bash
mvn test -Dcucumber.features=src/test/resources/features
```

### Execute Only Positive Tests
```bash
mvn test -Dcucumber.features=src/test/resources/features/pos
```

### Execute Only Negative Tests
```bash
mvn test -Dcucumber.features=src/test/resources/features/neg
```

### Execute Specific Feature File
```bash
mvn test -Dcucumber.features=src/test/resources/features/pos/POS_Login.feature
```

### Execute with Report Generation
```bash
mvn test -Dcucumber.features=src/test/resources/features \
         -Dcucumber.options="--plugin json:target/cucumber.json"
```

---

## 🔟 Integration with Existing Framework

### Compatibility
✅ Compatible with existing TestNG tests  
✅ Uses same page objects (POM)  
✅ Follows existing naming conventions  
✅ Integrates with current test data management  
✅ Works with Klov reporting  

### No Breaking Changes
- Existing Java test classes remain unchanged
- Feature files provide BDD documentation
- Tests can run independently or with Cucumber
- All configurations remain the same

---

## 1️⃣1️⃣ Document References

### Summary Documents Created

1. **FEATURE_FILES_SUMMARY.md**
   - Comprehensive feature file overview
   - Scenario details for each feature
   - Coverage analysis
   - Best practices

2. **FEATURE_FILES_QUICK_REFERENCE.md**
   - Quick lookup guide
   - Tag usage guide
   - Execution commands
   - Summary table

3. **FEATURE_FILES_IMPLEMENTATION_GUIDE.md** (This file)
   - Step-by-step implementation
   - File organization
   - Execution examples
   - Integration points

---

## 1️⃣2️⃣ Next Steps

### Step 1: Review Feature Files
- [ ] Review all 8 feature files in IDE
- [ ] Verify content accuracy
- [ ] Check scenario coverage

### Step 2: Setup Cucumber Integration (Optional)
- [ ] Add Cucumber dependency to pom.xml
- [ ] Create step definition classes
- [ ] Configure Cucumber runner
- [ ] Map step definitions to features

### Step 3: Verify Execution
- [ ] Run positive tests: `mvn test -Dcucumber.options="--tags @Positive"`
- [ ] Run negative tests: `mvn test -Dcucumber.options="--tags @Negative"`
- [ ] Verify report generation

### Step 4: Integrate with CI/CD
- [ ] Update Azure Pipeline YAML
- [ ] Add feature file execution
- [ ] Configure report publishing
- [ ] Test pipeline execution

### Step 5: Documentation
- [ ] Update README with feature file info
- [ ] Add to documentation index
- [ ] Create developer guide
- [ ] Document tag strategy

---

## 1️⃣3️⃣ Troubleshooting

### Feature Files Not Found
```bash
# Ensure correct path in maven command
mvn test -Dcucumber.features=src/test/resources/features
```

### Tags Not Recognized
```bash
# Verify tag syntax (starts with @)
# Example: @Positive @Regression
```

### Step Definition Not Found
```bash
# Create step definitions that match feature file steps
# Ensure package structure matches Cucumber configuration
```

---

## 1️⃣4️⃣ Best Practices

### Writing Scenarios
✅ Use clear, business-language descriptions  
✅ Follow Given-When-Then format strictly  
✅ Keep scenarios focused and simple  
✅ Use background for common steps  

### Using Tags
✅ Use consistent tag naming  
✅ Tag by functionality and test type  
✅ Avoid excessive tag nesting  

### Data Management
✅ Use examples for data-driven tests  
✅ Keep test data in feature files where possible  
✅ Reference external data clearly  

### Organization
✅ One feature per functionality area  
✅ Group related scenarios together  
✅ Use clear naming conventions  
✅ Document complex scenarios  

---

## 📞 Support

For questions or issues:
1. Check FEATURE_FILES_SUMMARY.md for details
2. Review FEATURE_FILES_QUICK_REFERENCE.md for examples
3. Consult existing test classes for reference
4. Review DOCUMENTATION.md for framework info

---

**Document Version**: 1.0  
**Created**: February 12, 2026  
**Framework**: Cucumber BDD + TestNG + Selenium  
**Location**: E:\AUTOMATION\PARABANK\src\test\resources\features


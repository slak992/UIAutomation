# ParaBank BDD Feature Files - Quick Reference

## 📁 Directory Structure

```
src/test/resources/
└── features/
    ├── pos/                           # Positive Test Scenarios
    │   ├── POS_Login.feature          (4 scenarios)
    │   ├── POS_Account.feature        (4 scenarios)
    │   ├── POS_TransferFunds.feature  (4 scenarios)
    │   ├── POS_LoanProcessing.feature (5 scenarios)
    │   └── POS_Transaction.feature    (4 scenarios)
    │
    └── neg/                           # Negative Test Scenarios
        ├── NEG_Login.feature          (11 scenarios)
        ├── NEG_LoanProcessing.feature (8 scenarios)
        └── NEG_Account.feature        (6 scenarios)
```

## 📊 Quick Summary

| Type | Feature Files | Total Scenarios | Test Methods |
|------|--------------|-----------------|--------------|
| **Positive (POS)** | 5 | 21 | ✅ Happy Path Tests |
| **Negative (NEG)** | 3 | 25 | ❌ Error Handling Tests |
| **TOTAL** | 8 | 46 | Complete Coverage |

---

## ✅ Positive Tests (POS) - 21 Scenarios

### POS_Login.feature
```gherkin
✓ Register new user successfully
✓ Login with valid credentials
✓ Login with different user credentials (Data-Driven)
✓ Logout successfully
```

### POS_Account.feature
```gherkin
✓ Open new account successfully
✓ Verify new account details
✓ View all accounts overview
✓ Verify default minimum balance
```

### POS_TransferFunds.feature
```gherkin
✓ Transfer funds successfully
✓ Verify balance after transfer
✓ Verify destination account balance after transfer
✓ Transfer with formatted decimal amounts
```

### POS_LoanProcessing.feature
```gherkin
✓ Apply for loan successfully
✓ Verify loan account created after application
✓ Verify loan account balance matches loan amount
✓ Verify source account balance updated after loan application
✓ Verify loan account type created
```

### POS_Transaction.feature
```gherkin
✓ View transaction history
✓ Find transactions by date range
✓ Find transactions by amount
✓ Verify transaction balance calculation
```

---

## ❌ Negative Tests (NEG) - 25 Scenarios

### NEG_Login.feature (11 scenarios)
```gherkin
✗ Registration fails with first name blank
✗ Registration fails with last name blank
✗ Registration fails with address blank
✗ Registration fails with city blank
✗ Registration fails with state blank
✗ Registration fails with zip code blank
✗ Registration fails with SSN blank
✗ Registration fails with password blank
✗ Registration fails with password mismatch
✗ Login fails with invalid username
✗ Login fails with invalid password
✗ Login fails with both username and password invalid
✗ Login fails with blank username
✗ Login fails with blank password
```

### NEG_LoanProcessing.feature (8 scenarios)
```gherkin
✗ Loan application fails with invalid loan amount
✗ Loan application fails with insufficient funds
✗ Loan application fails with down payment greater than loan amount
✗ Loan application fails with blank loan amount
✗ Loan application fails with blank down payment
✗ Loan application fails with invalid account selection
✗ Loan application fails with negative loan amount
✗ Loan balance does not increase without successful application
```

### NEG_Account.feature (6 scenarios)
```gherkin
✗ Cannot create account without base account selection
✗ Cannot create account without account type selection
✗ Account details should not match with different parameters
✗ Duplicate account creation should be prevented
✗ Cannot view account without valid account selection
✗ Account balance should not be negative without loan
```

---

## 🏷️ Tag Usage Guide

### By Test Type
```bash
# Run all positive tests
mvn test -Dcucumber.options="--tags @Positive"

# Run all negative tests
mvn test -Dcucumber.options="--tags @Negative"
```

### By Functionality
```bash
# Run all login tests
mvn test -Dcucumber.options="--tags @Login"

# Run all account tests
mvn test -Dcucumber.options="--tags @Account"

# Run all loan tests
mvn test -Dcucumber.options="--tags @LoanProcessing"

# Run all transaction tests
mvn test -Dcucumber.options="--tags @Transaction"

# Run transfer funds tests
mvn test -Dcucumber.options="--tags @TransferFunds"
```

### By Test Category
```bash
# Run all regression tests
mvn test -Dcucumber.options="--tags @Regression"

# Run mobile-specific tests
mvn test -Dcucumber.options="--tags @Mobile"

# Run data-driven tests
mvn test -Dcucumber.options="--tags @DataDriven"
```

### Combined Tags
```bash
# Run positive regression tests
mvn test -Dcucumber.options="--tags @Positive and @Regression"

# Run login tests excluding mobile
mvn test -Dcucumber.options="--tags @Login and not @Mobile"
```

---

## 📋 Feature File Content Structure

### Standard Gherkin Template

```gherkin
@TagName @TestType
Feature: Feature Description
  As a user role
  I want to perform action
  So that expected benefit occurs

  Background:
    Given precondition 1
    And precondition 2

  @SubTag @Category
  Scenario: Clear scenario description
    When user performs action
    Then expected result 1
    And expected result 2

  @DataDriven
  Scenario Outline: Template scenario
    When user enters "<parameter>"
    Then result should be "<expected>"
    Examples:
      | parameter | expected |
      | value1    | result1  |
```

---

## 🎯 Mapping to Existing Test Classes

| Feature File | Java Test Class | Scenarios | Status |
|-------------|-----------------|-----------|--------|
| POS_Login.feature | POS_Login_Test.java | 4 | ✅ Implemented |
| POS_Account.feature | POS_Account_Test.java | 4 | ✅ Implemented |
| POS_TransferFunds.feature | POS_TransferFunds.java | 4 | ✅ Implemented |
| POS_LoanProcessing.feature | POS_loanProcessingTest.java | 5 | ✅ Implemented |
| POS_Transaction.feature | POS_Transaction.java | 4 | ✅ Implemented |
| NEG_Login.feature | NEG_Login_Test.java | 15 | ✅ Implemented |
| NEG_LoanProcessing.feature | NEG_loanProcessingTest.java | 8 | ✅ Implemented |
| NEG_Account.feature | POS_Account_Test.java | 6 | 📝 New Scenarios |

---

## 🚀 Execution Commands

### Individual Feature File Execution
```bash
# Login feature only
mvn test -Dcucumber.features=src/test/resources/features/pos/POS_Login.feature

# Account feature only
mvn test -Dcucumber.features=src/test/resources/features/pos/POS_Account.feature

# All NEG_Login scenarios
mvn test -Dcucumber.features=src/test/resources/features/neg/NEG_Login.feature
```

### Suite Execution
```bash
# All positive tests
mvn test -Dcucumber.features=src/test/resources/features/pos

# All negative tests
mvn test -Dcucumber.features=src/test/resources/features/neg

# All tests (pos + neg)
mvn test -Dcucumber.features=src/test/resources/features
```

### Parallel Execution
```bash
# Run tests in parallel (if configured)
mvn test -Dcucumber.options="--parallel"
```

---

## 📝 Feature File Naming Convention

```
[TEST_TYPE]_[FUNCTIONALITY].feature

Examples:
├── POS_Login.feature           (Positive)
├── POS_Account.feature         (Positive)
├── NEG_Login.feature           (Negative)
└── NEG_LoanProcessing.feature  (Negative)
```

---

## 🔍 Scenario Naming Convention

```
[Action/Result] [Functionality] [Condition]

Examples:
✓ Register new user successfully
✓ Login with valid credentials
✗ Registration fails with first name blank
✗ Loan application fails with insufficient funds
```

---

## 📈 Test Data in Feature Files

### Data Tables
```gherkin
When User registers a new account with valid details
  | firstName | lastName | address |
  | testUser  | userTest | 123 Main|
```

### Scenario Outlines with Examples
```gherkin
Scenario Outline: Login with different users
  When User enters username "<user>" and password "<password>"
  Then User should be logged in
  Examples:
    | user  | password |
    | demo1 | demo1    |
```

---

## 🔗 Integration Points

### With Existing Framework
- ✅ Aligns with existing POS/NEG test structure
- ✅ Maps to current Java test classes
- ✅ Uses existing page objects
- ✅ Compatible with TestNG

### With CI/CD
- ✅ Azure DevOps compatible
- ✅ Cucumber reporting enabled
- ✅ JUnit XML report generation
- ✅ Klov integration support

---

## 📚 Additional Resources

- See **FEATURE_FILES_SUMMARY.md** for detailed feature descriptions
- See **DOCUMENTATION.md** for framework overview
- See **IMPLEMENTATION_CHECKLIST.md** for setup steps
- See **KLOV_AZURE_PIPELINE_ISSUES.md** for Azure integration

---

**Created**: February 12, 2026  
**Framework**: Cucumber BDD + TestNG + Selenium  
**Total Features**: 8 files  
**Total Scenarios**: 46 scenarios  
**Coverage**: All major ParaBank functionalities


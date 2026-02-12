# ParaBank Feature Files Structure

## Overview
This document describes the feature files created for ParaBank automation testing, organized into Positive and Negative test scenarios.

## Directory Structure

```
src/test/resources/features/
├── pos/
│   ├── POS_Login.feature
│   ├── POS_Account.feature
│   ├── POS_TransferFunds.feature
│   ├── POS_LoanProcessing.feature
│   └── POS_Transaction.feature
└── neg/
    ├── NEG_Login.feature
    ├── NEG_LoanProcessing.feature
    └── NEG_Account.feature
```

## Positive Test Feature Files (tests/pos)

### 1. POS_Login.feature
**Scope**: User registration and login functionality
**Test Scenarios**:
- Register new user successfully
- Login with valid credentials
- Login with multiple user credentials (data-driven)
- Logout functionality

**Tags**: `@Positive` `@Login` `@Regression` `@Mobile` `@DataDriven`

---

### 2. POS_Account.feature
**Scope**: Account management operations
**Test Scenarios**:
- Open new account successfully
- Verify new account details
- View all accounts overview
- Verify default minimum balance

**Tags**: `@Positive` `@Account` `@Regression`

---

### 3. POS_TransferFunds.feature
**Scope**: Fund transfer between accounts
**Test Scenarios**:
- Transfer funds successfully
- Verify source account balance after transfer
- Verify destination account balance after transfer
- Transfer with formatted decimal amounts

**Tags**: `@Positive` `@TransferFunds` `@Regression`

---

### 4. POS_LoanProcessing.feature
**Scope**: Loan application and processing
**Test Scenarios**:
- Apply for loan successfully
- Verify loan account created after application
- Verify loan account balance matches loan amount
- Verify source account balance updated after loan
- Verify loan account type created

**Tags**: `@Positive` `@LoanProcessing` `@Regression`

---

### 5. POS_Transaction.feature
**Scope**: Transaction viewing and management
**Test Scenarios**:
- View transaction history
- Find transactions by date range
- Find transactions by amount
- Verify transaction balance calculation

**Tags**: `@Positive` `@Transaction` `@Regression`

---

## Negative Test Feature Files (tests/neg)

### 1. NEG_Login.feature
**Scope**: Invalid registration and login attempts
**Test Scenarios**:
- Registration fails with required fields blank:
  - First name blank
  - Last name blank
  - Address blank
  - City blank
  - State blank
  - Zip code blank
  - SSN blank
  - Password blank
- Password mismatch during registration
- Login fails with invalid credentials:
  - Invalid username
  - Invalid password
  - Both invalid
  - Blank username
  - Blank password

**Tags**: `@Negative` `@Login` `@Regression`

---

### 2. NEG_LoanProcessing.feature
**Scope**: Invalid loan applications
**Test Scenarios**:
- Loan application fails with invalid loan amount
- Loan application fails with insufficient funds
- Loan application fails with down payment > loan amount
- Loan application fails with blank fields:
  - Blank loan amount
  - Blank down payment
- Loan application fails with invalid account selection
- Loan application fails with negative amount
- Loan balance does not increase without successful application

**Tags**: `@Negative` `@LoanProcessing` `@Regression`

---

### 3. NEG_Account.feature
**Scope**: Invalid account operations
**Test Scenarios**:
- Cannot create account without base account selection
- Cannot create account without account type selection
- Account details should not match with different parameters
- Duplicate account creation prevention
- Cannot view account without valid selection
- Account balance should not be negative without loan

**Tags**: `@Negative` `@Account` `@Regression`

---

## Usage

### Running All Feature Files
```bash
mvn test -Dcucumber.features=src/test/resources/features
```

### Running Only Positive Tests
```bash
mvn test -Dcucumber.features=src/test/resources/features/pos
```

### Running Only Negative Tests
```bash
mvn test -Dcucumber.features=src/test/resources/features/neg
```

### Running by Tag
```bash
# Run all regression tests
mvn test -Dcucumber.options="--tags @Regression"

# Run only login tests
mvn test -Dcucumber.options="--tags @Login"

# Run mobile tests
mvn test -Dcucumber.options="--tags @Mobile"
```

---

## Feature File Statistics

| Category | File Count | Total Scenarios |
|----------|-----------|-----------------|
| **Positive Tests** | 5 | 18 |
| **Negative Tests** | 3 | 24 |
| **TOTAL** | 8 | 42 |

---

## Test Coverage

### Functional Areas Covered

1. **Authentication & Authorization** (13 scenarios)
   - User registration (8)
   - User login (5)

2. **Account Management** (10 scenarios)
   - Account creation (2)
   - Account viewing (2)
   - Account validation (6)

3. **Transactions** (8 scenarios)
   - Transaction viewing (4)
   - Transfer funds (4)

4. **Loan Processing** (13 scenarios)
   - Loan application (5)
   - Loan verification (3)
   - Loan validation (5)

---

## Mapping to Java Test Classes

| Feature File | Java Test Class |
|-------------|-----------------|
| POS_Login.feature | POS_Login_Test.java |
| POS_Account.feature | POS_Account_Test.java, POS_OpenNewAccount.java |
| POS_TransferFunds.feature | POS_TransferFunds.java |
| POS_LoanProcessing.feature | POS_loanProcessingTest.java |
| POS_Transaction.feature | POS_Transaction.java |
| NEG_Login.feature | NEG_Login_Test.java |
| NEG_LoanProcessing.feature | NEG_loanProcessingTest.java |
| NEG_Account.feature | (New - validates POS_Account_Test.java scenarios) |

---

## Best Practices Applied

1. **Gherkin Conventions**
   - Feature names aligned with functionality
   - Clear scenario descriptions
   - Given-When-Then format

2. **Tagging Strategy**
   - `@Positive` / `@Negative` for test type
   - `@Regression` for regression suite
   - `@Mobile` for mobile-specific tests
   - Functional tags (`@Login`, `@Account`, etc.)

3. **Data-Driven Testing**
   - Examples table for multiple scenarios
   - Parameter-based scenarios

4. **Background Setup**
   - Common preconditions defined
   - Reduces scenario duplication

---

## Integration with CI/CD

These feature files can be integrated with:
- **Azure DevOps**: Execute with Cucumber plugin
- **Jenkins**: Use Cucumber reporting
- **Cucumber Reports**: Generate HTML/XML reports
- **Klov Reporting**: Extended reporting (see KLOV_AZURE_PIPELINE_ISSUES.md)

---

## Next Steps

1. Set up Cucumber step definitions matching these scenarios
2. Configure Cucumber runner with TestNG
3. Integrate with existing test framework
4. Generate reports using Cucumber Report Plugin
5. Add to CI/CD pipeline

---

**Last Updated**: February 12, 2026
**Created For**: ParaBank Automation Test Suite
**Framework**: Cucumber BDD + TestNG + Selenium


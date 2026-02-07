# 🗄️ PARABANK.DB - DATABASE USAGE ANALYSIS

## Your Question
**"Any test case using parabank.db?"**

---

## 📊 ANSWER

### **Current Status**: ❌ NO explicit test cases use the database directly

**However**: The database infrastructure IS set up and ready to use.

---

## 🏗️ DATABASE INFRASTRUCTURE

### **Database File Location**
```
src/test/java/resources/DB/parabank.db
```

### **Database Configuration** (in config.properties)
```properties
dbName=parabank.db
dbDriverName=sqlite
```

### **Database Helper Class**
```
src/main/java/com/parabank/ui/PARABANK/helpers/SQLHelper.java
```

**Capabilities**:
- ✅ Connect to SQLite database
- ✅ Execute SELECT queries
- ✅ Execute INSERT queries
- ✅ Execute DELETE queries
- ✅ Execute DML queries
- ✅ Retrieve query results as Map<String, Object>

### **BaseTest Integration**
```java
// In @BeforeSuite (runs before all tests)
sqlObj = new SQLHelper();
connectDB();

// In @AfterSuite (runs after all tests)
sqlObj.closeConnection();
```

**Meaning**: Database connection is automatically initialized for every test suite execution!

---

## 📋 AVAILABLE DATABASE OPERATIONS

### SQLHelper Class Methods

**1. Connect to Database**
```java
sqlObj.getConnection(String dbDriverName, String dbPath)
```
- Called in BaseTest's globalSetup()
- Connects to: sqlite:src/test/java/resources/DB/parabank.db

**2. Close Connection**
```java
sqlObj.closeConnection()
```
- Called in BaseTest's tearDownSetup()
- Automatically closes after all tests

**3. Execute SELECT Query**
```java
List<Map<String,Object>> executeSimpleSelectQuery(String query)
```
- Returns results as list of maps
- Example: `SELECT * from USER_DETAILS`

**4. Execute INSERT/UPDATE/DELETE Query**
```java
void executeDMLQueries(String query)
```
- Updates database
- Example: `DELETE from USER_DETAILS where USERNAME='test'`

**5. Execute INSERT with Prepared Statement**
```java
void executeInserQueries(String query, Map<String,List<tableCellDetails>> data)
```
- Parameterized insert queries
- Safe against SQL injection

---

## 🗄️ AVAILABLE DATABASE TABLES

### USER_DETAILS Table
Based on the commented code in SQLHelper.java:

```sql
CREATE TABLE USER_DETAILS (
  ID INTEGER PRIMARY KEY AUTOINCREMENT,
  USERNAME VARCHAR(10),
  STATUS VARCHAR(1)
)
```

**Current Data** (can be queried with):
```java
String selectQuery = "SELECT * from USER_DETAILS;";
List<Map<String,Object>> userDetails = sqlObj.executeSimpleSelectQuery(selectQuery);
```

---

## 🎯 WHY NO TEST CASES USE IT (Currently)

**Possible Reasons**:
1. Database validation is secondary to UI testing
2. Focus is on Selenium web automation
3. Database operations may be done separately

**But You Can Create Tests That Use It!**

---

## 💡 USE CASES FOR parabank.db

### Scenario 1: Data-Driven Testing
```java
// Read test data from database instead of Excel
List<Map<String,Object>> testUsers = sqlObj.executeSimpleSelectQuery(
    "SELECT USERNAME, STATUS FROM USER_DETAILS"
);

for(Map<String,Object> user : testUsers) {
    // Use user data in test
    String username = user.get("USERNAME").toString();
    // ... perform test ...
}
```

### Scenario 2: Data Cleanup Before Tests
```java
// Clean database before each test
sqlObj.executeDMLQueries("DELETE FROM USER_DETAILS WHERE USERNAME='test'");
```

### Scenario 3: Data Initialization
```java
// Insert test data before test execution
String insertQuery = "INSERT INTO USER_DETAILS (USERNAME, STATUS) VALUES ('testuser', 'N')";
sqlObj.executeDMLQueries(insertQuery);
```

### Scenario 4: Data Validation
```java
// Verify database state after test
List<Map<String,Object>> results = sqlObj.executeSimpleSelectQuery(
    "SELECT * FROM USER_DETAILS WHERE USERNAME='newuser'"
);
Assert.assertTrue(results.size() > 0, "User not created in database");
```

---

## 📝 HOW TO CREATE A DB TEST CASE

### Example: Database Validation Test

```java
package com.parabank.ui.tests.pos;

import org.testng.annotations.Test;
import com.parabank.ui.base.BaseTest;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class POS_DB_Validation extends BaseTest {
    
    @Test(description = "Validate user data in database")
    public void testUserInDatabase() throws SQLException {
        // Query database
        String query = "SELECT * FROM USER_DETAILS WHERE USERNAME='testuser'";
        List<Map<String,Object>> results = sqlObj.executeSimpleSelectQuery(query);
        
        // Assertions
        assert results.size() > 0 : "User not found in database";
        assert results.get(0).get("STATUS").equals("N") : "Invalid status";
        
        System.out.println("✅ Database validation passed!");
    }
}
```

---

## 🚀 SHOULD YOU CREATE DB TEST CASES?

### ✅ YES, if you want to:
1. Validate data persisted in database
2. Test database-dependent features
3. Clean and initialize test data
4. Verify data consistency

### ❌ NO, if you only need:
1. UI/Web automation tests
2. API endpoint testing
3. Excel-based data-driven tests

---

## 📊 CURRENT TEST COVERAGE

| Test Type | Count | Uses DB? |
|-----------|-------|----------|
| Positive (POS) Tests | 9 | ❌ No |
| Negative (NEG) Tests | 2 | ❌ No |
| **Total** | **11** | **❌ No** |

---

## 🎯 DATABASE FILES LOCATION

```
src/test/java/resources/DB/
└── parabank.db ← SQLite database file
```

**Size**: Check file system for actual size
**Type**: SQLite 3
**Driver**: sqlite (configured in config.properties)

---

## ✅ SUMMARY

| Item | Status | Details |
|------|--------|---------|
| **Database file exists?** | ✅ Yes | src/test/java/resources/DB/parabank.db |
| **Database configured?** | ✅ Yes | In config.properties |
| **Helper class available?** | ✅ Yes | SQLHelper.java |
| **Connected in tests?** | ✅ Yes | In BaseTest @BeforeSuite |
| **Test cases using it?** | ❌ No | No direct test cases |
| **Can I use it?** | ✅ Yes | Ready to use! |

---

## 🎉 NEXT STEPS

### Option 1: Use Existing Database
- Database is already connected in BaseTest
- Just use `sqlObj` in your test methods
- Query/insert/update as needed

### Option 2: Create DB Test Cases
- Would you like me to create test cases using parabank.db?
- Examples: Validation, cleanup, initialization tests

### Option 3: Create Clean & Initialize Test
- Want me to create `POS_DB_CleanAndInitiate` test case?
- Cleans database and initializes test data

---

**Would you like me to create a database test case for you?** 🚀


# 🔧 RECOMMENDED INTELLIJ PLUGINS FOR PARABANK PROJECT

## Overview
Your PARABANK project uses:
- ✅ Selenium (Web Automation)
- ✅ TestNG (Test Framework)
- ✅ Maven (Build Tool)
- ✅ Java 17 (Language)
- ✅ Excel Files (Test Data)

Here are the best plugins to enhance your development experience.

---

## ⭐ TOP PLUGINS (Must Have)

### 1. **TestNG** ⭐⭐⭐⭐⭐ ESSENTIAL
**Category**: Testing Framework  
**What it does**: 
- Run TestNG tests directly from IDE
- View test results with detailed reports
- Navigate between tests easily
- Create test templates

**How to Install**:
1. Go to: **File** → **Settings** → **Plugins**
2. Search: `TestNG`
3. Click: **Install**
4. Restart IntelliJ

**How to Use**:
- Right-click test class → **Run** or **Debug with TestNG**
- See real-time results in test runner
- Click to jump to failures

---

### 2. **Selenium UI Framework** ⭐⭐⭐⭐⭐ RECOMMENDED
**Category**: Web Automation  
**What it does**:
- Selenium-specific code snippets
- Better support for WebDriver code
- Highlight Selenium methods
- Auto-completion for Selenium APIs

**How to Install**:
1. **File** → **Settings** → **Plugins**
2. Search: `Selenium`
3. Install best match
4. Restart

---

### 3. **Maven Helper** ⭐⭐⭐⭐⭐ ESSENTIAL
**Category**: Build Tool  
**What it does**:
- Visual Maven dependency tree
- Run Maven goals from IDE
- Resolve dependency conflicts
- Show transitive dependencies

**How to Install**:
1. **File** → **Settings** → **Plugins**
2. Search: `Maven Helper`
3. Install (by Vojtech Krasa)
4. Restart

**How to Use**:
- Right-click `pom.xml` → **Run Maven** → Choose goal
- View dependency tree easily
- Quick Maven command execution

---

### 4. **SonarLint** ⭐⭐⭐⭐⭐ CODE QUALITY
**Category**: Code Analysis  
**What it does**:
- Real-time code quality analysis
- Detect bugs and vulnerabilities
- Code smell detection
- Best practices suggestions

**How to Install**:
1. **File** → **Settings** → **Plugins**
2. Search: `SonarLint`
3. Install by SonarSource
4. Restart

**Benefits**:
- ✅ Red squiggly lines for issues
- ✅ Quick fix suggestions
- ✅ Improves code quality

---

### 5. **Selenium Framework for Java** ⭐⭐⭐⭐
**Category**: Test Automation  
**What it does**:
- Framework-specific debugging
- Selenium-specific inspections
- Better error messages
- Integration with TestNG

**How to Install**:
1. **File** → **Settings** → **Plugins**
2. Search: `Selenium Support`
3. Install
4. Restart

---

## 🔍 EXCELLENT ADDITIONAL PLUGINS

### 6. **REST Client** ⭐⭐⭐⭐
**Category**: API Testing  
**Why**: Your project uses REST-assured for API testing

**How to Install**:
1. **File** → **Settings** → **Plugins**
2. Search: `REST Client`
3. Install by JetBrains
4. Restart

**Use For**:
- Test REST APIs directly from IDE
- Create .http files
- No need for Postman

**Example Usage**:
```http
GET http://localhost:8080/parabank/login

###

POST http://localhost:8080/parabank/register
Content-Type: application/json

{
  "username": "testuser",
  "password": "password123"
}
```

---

### 7. **String Manipulation** ⭐⭐⭐⭐
**Category**: Productivity  
**What it does**:
- Swap, reverse, sort text
- Encode/decode strings
- Case conversions
- Useful for test data manipulation

**How to Install**:
1. **File** → **Settings** → **Plugins**
2. Search: `String Manipulation`
3. Install
4. Restart

---

### 8. **Rainbow Brackets** ⭐⭐⭐⭐
**Category**: Code Readability  
**What it does**:
- Color-code matching brackets
- Makes code easier to read
- Visual bracket matching

**How to Install**:
1. **File** → **Settings** → **Plugins**
2. Search: `Rainbow Brackets`
3. Install
4. Restart

---

### 9. **Lombok** ⭐⭐⭐⭐
**Category**: Code Generation  
**What it does**:
- Support for Lombok annotations
- Auto-generates getters/setters
- Reduces boilerplate code

**How to Install**:
1. **File** → **Settings** → **Plugins**
2. Search: `Lombok`
3. Install
4. **Also enable annotation processing**:
   - **File** → **Settings** → **Build, Execution, Deployment** → **Compiler** → **Annotation Processors**
   - Check: **Enable annotation processing**

---

### 10. **CheckStyle-IDEA** ⭐⭐⭐
**Category**: Code Style  
**What it does**:
- Check code style compliance
- Enforce coding standards
- Real-time style checking

**How to Install**:
1. **File** → **Settings** → **Plugins**
2. Search: `CheckStyle-IDEA`
3. Install
4. Restart

---

### 11. **Excel Viewer** ⭐⭐⭐⭐⭐ BONUS FOR TEST DATA
**Category**: File Viewer  
**What it does**:
- View .xlsx files directly in IntelliJ
- Spreadsheet preview with formatting
- Multiple sheet navigation
- Perfect for viewing TestData.xlsx

**How to Install**:
1. **File** → **Settings** → **Plugins**
2. Click: **Marketplace** tab
3. Search: `Excel Viewer`
4. Install by GrapeCity
5. Restart

**How to Use**:
- Right-click: `TestData.xlsx`
- Select: **Open with** → **Excel Viewer**
- View spreadsheet in formatted table!

**Why For Your Project**:
- ✅ View test data directly in IDE
- ✅ No external applications needed
- ✅ See what data is in TestData.xlsx
- ✅ Reference while writing tests

---

## 📊 PLUGINS FOR SPECIFIC NEEDS

### For Selenium Automation
- ✅ Selenium UI Framework
- ✅ TestNG
- ✅ REST Client (for API testing)

### For Code Quality
- ✅ SonarLint
- ✅ CheckStyle-IDEA
- ✅ Inspections (built-in)

### For Productivity
- ✅ Maven Helper
- ✅ String Manipulation
- ✅ Rainbow Brackets

### For Test Data (Excel)
- ✅ Excel Viewer (optional)
- ✅ CSV Editor (for data files)

### For Documentation
- ✅ Markdown Support (built-in)
- ✅ PlantUML Integration (for diagrams)

---

## 🎯 QUICK INSTALLATION GUIDE

### Method 1: From IDE (Easiest)
```
1. File → Settings → Plugins
2. Search for plugin name
3. Click Install
4. Restart IDE
```

### Method 2: From JetBrains Marketplace
```
1. Open: https://plugins.jetbrains.com/
2. Search for plugin
3. Click Install
4. IntelliJ will download and install
```

### Method 3: Manual Installation
```
1. Download plugin JAR file
2. File → Settings → Plugins → ⚙️ → Install Plugin from Disk
3. Select JAR file
4. Restart IDE
```

---

## 📋 INSTALLATION CHECKLIST

### Essential Plugins (Install These!)
- [ ] TestNG
- [ ] Maven Helper
- [ ] SonarLint
- [ ] Selenium UI Framework

### Recommended Plugins (Install These Too!)
- [ ] REST Client
- [ ] String Manipulation
- [ ] Rainbow Brackets
- [ ] Lombok
- [ ] CheckStyle-IDEA

### Optional Plugins
- [ ] PlantUML Integration
- [ ] CSV Editor
- [ ] Markdown Support (usually built-in)

---

## 🚀 TOP 5 MUST-HAVE PLUGINS FOR YOUR PROJECT

### Ranked by Usefulness:

1. **Maven Helper** ⭐⭐⭐⭐⭐
   - Essential for Maven project management
   - Run tests and builds from IDE
   - Dependency management

2. **TestNG** ⭐⭐⭐⭐⭐
   - Run tests directly
   - See results in real-time
   - Navigate test failures

3. **SonarLint** ⭐⭐⭐⭐⭐
   - Improve code quality
   - Catch bugs early
   - Best practice suggestions

4. **REST Client** ⭐⭐⭐⭐
   - Test API endpoints
   - No need for Postman
   - Direct IDE integration

5. **String Manipulation** ⭐⭐⭐⭐
   - Test data manipulation
   - Quick conversions
   - Productivity boost

---

## 💡 PRO TIPS

### Tip 1: Disable Unnecessary Plugins
- Go to: **File** → **Settings** → **Plugins**
- Uncheck plugins you don't use
- Faster IDE performance

### Tip 2: Update Plugins Regularly
- **File** → **Settings** → **Plugins** → **Updates**
- Install latest versions
- Better compatibility and features

### Tip 3: Create Plugin Groups
- Organize plugins by category
- File → Settings → Plugins → Sort by Category
- Easier management

### Tip 4: Check Plugin Compatibility
- Some plugins require specific Java versions
- Your project uses Java 17 - most plugins support this
- Check plugin page for requirements

---

## ⚙️ CONFIGURATION TIPS

### Enable Code Inspections
1. **File** → **Settings** → **Editor** → **Inspections**
2. Enable desired inspections
3. Right-click code for suggestions

### Configure Run Configurations
1. **Run** → **Edit Configurations**
2. Add TestNG test runner
3. Set VM options if needed

### Set Up Maven
1. **File** → **Settings** → **Build, Execution, Deployment** → **Maven**
2. Set Maven home directory
3. Configure JVM options

---

## 🎓 KEYBOARD SHORTCUTS (With Plugins)

| Shortcut | Action |
|----------|--------|
| **Ctrl+Shift+F10** | Run TestNG test |
| **Ctrl+Shift+F9** | Debug TestNG test |
| **Ctrl+Alt+M** | Extract method |
| **Ctrl+Alt+V** | Introduce variable |
| **Ctrl+Shift+Alt+M** | Run Maven goal |

---

## 📊 PLUGIN STATISTICS

### Most Important: 🔥
- TestNG
- Maven Helper
- SonarLint

### Very Useful: ⭐
- REST Client
- Selenium Framework
- String Manipulation

### Nice to Have: ✨
- Rainbow Brackets
- CheckStyle-IDEA
- Lombok

---

## 🔗 USEFUL LINKS

| Plugin | Download |
|--------|----------|
| Maven Helper | https://plugins.jetbrains.com/plugin/7179-maven-helper |
| TestNG | https://plugins.jetbrains.com/plugin/22812-testng |
| SonarLint | https://plugins.jetbrains.com/plugin/7973-sonarlint |
| REST Client | https://plugins.jetbrains.com/plugin/14456-rest-client |
| String Manipulation | https://plugins.jetbrains.com/plugin/2162-string-manipulation |

---

## ✅ AFTER INSTALLATION

### Verify Installation
1. **File** → **Settings** → **Plugins**
2. Search for installed plugin
3. Should show "Installed" badge
4. Restart IDE if needed

### Start Using
1. Right-click in project
2. Look for plugin options
3. Use plugin features

### Check Documentation
- Most plugins have help in IDE
- **Help** → **Find Action** → Plugin name
- Online documentation available

---

## 🎉 SUMMARY

**For Your PARABANK Project**, install these essential plugins:

1. ✅ **TestNG** - Run tests
2. ✅ **Maven Helper** - Manage Maven
3. ✅ **SonarLint** - Code quality
4. ✅ **REST Client** - API testing
5. ✅ **String Manipulation** - Productivity

**Total Installation Time**: ~10 minutes

**Expected Benefits**:
- ✅ Faster test execution
- ✅ Better code quality
- ✅ Improved productivity
- ✅ Easier debugging

---

**Status**: ✅ Complete Plugin Guide Provided  
**Recommendation**: Install top 5 plugins first  
**Date**: February 6, 2026

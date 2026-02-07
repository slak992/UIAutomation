# 📊 How to View & Work with .xlsx Files

## File Found in Your Project
**Location**: `src/test/java/resources/data/TestData.xlsx`

This Excel file contains your test data for the PARABANK automation framework.

---

## 🖥️ OPTION 1: View in Microsoft Excel (Best for Full Features)

### Windows
1. **If Excel is installed**:
   - Navigate to: `E:\AUTOMATION\PARABANK\src\test\java\resources\data\`
   - Right-click on `TestData.xlsx`
   - Select: **Open with** → **Microsoft Excel**
   - File opens with full functionality

2. **If Excel is NOT installed**:
   - Download: [Microsoft Excel (Paid)](https://www.microsoft.com/en-us/microsoft-365/excel)
   - Or: [Excel Online (Free)](https://www.office.com) - Web version

### Mac
1. Download: [Microsoft Excel for Mac](https://www.microsoft.com/en-us/microsoft-365/mac/microsoft-office-for-mac)
2. Open file in Excel

---

## 🌐 OPTION 2: View Online (Easiest - No Installation)

### Using Microsoft OneDrive
1. Go to: https://www.onedrive.live.com
2. Sign in with Microsoft account
3. Click: **Upload** 
4. Select: `TestData.xlsx`
5. Excel opens in browser (read-only or edit)

### Using Google Sheets
1. Go to: https://sheets.google.com
2. Click: **Upload** (File → Open)
3. Select: `TestData.xlsx`
4. File converts and opens in Google Sheets
5. **Advantages**:
   - ✅ Free
   - ✅ Automatic sync
   - ✅ Easy sharing
   - ✅ No installation needed

---

## 💻 OPTION 3: Open with Free Desktop Applications

### LibreOffice Calc (Recommended - Free)
**Windows**:
1. Download: https://www.libreoffice.org/
2. Install LibreOffice
3. Open file with LibreOffice Calc
4. Full Excel compatibility

**Mac/Linux**:
```bash
# Installation
sudo apt-get install libreoffice  # Linux
brew install libreoffice          # Mac

# Open file
libreoffice --calc TestData.xlsx
```

### WPS Office (Free)
1. Download: https://www.wps.com
2. Install WPS Office
3. Open Excel files
4. Good Excel compatibility

### Apache OpenOffice (Free)
1. Download: https://www.openoffice.org/
2. Install OpenOffice
3. Use Calc application
4. Open your .xlsx files

---

## 🔍 OPTION 4: View in Text Editor (Quick Peek)

### PowerShell (View Content)
```powershell
# Extract and view file structure
$excelPath = "E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx"

# XLSX is a ZIP file - rename to see structure
Copy-Item $excelPath "TestData.zip"
Expand-Archive "TestData.zip" -DestinationPath "TestData_extracted"

# View sheet data (XML format)
Get-Content "TestData_extracted\xl\worksheets\sheet1.xml"
```

### Command Line (Quick View)
```bash
# Windows - Using unzip
unzip TestData.xlsx

# View the extracted XML
type xl\worksheets\sheet1.xml
```

---

## 🎯 OPTION 5: View Programmatically (Java Code)

### Using Apache POI (Already in Your pom.xml!)

```java
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {
    public static void main(String[] args) throws IOException {
        // Read Excel file
        FileInputStream file = new FileInputStream(
            "src/test/java/resources/data/TestData.xlsx"
        );
        
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0); // First sheet
        
        System.out.println("📊 Excel File Contents:");
        System.out.println("=".repeat(50));
        
        // Iterate through rows
        for (Row row : sheet) {
            for (Cell cell : row) {
                System.out.print(cell.getStringCellValue() + "\t");
            }
            System.out.println();
        }
        
        workbook.close();
        file.close();
    }
}
```

### Using ExcelHelper (Your Own Class!)

Your project already has: `com.parabank.ui.PARABANK.helpers.ExecelHelper`

```java
// You can use it in your tests:
ExecelHelper excelHelper = new ExecelHelper();
List<Map<String, String>> testData = excelHelper.readExcelData(
    "src/test/java/resources/data/TestData.xlsx",
    "SheetName"
);

// Now you have all test data in memory
for (Map<String, String> row : testData) {
    System.out.println(row);
}
```

---

## 🖥️ OPTION 6: View in Visual Studio Code

### Method 1: Excel Viewer Extension
1. Open VS Code
2. Go to: **Extensions** (Ctrl+Shift+X)
3. Search: "Excel Viewer"
4. Install: **Excel Viewer** by GrapeCity
5. Right-click .xlsx file → **Open with Excel Viewer**
6. View in table format

### Method 2: View as Code
1. Right-click: `TestData.xlsx`
2. Select: **Open with Code**
3. You'll see XML/text representation
4. Not ideal but shows structure

---

## 🔧 OPTION 7: View via Command Line

### PowerShell (Windows)
```powershell
# Navigate to file location
cd E:\AUTOMATION\PARABANK\src\test\java\resources\data\

# View file info
Get-Item TestData.xlsx | Format-List *

# Open file (default application)
.\TestData.xlsx

# Open with Excel specifically
& 'C:\Program Files\Microsoft Office\Office16\EXCEL.EXE' TestData.xlsx
```

### Bash (Linux/Mac)
```bash
# Navigate to file
cd src/test/java/resources/data/

# View file info
ls -lh TestData.xlsx
file TestData.xlsx

# Open with default app
xdg-open TestData.xlsx      # Linux
open TestData.xlsx          # Mac

# Open with specific app
libreoffice --calc TestData.xlsx
```

---

## 📋 QUICK COMPARISON

| Method | Ease | Free | Features | Best For |
|--------|------|------|----------|----------|
| **Excel Desktop** | ✅ Easy | ❌ Paid | ⭐⭐⭐⭐⭐ | Full editing |
| **Google Sheets** | ✅ Very Easy | ✅ Free | ⭐⭐⭐⭐ | **Recommended** ✅ |
| **LibreOffice** | ✅ Easy | ✅ Free | ⭐⭐⭐⭐ | Desktop alternative |
| **Excel Online** | ✅ Easy | ✅ Free | ⭐⭐⭐⭐ | Quick view |
| **VS Code** | ⚠️ Medium | ✅ Free | ⭐⭐⭐ | Dev environment |
| **Java Code** | ⚠️ Medium | ✅ Free | ⭐⭐⭐ | Programmatic access |
| **Text Editor** | ⚠️ Hard | ✅ Free | ⭐ | Last resort |

---

## ⭐ RECOMMENDED: Google Sheets (Easiest)

### Why Google Sheets?
- ✅ **No installation** required
- ✅ **Completely free**
- ✅ **Easy to share** with team
- ✅ **Automatic formatting**
- ✅ **Works anywhere** (browser)
- ✅ **Offline access** available
- ✅ **Easy to edit** test data

### How to Use
1. Open: https://sheets.google.com
2. Click: **File** → **Open**
3. Search: **Upload** tab
4. Select: `TestData.xlsx`
5. Google converts automatically
6. Now you can view/edit easily

---

## 📖 Your Excel File Content

The file at `src/test/java/resources/data/TestData.xlsx` contains:
- **Test data** for your PARABANK tests
- **Multiple sheets** possibly (UI tests, etc.)
- **Rows and columns** with test scenarios
- **Used by**: `ExecelHelper` class in your code

### How Your Code Uses It
```java
// In BaseTest.java
excelData = this.excelHelperObj.readExcelData(
    System.getProperty("user.dir") + prop.getProperty("excelFilePath"),
    "UI"
);
```

Your project automatically reads this Excel file during test execution!

---

## 🎯 STEP-BY-STEP: View Your Excel File

### Fastest Way (Google Sheets - 2 minutes)
1. Go to: https://sheets.google.com
2. Sign in (or create account - free)
3. Click: **File** → **Open** 
4. Click: **Upload** tab
5. Drag or browse: `TestData.xlsx`
6. ✅ View and edit!

### Desktop Way (LibreOffice - 5 minutes)
1. Download: https://www.libreoffice.org/
2. Install LibreOffice
3. Open your file
4. ✅ Full editing capabilities!

### Quick Peek (No Installation)
1. Right-click: `TestData.xlsx`
2. Open with: Notepad
3. Scroll through XML
4. ⚠️ Harder to read but works!

---

## 🔗 USEFUL LINKS

- **Google Sheets**: https://sheets.google.com
- **Microsoft Excel Online**: https://www.office.com
- **LibreOffice**: https://www.libreoffice.org/
- **Apache POI Docs**: https://poi.apache.org/
- **Excel File Format**: https://en.wikipedia.org/wiki/Office_Open_XML

---

## 💡 TIPS

### Tip 1: Set Default Application
Windows:
1. Right-click: `.xlsx` file
2. Select: **Open with** → **Choose another app**
3. Select Excel or LibreOffice
4. Check: **Always use this app**
5. ✅ All .xlsx files open with that app

### Tip 2: Add to Favorites
- Google Sheets: Star the file in Drive
- Excel Desktop: Pin to Quick Access
- LibreOffice: Add to recent documents

### Tip 3: Keep Backup
- Before editing, create backup
- File → Save As → Keep original
- Prevents accidental data loss

### Tip 4: Share with Team
Google Sheets makes sharing easy:
1. Click: **Share**
2. Enter team emails
3. Set permissions (Edit/View)
4. ✅ Team can view/edit!

---

## ❓ FAQ

**Q: Can I view .xlsx without installation?**
A: Yes! Use Google Sheets or Excel Online (browser-based)

**Q: Is Google Sheets free?**
A: Yes! 15GB free storage for Google Account

**Q: Can I edit the Excel file?**
A: Yes! Google Sheets, Excel, or LibreOffice allow editing

**Q: How does my code read the Excel file?**
A: Uses Apache POI library (already in your pom.xml)

**Q: Can I use Excel Online for free?**
A: Yes! Microsoft Office Online is free with account

**Q: Best for team collaboration?**
A: Google Sheets - easy sharing and permissions

---

## 🚀 SUMMARY

**Your Excel File**: `src/test/java/resources/data/TestData.xlsx`

**To View It**:
- 🥇 **Best**: Google Sheets (free, no install)
- 🥈 **Good**: LibreOffice (free, desktop)
- 🥉 **Professional**: Microsoft Excel (paid, full features)

**To Use in Code**: Already integrated with `ExecelHelper` class!

---

**Status**: ✅ Complete Guide Provided  
**Recommendation**: Use Google Sheets for viewing/editing  
**Date**: February 6, 2026


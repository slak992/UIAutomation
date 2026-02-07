# 🚀 QUICK COMMANDS TO VIEW YOUR EXCEL FILE

## Your Excel File
```
E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx
```

---

## ⚡ ONE-LINER COMMANDS

### Option 1: Default Application (Fastest)
```powershell
Start-Process 'E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx'
```
**Result**: Opens with your default .xlsx application (Excel or LibreOffice)

---

### Option 2: Force Excel
```powershell
& 'C:\Program Files\Microsoft Office\Office16\EXCEL.EXE' 'E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx'
```
**Result**: Opens with Microsoft Excel (if installed)

---

### Option 3: Force LibreOffice
```powershell
& 'C:\Program Files\LibreOffice\program\soffice.exe' 'E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx'
```
**Result**: Opens with LibreOffice Calc (if installed)

---

### Option 4: Quick Peek in Notepad
```powershell
notepad 'E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx'
```
**Result**: Opens raw XML format (hard to read but works)

---

### Option 5: View File Info
```powershell
Get-Item 'E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx' | Format-List *
```
**Result**: Shows file size, creation date, modified date, etc.

---

## 🌐 Browser-Based (No Installation)

### Google Sheets
```
1. Go to: https://sheets.google.com
2. Click: File → Open → Upload
3. Select your TestData.xlsx
```

### Excel Online
```
1. Go to: https://www.office.com
2. Click: Upload
3. Select your TestData.xlsx
```

---

## 📍 Direct File Path
```
E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx
```

**Just double-click this file to open with default application!**

---

## 🎯 SIMPLEST METHOD

**In Windows File Explorer**:
1. Navigate to: `E:\AUTOMATION\PARABANK\src\test\java\resources\data\`
2. Find: `TestData.xlsx`
3. Double-click it
4. ✅ Opens!

---

**Status**: ✅ Quick Commands Ready to Use


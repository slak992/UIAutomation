# 📊 QUICK EXCEL VIEWING GUIDE

## Your Excel File Location
```
E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx
```

---

## ⚡ QUICKEST WAYS TO VIEW (Pick One)

### 🌐 OPTION 1: Google Sheets (Easiest) - 2 Minutes

1. Open browser → https://sheets.google.com
2. Sign in (free account)
3. **File** → **Open** → **Upload** tab
4. Drag `TestData.xlsx` to upload area
5. ✅ Done! View and edit

**Advantages**:
- ✅ No installation
- ✅ Free
- ✅ Easy sharing
- ✅ Works anywhere

---

### 💾 OPTION 2: Microsoft Excel - 1 Minute (If installed)

**Windows**:
1. Open file manager
2. Navigate to: `E:\AUTOMATION\PARABANK\src\test\java\resources\data\`
3. Double-click: `TestData.xlsx`
4. ✅ Opens in Excel

**Or use command**:
```powershell
& 'C:\Program Files\Microsoft Office\Office16\EXCEL.EXE' 'E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx'
```

---

### 🆓 OPTION 3: LibreOffice Calc (Free Alternative) - 5 Minutes

**Installation**:
```powershell
# Download from https://www.libreoffice.org/
# Or use package manager:

# Windows (via Chocolatey)
choco install libreoffice-fresh

# Then open file
C:\Program Files\LibreOffice\program\soffice.exe 'E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx'
```

---

### 🖥️ OPTION 4: Excel Online (Free Browser) - 1 Minute

1. Go to: https://www.office.com
2. Click: **Upload**
3. Select: `TestData.xlsx`
4. ✅ View in browser (no download needed)

---

### 📝 OPTION 5: Quick Text Peek (No Installation)

```powershell
# Right-click file → Open with → Notepad
# Or command line:
notepad 'E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx'

# View with PowerShell (shows first 100 lines)
Get-Content 'E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx' | Select-Object -First 100
```

---

## 📊 FILE INFORMATION

**File Path**: 
```
E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx
```

**Used By**: 
- `com.parabank.ui.PARABANK.helpers.ExecelHelper.java`
- Your test automation framework

**Contains**:
- Test data for PARABANK tests
- Multiple sheets (UI tests, etc.)
- Test scenarios and parameters

---

## 🎯 RECOMMENDED APPROACH

### For Viewing Only
→ **Google Sheets** (fastest, no install)

### For Editing
→ **Google Sheets** (easy sharing)

### For Full Features
→ **Microsoft Excel** (if you have it)

### For Free Desktop
→ **LibreOffice Calc** (good alternative)

---

## 📍 DIRECT FILE SHORTCUTS

### Windows File Explorer
```
E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx
```

### Command to Open
```powershell
# Default application
Start-Process 'E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx'

# Specific app - Excel
& 'C:\Program Files\Microsoft Office\Office16\EXCEL.EXE' 'E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx'

# Specific app - LibreOffice
& 'C:\Program Files\LibreOffice\program\soffice.exe' 'E:\AUTOMATION\PARABANK\src\test\java\resources\data\TestData.xlsx'
```

---

## ✅ SUMMARY

| Method | Time | Free | Install | Best For |
|--------|------|------|---------|----------|
| **Google Sheets** | 2 min | ✅ | ❌ | **Recommended** |
| **Excel** | 1 min | ❌ | ✅ | Full features |
| **Excel Online** | 1 min | ✅ | ❌ | Quick view |
| **LibreOffice** | 5 min | ✅ | ✅ | Desktop free |
| **Notepad** | 1 min | ✅ | ✅ | Quick peek |

---

**Choose Google Sheets (Fastest!)** → https://sheets.google.com


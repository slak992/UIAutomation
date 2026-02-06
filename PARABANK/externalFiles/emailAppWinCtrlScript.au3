#cs ----------------------------------------------------------------------------

	 AutoIt Version: 3.3.18.0
	 Author:         myName

	 Script Function:
		Template AutoIt script.

#ce ----------------------------------------------------------------------------

; Script Start - Add your code below here
$filePath = $CmdLine[1]
WinWaitActive("Open")
ControlSetText("Open","","Edit1",$filePath)
ControlClick("Open","","Button1")

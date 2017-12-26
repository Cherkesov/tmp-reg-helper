@echo off

:: Move distributives
md "%USERPROFILE%\tpl-reg-helper"
xcopy ".\blank-form.xls" "%USERPROFILE%\tpl-reg-helper"
xcopy ".\tpl-reg-helper-1.0-SNAPSHOT.jar" "%USERPROFILE%\tpl-reg-helper"

:: Create shortcut
echo [InternetShortcut] >> "%AllUsersProfile%\desktop\TplRH.url"
echo URL="C:\WINDOWS\NOTEPAD.EXE" >> "%AllUsersProfile%\desktop\TplRH.url"
echo IconFile=C:\WINDOWS\system32\SHELL32.dll >> "%AllUsersProfile%\desktop\TplRH.url"
echo IconIndex=20 >> "%AllUsersProfile%\desktop\TplRH.url"
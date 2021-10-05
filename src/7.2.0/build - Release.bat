@echo off

set WORKDIR=%CD%

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.

cd /d "%DIRNAME%"

rmdir build\classes /S /Q
rmdir build\sources /S /Q
rmdir build\resources /S /Q

call gradlew.bat build

xcopy "build\libs\*.jar" "..\..\7.2\*.jar" /Y /Q
xcopy "updateforge.json" "..\..\7.2\updateforge.json" /Y /Q

cd /d "%WORKDIR%"

pause
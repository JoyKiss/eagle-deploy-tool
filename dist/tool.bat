@echo off
setlocal

REM set JAVA_HOME=D:\Java\jdk1.8.0_65
REM set PATH=%JAVA_HOME%\bin;%PATH%

set CLASSPATH=.
FOR %%F IN (lib\*.jar) DO call :editClassPath %%F
goto editClassPathDone
:editClassPath 
SET CLASSPATH=%CLASSPATH%;%1
goto :eof
:editClassPathDone

java -classpath %CLASSPATH% com.linkstec.raptor.eagle.tool.Main

endlocal
echo on


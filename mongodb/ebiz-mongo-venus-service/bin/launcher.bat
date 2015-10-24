@rem ----------------------------------------------------------------------------
@rem ����Amoeba�Ľű�
@rem
@rem ��Ҫ�������»���������
@rem
@rem    JAVA_HOME           - JDK�İ�װ·��
@rem
@rem ----------------------------------------------------------------------------
@echo off
if "%OS%"=="Windows_NT" setlocal

:CHECK_JAVA_HOME
if not "%JAVA_HOME%"=="" goto SET_PROJECT_HOME

echo.
echo ����: �������û���������JAVA_HOME����ָ��JDK�İ�װ·��
echo.
goto END

:SET_PROJECT_HOME
set PROJECT_HOME=%~dp0..
if not "%PROJECT_HOME%"=="" goto START_PROJECT

echo.
echo ����: �������û���������PROJECT_HOME����ָ��Amoeba�İ�װ·��
echo.
goto END

:START_PROJECT


@REM FOR /F "tokens=1,* delims=.=" %%G IN (%PROJECT_HOME%\jvm.properties) DO (
@REM  	 set %%G=%%H
@REM  	 echo %%G=%%H
@REM  )


set DEFAULT_OPTS=-server -Xms256m -Xmx1024m -Xss196k
set DEFAULT_OPTS=%DEFAULT_OPTS% -XX:+HeapDumpOnOutOfMemoryError -XX:+AggressiveOpts -XX:+UseParallelGC -XX:+UseBiasedLocking -XX:NewSize=64m
set DEFAULT_OPTS=%DEFAULT_OPTS% "-Dproject.home=%PROJECT_HOME%"
set DEFAULT_OPTS=%DEFAULT_OPTS% "-Dproject.name=VENUS_BUS"
set DEFAULT_OPTS=%DEFAULT_OPTS% "-Dclassworlds.conf=%PROJECT_HOME%\bin\launcher.classpath"

set JAVA_EXE="%JAVA_HOME%\bin\java.exe"
set CLASSPATH="%PROJECT_HOME%\lib\plexus-classworlds-2.4.4-HEXNOVA.jar"
set MAIN_CLASS="org.codehaus.classworlds.Launcher"

%JAVA_EXE% %DEFAULT_OPTS% -classpath %CLASSPATH% %MAIN_CLASS% %*

:END
if "%OS%"=="Windows_NT" endlocal
pause
@echo off
set JAVA="%JAVA_HOME%\bin\java"
set CP=.;./cfg
for %%i in (.\lib\*.jar) do call cp.bat %%i
%JAVA% -server -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=2047 -Dlog.encode=false -Drmgr.env=DV  -Dcom.sun.management.jmxremote.port=9001 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dsecurity.disable=true  -classpath %CP% org.jpos.q2.Q2 -r

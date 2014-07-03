@echo off


Echo %CD%

cd C:\Users\aj60940\git\LOG735-lab3_2\src

Echo %CD%

REM compile les .java
javac Banque/Banque.java
javac Succursale/Succursale.java

REM part la Banque
start CMD /C java Banque/Banque
sleep 2

REM part les Succursale
REM FOR /L %%G IN (1,1,5) DO (
FOR /L %%G IN (1,1,2) DO (
    start CMD /C java Succursale/Succursale
    sleep 1
)



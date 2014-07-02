@echo off


REM compile les .java



REM part la Banque
start CMD /C java Banque/Banque
sleep 2

REM part les Succursale
FOR /L %%G IN (1,1,5) DO (
    start CMD /C java Succursale/Succursale
    sleep 1
)



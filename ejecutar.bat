@echo off
if "%~1"=="" (
    java -cp out juego.Main
) else (
    java -cp out %1
)

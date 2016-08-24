@echo off
color b
cls

title LeaderMS Starter
echo ############################ LeaderMS foi iniciado #############################
start /b launch_world.bat
echo                               Mundos carregados ...

ping localhost -w 10000 >nul
ping localhost -w 10000 >nul
start /b launch_login.bat
echo                                Login carregado ...
echo ################################################################################
echo ############################    Configuracoes  #################################
ping localhost -w 10000 >nul
ping localhost -w 10000 >nul
start /b launch_channel.bat
echo ################################################################################
echo                                Canais carregados ...
echo ############################   Listagem de Canais ##############################


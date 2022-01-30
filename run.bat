@echo off
call chcp 1251
call mvn clean install
start "vaccination" /min java -jar vaccination-supplier/target/vaccination-supplier-0.0.1-SNAPSHOT.jar
start "main" /min java -jar doctor-all-main/target/doctor-all-main-0.0.1-SNAPSHOT.jar
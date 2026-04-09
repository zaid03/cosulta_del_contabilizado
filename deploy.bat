@echo on

cd frontend
call ng build

cd ..

rmdir /s /q "backend\src\main\resources\static"
mkdir "backend\src\main\resources\static"

xcopy "frontend\dist\frontend\browser\*" "backend\src\main\resources\static" /E /I /Y

cd backend
call gradlew clean build

copy "build\libs\scap.war" "C:\Program Files\Tomcat 10\webapps" /Y

pause
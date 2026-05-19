@echo off
REM ============================================================
REM ShopFlow - Iniciar todos los microservicios en Laragon
REM Asegúrate que Laragon (MySQL) esté corriendo antes de ejecutar
REM ============================================================

echo Iniciando ShopFlow microservicios...
echo Asegurate que Laragon este corriendo con MySQL activo.
echo.

start "user-service       :8081" cmd /k "cd user-service && mvnw spring-boot:run"
timeout /t 5 /nobreak > nul

start "auth-service       :8082" cmd /k "cd auth-service && mvnw spring-boot:run"
timeout /t 3 /nobreak > nul

start "product-service    :8083" cmd /k "cd product-service && mvnw spring-boot:run"
timeout /t 3 /nobreak > nul

start "inventory-service  :8084" cmd /k "cd inventory-service && mvnw spring-boot:run"
timeout /t 3 /nobreak > nul

start "cart-service       :8085" cmd /k "cd cart-service && mvnw spring-boot:run"
timeout /t 3 /nobreak > nul

start "order-service      :8086" cmd /k "cd order-service && mvnw spring-boot:run"
timeout /t 3 /nobreak > nul

start "payment-service    :8087" cmd /k "cd payment-service && mvnw spring-boot:run"
timeout /t 3 /nobreak > nul

start "shipping-service   :8088" cmd /k "cd shipping-service && mvnw spring-boot:run"
timeout /t 3 /nobreak > nul

start "notification-service:8089" cmd /k "cd notification-service && mvnw spring-boot:run"
timeout /t 3 /nobreak > nul

start "review-service     :8090" cmd /k "cd review-service && mvnw spring-boot:run"

echo.
echo Todos los servicios iniciando. Espera ~30 segundos para que levanten.
echo.
echo Puertos disponibles:
echo   user-service        http://localhost:8081
echo   auth-service        http://localhost:8082
echo   product-service     http://localhost:8083
echo   inventory-service   http://localhost:8084
echo   cart-service        http://localhost:8085
echo   order-service       http://localhost:8086
echo   payment-service     http://localhost:8087
echo   shipping-service    http://localhost:8088
echo   notification-service http://localhost:8089
echo   review-service      http://localhost:8090

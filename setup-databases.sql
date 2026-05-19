-- =================================================
-- ShopFlow - Crear bases de datos en Laragon/MySQL
-- Ejecutar en HeidiSQL o en la terminal de Laragon:
--   mysql -u root -p < setup-databases.sql
-- =================================================

CREATE DATABASE IF NOT EXISTS shopflow_users     CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopflow_auth       CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopflow_products   CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopflow_inventory  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopflow_cart       CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopflow_orders     CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopflow_payment    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopflow_shipping   CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopflow_notification CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE IF NOT EXISTS shopflow_review     CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

SELECT 'Bases de datos creadas exitosamente.' AS resultado;
SHOW DATABASES LIKE 'shopflow_%';

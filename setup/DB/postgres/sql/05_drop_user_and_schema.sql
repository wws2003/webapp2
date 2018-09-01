-- User
DROP USER IF EXISTS :v1;

-- Database
DROP DATABASE IF EXISTS :v3;

-- Role (must be after dropping DB)
DROP ROLE IF EXISTS :v2;

-- Tablespace
DROP TABLESPACE IF EXISTS :v4;

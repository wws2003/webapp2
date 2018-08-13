-- Table space
--  TODO: Use setting variable (psql needed probably)
CREATE TABLESPACE MENDEL_TBSP LOCATION '/c/Users/trungpt/lab/mvn_modules/sample2/setup/DB/postgres/dataspace';

-- Database
CREATE DATABASE MENDEL_DB TABLESPACE MENDEL_TBSP;

-- Schema (not needed for now, all tables in the database would be in the public schema)

-- Role
CREATE ROLE MENDEL_ROLE;

GRANT ALL PRIVILEGES 
ON DATABASE MENDEL_DB
TO MENDEL_ROLE WITH GRANT OPTION;

-- User (with granted role)
--  TODO: Use setting variable (psql needed probably)
CREATE USER MENDEL_USER PASSWORD 'pass001';

GRANT MENDEL_ROLE
TO MENDEL_USER;
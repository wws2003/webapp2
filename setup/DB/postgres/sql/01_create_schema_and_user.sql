-- Table space
--  TODO: Use setting variable (psql needed probably)
CREATE TABLESPACE mendel_tbsp LOCATION '/Users/wws2003/Desktop/Apps/WEBAPPS/Mendel/lab/multi-modules/R2/setup/DB/postgres/dataspace';

-- Database
CREATE DATABASE mendel_db TABLESPACE mendel_tbsp;

-- Schema (not needed for now, all tables in the database would be in the public schema)

-- Role
CREATE ROLE mendel_role;

GRANT ALL PRIVILEGES 
ON DATABASE mendel_db
TO mendel_role WITH GRANT OPTION;

-- User (with granted role)
--  TODO: Use setting variable (psql needed probably)
CREATE USER mendel_user PASSWORD 'pass001';

GRANT mendel_role
TO mendel_user;
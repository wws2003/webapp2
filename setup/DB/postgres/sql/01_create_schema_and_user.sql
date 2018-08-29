-- Table space
CREATE TABLESPACE mendel_tbsp LOCATION :v1;

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

-- TODO: Table space, database, schema, role, privileges for log database (for experimental 2-phase commit)
-- Table space
CREATE TABLESPACE :v1 LOCATION :v2;

-- Database
CREATE DATABASE :v3 TABLESPACE :v1;

-- Schema (not needed for now, all tables in the database would be in the public schema)

-- Role
CREATE ROLE :v4;

GRANT ALL PRIVILEGES 
ON DATABASE :v3
TO :v4 WITH GRANT OPTION;

-- User (with granted role)
--  TODO: Use setting variable (psql needed probably)
CREATE USER :v5 PASSWORD :v6;

GRANT :v4 TO :v5;

-- TODO: Table space, database, schema, role, privileges for log database (for experimental 2-phase commit)
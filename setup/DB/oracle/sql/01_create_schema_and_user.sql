-- Table space
--  TODO: Use setting variable (psql needed probably)

-- Small file: Do not create a large single file for all objects
CREATE SMALLFILE TABLESPACE MENDEL_TBSP 
	DATAFILE '/c/Users/trungpt/lab/mvn_modules/sample2/setup/DB/oracle/dataspace/mendel_datafile_001.DBF'
	SIZE 10M AUTOEXTEND ON
	RETENTION GUARANTEE
	SEGMENT SPACE MANAGEMENT AUTO;

-- Schema (user) with default table space
CREATE USER MENDEL_USER 
	IDENTIFIED BY 'pass001'
	DEFAULT TABLESPACE MENDEL_TBSP;

-- Role, privileges may not needed if working as the DB owner (MENDEL_USER)

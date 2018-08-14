-- Choose the Database
\c mendel_db;

-- ================================Roles: Admin and User
INSERT INTO TBL_ROLE(
	name
) VALUES ('ADMIN');

INSERT INTO TBL_ROLE(
	name
) VALUES ('USER');

INSERT INTO TBL_ROLE(
	name
) VALUES ('GUEST');

INSERT INTO TBL_ROLE(
	name
) VALUES ('DBA');

-- ================================Privileges: Fixed ones
INSERT INTO TBL_PRIVILEGE(
	code, name
) VALUES ('M001', 'Manage users');

INSERT INTO TBL_PRIVILEGE(
	code, name
) VALUES ('M002', 'Manage system');

INSERT INTO TBL_PRIVILEGE(
	code, name
) VALUES ('M005', 'Create document');

INSERT INTO TBL_PRIVILEGE(
	code, name
) VALUES ('M006', 'Index document (for fulltext search)');

INSERT INTO TBL_PRIVILEGE(
	code, name
) VALUES ('M007', 'Comment public document');

INSERT INTO TBL_PRIVILEGE(
	code, name
) VALUES ('M008', 'List public document');

INSERT INTO TBL_PRIVILEGE(
	code, name
) VALUES ('M009', 'Search public user');


-- ================================User: Root and User Admin (password pass001)
INSERT INTO TBL_USER(
	name, displayed_name, role_id, password
) VALUES (
	'ROOT', 
	'Root Administrator', 
	(SELECT id FROM TBL_ROLE WHERE name = 'ADMIN'), 
	'$2a$10$VQWHAxM4PokPGX/L7I8dQefCw.QMmssZ754ISsRrkC7.EWis9bBMG'
);

INSERT INTO TBL_USER(
	name, displayed_name, role_id, password
) VALUES (
	'admin', 
	'User Administrator', 
	(SELECT id FROM TBL_ROLE WHERE name = 'ADMIN'), 
	'$2a$10$VQWHAxM4PokPGX/L7I8dQefCw.QMmssZ754ISsRrkC7.EWis9bBMG'
);

-- ================================User-Privilegs map: Privileges for root and user admin
INSERT INTO TBL_USER_PRIV(
	user_id, privilege_id
) VALUES (
	(SELECT id FROM TBL_USER WHERE name = 'ROOT'),
	(SELECT id FROM TBL_PRIVILEGE WHERE code = 'M001')
);

INSERT INTO TBL_USER_PRIV(
	user_id, privilege_id
) VALUES (
	(SELECT id FROM TBL_USER WHERE name = 'ROOT'),
	(SELECT id FROM TBL_PRIVILEGE WHERE code = 'M002')
);

INSERT INTO TBL_USER_PRIV(
	user_id, privilege_id
) VALUES (
	(SELECT id FROM TBL_USER WHERE name = 'UserAdmin'),
	(SELECT id FROM TBL_PRIVILEGE WHERE code = 'M001')
);


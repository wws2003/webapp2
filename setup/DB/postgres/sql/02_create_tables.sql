-- Choose the Database
\c :v1;

-- Roles
--  ID (2-bytes auto incremental, PK)
--  Name
DROP TABLE IF EXISTS TBL_ROLE;
CREATE TABLE TBL_ROLE(
	id smallserial PRIMARY KEY NOT NULL,
	name varchar(40) UNIQUE NOT NULL
);

-- Privileges
--  ID (2-bytes auto incremental, PK)
--  Name
DROP TABLE IF EXISTS TBL_PRIVILEGE;
CREATE TABLE TBL_PRIVILEGE(
	id smallserial PRIMARY KEY NOT NULL,
	code varchar(40) UNIQUE NOT NULL,
	name varchar(40) NOT NULL
);

-- User
--  ID (8-bytes auto incremental, PK)
--  Name (unique)
--  Role ID (foreign key)
--  Display_name
--  Password
--  CDate
--  MDate
--  Enabled (TRUE/FALSE)
DROP TABLE IF EXISTS TBL_USER;
CREATE TABLE TBL_USER(
	id bigserial PRIMARY KEY NOT NULL,
	name varchar(20) NOT NULL UNIQUE,
	displayed_name varchar(40) NOT NULL,
	role_id smallserial NOT NULL,
	password varchar(256) NOT NULL,
	FOREIGN KEY (role_id) REFERENCES TBL_ROLE(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- User-Privileges mapping
--  ID (8-bytes auto incremental, PK)
--  User_ID (foreign key)
--  Privilege_ID (foreign key)
--  User_ID + Privilege_ID -> Unique key
DROP TABLE IF EXISTS TBL_USER_PRIV;
CREATE TABLE TBL_USER_PRIV(
	id bigserial PRIMARY KEY NOT NULL,
	user_id bigint NOT NULL,
	privilege_id smallserial NOT NULL,
	FOREIGN KEY (user_id) REFERENCES TBL_USER(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (privilege_id) REFERENCES TBL_PRIVILEGE(id) ON UPDATE CASCADE ON DELETE CASCADE,
	UNIQUE(user_id, privilege_id)
);

-- Project
--  ID (8-bytes auto incremental, PK)
--  Code (unique)
--  Display name
--  Description
--  Status (1: Active, 2: Pending, 3: Close)
--  Refer scope (1: Public, 2: Private)
--  CDate
--  MDate
DROP TABLE IF EXISTS TBL_PROJECT;
CREATE TABLE TBL_PROJECT(
	id bigserial PRIMARY KEY NOT NULL,
	code varchar(8) NOT NULL,
	displayed_name varchar(32) NOT NULL,
	description varchar(4000) NOT NULL,
	status smallint NOT NULL,
	refer_scope smallint NOT NULL,
	cdate timestamp(3) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
	mdate timestamp(3) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
	UNIQUE(code)
);

-- User-Project mapping
--  ID (8-bytes auto incremental, PK)
--  User_ID (foreign key)
--  Project_ID (foreign key)
--  User_ID + Project_ID -> Unique key
DROP TABLE IF EXISTS TBL_USER_PROJECT;
CREATE TABLE TBL_USER_PROJECT(
	id bigserial PRIMARY KEY NOT NULL,
	user_id bigint NOT NULL,
	project_id bigint NOT NULL,
	FOREIGN KEY (user_id) REFERENCES TBL_USER(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (project_id) REFERENCES TBL_PROJECT(id) ON UPDATE CASCADE ON DELETE CASCADE,
	UNIQUE(user_id, project_id)
);

-- Document
--  ID (8-bytes auto incremental, PK)
--  Name
--  Type (1: Text origin, 2: Image origin)
--  Author_ID (foreign key -> user)
--  Description
--  Public document flag
--  CDate
--  MDate
DROP TABLE IF EXISTS TBL_DOCUMENT;
CREATE TABLE TBL_DOCUMENT(
	id bigserial PRIMARY KEY NOT NULL, 
	name varchar(20) NOT NULL,
	type smallint NOT NULL,
	author_id bigint NOT NULL,
	project_id bigint NOT NULL,
	description varchar(1000) NOT NULL,
	cdate timestamp(3) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
	mdate timestamp(3) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
	FOREIGN KEY (author_id) REFERENCES TBL_USER(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (project_id) REFERENCES TBL_PROJECT(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- References across documents
--  ID (8-bytes auto incremental, PK)
--  DocID1 (foreign key -> document)
--  DocID2 (foreign key -> document)
DROP TABLE IF EXISTS TBL_DOCUMENT_REFERENCE;
CREATE TABLE TBL_DOCUMENT_REFERENCE(
	id bigserial PRIMARY KEY NOT NULL,
	doc1_id bigint NOT NULL,
	doc2_id bigint NOT NULL,
	FOREIGN KEY (doc1_id) REFERENCES TBL_DOCUMENT(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (doc2_id) REFERENCES TBL_DOCUMENT(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- References across documents
--  ID (8-bytes auto incremental, PK)
--  PageNo
--  DocID (foreign key -> document)
--  Content
DROP TABLE IF EXISTS TBL_PAGE;
CREATE TABLE TBL_PAGE(
	id bigserial PRIMARY KEY NOT NULL,
	page_no bigint NOT NULL,
	doc_id bigint NOT NULL,
	content text NOT NULL,
	FOREIGN KEY (doc_id) REFERENCES TBL_DOCUMENT(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- POSTIT
--  ID (8-bytes auto incremental, PK)
--  Document ID (foreign key -> document)
--  Creator ID (foreign key -> user)
--  Content
--  Coordinates
--  Color
--  Border color
--  Comment
--  ...
DROP TABLE IF EXISTS TBL_POSTIT;
CREATE TABLE TBL_POSTIT(
	id bigserial PRIMARY KEY NOT NULL,
	page_id bigint NOT NULL,
	creator_id bigint NOT NULL,
	content varchar(256) NOT NULL,
	comment text NOT NULL,
	color integer NOT NULL,
	border_color integer NOT NULL,
	coord box NOT NULL,
	cdate timestamp(3) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
	mdate timestamp(3) with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
	FOREIGN KEY (page_id) REFERENCES TBL_POSTIT(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (creator_id) REFERENCES TBL_USER(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Grant roles for all tables
GRANT ALL PRIVILEGES
ON ALL TABLES IN SCHEMA public
TO :v2 WITH GRANT OPTION;

-- Grant roles for all sequences
GRANT ALL PRIVILEGES
ON ALL SEQUENCES IN SCHEMA public
TO :v2 WITH GRANT OPTION;

-- Functions and triggers
CREATE OR REPLACE FUNCTION FUNC_UPDATE_MDATE()
RETURNS TRIGGER AS $$
BEGIN
   NEW.mdate = now(); 
   RETURN NEW;
END;
$$ language 'plpgsql';

DROP TRIGGER IF EXISTS TRG_UPDATE_MDATE_PROJECT ON TBL_PROJECT;
CREATE TRIGGER TRG_UPDATE_MDATE BEFORE UPDATE
	ON TBL_PROJECT
	FOR EACH ROW
	EXECUTE PROCEDURE FUNC_UPDATE_MDATE()

DROP TRIGGER IF EXISTS TRG_UPDATE_MDATE_DOCUMENT ON TBL_DOCUMENT;
CREATE TRIGGER TRG_UPDATE_MDATE BEFORE UPDATE
	ON TBL_DOCUMENT
	FOR EACH ROW
	EXECUTE PROCEDURE FUNC_UPDATE_MDATE()

-- TODO: Grant roles for all views (if any)

-- TODO: Tables for log database (for experimental 2-phase commit)


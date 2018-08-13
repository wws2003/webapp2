-- Roles
--  ID (2-bytes auto incremental, PK)
--  Name
DROP TABLE IF EXISTS TBL_ROLE;
CREATE TABLE TBL_ROLE(
	id NUMBER(2,0) PRIMARY KEY NOT NULL,
	name varchar2(40) UNIQUE NOT NULL
);

-- Privileges
--  ID (2-bytes auto incremental, PK)
--  Name
DROP TABLE IF EXISTS TBL_PRIVILEGE;
CREATE TABLE TBL_PRIVILEGE(
	id NUMBER(2,0) PRIMARY KEY NOT NULL,
	code varchar2(40) UNIQUE NOT NULL,
	name varchar2(40) NOT NULL
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
	id NUMBER(10,0) PRIMARY KEY NOT NULL,
	name varchar2(20) NOT NULL UNIQUE,
	displayed_name varchar2(40) NOT NULL,
	role_id smallserial NOT NULL,
	password varchar2(256) NOT NULL,
	FOREIGN KEY (role_id) REFERENCES TBL_ROLE(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- User-Privileges mapping
--  ID (8-bytes auto incremental, PK)
--  User_ID (foreign key)
--  Privilege_ID (foreign key)
--  User_ID + Privilege_ID -> Unique key
DROP TABLE IF EXISTS TBL_USER_PRIV;
CREATE TABLE TBL_USER_PRIV(
	id NUMBER(10,0) PRIMARY KEY NOT NULL,
	user_id NUMBER(10,0) NOT NULL,
	privilege_id smallserial NOT NULL,
	FOREIGN KEY (user_id) REFERENCES TBL_USER(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (privilege_id) REFERENCES TBL_PRIVILEGE(id) ON UPDATE CASCADE ON DELETE CASCADE,
	UNIQUE(user_id, privilege_id)
);

-- Document
--  ID (8-bytes auto incremental, PK)
--  Name
--  Type (1: Text origin, 2: Image origin)
--  Author_ID (foreign key -> user)
--  Content
--  Public document flag
--  CDate
--  MDate
DROP TABLE IF EXISTS TBL_DOCUMENT;
CREATE TABLE TBL_DOCUMENT(
	id NUMBER(10,0) PRIMARY KEY NOT NULL, 
	name varchar2(20) NOT NULL,
	type smallint NOT NULL,
	author_id bigint NOT NULL,
	content text NOT NULL,
	public boolean NOT NULL,
	cdate timestamp(3) with time zone NOT NULL,
	mdate timestamp(3) with time zone NOT NULL,
	FOREIGN KEY (author_id) REFERENCES TBL_USER(id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- References across documents
--  ID (8-bytes auto incremental, PK)
--  DocID1 (foreign key -> document)
--  DocID2 (foreign key -> document)
DROP TABLE IF EXISTS TBL_DOCUMENT_REFERENCE;
CREATE TABLE TBL_DOCUMENT_REFERENCE(
	id NUMBER(10,0) PRIMARY KEY NOT NULL,
	doc1_id bigint NOT NULL,
	doc2_id bigint NOT NULL,
	FOREIGN KEY (doc1_id) REFERENCES TBL_DOCUMENT(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (doc2_id) REFERENCES TBL_DOCUMENT(id) ON UPDATE CASCADE ON DELETE CASCADE
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
	id NUMBER(10,0) PRIMARY KEY NOT NULL,
	document_id bigint NOT NULL,
	creator_id bigint NOT NULL,
	content varchar2(256) NOT NULL,
	comment text NOT NULL,
	color integer NOT NULL,
	border_color integer NOT NULL,
	coord box NOT NULL,
	FOREIGN KEY (document_id) REFERENCES TBL_DOCUMENT(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (creator_id) REFERENCES TBL_USER(id) ON UPDATE CASCADE ON DELETE CASCADE
);




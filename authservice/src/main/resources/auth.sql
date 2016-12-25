--Таблица пользователей
create table USERS( USER_ID NUMBER NOT NULL,
		    LOGIN VARCHAR2(21) NOT NULL,
		    PASS VARCHAR2(16) NOT NULL,
		    DEL_USER NUMBER,
		    constraint PK_USER primary key (USER_ID),
		    constraint UN_LOGIN unique(LOGIN),
                    constraint DEL_USERS foreign key(DEL_USER ) REFERENCES USERS(USER_ID));

--Таблица Ролей
create table ROLES(ROLE_ID NUMBER NOT NULL,
		   ROLE_NAME VARCHAR2(21) NOT NULL,
		   constraint PK_ROLE primary key (ROLE_ID),
		   constraint UN_NAME unique(ROLE_NAME));


--Таблица операций
create table OPERATIONS(OPER_ID NUMBER NOT NULL,
			OPER_NAME VARCHAR2(21) NOT NULL,
		   	constraint PK_OPER primary key (OPER_ID),
		   	constraint UN_OPNAME unique(OPER_NAME));

--Таблица операций доступных ролям
create table ROLE_OPERATION(RLOP_ID NUMBER NOT NULL,
			    ROLE_ROLE_ID NUMBER NOT NULL,
		            OPER_OPER_ID NUMBER NOT NULL,
		            constraint PK_USRK primary key (RLOP_ID),
		            constraint UN_RLOP unique(ROLE_ROLE_ID, OPER_OPER_ID),
			    constraint FKRP_ROLE foreign key (ROLE_ROLE_ID) REFERENCES ROLES(ROLE_ID),
			    constraint FKRP_OPER foreign key (OPER_OPER_ID) REFERENCES OPERATIONS(OPER_ID));


--Таблица ролей доступных пользователям
create table USER_ROLE(USRL_ID  NUMBER NOT NULL,
		       ROLE_ROLE_ID NUMBER NOT NULL,
		       USER_USER_ID NUMBER NOT NULL,
		       constraint PK_USR primary key (USRL_ID),
		       constraint UN_USRL unique(ROLE_ROLE_ID, USER_USER_ID),
		       constraint FKUR_ROLE  foreign key (ROLE_ROLE_ID) REFERENCES ROLES(ROLE_ID),
		       constraint FKUR_USER foreign key(USER_USER_ID) REFERENCES USERS(USER_ID));

--Создание последовательности для таблицы USERS
CREATE SEQUENCE USER_SEQ;
--Создание последовательности для таблицы ROLES
CREATE SEQUENCE ROLE_SEQ;
--Создание последовательности для таблицы OPERATIONS
CREATE SEQUENCE OPER_SEQ;
--Создание последовательности для таблицы ROLE_OPERATION
CREATE SEQUENCE RLOP_SEQ;
--Создание последовательности для таблицы USER_ROLE
CREATE SEQUENCE USRL_SEQ;

INSERT INTO ROLES VALUES(ROLE_SEQ.NEXTVAL, 'admin');
INSERT INTO ROLES VALUES(ROLE_SEQ.NEXTVAL, 'user');
--Создание системного пользователя
INSERT INTO USERS(USER_ID, LOGIN, PASS) VALUES(USER_SEQ.NEXTVAL, 'system', 'administrator');
INSERT INTO USER_ROLE VALUES(USRL_SEQ.NEXTVAL,1,1);


































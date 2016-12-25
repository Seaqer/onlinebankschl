--Таблица Типа счетов
create table ACCOUNT_TYPE(ACTP_ID NUMBER NOT NULL, 
		   ACTP_NAME VARCHAR2(21) NOT NULL,
		   constraint ACTP_CH_ID primary key (ACTP_ID),
		   constraint ACTP_CH_NM unique(ACTP_NAME));

--Таблица статусов
create table STATUS(STAT_ID NUMBER NOT NULL, 
		   STAT_NAME VARCHAR2(21) NOT NULL,
		   constraint STAT_CH_ID primary key (STAT_ID),
		   constraint STAT_CH_NM unique(STAT_NAME));


--Таблица статусов
create table ACCOUNT(	ACC_ID NUMBER NOT NULL, 
			ACC_NUMBER NUMBER NOT NULL,
			BALANCE NUMBER (10,2) NOT NULL,
			ACTP_ACTP_ID NUMBER NOT NULL,
			CLIENT_ID NUMBER NOT NULL, 
			CREATION_DATE DATE NOT NULL,
			STAT_STAT_ID NUMBER NOT NULL, 
		   	constraint ACC_CH_ID primary key (ACC_ID),
		   	constraint ACC_CH_NM unique(ACC_NUMBER),
			constraint FK_ACTP_ID foreign key (ACTP_ACTP_ID) REFERENCES ACCOUNT_TYPE(ACTP_ID),
			constraint FK_STAT_ID foreign key (STAT_STAT_ID) REFERENCES STATUS(STAT_ID));

--Таблица с картами
create table Card(	CRD_ID NUMBER NOT NULL, 
			CRD_NUMBER VARCHAR2(16) NOT NULL,
			CRD_OWNER  VARCHAR2(21) NOT NULL,
			CRD_CVV VARCHAR2(3) NOT NULL,
			EXIPIRY_DATE DATE NOT NULL,
			SECURE_CODE VARCHAR2(4) NOT NULL,
			ACC_ACC_ID NUMBER NOT NULL,
			STAT_STAT_ID NUMBER NOT NULL, 
		            constraint CRD_CH_ID primary key (CRD_ID),
		            constraint CRD_NUMBER unique(CRD_NUMBER),
			    constraint FKSTAT_STAT foreign key (STAT_STAT_ID) REFERENCES STATUS(STAT_ID),
			    constraint FKRP_OPER foreign key (ACC_ACC_ID) REFERENCES ACCOUNT(ACC_ID));

--Таблица с платежами
create table Transfers(	TRNS_ID NUMBER NOT NULL, 
			AMOUNT NUMBER (10,2) NOT NULL,
			FROM_ACCOUNT NUMBER NOT NULL,
			TO_ACCOUNT NUMBER NOT NULL,
			TRNS_DATE DATE NOT NULL DEFAULT SYSDATE,
		            constraint TRNS_CH_ID primary key (TRNS_ID),
			    constraint FROM_ACC_NUM foreign key (FROM_ACCOUNT) REFERENCES ACCOUNT(ACC_ID),
			    constraint TO_ACC_NUM foreign key (TO_ACCOUNT) REFERENCES ACCOUNT(ACC_ID));

--Создание последовательности для ACTP_ID
CREATE SEQUENCE ACTP_ID_SEQ;
--Создание последовательности для STAT_ID
CREATE SEQUENCE STAT_ID_SEQ;
--Создание последовательности для ACC_ID
CREATE SEQUENCE ACC_ID_SEQ;
--Создание последовательности для ACC_NUMBER
CREATE SEQUENCE ACC_NUMBER_SEQ;
--Создание последовательности для TRNS_ID
CREATE SEQUENCE TRNS_ID_SEQ;


insert into STATUS  VALUES(STAT_ID_SEQ.NEXTVAL,'Открыт');
insert into STATUS  VALUES(STAT_ID_SEQ.NEXTVAL,'Закрыт');

INSERT INTO ACCOUNT_TYPE VALUES(ACTP_ID_SEQ.NEXTVAL,'Вклад);
INSERT INTO ACCOUNT_TYPE VALUES(ACTP_ID_SEQ.NEXTVAL,'Карта');

INSERT INTO ACCOUNT VALUES(ACC_ID_SEQ.NEXTVAL,ACC_NUMBER_SEQ.NEXTVAL,1,1,SYSDATE,1);



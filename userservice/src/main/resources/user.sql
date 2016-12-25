create table CLIENTS(	CLN_ID NUMBER NOT NULL, 
                                        USER_USER_ID NUMBER NOT NULL, 
                                         FIST_NAME VARCHAR2(21) NOT NULL,
			                LAST_NAME VARCHAR2(16) NOT NULL,
			                SECOND_NAME VARCHAR2(16) NOT NULL,
			                INN NUMBER NOT NULL,
		                        constraint CLN_CH_ID primary key (CLN_ID),
		                        constraint USR_UNIQ unique(USER_USER_ID),
			                constraint INN_UNIQ unique(INN));



CREATE SEQUENCE CLIENT_SEQ;
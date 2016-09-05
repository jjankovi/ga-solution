
    create table APP_PARAM (
        ID varchar2(255 char) not null,
        description varchar2(255 char),
        PARAM_TYPE varchar2(255 char),
        value varchar2(255 char),
        primary key (ID)
    );

    create table GA_EVENT (
        ID number(19,0) not null,
        CID varchar2(255 char),
        CREATION_TS raw(255),
        GA_EVENT_STATUS varchar2(255 char),
        GA_APP_ID varchar2(255 char),
        primary key (ID)
    );

    create sequence GA_EVENT_SEQ;


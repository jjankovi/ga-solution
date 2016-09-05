
    create table Client (
        ID number(19,0) not null,
        BIRTHDAY date,
        FIRST_NAME varchar2(255 char),
        LAST_NAME varchar2(255 char),
        LOGIN varchar2(255 char),
        PASSWORD varchar2(255 char),
        primary key (ID)
    );

    create sequence CLIENT_SEQ;

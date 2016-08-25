
    create table Client (
        ID number(19,0) not null,
        login varchar2(255 char),
        name varchar2(255 char),
        password varchar2(255 char),
        primary key (ID)
    );

    create sequence CLIENT_SEQ;

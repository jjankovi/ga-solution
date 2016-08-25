
    create table CRM_CLIENT_RATE (
        CLIENT_ID number(19,0) not null,
        ltv number(19,0),
        primary key (CLIENT_ID)
    );

    create table CRM_EVENT (
        ID number(19,0) not null,
        CREATION_TS timestamp,
        EVENT_TYPE varchar2(255 char),
        CLIENT_ID number(19,0) not null,
        primary key (ID)
    );

    alter table CRM_EVENT 
        add constraint FK_tkt84bpwtujy22if92vmbekoe 
        foreign key (CLIENT_ID) 
        references CRM_CLIENT_RATE;

    create sequence CRM_EVENT_SEQ;

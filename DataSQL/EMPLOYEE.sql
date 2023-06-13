create table EMPLOYEE
(
    EMPLOYEE_ID INTEGER           not null
        primary key,
    EMAIL       VARCHAR(255),
    FIRSTNAME   VARCHAR(255),
    LASTNAME    VARCHAR(255),
    PASSWORD    VARCHAR(255),
    USERNAME    VARCHAR(255),
    VERSION     INTEGER default 0 not null
);

INSERT INTO APP.EMPLOYEE (EMPLOYEE_ID, EMAIL, FIRSTNAME, LASTNAME, PASSWORD, USERNAME, VERSION) VALUES (20, 'newemployee@example.com', 'T', 'B', 'a', 'T', 0);

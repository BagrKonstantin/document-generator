CREATE TABLE roles
(
    role_id serial2,
    title   varchar(20),
    UNIQUE (title),
    CONSTRAINT roles_pk PRIMARY KEY (role_id)
);

CREATE TABLE document_types
(
    document_type_id int2,
    tittle           varchar(30),
    UNIQUE (tittle),
    CONSTRAINT document_type_pk PRIMARY KEY (document_type_id)
);



CREATE TABLE person
(
    person_id int8         NOT NULL GENERATED ALWAYS AS IDENTITY,
    firstname varchar(20)  NOT NULL,
    surname   varchar(20)  NOT NULL,
    lastname  varchar(20)  NOT NULL,
    status    varchar(200) NOT NULL,
    CONSTRAINT person_pk PRIMARY KEY (person_id)
);

CREATE TABLE faculty
(
    faculty_id int4         NOT NULL GENERATED ALWAYS AS IDENTITY,
    title      varchar(100) NOT NULL,
    CONSTRAINT faculty_pk PRIMARY KEY (faculty_id)
);

CREATE TABLE department
(
    department_id int4         NOT NULL GENERATED ALWAYS AS IDENTITY,
    faculty_id    int4         NOT NULL,
    title         varchar(100) NOT NULL,

    CONSTRAINT department_pk PRIMARY KEY (department_id),
    CONSTRAINT faculty_fk FOREIGN KEY (faculty_id) REFERENCES faculty (faculty_id)

);


CREATE TABLE chapters
(
    chapter_id int2         NOT NULL GENERATED ALWAYS AS IDENTITY,
    title      varchar(200) NOT NULL,
    code       varchar(2)   NOT NULL,
    UNIQUE (code),
    CONSTRAINT chapter_pk PRIMARY KEY (chapter_id)
);

CREATE TABLE classes
(
    class_id    int2          NOT NULL GENERATED ALWAYS AS IDENTITY,
    chapter_id  int2          NOT NULL,
    title       varchar(300)  NOT NULL,
    description varchar(1000) NOT NULL,
    code        varchar(2)    NOT NULL,
    UNIQUE (code, chapter_id),
    CONSTRAINT class_pk PRIMARY KEY (class_id),
    CONSTRAINT chapter_fk FOREIGN KEY (chapter_id) REFERENCES chapters (chapter_id)
);


CREATE TABLE "user"
(
    user_id         int8        NOT NULL GENERATED ALWAYS AS IDENTITY,
    firstname       varchar(20) NOT NULL,
    surname         varchar(20) NOT NULL,
    lastname        varchar(20) NOT NULL,
    email           varchar(30) NOT NULL,
    username        varchar(50),
    hashed_password varchar(100),
    CONSTRAINT users_pk PRIMARY KEY (user_id),
    UNIQUE (email)
);

CREATE TABLE user_roles
(
    user_id int8 NOT NULL,
    role_id int2 NOT NULL,
    CONSTRAINT roles2_fk FOREIGN KEY (role_id) REFERENCES roles (role_id),
    CONSTRAINT users2_fk FOREIGN KEY (user_id) REFERENCES "user" (user_id)
);

CREATE TABLE document
(
    document_id        int8    NOT NULL GENERATED ALWAYS AS IDENTITY,
    document_type_id   int2,
    user_id            int8,
    teacher_id         int8,
    head_teacher_id    int8,
    department_id      int4,

    year_of_work       int2             DEFAULT EXTRACT('YEAR' FROM CURRENT_DATE),
    program_name       varchar(200),
    program_short_name varchar(100),
    program_name_en    varchar(200),
    description        varchar(1000),
    by_document        varchar(100),

    class_id           int2,

    CONSTRAINT sample_pk PRIMARY KEY (document_id),
    CONSTRAINT document_type_fk FOREIGN KEY (document_type_id) REFERENCES document_types (document_type_id),
    CONSTRAINT users_fk FOREIGN KEY (user_id) REFERENCES "user" (user_id),
    CONSTRAINT teacher_fk FOREIGN KEY (teacher_id) REFERENCES person (person_id),
    CONSTRAINT head_teacher_fk FOREIGN KEY (head_teacher_id) REFERENCES person (person_id),
    CONSTRAINT department_fk FOREIGN KEY (department_id) REFERENCES department (department_id),
    CONSTRAINT class_fk FOREIGN KEY (class_id) REFERENCES classes (class_id)
);


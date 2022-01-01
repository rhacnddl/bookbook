create table posts (
                       id bigint not null auto_increment,
                       created_date datetime(6),
                       modified_date datetime(6),
                       author varchar(255),
                       content TEXT not null,
                       title varchar(500) not null,
                       primary key (id)
) engine=InnoDB;
create table users (
                       id bigint not null auto_increment,
                       created_date datetime(6),
                       modified_date datetime(6),
                       email varchar(255) not null,
                       name varchar(255) not null,
                       picture varchar(255),
                       role varchar(255) not null,
                       primary key (id)
) engine=InnoDB;
CREATE TABLE SPRING_SESSION_ATTRIBUTES (
                                           SESSION_PRIMARY_ID CHAR(36) NOT NULL,
                                           ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
                                           ATTRIBUTE_BYTES BLOB NOT NULL,
                                           CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
                                           CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
) ENGINE=InnoDB ROW_FORMAT=DYNAMIC;
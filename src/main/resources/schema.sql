drop table if exists USER_DETAILS;
drop table if exists AUTHORITIES;
drop table if exists USER_CONFIG;
drop table if exists USER_ACCESS;

CREATE TABLE USERS (
  USER_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
  USERNAME VARCHAR(50) NOT NULL UNIQUE,
  PASSWORD VARCHAR(500) NOT NULL,
  ENABLED BOOLEAN NOT NULL
);

CREATE TABLE AUTHORITIES(
AUTH_ID BIGINT AUTO_INCREMENT PRIMARY KEY ,
USER_ID BIGINT NOT NULL,
AUTHORITY VARCHAR(50) NOT NULL,
FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID),
UNIQUE(USER_ID,AUTHORITY)
);

CREATE TABLE USER_CONFIG(
config_id bigint AUTO_INCREMENT primary key,
user_id BIGINT NOT NULL,
uri VARCHAR(250) NOT NULL,
rate_limit int default 20,
foreign key(user_id) references USERS(user_id));

CREATE TABLE USER_ACCESS(
access_id bigint AUTO_INCREMENT primary key,
config_id bigint,
request_arr CLOB,
foreign key(config_id) references user_config(config_id)
);
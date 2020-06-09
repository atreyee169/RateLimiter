INSERT INTO USERS(USER_ID,USERNAME,PASSWORD,ENABLED) VALUES(101,'user_1','$2a$11$CofoWwhd3isrretUthbFD.eU3xVihT1qLVXqkOkukC4bC32TNsAha',true),
(102,'user_2','$2a$11$2hXDxBruWRR0QRHxZjTy.uPNXWJEL062eD0r3sl7S3NlmFQB7ezpC',true),
(103,'admin','$2a$11$s8YPmk4/1MhDsGoymJUFSeYd9GDgHvHIu2yAiK4qaaD3NdMpXkHG2',true);

INSERT INTO AUTHORITIES(USER_ID,AUTHORITY) VALUES(101,'ROLE_USER'),
(102,'ROLE_USER'),
(103,'ROLE_USER'),
(103,'ROLE_ADMIN');

INSERT INTO USER_CONFIG(CONFIG_ID,USER_ID,URI,RATE_LIMIT) VALUES(10,101,'/api/v1/developers',100),
(11,102,'/api/v1/developers',50),
(12,101,'/api/v1/organizations',250),
(13,102,'/api/v1/organizations',500),
(14,103,'/api/v1/developers',5),
(15,103,'/api/v1/organizations',4);


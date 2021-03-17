CREATE TABLE IF NOT EXISTS USERS
(
  ID_USER     SERIAL                              not null
    constraint USERS_PK
      primary key,
  NAME        VARCHAR(100)                        not null,
  LAST_NAME   VARCHAR(100)                        not null,
  EMAIL       VARCHAR(200)                        not null,
  PASSWORD    VARCHAR(200)                        not null,
  ROLE        VARCHAR(50),
  STATUS      int       DEFAULT 1                 not null,
  DT_REGISTER TIMESTAMP default CURRENT_TIMESTAMP not null
);

CREATE TABLE IF NOT EXISTS GAMA
(
  ID_GAMA     SERIAL         not null
    constraint GAMA_PK
      primary key,
  NAME        VARCHAR(100)   not null,
  DESCRIPTION VARCHAR(500)   not null,
  PRICE       NUMERIC(12, 4) NOT NULL,
  STATUS      int DEFAULT 1  not null
);

CREATE TABLE IF NOT EXISTS CARS
(
  LICENSE_PLATE VARCHAR(50)  not null
    constraint CARS_PK
      primary key,
  BRAND         VARCHAR(100) not null,
  MODEL         VARCHAR(100) not null,
  TYPE          VARCHAR(100) not null,
  DESCRIPTION   VARCHAR(500) not null,
  ID_GAMA       BIGINT       NOT NULL
    constraint FK_CARS_GAMA
      references GAMA (ID_GAMA),
  COLOR         VARCHAR(40)  not null,
  KM            INTEGER      not null,
  PRICE         NUMERIC(12, 4),
  STATUS        int          not null
);

comment on column CARS.STATUS is '0 UNAVAILABLE, 1 AVAILABLE, 2 RESERVED, 3 RENTED';

CREATE TABLE IF NOT EXISTS RESERVE
(
  ID_RESERVE    SERIAL                              not null
    constraint RESERVE_PK
      primary key,
  ID_USER       BIGINT                              NOT NULL
    constraint FK_RESERVE_USERS
      references USERS (ID_USER),
  LICENSE_PLATE VARCHAR(50)                                 NOT NULL
    constraint FK_RESERVE_CAR
      references CARS (LICENSE_PLATE),
  DT_FROM       TIMESTAMP default CURRENT_TIMESTAMP not null,
  DT_TO         TIMESTAMP default CURRENT_TIMESTAMP not null,
  PRICE         NUMERIC(12, 4)                      NOT NULL,
  STATUS        int       DEFAULT 1                 not null
);

comment on column RESERVE.STATUS is '0 CANCEL, 1 ACTIVE, 2 IN PROGRESS, 3 CLOSE';

CREATE TABLE IF NOT EXISTS ORDERS
(
  ID_ORDER      SERIAL                              not null
    constraint ORDERS_PK
      primary key,
  ID_RESERVE    BIGINT                              NOT NULL
    constraint FK_ORDERS_RESERVE
      references RESERVE (ID_RESERVE),
  DT_PICK_UP    TIMESTAMP default CURRENT_TIMESTAMP not null,
  PLACE_PICK_UP VARCHAR(200)                        not null,
  DT_GIVE_UP    TIMESTAMP default CURRENT_TIMESTAMP,
  PLACE_GIVE_UP VARCHAR(200),
  PRICE         NUMERIC(12, 4)                      NOT NULL,
  RECHARGE      NUMERIC(12, 4),
  STATUS        int       DEFAULT 1                 not null
);

comment on column ORDERS.STATUS is '0 CANCEL, 1 ACTIVE, 2 COMPLETE';
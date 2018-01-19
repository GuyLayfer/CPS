#used for first inialization. can also be useful for testing to avoid conflicts in primary keys, uniques.

#!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!NOTE: IT WILL DELETE ALL CURRENT TABLES WITH ALL DATA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


#!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!WARNING: IT WILL DELETE ALL CURRENT TABLES WITH ALL DATA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

#!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! think twice before using!!! !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
DROP TABLE current_cars_planed_being_in_parking;
DROP TABLE current_cars_planed_being_in_parking_log;
DROP TABLE complaints;
DROP TABLE workers;
DROP TABLE daily_stats;
DROP TABLE subscriptions;
DROP TABLE cars;
DROP TABLE accounts;
DROP TABLE lot_num_of_columns;
DROP TABLE parking_map;
DROP TABLE rates;
DROP TABLE rates_pending;
DROP TABLE server_info

CREATE TABLE server_info(
lot_id int AUTO_INCREMENT,
info TEXT,
UNIQUE(lot_id),
PRIMARY KEY (lot_id)
);


CREATE TABLE current_cars_planed_being_in_parking(
entrance_id  int,
car_id varchar(10),
account_id  int,
lot_id int,
order_type ENUM('oneTime', 'order', 'subscriptionRegular', 'subscriptionFull'),
arrive_prediction timestamp,
leave_prediction timestamp,
arrive_time timestamp,
leave_time timestamp,
# UNIQUE (car_id), for debugging it is not unique. for final version it is.
UNIQUE(entrance_id),
PRIMARY KEY (entrance_id)
);

CREATE TABLE current_cars_planed_being_in_parking_log(
car_id varchar(10),
account_id  int,
lot_id int,
order_type ENUM('oneTime', 'order', 'subscriptionRegular', 'subscriptionFull'),
arrive_prediction timestamp,
leave_prediction timestamp,
arrive_time timestamp,
leave_time timestamp,
entrance_id  int AUTO_INCREMENT,
UNIQUE(entrance_id),
PRIMARY KEY (entrance_id)
);


CREATE TABLE complaints(
complaint_id int AUTO_INCREMENT,
account_id  int,
complaint_description text,
customer_service_response text,
entrance_id  int,
lot_id int,
filled ENUM('true', 'false'),
date_complaint timestamp,
UNIQUE(entrance_id),
UNIQUE(complaint_id),
PRIMARY KEY (complaint_id)
);

CREATE TABLE workers(
worker_id  int,
password varchar(255),
role_type ENUM('LOT_WORKER','LOT_MANAGER','CUSTOMER_SERVICE', 'FIRM_MANAGER'),
customer_service_response text,
lot_id int,
UNIQUE(worker_id),
PRIMARY KEY (worker_id)
);

CREATE TABLE daily_stats(
day_id date,
lot_id int,
filled_reservations  int,
canceled_reservations int,
latings_per_park int,
UNIQUE(day_id),
CONSTRAINT PK_lot_id_date PRIMARY KEY (lot_id,day_id)
);

CREATE TABLE subscriptions(
subscription_id int AUTO_INCREMENT,
account_id int,
lot_id int,
subscription_type ENUM('routine', 'routine_muliple_cars', 'full' ),
expired_date timestamp,
start_date timestamp,
leave_time timestamp,
UNIQUE(subscription_id),
PRIMARY KEY (subscription_id)
);

#CREATE TABLE subscriptionsNotActive(
#subscription_id int,
#account_id  int,
#car_id varchar(10),
#occasional ENUM('true', 'false'),
#came_in_today ENUM('true', 'false'),
#lot_id int,
#expired_date timestamp,
#UNIQUE(subscription_id),
#PRIMARY KEY (subscription_id)
#);


CREATE TABLE cars(
subscription_id int,
account_id  int,
car_id varchar(10)
#UNIQUE(car_id),
#PRIMARY KEY (car_id)
);

CREATE TABLE accounts(
account_id  int,
email varchar(255),
car_id varchar(10),
balance double,
has_subscription ENUM('true', 'false') #, for debugging it is not unique. for final version it is.
#UNIQUE(account_id), for debugging it is not unique. for final version it is.
#PRIMARY KEY (account_id) for debugging it is not unique. for final version it is.

);

CREATE TABLE lot_num_of_columns(
lot_id int,
num_of_cols int,
UNIQUE(lot_id),
PRIMARY KEY (lot_id)

);


CREATE TABLE rates (
lot_id int, 
subscription_regular double,
subscription_full double,
order_one_time double,
order_regular double,
subscription_multiple_cars double,
UNIQUE(lot_id),
PRIMARY KEY (lot_id)
);

CREATE TABLE rates_pending (
lot_id int, 
subscription_regular double,
subscription_full double,
order_one_time double,
order_regular double,
subscription_multiple_cars double
);



CREATE TABLE parking_map(
lot_id int, number_of_columns int,
c1 VARCHAR(12), c2 VARCHAR(12), c3 VARCHAR(12), c4 VARCHAR(12), c5 VARCHAR(12), c6 VARCHAR(12), c7 VARCHAR(12), c8 VARCHAR(12), c9 VARCHAR(12),
c10 VARCHAR(12), c11 VARCHAR(12), c12 VARCHAR(12), c13 VARCHAR(12), c14 VARCHAR(12), c15 VARCHAR(12), c16 VARCHAR(12), c17 VARCHAR(12),
c18 VARCHAR(12), c19 VARCHAR(12), c20 VARCHAR(12), c21 VARCHAR(12), c22 VARCHAR(12), c23 VARCHAR(12), c24 VARCHAR(12), c25 VARCHAR(12),
c26 VARCHAR(12), c27 VARCHAR(12), c28 VARCHAR(12), c29 VARCHAR(12), c30 VARCHAR(12), c31 VARCHAR(12), c32 VARCHAR(12), c33 VARCHAR(12),
c34 VARCHAR(12), c35 VARCHAR(12), c36 VARCHAR(12), c37 VARCHAR(12), c38 VARCHAR(12), c39 VARCHAR(12), c40 VARCHAR(12), c41 VARCHAR(12),
c42 VARCHAR(12), c43 VARCHAR(12), c44 VARCHAR(12), c45 VARCHAR(12), c46 VARCHAR(12), c47 VARCHAR(12), c48 VARCHAR(12), c49 VARCHAR(12),
c50 VARCHAR(12), c51 VARCHAR(12), c52 VARCHAR(12), c53 VARCHAR(12), c54 VARCHAR(12), c55 VARCHAR(12), c56 VARCHAR(12), c57 VARCHAR(12), 
c58 VARCHAR(12), c59 VARCHAR(12), c60 VARCHAR(12), c61 VARCHAR(12), c62 VARCHAR(12), c63 VARCHAR(12), c64 VARCHAR(12), c65 VARCHAR(12), 
c66 VARCHAR(12), c67 VARCHAR(12), c68 VARCHAR(12), c69 VARCHAR(12), c70 VARCHAR(12), c71 VARCHAR(12), c72 VARCHAR(12), c73 VARCHAR(12),
c74 VARCHAR(12), c75 VARCHAR(12), c76 VARCHAR(12), c77 VARCHAR(12), c78 VARCHAR(12), c79 VARCHAR(12), c80 VARCHAR(12), c81 VARCHAR(12),
c82 VARCHAR(12), c83 VARCHAR(12), c84 VARCHAR(12), c85 VARCHAR(12), c86 VARCHAR(12), c87 VARCHAR(12), c88 VARCHAR(12), c89 VARCHAR(12),
c90 VARCHAR(12), c91 VARCHAR(12), c92 VARCHAR(12), c93 VARCHAR(12), c94 VARCHAR(12), c95 VARCHAR(12), c96 VARCHAR(12), c97 VARCHAR(12),
c98 VARCHAR(12), c99 VARCHAR(12), c100 VARCHAR(12), c101 VARCHAR(12), c102 VARCHAR(12), c103 VARCHAR(12), c104 VARCHAR(12), 
c105 VARCHAR(12), c106 VARCHAR(12), c107 VARCHAR(12), c108 VARCHAR(12), c109 VARCHAR(12),

UNIQUE(lot_id),
PRIMARY KEY (lot_id)
);



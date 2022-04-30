CREATE TABLE IF NOT EXISTS shops
(shop_id int auto_increment constraint shops_pk primary key, shop varchar not null);

create unique index shops_shop_id_uindex
    on shops (shop_id);
create unique index shops_shop_uindex
    on shops (shop);
INSERT INTO shops (shop_id, shop) VALUES (DEFAULT, 'Кира Ауто');
INSERT INTO shops (shop_id, shop) VALUES (DEFAULT, 'BNW');
INSERT INTO shops (shop_id, shop) VALUES (DEFAULT, 'Е-мобилс');

CREATE TABLE IF NOT EXISTS owners
(id int auto_increment,
 first_name VARCHAR NOT NULL,
 last_name VARCHAR NOT NULL,
 car_id INT);
INSERT INTO owners (first_name, last_name, car_id) VALUES ('Михаил', 'Шишкин', 1);
INSERT INTO owners (first_name, last_name, car_id) VALUES ('Михаэль', 'Шумахер', 2);
INSERT INTO owners (first_name, last_name, car_id) VALUES ('Капибар', 'Григорьевич', 13);
INSERT INTO owners (first_name, last_name, car_id) VALUES ('Али', 'Дон-Донович', 20);
INSERT INTO owners (first_name, last_name, car_id) VALUES ('Имануил','Кант', 11);
INSERT INTO owners (first_name, last_name, car_id) VALUES ('Иван','Иванов', 7);

CREATE TABLE IF NOT EXISTS cars
(id int auto_increment,
 brand VARCHAR NOT NULL,
 year_of_produce INT NOT NULL,
 net_worth INT NOT NULL);

create unique index cars_id_uindex
    on cars (id);

INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Maddyson', 1984,2000000);
INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Lada', 2006,900000);
INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('GAZ', 2010,1000000);
INSERT INTO cars (brand, year_of_produce, net_worth) VALUES ('Lada', 1999,700000);

CREATE TABLE IF NOT EXISTS car_shops
(car_id  int not null,
id_shop int not null);
create unique index car_shops_car_id_shop_id_uindex on car_shops (car_id, id_shop);
INSERT INTO car_shops (car_id, id_shop) VALUES (1,2);
INSERT INTO car_shops (car_id, id_shop) VALUES (1,3);
INSERT INTO car_shops (car_id, id_shop) VALUES (2,1);
INSERT INTO car_shops (car_id, id_shop) VALUES (2,2);
INSERT INTO car_shops (car_id, id_shop) VALUES (3,1);
INSERT INTO car_shops (car_id, id_shop) VALUES (4,1);
INSERT INTO car_shops (car_id, id_shop) VALUES (4,2);
INSERT INTO car_shops (car_id, id_shop) VALUES (4,3);
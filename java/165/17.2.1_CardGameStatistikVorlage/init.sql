drop database if exists  carCards;
create database carCards;
use carCards;

create table car (
    id int not null auto_increment primary key,
    imageUrl text not null,
    tradeName text not null,
    model text not null,
    prize double,
    velocity int not null
);

insert into car (imageUrl, tradeName, model, prize, velocity) VALUES ('images/audi.jpg', 'Audi', 'Flaschback 300', 50000.00, 190);
insert into car (imageUrl, tradeName, model, prize, velocity) VALUES ('images/opel.jpg', 'Opel', 'Manta SE', 20000.00, 250);
insert into car (imageUrl, tradeName, model, prize, velocity) VALUES ('images/vw.jpg', 'VW', 'Golf GL', 12000.00, 180);
insert into car (imageUrl, tradeName, model, prize, velocity) VALUES ('images/fiat.jpg', 'Fiat', '500', 15000.00, 150);

insert into car (imageUrl, tradeName, model, prize, velocity) VALUES ('images/amdb9gt.jpg', 'Aston Martin', 'DB9', 120000.00, 240);
insert into car (imageUrl, tradeName, model, prize, velocity) VALUES ('images/defender110.jpg', 'Land Rover', 'Defender', 20000.00, 140);
insert into car (imageUrl, tradeName, model, prize, velocity) VALUES ('images/mganesport.jpg', 'Renault', 'Megan', 25000.00, 170);
insert into car (imageUrl, tradeName, model, prize, velocity) VALUES ('images/hondacivic.jfif', 'Honda', 'Civic', 31000.00, 190);


-- ignorieren
-- ALTER USER 'root'@'localhost' IDENTIFIED  WITH mysql_native_password BY 'root';
--create schema car_app

create table car (
	id serial primary key,
	make varchar(100) not null,
	model varchar(100) not null,
	year integer not null,
	status varchar(30) not null
	
);

create table person (
	id serial primary key,
	fullname varchar(100) not null,
	username varchar(30) unique not null,
	passwd varchar(45) not null
	
);

create table car_owner (
	car_id integer references car,
	owner_id integer references person
); 

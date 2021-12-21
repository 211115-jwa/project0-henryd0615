--DML
insert into car (id,make,model,year,status) values 
	(default, 'BMW','X5', 2020, 'Available'),
	(default, 'BMW','X6', 2020, 'Available'),
	(default, 'BMW','i8', 2019, 'Available'),
	(default, 'Honda','Accord', 2021, 'Available'),
	(default, 'Honda','Civic', 2022,'Available'),
	(default, 'Range Rover','Velar', 2019,'Available'),
	(default, 'Range Rover','Evoke', 2018, 'Available');


insert into person (id, fullname, username, passwd) values
(default, 'Henry De Jesus', 'BookJean', 'BookJeanpass'),
(default, 'Jefrey Alvarado', 'Twitternage', 'Twitternagepass'),
(default, 'Eddy Paulino', 'Plugbital', 'Plugbitalpass'),
(default, 'Edwin Cortes', 'Booklith', 'Booklithpass'),
(default, 'Matt Murdock', 'SimplyMc', 'SimplyMcpass'),
(default, 'Peter Parker', 'EatInstant', 'EatInstantpass');

--DQL

delete from car where status = 'purchased';
select * from car;
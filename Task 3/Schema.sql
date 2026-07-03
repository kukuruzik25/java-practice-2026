create table Product (
	id serial unique not null,
	name char(20) not null,
	cost integer check(cost > -1)
)
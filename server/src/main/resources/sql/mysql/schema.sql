drop table if exists app_task;
drop table if exists app_user;
drop table if exists app_news;
drop table if exists app_news_category;

create table app_task (
	id bigint auto_increment,
	title varchar(128) not null,
	description varchar(255),
	user_id bigint not null,
    primary key (id)
) engine=InnoDB;

create table app_user (
	id bigint auto_increment,
	login_name varchar(64) not null unique,
	name varchar(64) not null,
	password varchar(255) not null,
	salt varchar(64) not null,
	roles varchar(255) not null,
	register_date timestamp not null default 0,
	primary key (id)
) engine=InnoDB;

create table app_news (
	id bigint auto_increment,
	title varchar(64) not null,
	intro varchar(255) not null,
	pic varchar(255),
	video varchar(255),
	detail  varchar(255),
	categoryId integer,
	url varchar(64),
	uid varchar(255),
	crt_date timestamp not null default 0,
	up_date timestamp not null default 0,
	status varchar(10),
	primary key (id)
) engine=InnoDB;

create table app_news_category (
	id bigint auto_increment,
	name varchar(64) not null,
	uid varchar(255),
	crt_date timestamp not null default 0,
	up_date timestamp not null default 0,
	status varchar(10),
	primary key (id)
) engine=InnoDB;
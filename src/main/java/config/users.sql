drop database if exists users;
create database users;

\c users

drop table if exists departments;
create table departments (
	department_id int primary key,
	department_name varchar(50) unique
);
insert into departments values(0, 'N/A');
drop sequence if exists departments_sequence;
create sequence departments_sequence as int increment by 1 start with 1;
alter table departments alter column departments_id set default nextval('departments_sequence');


drop table if exists positions;
create table positions (
	position_id int primary key,
	position_name varchar(50) unique
);
insert into positions values(0, 'N/A');
drop sequence if exists position_sequence;
create sequence position_sequence as int increment by 1 start with 1;
alter table positions alter column position_id set default nextval('position_sequence');


drop table if exists users;
create table users (
    employee_id serial primary key,
    email varchar(70) unique,
    mobile_number varchar(20) unique,
    password varchar(70),
    user_type varchar(70),
    first_name varchar(70),
    middle_name varchar(70),
    last_name varchar(70),
    dept_id int,
    birth_date date,
    gender varchar(10),
    position_id int,
    foreign key(dept_id) references departments(department_id),
    foreign key(position_id) references positions(position_id)
);

drop table if exists user_tokens;
create table user_tokens (
	user_id int,
	token varchar(200),
	foreign key(user_id) references users(employee_id) on delete cascade
);

insert into departments(department_name) values ('IT');
insert into departments(department_name) values ('HR');

insert into positions(position_name) values ('Developer');
insert into positions(position_name) values ('HELPER');

insert into users(email, mobile_number, password, user_type, first_name, middle_name, last_name, dept_id, birth_date, gender, position_id) 
values('ally@gmail.com', '9178192726', '123456', 'admin', 'ally', 'artuz', 'astrero', 1, '2015-07-25', 'female', 1);

insert into users(email, mobile_number, password, user_type, first_name, middle_name, last_name, dept_id, birth_date, gender, position_id) 
values('patz@gmail.com', '9178192726', '123456', 'employee', 'patz', 'artuz', 'astrero', 1, '2015-07-25', 'female', 1);
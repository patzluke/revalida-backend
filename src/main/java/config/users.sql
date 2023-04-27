drop database if exists users;
create database users;

\c users

create extension pgcrypto;

drop table if exists departments;
create table departments (
	department_id int primary key,
	department_name varchar(50) unique
);
insert into departments values(0, 'TBD');
drop sequence if exists departments_sequence;
create sequence departments_sequence as int increment by 1 start with 1;
alter table departments alter column department_id set default nextval('departments_sequence');


drop table if exists positions;
create table positions (
	position_id int primary key,
	dept_id int not null,
	position_name varchar(50) unique,
	foreign key(dept_id) references departments(department_id) on delete set null
);
insert into positions values(0, 0, 'TBD');
drop sequence if exists position_sequence;
create sequence position_sequence as int increment by 1 start with 1;
alter table positions alter column position_id set default nextval('position_sequence');

drop sequence if exists users_sequence;
create sequence users_sequence as int increment by 1 start with 227001;

drop table if exists users cascade;
create table users (
    employee_id int default nextval('users_sequence') not null primary key,
    email varchar(70) unique,
    mobile_number varchar(20) unique,
    password varchar(200),
    user_type varchar(70),
    first_name varchar(70),
    middle_name varchar(70),
    last_name varchar(70),
    dept_id int,
    birth_date date,
    gender varchar(10),
    position_id int,
    foreign key(dept_id) references departments(department_id) on delete set null,
    foreign key(position_id) references positions(position_id) on delete set null
);

drop table if exists password_requests;
create table password_requests (
	id serial primary key,
	emp_id int,
	status varchar(20) default 'Pending',
	foreign key(emp_id) references users(employee_id) on delete cascade
);

drop table if exists user_tokens;
create table user_tokens (
	user_id int,
	token varchar(250),
	foreign key(user_id) references users(employee_id) on delete cascade
);

insert into departments(department_name) values ('IT');
insert into departments(department_name) values ('HR');

insert into positions(dept_id, position_name) values (1, initcap('DEVELOPER'));
insert into positions(dept_id, position_name) values (1, initcap('Programmer'));

insert into users(email, mobile_number, password, user_type, first_name, middle_name, last_name, dept_id, birth_date, gender, position_id) 
values('ally@gmail.com', '9178192726', pgp_sym_encrypt('123456', 'r3vaLid@'), initcap('admin'), initcap('ally'), initcap('artuz'), initcap('astrero'), 1, '2015-07-25', initcap('female'), 1);

insert into users(email, mobile_number, password, user_type, first_name, middle_name, last_name, dept_id, birth_date, gender, position_id) 
values('patz@gmail.com', '9178192725', pgp_sym_encrypt('123456', 'r3vaLid@'), initcap('employee'), initcap('patz'), initcap('artuz'), initcap('astrero'), 1, '2015-07-25', initcap('female'), 1);

CREATE OR REPLACE FUNCTION set_dept_id_to_zero()
RETURNS TRIGGER AS $$
BEGIN
  UPDATE users SET dept_id = 0 WHERE dept_id = OLD.department_id;
  UPDATE positions set dept_id = 0 where dept_id = OLD.department_id;
  RETURN OLD;
END;
$$
LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION set_position_id_to_zero()
RETURNS TRIGGER AS $$
BEGIN
  UPDATE users SET position_id = 0 WHERE position_id = OLD.position_id;
  RETURN OLD;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER set_dept_id_to_zero_trigger
BEFORE DELETE ON departments
FOR EACH ROW
EXECUTE FUNCTION set_dept_id_to_zero();

CREATE TRIGGER set_position_id_to_zero_trigger
BEFORE DELETE ON positions
FOR EACH ROW
EXECUTE FUNCTION set_position_id_to_zero();
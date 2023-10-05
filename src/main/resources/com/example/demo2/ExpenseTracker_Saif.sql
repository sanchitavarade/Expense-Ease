drop database exp_tracker;
create database Exp_Tracker;
use Exp_Tracker;

create table User(User_id int(2) auto_increment primary key,Username varchar(20),Password varchar(20)) auto_increment=101;
insert into User (Username, Password) values('Narendra','Tejas30'),('Sanchita','Sanchi41');

create table Transaction_category(category_id int(2) auto_increment primary key,categoryname varchar(20),user_id int (2),foreign key (user_id) references User(user_id)) auto_increment= 501;
insert into transaction_category (categoryname, user_id) values('xyz', 101),('abc', 102);

create table transactions(transaction_id int(5) auto_increment primary key,transactiondate DATE,amount int(4),transactiontype varchar(10),categoryname varchar(15),category_id int(2),user_id int(2),foreign key (category_id) references Transaction_category(category_id),foreign key (user_id) references User(user_id)) auto_increment=401;
insert into transactions (transactiondate, amount, transactiontype, categoryname, category_id, user_id) values('2023-08-17',40000,'Expense','School',501,101),('2023-08-18',20000,'Expense','Food',502,102);

create table borrow_lend(bor_len_id int(2) auto_increment primary key,bor_len_date Date,bor_len_due_date Date,bor_len_type varchar(10),bor_len_description varchar(30),bor_len_amount int(6),user_id int(2),foreign key (user_id) references User(user_id)) auto_increment=601;
insert into borrow_lend (bor_len_date, bor_len_due_date,bor_len_type, bor_len_description, bor_len_amount, user_id)  values('2023-08-30','2023-09-07','Borrowed','Borrowed from John',5000,101);

create table Savings(saving_id int(2) auto_increment primary key,savingsdate DATE,amount int,User_id int(2), foreign key(User_id) references User(User_id)) auto_increment=201;
insert into savings (savingsdate, amount, user_id) values('2023-09-18',4000,101),('2023-09-17',3000,102);

create table budget(budget_id int(2) auto_increment primary key,elimit int(4),category_id int(3),category_name varchar(15),user_id int(2),foreign key (category_id) references Transaction_category(category_id),foreign key (user_id) references User(user_id)) auto_increment=701;
insert into budget (elimit, category_id, category_name, user_id) values(70000,501,'Transportation',101),(75000,502,'Food',102);


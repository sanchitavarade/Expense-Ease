-- Drop the database if it exists
DROP DATABASE exp_tracker;

-- Create a new database
CREATE DATABASE Exp_Tracker;

-- Use the new database
USE Exp_Tracker;

-- Create the User table
CREATE TABLE User (
                      User_id INT(2) AUTO_INCREMENT PRIMARY KEY,
                      Username VARCHAR(20),
                      Password VARCHAR(20)
) AUTO_INCREMENT=101;

-- Create the budget table
CREATE TABLE budget (
                        Budget_id varchar(20) primary key ,
                        elimit INT(7),
                        category_name VARCHAR(15),
                        user_id INT(2),
                        FOREIGN KEY (user_id) REFERENCES User(user_id)
);

delimiter //
Create trigger t1
    after insert
    on User
    for each row
begin
    declare id int;
		set id = new.user_id;
    INSERT INTO budget (Budget_id, elimit, category_name, user_id) VALUES
                                                                       -- some inbuilt categories
                                                                       (concat("Transportation", id),6000, 'Transportation', id),
                                                                       (concat("Entertainment", id),6000, 'Entertainment', id),
                                                                       (concat('Utilities', id),7500, 'Utilities', id),
                                                                       (concat('Clothing', id),7500, 'Clothing', id),
                                                                       (concat('Shopping', id),7500, 'Shopping', id),
                                                                       (concat('Groceries', id),7500, 'Groceries', id),
                                                                       (concat('Travel', id),7500, 'Travel', id),
                                                                       (concat('Electronics', id),7500, 'Electronics', id),
                                                                       (concat('Dining out', id),7500, 'Dining out', id),
                                                                       (concat('Healthcare', id),7500, 'Healthcare', id),
                                                                       (concat('Education', id),7500, 'Education', id),
                                                                       (concat('Food', id),7500, 'Food', id);
end //
delimiter ;

-- Insert user data
INSERT INTO User (Username, Password) VALUES
                                          ('Narendra', 'Tejas30'),
                                          ('Sanchita', 'Sanchi41');

-- Create the transactions table
CREATE TABLE transactions (
                              transaction_id INT(5) AUTO_INCREMENT PRIMARY KEY,
                              transactiondate DATE,
                              amount INT(4),
                              transactiontype VARCHAR(10),
                              Budget_id VARCHAR(20),
                              FOREIGN KEY (Budget_id) REFERENCES budget(Budget_id),
                              category_name VARCHAR(15),
                              user_id INT(2),
                              FOREIGN KEY (user_id) REFERENCES User(user_id)
) AUTO_INCREMENT=401;

-- delimiter //
--  Create trigger t2
-- 	before insert
-- 	on transactions
--     for each row
--     begin
--  		set new.Budget_id= concat(new.category_name, new.user_id);
-- end //
-- delimiter ;

-- Insert expense transactions
INSERT INTO transactions ( transactiondate, amount, transactiontype, category_name, user_id, Budget_id) VALUES
                                                                                                            ('2023-08-18', 20000, 'Expense', 'Food', 102, 'Food102'),
                                                                                                            ('2023-09-20', 2500, 'Expense', 'Groceries', 102, 'Groceries102'),
                                                                                                            ('2023-09-22', 1400, 'Expense', 'Entertainment', 101, 'Entertainment101'),
                                                                                                            ('2023-09-22', 1200, 'Expense', 'Travel', 101, 'Travel101'),
                                                                                                            ('2023-09-26', 3000, 'Expense', 'Electronics',  101, 'Electronics101'),
                                                                                                            ('2023-09-26', 3200, 'Expense', 'Electronics',  101, 'Electronics101'),
                                                                                                            ('2023-09-27', 800, 'Expense', 'Healthcare', 101, 'Healthcare101'),
                                                                                                            ('2023-09-25', 2200, 'Expense', 'Utilities', 102, 'Utilities102'),
                                                                                                            ('2023-09-26', 1200, 'Expense', 'Groceries', 101,'Groceries101'),
                                                                                                            ('2023-09-26', 1100, 'Expense', 'Groceries', 101,'Groceries101'),
                                                                                                            ('2023-09-27', 500, 'Expense', 'Clothing', 102, 'Clothing102'),
                                                                                                            ('2023-08-18', 2000, 'Expense', 'Food', 101, 'Food101'),
                                                                                                            ('2023-09-20', 200, 'Income', 'Groceries', 101, 'Groceries101'),
                                                                                                            ('2023-09-22', 1200, 'Expense', 'Entertainment', 101, 'Entertainment101'),
                                                                                                            ('2023-09-26', 300, 'Income', 'Electronics',  101, 'Electronics101'),
                                                                                                            ('2023-09-27', 800, 'Expense', 'Healthcare', 101, 'Healthcare101'),
                                                                                                            ('2023-09-25', 1125, 'Expense', 'Utilities', 101, 'Utilities101'),
                                                                                                            ('2023-09-26', 120, 'Expense', 'Groceries', 101,'Groceries101'),
                                                                                                            ('2023-09-27', 500, 'Expense', 'Clothing', 102, 'Clothing102');


-- Create the borrow_lend table
CREATE TABLE borrow_lend (
                             bor_len_id INT(2) AUTO_INCREMENT PRIMARY KEY,
                             bor_len_date DATE,
                             bor_len_due_date DATE,
                             bor_len_type VARCHAR(10),
                             bor_len_description VARCHAR(30),
                             bor_len_amount INT(6),
                             user_id INT(2),
                             FOREIGN KEY (user_id) REFERENCES User(user_id)
) AUTO_INCREMENT=601;

-- Insert borrowed/lent transactions
INSERT INTO borrow_lend (bor_len_date, bor_len_due_date, bor_len_type, bor_len_description, bor_len_amount, user_id) VALUES
                                                                                                                         ('2023-08-30', '2023-12-07', 'Borrowed', 'Borrowed from John', 5000, 101),
                                                                                                                         ('2023-09-28', '2023-11-02', 'Lent', 'Lent to Mark', 2000, 101),
                                                                                                                         ('2023-09-29', '2023-12-06', 'Borrowed', 'Borrowed from Emily', 2500, 101),
                                                                                                                         ('2023-09-28', '2023-12-05', 'Borrowed', 'Borrowed from Lucas', 1800, 102),
                                                                                                                         ('2023-09-29', '2023-12-06', 'Lent', 'Lent to Rachel', 2100, 102);

-- Create the Savings table
CREATE TABLE Savings (
                         saving_id INT(2) AUTO_INCREMENT PRIMARY KEY,
                         savingsdate DATE,
                         amount INT,
                         User_id INT(2),
                         FOREIGN KEY (User_id) REFERENCES User(User_id)
) AUTO_INCREMENT=201;

-- Insert savings transactions
INSERT INTO Savings (savingsdate, amount, User_id) VALUES
                                                       ('2023-09-18', 4000, 101),
                                                       ('2023-09-17', 3000, 102),
                                                       ('2023-09-28', 3500, 101),
                                                       ('2023-09-29', 2200, 101),
                                                       ('2023-09-28', 2800, 102),
                                                       ('2023-09-29', 1900, 102);

delimiter //

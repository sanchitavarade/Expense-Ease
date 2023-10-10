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
	Budget_id int(2) primary key auto_increment,
	elimit INT(4),
    category_name VARCHAR(15),
    user_id INT(2),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
) AUTO_INCREMENT=701;

delimiter //
Create trigger t1
	after insert
    on User
    for each row
    begin
		INSERT INTO budget (elimit, category_name, user_id) VALUES
        (7000, 'Transportation', user_id),
        (7500, 'Utilities', user_id),
        (7500, 'Shopping', user_id),
        (7500, 'Groceries', user_id),
        (7500, 'Travel', user_id),
        (7500, 'Electronics', user_id),
        (7500, 'Dining out', user_id),
        (7500, 'Healthcare', user_id),
        (7500, 'Education', user_id),
		(7500, 'Food', user_id);
	end //
delimiter ;

-- Insert user data
INSERT INTO User (Username, Password) VALUES
    ('Narendra', 'Tejas30'),
    ('Sanchita', 'Sanchi41');

-- Insert budget limits
INSERT INTO budget (elimit, category_name, user_id) VALUES
    (70000, 'Transportation', 101),
    (75000, 'Food', 102),
    (6000, 'Shopping', 101),    -- Updated category_id to match Transaction_category
    (4500, 'Travel', 101),      -- Updated category_id to match Transaction_category
    (7000, 'Healthcare', 102),  -- Updated category_id to match Transaction_category
    (5500, 'Education', 102);   -- Updated category_id to match Transaction_category

-- Create the transactions table
CREATE TABLE transactions (
    transaction_id INT(5) AUTO_INCREMENT PRIMARY KEY,
    transactiondate DATE,
    amount INT(4),
    transactiontype VARCHAR(10),
    category_name VARCHAR(15),
    category_id int(2),
    user_id INT(2),
    FOREIGN KEY (category_id) REFERENCES budget(budget_id),
    FOREIGN KEY (user_id) REFERENCES User(user_id)
) AUTO_INCREMENT=401;

delimiter //
Create trigger t2
	before insert
    on transactions
    for each row
    begin
		Update transactions set category_id = (select budget_id from budget where transactions.category_name = budget.category_name);
	end //
delimiter ;

-- Insert expense transactions
INSERT INTO transactions ( transactiondate, amount, transactiontype, category_name, category_id, user_id) VALUES
    ('2023-08-17', 40000, 'Expense', 'School',701,  101),
    ('2023-08-18', 20000, 'Expense', 'Food',701, 102),
    ('2023-09-20', 2500, 'Expense', 'Groceries',701, 102),
    ('2023-09-22', 1200, 'Expense', 'Entertainment',701, 101),
    ('2023-09-25', 1500, 'Expense', 'Dining Out',701, 101),
    ('2023-09-26', 3000, 'Expense', 'Electronics',701, 101),
    ('2023-09-27', 800, 'Expense', 'Health',701, 101),
    ('2023-09-25', 2200, 'Expense', 'Utilities',701, 102),
    ('2023-09-26', 1200, 'Expense', 'Groceries',701, 101),
    ('2023-09-27', 500, 'Expense', 'Clothing',701, 102);

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
    ('2023-08-30', '2023-09-07', 'Borrowed', 'Borrowed from John', 5000, 101),
    ('2023-09-28', '2023-10-05', 'Lent', 'Lent to Mark', 2000, 101),
    ('2023-09-29', '2023-10-06', 'Borrowed', 'Borrowed from Emily', 2500, 101),
    ('2023-09-28', '2023-10-05', 'Borrowed', 'Borrowed from Lucas', 1800, 102),
    ('2023-09-29', '2023-10-06', 'Lent', 'Lent to Rachel', 2100, 102);

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

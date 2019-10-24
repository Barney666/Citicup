# Citicup
数据库名citicup
两个表名 account 和 login_account

MySQL语句：
CREATE TABLE account(
username VARCHAR(20) PRIMARY KEY NOT NULL,
`password` VARCHAR(20) NOT NULL,
mark VARCHAR(20),
footprint VARCHAR(1000)
);
CREATE TABLE loginAccount(
username VARCHAR(20) PRIMARY KEY NOT NULL
);

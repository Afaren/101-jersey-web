
# create database 
DROP DATABASE IF EXISTS `supermarket`;
CREATE DATABASE `supermarket` CHARACTER SET utf8;

USE supermarket;

# create table
DROP TABLE IF EXISTS items;
CREATE TABLE `items` (
	`id` int(11) unsigned NOT NULL AUTO_INCREMENT,
	`price` double unsigned NOT NULL DEFAULT '0',
	`name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

# insert data
INSERT INTO items(id, price, name) value (1, 1, 'apple');
INSERT INTO items(id, price, name) value (2, 2, 'orange');


# check result
SELECT * FROM supermarket.items ;



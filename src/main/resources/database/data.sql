CREATE SCHEMA IF NOT EXISTS `franchises`;

USE `franchises`;

CREATE TABLE IF NOT EXISTS `franchise` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
    );

CREATE TABLE IF NOT EXISTS `branch` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    `franchise_id` INT NOT NULL,
    PRIMARY KEY (`id`)
    );

CREATE TABLE IF NOT EXISTS `product` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`id`)
    );

CREATE TABLE IF NOT EXISTS `product_branch` (
     `id` INT NOT NULL AUTO_INCREMENT,
     `product_id` INT NOT NULL,
     `branch_id` INT NOT NULL,
     `stock` INT NOT NULL,
     PRIMARY KEY (`id`)
    );
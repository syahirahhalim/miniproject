-- drop table if exists
DROP DATABASE IF EXISTS test_shop;

-- create a new database
CREATE DATABASE test_shop;

-- select database
USE test_shop;

CREATE TABLE `users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `full_name` varchar(255),
  `created_at` timestamp,
  `country_code` int
);

CREATE TABLE `countries` (
  `code` int PRIMARY KEY,
  `currency` varchar(255),
  `country_name` varchar(255)
);

CREATE TABLE `order_items` (
  `order_id` int PRIMARY KEY,
  `product_id` int,
  `quantity` int DEFAULT 1
);

CREATE TABLE `orders` (
  `id` int PRIMARY KEY,
  `user_id` int UNIQUE NOT NULL,
  `status` varchar(255),
  `created_at` varchar(255) COMMENT 'When order created'
);

CREATE TABLE `products` (
  `id` int PRIMARY KEY,
  `name` varchar(255),
  `price` int,
  `status` ENUM ('out_of_stock', 'in_stock', 'running_low'),
  `created_at` datetime DEFAULT now()
);

CREATE TABLE `payment` (
  `id` int,
  `country_code` int,
  `currency` varchar(255),
  `created at` varchar(255),
  PRIMARY KEY (`id`, `country_code`)
);

CREATE INDEX `product_status` ON `products` (`id`, `status`);

CREATE UNIQUE INDEX `products_index_1` ON `products` (`id`);

ALTER TABLE `users` ADD FOREIGN KEY (`country_code`) REFERENCES `countries` (`code`);

ALTER TABLE `payment` ADD FOREIGN KEY (`country_code`) REFERENCES `countries` (`code`);

ALTER TABLE `order_items` ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `order_items` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);
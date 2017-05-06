create database hello;

use hello;


CREATE  TABLE `employee` (

  `id` INT NOT NULL AUTO_INCREMENT ,

  `name` VARCHAR(45) NULL ,

  `location` VARCHAR(45) NULL ,

  PRIMARY KEY (`id`) );

  
  
INSERT INTO `employee` (`name`, `location`) VALUES ('andy', 'uk');

INSERT INTO `employee` (`name`, `location`) VALUES ('alex', 'usa');

INSERT INTO `employee` (`name`, `location`) VALUES ('roger', 'france');

INSERT INTO `employee` (`name`, `location`) VALUES ('alice', 'germany');



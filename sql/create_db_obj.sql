--
-- This is the all-in-one script that can be used to create all database objects (including
-- database, tables, views, triggers, etc) and insert data from an empty mysql database.
--
-- It's the developer's responsibility to merge database scheme changes to this scripts for
-- every release.
--
-- For example, if I want to add a new table for v1.2 release, I will make the change to
-- both this create_db_obj.sql script and another sql patch script for v1.2 (namely db_patch_1_2.sql)
-- so that we can run the patch script against the running mysql instance.

-- -----------------------------------------------------
-- Schema demo
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `demo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `demo`;

-- -----------------------------------------------------
-- Table `demo`.`h$account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `h$account` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(100) NOT NULL COMMENT 'Email, or mobile',
  `password` VARCHAR(255) NOT NULL DEFAULT '' COMMENT 'The password of the user',
  `roles` VARCHAR(100) NOT NULL DEFAULT 'USER' COMMENT 'The roles of users, separated by commas, such as ‘USER,ADMIN’.',
  `activated` BOOLEAN DEFAULT FALSE COMMENT 'TRUE: The account is activated.',
  `access_token` VARCHAR(255) NULL DEFAULT NULL COMMENT 'The access toke for this account.',
  `ctime` BIGINT(13) NOT NULL DEFAULT 0 COMMENT 'The created time of the account',
  `utime` BIGINT(13) NOT NULL DEFAULT 0 COMMENT 'The last modified time of the account',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_unique_idx` (`login` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo`.`h$profile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `h$profile` (
  `user_id` INT(10) NOT NULL COMMENT 'The unique user identifier.',
  `name` VARCHAR(100) NOT NULL COMMENT 'The name of the user',
  `avatar` VARCHAR(100) NOT NULL COMMENT 'The URL of user avatar',
  `gender` VARCHAR(1) NOT NULL DEFAULT 'M' COMMENT 'M: Male, F: Female.',
  `ctime` BIGINT(13) NULL,
  `utime` BIGINT(13) NULL,
  INDEX `profile_user_id_idx` (`user_id` ASC),
  PRIMARY KEY (`user_id`),
  CONSTRAINT `profile_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `h$account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `demo`.`h$config`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `h$config` (
  `id` INT(10) NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(100) NULL COMMENT 'The type of the config.\nFor example, we can define some type of the config, such as userConfig for serving user, systemConfig for serving system and so on.\nThe type is just like a group for configs which will be also make searching more convenience.\n',
  `key` VARCHAR(100) NULL COMMENT 'The key of the config.\nThe name of the config.\n',
  `value` TEXT NULL COMMENT 'The value of the config. This is could be a JSON doc as well.\n',
  `ctime` BIGINT(13) NULL COMMENT 'The created time of the config record\n',
  `utime` BIGINT(13) NULL COMMENT 'The updated time of the config record',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;
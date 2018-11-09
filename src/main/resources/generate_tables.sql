-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema library_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema library_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `library_db` DEFAULT CHARACTER SET latin1 ;
USE `library_db` ;

-- -----------------------------------------------------
-- Table `library_db`.`Books`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Books` (
  `idBook` INT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `publisher` VARCHAR(45) NOT NULL,
  `authors` VARCHAR(45) NOT NULL,
  `ISBN` VARCHAR(45) NULL,
  PRIMARY KEY (`idBook`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_db`.`Users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Users` (
  `idUser` INT NOT NULL,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `usertype` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idUser`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_db`.`Loans`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Loans` (
  `idLoans` INT NOT NULL,
  `loanDate` DATE NOT NULL,
  `loanDue` DATE NOT NULL,
  `idBook` INT NOT NULL,
  `idUser` INT NOT NULL,
  PRIMARY KEY (`idLoans`, `idBook`, `idUser`),
  INDEX `fk_Loans_Books1_idx` (`idBook` ASC),
  INDEX `fk_Loans_Users1_idx` (`idUser` ASC),
  CONSTRAINT `fk_Loans_Books1`
    FOREIGN KEY (`idBook`)
    REFERENCES `library_db`.`Books` (`idBook`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loans_Users1`
    FOREIGN KEY (`idUser`)
    REFERENCES `library_db`.`Users` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_db`.`Branches`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Branches` (
  `idBranch` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idBranch`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_db`.`Book_Copies`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `library_db`.`Book_Copies` (
  `copies` INT NOT NULL,
  `idBook` INT NOT NULL,
  `idBranch` INT NOT NULL,
  PRIMARY KEY (`idBook`, `idBranch`),
  INDEX `fk_Book_Copies_Branches1_idx` (`idBranch` ASC),
  CONSTRAINT `fk_Book_Copies_Books`
    FOREIGN KEY (`idBook`)
    REFERENCES `library_db`.`Books` (`idBook`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Book_Copies_Branches1`
    FOREIGN KEY (`idBranch`)
    REFERENCES `library_db`.`Branches` (`idBranch`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

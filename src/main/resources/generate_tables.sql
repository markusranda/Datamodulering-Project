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
-- Table `library_db`.`Authors`
-- -----------------------------------------------------
DROP TABLE Authors;
CREATE TABLE IF NOT EXISTS Authors (
  `idAuthors` INT(11) NOT NULL AUTO_INCREMENT,
  `lName` VARCHAR(45) NULL DEFAULT NULL,
  `fName` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idAuthors`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Books`
-- -----------------------------------------------------
DROP TABLE Books;
CREATE TABLE IF NOT EXISTS Books (
  `idBook` INT(11) NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NOT NULL,
  `publisher` VARCHAR(45) NOT NULL,
  `ISBN` VARCHAR(45) NULL DEFAULT NULL,
  `image` MEDIUMBLOB NULL DEFAULT NULL,
  PRIMARY KEY (`idBook`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Book_Authors`
-- -----------------------------------------------------
DROP TABLE Book_Authors;
CREATE TABLE IF NOT EXISTS Book_Authors (
  `idBook` INT(11) NOT NULL,
  `idAuthors` INT(11) NOT NULL,
  PRIMARY KEY (`idBook`, `idAuthors`),
  CONSTRAINT `fk_Book_Authors_Authors1`
    FOREIGN KEY (`idAuthors`)
    REFERENCES Authors (`idAuthors`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Book_Authors_Books1`
    FOREIGN KEY (`idBook`)
    REFERENCES Books (`idBook`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Genres`
-- -----------------------------------------------------
DROP TABLE Genres;
CREATE TABLE IF NOT EXISTS Genres (
  `idGenre` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idGenre`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Book_Genres`
-- -----------------------------------------------------
DROP TABLE Book_Genres;
CREATE TABLE IF NOT EXISTS Book_Genres (
  `idGenre` INT(11) NOT NULL,
  `idBook` INT(11) NOT NULL,
  PRIMARY KEY (`idGenre`, `idBook`),
  CONSTRAINT `fk_Book_Genres_Books1`
    FOREIGN KEY (`idBook`)
    REFERENCES Books (`idBook`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Book_Genres_Genre1`
    FOREIGN KEY (`idGenre`)
    REFERENCES Genres (`idGenre`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Branches`
-- -----------------------------------------------------
DROP TABLE Branches;
CREATE TABLE IF NOT EXISTS Branches (
  `idBranch` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idBranch`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Book_Quantity`
-- -----------------------------------------------------
DROP TABLE Book_Quantity;
CREATE TABLE IF NOT EXISTS Book_Quantity (
  `quantity` INT(11) NOT NULL,
  `idBook` INT(11) NOT NULL,
  `idBranch` INT(11) NOT NULL,
  PRIMARY KEY (`idBook`, `idBranch`),
  CONSTRAINT `fk_Book_Copies_Books`
    FOREIGN KEY (`idBook`)
    REFERENCES Books (`idBook`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Book_Copies_Branches1`
    FOREIGN KEY (`idBranch`)
    REFERENCES Branches (`idBranch`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Customer`
-- -----------------------------------------------------
DROP TABLE Customer;
CREATE TABLE IF NOT EXISTS Customer (
  `idCustomer` INT(11) NOT NULL AUTO_INCREMENT,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idCustomer`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Employee`
-- -----------------------------------------------------
DROP TABLE Employee;
CREATE TABLE IF NOT EXISTS Employee (
  `idEmployee` INT(11) NOT NULL AUTO_INCREMENT,
  `fname` VARCHAR(45) NOT NULL,
  `lname` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `phone` VARCHAR(45) NOT NULL,
  `accountNumber` VARCHAR(45) NULL DEFAULT NULL,
  `SSN` VARCHAR(11) NOT NULL,
  `position` VARCHAR(45) NOT NULL,
  `idBranch` INT(11) NOT NULL,
  PRIMARY KEY (`idEmployee`, `idBranch`),
  CONSTRAINT `fk_Employee_Branches1`
    FOREIGN KEY (`idBranch`)
    REFERENCES Branches (`idBranch`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Users`
-- -----------------------------------------------------
DROP TABLE Users;
CREATE TABLE IF NOT EXISTS Users (
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `usertype` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`username`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Loans`
-- -----------------------------------------------------
DROP TABLE Loans;
CREATE TABLE IF NOT EXISTS Loans (
  `idLoan` INT(11) NOT NULL AUTO_INCREMENT,
  `loanDate` DATE NOT NULL,
  `loanDue` DATE NOT NULL,
  `idBook` INT(11) NOT NULL,
  `idBranch` INT(11) NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idLoan`, `idBook`, `username`, `idBranch`),
  CONSTRAINT `fk_Loans_Books1`
    FOREIGN KEY (`idBook`)
    REFERENCES Books (`idBook`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loans_Users1`
    FOREIGN KEY (`username`)
    REFERENCES Users (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Loans_Branches1`
    FOREIGN KEY (`idBranch`)
    REFERENCES Branches (`idBranch`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `library_db`.`Customer_Users`
-- -----------------------------------------------------
DROP TABLE Customer_Users;
CREATE TABLE IF NOT EXISTS Customer_Users (
  username VARCHAR(45) NOT NULL,
  idCustomer INT(11) NOT NULL,
  PRIMARY KEY (username, idCustomer),
  CONSTRAINT `fk_Customer_Users_Users1`
    FOREIGN KEY (username)
    REFERENCES Users (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Customer_Users_Customer1`
    FOREIGN KEY (idCustomer)
    REFERENCES Customer (`idCustomer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library_db`.`Employee_Users`
-- -----------------------------------------------------
DROP TABLE Employee_Users;
CREATE TABLE IF NOT EXISTS Employee_Users (
  `username` VARCHAR(45) NOT NULL,
  `idEmployee` INT(11) NOT NULL,
  PRIMARY KEY (`username`, `idEmployee`),
  CONSTRAINT `fk_Employee_Users_Users1`
    FOREIGN KEY (`username`)
    REFERENCES Users (`username`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Employee_Users_Employee1`
    FOREIGN KEY (`idEmployee`)
    REFERENCES Employee (`idEmployee`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

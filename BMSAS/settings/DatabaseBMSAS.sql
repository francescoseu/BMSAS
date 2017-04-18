-- BMS Anagraphic System
-- Versione 1.6
-- Data 12/04/2017

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema BMSAS_db
-- -----------------------------------------------------
-- Database per BMS Anagraphic System
DROP SCHEMA IF EXISTS `BMSAS_db` ;

-- -----------------------------------------------------
-- Schema BMSAS_db
--
-- Database per BMS Anagraphic System
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `BMSAS_db` DEFAULT CHARACTER SET utf8 ;
USE `BMSAS_db` ;

-- -----------------------------------------------------
-- Table `BMSAS_db`.`Dipendenti`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BMSAS_db`.`Dipendenti` ;

CREATE TABLE IF NOT EXISTS `BMSAS_db`.`Dipendenti` (
  `codiceFiscale` CHAR(16) NOT NULL COMMENT 'Tabella Dipendenti',
  `nome` VARCHAR(45) NOT NULL,
  `cognome` VARCHAR(45) NOT NULL,
  `sesso` ENUM('M', 'F') NOT NULL,
  `dataNascita` DATE NULL,
  `telefono` VARCHAR(50) NULL,
  `email` VARCHAR(50) NULL,
  `domicilio` VARCHAR(60) NULL,
  `mansione` VARCHAR(45) NULL,
  PRIMARY KEY (`codiceFiscale`))
ENGINE = InnoDB
COMMENT = 'Tabella descrizione Dipendenti';


-- -----------------------------------------------------
-- Table `BMSAS_db`.`Strumenti`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BMSAS_db`.`Strumenti` ;

CREATE TABLE IF NOT EXISTS `BMSAS_db`.`Strumenti` (
  `idStrumento` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `modello` VARCHAR(45) NULL,
  `marca` VARCHAR(45) NULL,
  `tipo` VARCHAR(45) NULL,
  `annoAcquisto` VARCHAR(4) NULL,
  PRIMARY KEY (`idStrumento`))
ENGINE = InnoDB
COMMENT = 'Tabella descrizione strumenti';


-- -----------------------------------------------------
-- Table `BMSAS_db`.`Spazi`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BMSAS_db`.`Spazi` ;

CREATE TABLE IF NOT EXISTS `BMSAS_db`.`Spazi` (
  `idSpazio` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `ubicazione` VARCHAR(45) NULL,
  `caratteristiche` VARCHAR(100) NULL,
  PRIMARY KEY (`idSpazio`))
ENGINE = InnoDB
COMMENT = 'Tabella descrizione spazi';


-- -----------------------------------------------------
-- Table `BMSAS_db`.`UsoDipendenteStrumento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BMSAS_db`.`UsoDipendenteStrumento` ;

CREATE TABLE IF NOT EXISTS `BMSAS_db`.`UsoDipendenteStrumento` (
  `Strumenti_idStrumento` INT UNSIGNED NOT NULL,
  `Dipendenti_codiceFiscale` CHAR(16) NOT NULL,
  INDEX `fk_UsoDipendenteStrumento_Strumenti1_idx` (`Strumenti_idStrumento` ASC),
  INDEX `fk_UsoDipendenteStrumento_Dipendenti1_idx` (`Dipendenti_codiceFiscale` ASC),
  PRIMARY KEY (`Strumenti_idStrumento`, `Dipendenti_codiceFiscale`),
  CONSTRAINT `fk_UsoDipendenteStrumento_Strumenti1`
    FOREIGN KEY (`Strumenti_idStrumento`)
    REFERENCES `BMSAS_db`.`Strumenti` (`idStrumento`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_UsoDipendenteStrumento_Dipendenti1`
    FOREIGN KEY (`Dipendenti_codiceFiscale`)
    REFERENCES `BMSAS_db`.`Dipendenti` (`codiceFiscale`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Occupazione di uno Spazio da un particolare Dipendente';


-- -----------------------------------------------------
-- Table `BMSAS_db`.`AssegnazioneSpazioStrumento`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BMSAS_db`.`AssegnazioneSpazioStrumento` ;

CREATE TABLE IF NOT EXISTS `BMSAS_db`.`AssegnazioneSpazioStrumento` (
  `Spazio_idSpazio` INT UNSIGNED NOT NULL,
  `Strumenti_idStrumento` INT UNSIGNED NOT NULL,
  INDEX `fk_AssegnazioneSpazioStrumento_Spazio1_idx` (`Spazio_idSpazio` ASC),
  INDEX `fk_AssegnazioneSpazioStrumento_Strumenti1_idx` (`Strumenti_idStrumento` ASC),
  PRIMARY KEY (`Spazio_idSpazio`, `Strumenti_idStrumento`),
  UNIQUE INDEX `Strumenti_idStrumento_UNIQUE` (`Strumenti_idStrumento` ASC),
  CONSTRAINT `fk_AssegnazioneSpazioStrumento_Spazio1`
    FOREIGN KEY (`Spazio_idSpazio`)
    REFERENCES `BMSAS_db`.`Spazi` (`idSpazio`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_AssegnazioneSpazioStrumento_Strumenti1`
    FOREIGN KEY (`Strumenti_idStrumento`)
    REFERENCES `BMSAS_db`.`Strumenti` (`idStrumento`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Assegnazione di uno Strumento ad un particolare Spazio (lo strumento è pubblico)\n';


-- -----------------------------------------------------
-- Table `BMSAS_db`.`OccupazioneDipendenteSpazio`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BMSAS_db`.`OccupazioneDipendenteSpazio` ;

CREATE TABLE IF NOT EXISTS `BMSAS_db`.`OccupazioneDipendenteSpazio` (
  `Spazio_idSpazio` INT UNSIGNED NOT NULL,
  `Dipendenti_codiceFiscale` CHAR(16) NOT NULL,
  INDEX `fk_OccupazioneDipendenteSpazio_Spazio1_idx` (`Spazio_idSpazio` ASC),
  INDEX `fk_OccupazioneDipendenteSpazio_Dipendenti1_idx` (`Dipendenti_codiceFiscale` ASC),
  PRIMARY KEY (`Spazio_idSpazio`, `Dipendenti_codiceFiscale`),
  UNIQUE INDEX `Dipendenti_codiceFiscale_UNIQUE` (`Dipendenti_codiceFiscale` ASC),
  CONSTRAINT `fk_OccupazioneDipendenteSpazio_Spazio1`
    FOREIGN KEY (`Spazio_idSpazio`)
    REFERENCES `BMSAS_db`.`Spazi` (`idSpazio`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_OccupazioneDipendenteSpazio_Dipendenti1`
    FOREIGN KEY (`Dipendenti_codiceFiscale`)
    REFERENCES `BMSAS_db`.`Dipendenti` (`codiceFiscale`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Occupazione di uno Spazio ad un particolare Dipendente (luogo di lavoro)\n';


-- -----------------------------------------------------
-- Table `BMSAS_db`.`Utenti`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BMSAS_db`.`Utenti` ;

CREATE TABLE IF NOT EXISTS `BMSAS_db`.`Utenti` (
  `idUtente` INT NOT NULL AUTO_INCREMENT,
  `User` VARCHAR(45) NOT NULL,
  `Chiave` VARCHAR(45) NOT NULL,
  `Amministratore` BIT(1) NOT NULL,
  PRIMARY KEY (`idUtente`),
  UNIQUE INDEX `User_UNIQUE` (`User` ASC))
ENGINE = InnoDB
COMMENT = 'Tabella degli Utenti che possono utilizzare il sistema';


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


INSERT INTO Dipendenti VALUES ('RSSMRA85T10A562S', 'Mario', 'Rossi', 'M', '1986-12-10', '080332211', 'mio@email.it', 'Via Togliatti, San Giuliano Terme', 'Reparto Vendite');
INSERT INTO Dipendenti VALUES ('BNCFRC90D20A662R', 'Federico', 'Bianchi', 'M', '1990-04-20', 'casa: 082334455, ufficio: 090887766', 'thunder@gmail.com', 'Via Gramsci, Bari', 'Magazziniere');
INSERT INTO Dipendenti VALUES ('VRDGPP71C01A001E', 'Giuseppe', 'Verdi', 'M', '1971-03-01', '021-337700', 'giuseppe@thunder.com', 'Viale Indipendenza 32, Abano Terme', 'Dirigenza');

INSERT INTO Spazi VALUES (default, 'Sala Riunioni', 'Primo piano, Via Marx', 'Videoproiettore per riuniuni');
INSERT INTO Spazi VALUES (default, 'Sala Server', 'Terzo piano, Via Marx', 'Server per condivisione dati interni');
INSERT INTO Spazi VALUES (default, 'Magazzino', 'Piano terra, Via Marx', 'Magazzino prodotti da evadere');
INSERT INTO Spazi VALUES (default, 'Dirigenza', 'Quarto piano, Via Marx', 'Ufficio dirigenza');

INSERT INTO Strumenti VALUES (default, 'Montacarichi', 'LOAD3000', 'proSolution', 'Lavori pesanti', '2006');
INSERT INTO Strumenti VALUES (default, 'Video Proiettore', 'MaxLumen700F', 'fullRes', 'Ufficio', '2012');
INSERT INTO Strumenti VALUES (default, 'Server Atom 4GB 2x1TB', 'ATOM2t4GB1TB', 'DELL', 'Sistema', '2015');
INSERT INTO Strumenti VALUES (default, 'C14', 'C14-Pro-mod', 'Thunder', 'Prodotto in vendita', '2017');
INSERT INTO Strumenti VALUES (default, 'C14', 'C14-Pro-mod', 'Thunder', 'Prodotto in vendita', '2017');
INSERT INTO Strumenti VALUES (default, 'C14', 'C14-Pro-mod', 'Thunder', 'Prodotto in vendita', '2017');

INSERT INTO Utenti VALUES (default, 'admin', 'admin', 1);
INSERT INTO Utenti VALUES (default, 'utente', 'utente', 0);
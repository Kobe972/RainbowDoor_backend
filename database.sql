-- mysql -hrm-bp1z3kjls0l9871t4po.mysql.rds.aliyuncs.com -uroot -pXyl3331996
USE vrmeet;
DROP TABLE IF EXISTS User_Role;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Role;

CREATE TABLE Role (
  rid int(11) NOT NULL AUTO_INCREMENT,
  rname varchar(100) DEFAULT NULL,
  Constraint PK_R PRIMARY KEY(rid)
);

CREATE TABLE User (
  uid int(11) NOT NULL AUTO_INCREMENT,
  name varchar(100) DEFAULT NULL,
  password varchar(32) DEFAULT NULL,
  rid int(11) NOT NULL,
  PRIMARY KEY (uid),
  Constraint UQ_Q Unique(name),
  Constraint FK_U Foreign Key(rid) References Role(rid)
);

INSERT INTO Role (rname) VALUES('root');
INSERT INTO Role (rname) VALUES('developer');
INSERT INTO Role (rname) VALUES('member');
INSERT INTO User (name, password, rid) VALUES('root', 'xyl3331996', 1);

DROP PROCEDURE IF EXISTS register;
Delimiter //
CREATE PROCEDURE register(IN name Varchar(100), IN password Varchar(32), IN rid INT(11), OUT state INT)
BEGIN
	DECLARE STATUS INT DEFAULT 0;
	DECLARE CONTINUE HANDLER FOR SQLEXCEPTION
		SET STATUS = 1;
	START TRANSACTION;
    Select Count(*) from User Where User.name=name Into @a;
    If @a > 0 Then SET STATUS = 1; End IF;
    Insert Into User(name, password, rid) Values(name, password, rid);
    SET state = STATUS;
    IF STATUS != 0 THEN
        ROLLBACK;
	ELSE
		COMMIT;
	END IF;
END //
Delimiter ;

package database;

import static database.Constants.Tables.*;

public class SQLTableCreationFactory {
    public String getCreateSQLForTable(String table) {
        return switch (table) {
            case USER -> "CREATE TABLE IF NOT EXISTS user (" +
                    "  id int(11) NOT NULL AUTO_INCREMENT," +
                    "  username varchar(200) NOT NULL," +
                    "  password varchar(64) NOT NULL," +
                    "  role_id INT," +
                    "  INDEX role_id_idx (role_id ASC)," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE KEY id_UNIQUE (id)," +
                    "  UNIQUE INDEX username_UNIQUE (username ASC)," +
                    "  CONSTRAINT role_fkid" +
                    "    FOREIGN KEY (role_id)" +
                    "    REFERENCES role (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE" +
                    ");";
            case CLIENT -> "CREATE TABLE IF NOT EXISTS client (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  surname VARCHAR(200) NOT NULL," +
                    "  firstname VARCHAR(200) NOT NULL," +
                    "  idcardnumber VARCHAR(8) NOT NULL," +
                    "  cnp VARCHAR(13) NOT NULL," +
                    "  address VARCHAR(500) NOT NULL," +
                    "  account_id INT," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  UNIQUE INDEX cnp_UNIQUE (cnp ASC)," +
                    "  UNIQUE INDEX account_UNIQUE (account_id ASC)," +
                    "  INDEX account_id_idx (account_id ASC)," +
                    "  CONSTRAINT account_id" +
                    "    FOREIGN KEY (account_id)" +
                    "    REFERENCES `account` (id)" +
                    "    ON DELETE SET NULL" +
                    "    ON UPDATE CASCADE" +
                    ");";
            case ACCOUNT -> "  CREATE TABLE IF NOT EXISTS account (" +
                    "  id INT NOT NULL AUTO_INCREMENT," +
                    "  number VARCHAR(36) NOT NULL," +
                    "  type_id INT NOT NULL," +
                    "  money DOUBLE(11,2) NOT NULL," +
                    "  dateOfCreation datetime NOT NULL," +
                    "  PRIMARY KEY (id)," +
                    "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                    "  UNIQUE INDEX number_UNIQUE (number ASC)," +
                    "  INDEX type_id_idx (type_id ASC)," +
                    "  CONSTRAINT type_id" +
                    "    FOREIGN KEY (type_id)" +
                    "    REFERENCES account_type (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE" +
                    ");";
            case ACCOUNT_TYPE -> "  CREATE TABLE IF NOT EXISTS `account_type` (" +
                    "  `id` INT NOT NULL AUTO_INCREMENT," +
                    "  `type` VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                    "  UNIQUE INDEX `type_UNIQUE` (`type` ASC));";
            case ROLE -> "  CREATE TABLE IF NOT EXISTS `role` (" +
                    "  `id` INT NOT NULL AUTO_INCREMENT," +
                    "  `role` VARCHAR(100) NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                    "  UNIQUE INDEX `right_UNIQUE` (`role` ASC));";
            case REPORT -> "  CREATE TABLE IF NOT EXISTS `report` (" +
                    "  `id` INT NOT NULL AUTO_INCREMENT," +
                    "  `user_id` INT NOT NULL," +
                    "  `action` VARCHAR(100) NOT NULL," +
                    "  actionDate datetime NOT NULL," +
                    "  PRIMARY KEY (`id`)," +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                    "  INDEX user_id_idx (user_id ASC)," +
                    "  CONSTRAINT user_id" +
                    "    FOREIGN KEY (user_id)" +
                    "    REFERENCES user (id)" +
                    "    ON DELETE CASCADE" +
                    "    ON UPDATE CASCADE" +
                    ");";
            default -> "";
        };
    }
}

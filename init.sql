-- Database creation
CREATE DATABASE IF NOT EXISTS quizapplication;
USE quizapplication;

-- Creating tables
CREATE TABLE answer (
                        created_at DATETIME(6),
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        updated_at DATETIME(6),
                        answer VARCHAR(255) NOT NULL,
                        PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE answer_option (
                               correct BIT,
                               answer_id BIGINT NOT NULL,
                               created_at DATETIME(6),
                               id BIGINT NOT NULL AUTO_INCREMENT,
                               question_id BIGINT NOT NULL,
                               updated_at DATETIME(6),
                               PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE question (
                          category TINYINT NOT NULL CHECK (category BETWEEN 0 AND 6),
                          created_at DATETIME(6),
                          id BIGINT NOT NULL AUTO_INCREMENT,
                          quiz_id BIGINT NOT NULL,
                          updated_at DATETIME(6),
                          question VARCHAR(255) NOT NULL,
                          file VARBINARY(255),
                          PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE quiz (
                      category TINYINT NOT NULL CHECK (category BETWEEN 0 AND 6),
                      duration INTEGER,
                      start_date DATE,
                      created_at DATETIME(6),
                      creator_id BIGINT,
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      updated_at DATETIME(6),
                      user_statistic_id BIGINT,
                      PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE quiz_participants (
                                   quiz_id BIGINT NOT NULL,
                                   user_id BIGINT NOT NULL
) ENGINE=InnoDB;

CREATE TABLE user (
                      role TINYINT CHECK (role BETWEEN 0 AND 1),
                      salutation TINYINT CHECK (salutation BETWEEN 0 AND 2),
                      created_at DATETIME(6),
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      updated_at DATETIME(6),
                      country VARCHAR(255),
                      email VARCHAR(255),
                      first_name VARCHAR(255),
                      last_name VARCHAR(255),
                      password VARCHAR(255) NOT NULL,
                      PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE user_statistic (
                                points INTEGER,
                                created_at DATETIME(6),
                                id BIGINT NOT NULL AUTO_INCREMENT,
                                updated_at DATETIME(6),
                                user_id BIGINT NOT NULL,
                                PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Adding constraints
ALTER TABLE IF EXISTS user ADD CONSTRAINT UK_ob8kqyqqgmefl0aco34akdtpe UNIQUE (email);
ALTER TABLE IF EXISTS answer_option ADD CONSTRAINT FKftc5t8ovr3odleva6662dvdhu FOREIGN KEY (answer_id) REFERENCES answer (id);
ALTER TABLE IF EXISTS answer_option ADD CONSTRAINT FKfqeqisl0e28xp3yn9bmlgkhej FOREIGN KEY (question_id) REFERENCES question (id);
ALTER TABLE IF EXISTS question ADD CONSTRAINT FKb0yh0c1qaxfwlcnwo9dms2txf FOREIGN KEY (quiz_id) REFERENCES quiz (id);
ALTER TABLE IF EXISTS quiz ADD CONSTRAINT FK3blfbd5i0et34f65wbdfwf8iv FOREIGN KEY (creator_id) REFERENCES user (id);
ALTER TABLE IF EXISTS quiz ADD CONSTRAINT FK6iuoeil261ww1gguo0gk1t05t FOREIGN KEY (user_statistic_id) REFERENCES user_statistic (id);
ALTER TABLE IF EXISTS quiz_participants ADD CONSTRAINT FKfu8encynylskvwyw0ncun6cb8 FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE IF EXISTS quiz_participants ADD CONSTRAINT FK4x1g8effd85wcc0pr12qa3k19 FOREIGN KEY (quiz_id) REFERENCES quiz (id);
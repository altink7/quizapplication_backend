-- Database creation
CREATE DATABASE IF NOT EXISTS quizapplication;
USE quizapplication;

-- Drop sequences
DROP SEQUENCE IF EXISTS answer_option_seq;
DROP SEQUENCE IF EXISTS answer_seq;
DROP SEQUENCE IF EXISTS question_seq;
DROP SEQUENCE IF EXISTS quiz_seq;
DROP SEQUENCE IF EXISTS user_seq;
DROP SEQUENCE IF EXISTS user_statistic_seq;

-- Create sequences
CREATE SEQUENCE answer_option_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE answer_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE question_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE quiz_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE user_seq START WITH 1 INCREMENT BY 50;
CREATE SEQUENCE user_statistic_seq START WITH 1 INCREMENT BY 50;

-- Create tables
CREATE TABLE answer (
                        answer_id BIGINT NOT NULL,
                        created_at DATETIME(6),
                        updated_at DATETIME(6),
                        answer VARCHAR(255) NOT NULL,
                        PRIMARY KEY (answer_id)
);

CREATE TABLE answer_option (
                               correct BIT,
                               answer_id BIGINT NOT NULL,
                               created_at DATETIME(6),
                               question_id BIGINT NOT NULL,
                               updated_at DATETIME(6),
                               PRIMARY KEY (answer_id)
);

CREATE TABLE question (
                          created_at DATETIME(6),
                          question_id BIGINT NOT NULL,
                          quiz_id BIGINT NOT NULL,
                          updated_at DATETIME(6),
                          question VARCHAR(255) NOT NULL,
                          file VARBINARY(255),
                          PRIMARY KEY (question_id)
);

CREATE TABLE quiz (
                      category TINYINT,
                      created_at DATETIME(6),
                      quiz_id BIGINT NOT NULL,
                      updated_at DATETIME(6),
                      user_id BIGINT,
                      start_time DATETIME(6),
                      duration NUMBER,
                      PRIMARY KEY (quiz_id)
);

CREATE TABLE quiz_participants (
                                   quiz_id BIGINT NOT NULL,
                                   user_id BIGINT NOT NULL
);

CREATE TABLE quiz_statistics (
                                 quiz_id BIGINT NOT NULL,
                                 user_id BIGINT NOT NULL
);

CREATE TABLE user (
                      role TINYINT NOT NULL CHECK (role BETWEEN 0 AND 1),
                      salutation TINYINT,
                      created_at DATETIME(6),
                      updated_at DATETIME(6),
                      user_id BIGINT NOT NULL,
                      user_statistic_id BIGINT,
                      country VARCHAR(255),
                      email VARCHAR(255) NOT NULL,
                      first_name VARCHAR(255) NOT NULL,
                      last_name VARCHAR(255) NOT NULL,
                      password VARCHAR(255) NOT NULL,
                      PRIMARY KEY (user_id)
);

CREATE TABLE user_statistic (
                                points INTEGER,
                                created_at DATETIME(6),
                                updated_at DATETIME(6),
                                user_statistic_id BIGINT NOT NULL,
                                PRIMARY KEY (user_statistic_id)
);

-- Add unique constraints
ALTER TABLE IF EXISTS answer ADD CONSTRAINT UK_hjg7oo4hnwogq6khixxr1b4u3 UNIQUE (answer);
ALTER TABLE IF EXISTS question ADD CONSTRAINT UK_9jpxsp4xpwniiuq9fix978jyq UNIQUE (question);
ALTER TABLE IF EXISTS quiz ADD CONSTRAINT UK_27cdy9ux23mhpkg9a50j8f4f9 UNIQUE (category);
ALTER TABLE IF EXISTS user ADD CONSTRAINT UK_dqo2whafh605w8dmk9ymaobx9 UNIQUE (user_statistic_id);
ALTER TABLE IF EXISTS user ADD CONSTRAINT UK_ob8kqyqqgmefl0aco34akdtpe UNIQUE (email);

-- Add foreign keys
ALTER TABLE IF EXISTS answer_option ADD CONSTRAINT FKfqeqisl0e28xp3yn9bmlgkhej FOREIGN KEY (question_id) REFERENCES question (question_id);
ALTER TABLE IF EXISTS question ADD CONSTRAINT FKb0yh0c1qaxfwlcnwo9dms2txf FOREIGN KEY (quiz_id) REFERENCES quiz (quiz_id);
ALTER TABLE IF EXISTS quiz ADD CONSTRAINT FK1tofsm1qynhakggx7ttqh8ihu FOREIGN KEY (user_id) REFERENCES user (user_id);
ALTER TABLE IF EXISTS quiz_participants ADD CONSTRAINT FKfu8encynylskvwyw0ncun6cb8 FOREIGN KEY (user_id) REFERENCES user (user_id);
ALTER TABLE IF EXISTS quiz_participants ADD CONSTRAINT FK4x1g8effd85wcc0pr12qa3k19 FOREIGN KEY (quiz_id) REFERENCES quiz (quiz_id);
ALTER TABLE IF EXISTS quiz_statistics ADD CONSTRAINT FKr2rt3d8bbm0gob2ci1cdnn0rn FOREIGN KEY (user_id) REFERENCES user_statistic (user_statistic_id);
ALTER TABLE IF EXISTS quiz_statistics ADD CONSTRAINT FK5nk4roklugp20vf75krjin58l FOREIGN KEY (quiz_id) REFERENCES quiz (quiz_id);
ALTER TABLE IF EXISTS user ADD CONSTRAINT FKgkbbq8vnxiykg5rr0hiea0hes FOREIGN KEY (user_statistic_id) REFERENCES user_statistic (user_statistic_id);

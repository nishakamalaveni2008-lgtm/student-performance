CREATE DATABASE IF NOT EXISTS student_performance;
USE student_performance;
CREATE TABLE IF NOT EXISTS students (
 student_id VARCHAR(20) PRIMARY KEY,
 name VARCHAR(100) NOT NULL,
 department VARCHAR(50) NOT NULL,
 year_of_study INT NOT NULL CHECK (year_of_study BETWEEN 1 AND 5)
);
CREATE TABLE IF NOT EXISTS subjects (
 subject_id VARCHAR(20) PRIMARY KEY,
 subject_name VARCHAR(100) NOT NULL,
 max_marks INT NOT NULL CHECK (max_marks > 0),
 pass_marks INT NOT NULL DEFAULT 40 CHECK (pass_marks >= 0 AND pass_marks <= max_marks)
);
CREATE TABLE IF NOT EXISTS marks (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 student_id VARCHAR(20) NOT NULL,
 subject_id VARCHAR(20) NOT NULL,
 internal_marks INT NOT NULL CHECK (internal_marks >= 0),
 exam_marks INT NOT NULL CHECK (exam_marks >= 0),
 total_marks INT NOT NULL,
 average DOUBLE NOT NULL,
 grade VARCHAR(5) NOT NULL,
 status VARCHAR(10) NOT NULL,
 CONSTRAINT fk_marks_student FOREIGN KEY(student_id) REFERENCES students(student_id),
 CONSTRAINT fk_marks_subject FOREIGN KEY(subject_id) REFERENCES subjects(subject_id),
 CONSTRAINT uq_student_subject UNIQUE(student_id,subject_id)
);
-- Sample master data; safe to run once on an empty database.
INSERT IGNORE INTO students VALUES
('25IT001','Arun','IT',3),('25IT002','Bala','IT',3),('25CS001','Divya','CSE',3),('25CS002','Kavin','CSE',3);
INSERT IGNORE INTO subjects VALUES
('IT301','Java Programming',100,40),('IT302','Database Management',100,40),('IT303','Data Science',100,40);
INSERT INTO marks(student_id,subject_id,internal_marks,exam_marks,total_marks,average,grade,status)
SELECT '25IT001','IT301',25,62,87,87,'A+','PASS' WHERE NOT EXISTS(SELECT 1 FROM marks WHERE student_id='25IT001' AND subject_id='IT301');
INSERT INTO marks(student_id,subject_id,internal_marks,exam_marks,total_marks,average,grade,status)
SELECT '25IT002','IT301',20,35,55,55,'B','PASS' WHERE NOT EXISTS(SELECT 1 FROM marks WHERE student_id='25IT002' AND subject_id='IT301');
INSERT INTO marks(student_id,subject_id,internal_marks,exam_marks,total_marks,average,grade,status)
SELECT '25CS001','IT301',18,20,38,38,'RA','FAIL' WHERE NOT EXISTS(SELECT 1 FROM marks WHERE student_id='25CS001' AND subject_id='IT301');

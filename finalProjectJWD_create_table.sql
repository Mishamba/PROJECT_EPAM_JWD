CREATE DATABASE IF NOT EXISTS final_project_jwd;
USE final_project_jwd;

DROP TABLE IF EXISTS student_mark_review; 
DROP TABLE IF EXISTS student_course_references; 
DROP TABLE IF EXISTS teacher_achievements; 
DROP TABLE IF EXISTS hometask_responce;
DROP TABLE IF EXISTS hometasks; 
DROP TABLE IF EXISTS course_programs;
DROP TABLE IF EXISTS courses; 
DROP TABLE IF EXISTS teacher_subjects; 
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE IF NOT EXISTS roles (
	role_name varchar(45) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users (
	id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name varchar(45) NOT NULL,
    last_name varchar(45) NOT NULL,
    email varchar(45) NOT NULL UNIQUE,
    password_hash int NOT NULL,
    birthday varchar(10) NOT NULL,
    role varchar(45) NOT NULL,
    FOREIGN KEY (role) REFERENCES roles(role_name)
);

CREATE TABLE IF NOT EXISTS teacher_subjects (
	teacher_id int NOT NULL,
    subject_name varchar(255) NOT NULL UNIQUE,
    FOREIGN KEY (teacher_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS courses (
	id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	course_name varchar(255) NOT NULL,
    begin_of_course varchar(10),
    end_of_course varchar(10),
    course_teacher int,
    max_students_quantity int NOT NULL,
    finished boolean
);

CREATE TABLE IF NOT EXISTS course_programs (
	course_id int NOT NULL,
    step int NOT NULL,
    step_name varchar(50) NOT NULL,
    step_description varchar(255),
    start_date varchar(10) NOT NULL,
    end_date varchar(10) NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE IF NOT EXISTS hometasks (
	id int NOT NULL PRIMARY KEY AUTO_INCREMENT UNIQUE,
	course_id int NOT NULL,
    hometask_title varchar(45) NOT NULL,
    hometask_description varchar(255) NOT NULL,
    image varchar(255),
    begin_date varchar(10) NOT NULL,
    deadline varchar(10) NOT NULL,
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE IF NOT EXISTS hometask_responce (
	hometask_id int NOT NULL,
    student_id int NOT NULL,
    answer varchar(255) NOT NULL,
    mark int,
    FOREIGN KEY (hometask_id) REFERENCES hometasks(id),
    FOREIGN KEY (student_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS teacher_achievements (
	teacher_id int NOT NULL,
    leaded_course int NOT NULL,
    lead_start varchar(10) NOT NULL,
    lead_end varchar(10) NOT NULL,
    from_start_to_end boolean NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES users(id),
    FOREIGN KEY (leaded_course) REFERENCES courses(id)
);

CREATE TABLE IF NOT EXISTS student_course_references (
	student_id int NOT NULL,
    course_id int NOT NULL,
    FOREIGN KEY (student_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE IF NOT EXISTS student_mark_review (
	student_id int NOT NULL,
    teacher_id int NOT NULL,
    course_id int NOT NULL,
    finished boolean NOT NULL,
    mark int NOT NULL,
    finished_date varchar(10) NOT NULL,
    review varchar(255) NOT NULL,
    got_certificate boolean NOT NULL,
    FOREIGN KEY (student_id) REFERENCES users(id),
    FOREIGN KEY (teacher_id) REFERENCES users(id),
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

INSERT INTO roles VALUE ('student');
INSERT INTO roles VALUE ('teacher');
INSERT INTO roles VALUE ('admin');

INSERT INTO users (first_name, last_name, email, 
password_hash, birthday, role) 
VALUES ('Misha', 'Nenahov', 'misha.nenahov@gmail.com', 
3506402, '2000-11-10', 'student');
-- Misha Nehahov password "root"
INSERT INTO users (first_name, last_name, email,
password_hash, birthday, role)
VALUES ('Grisha', 'Zholud', 'grisha.zholud@gmail.com',
-1237641458, '2001-10-15', 'student');
-- Grisha Zholud password "grisha"
INSERT INTO users (first_name, last_name, email,
password_hash, birthday, role)
VALUES ('Kirill', 'Rachitskiy', 'big.dog@gmail.com',
115864178, '2001-05-20', 'student');
-- Kirill Rachitskiy password "zhaba"
INSERT INTO users (first_name, last_name, email,
password_hash, birthday, role)
VALUES ('Arkadiy', 'Lezhepekov', 'lezheboker@bsuir.com',
104079552, '1998-03-06', 'student');
-- Arkadiy Lezhepekov password "money"
INSERT INTO users (first_name, last_name, email,
password_hash, birthday, role)
VALUES ('Vasiliy', 'Grib', 'vasiliy.grib@gmail.com',
190281326, '2004-09-05', 'student');
-- Vasiliy Grib password "university"

INSERT INTO users (first_name, last_name, email,
password_hash, birthday, role)
VALUES ('Mihail', 'Metelskiy', 'metelskiy@mail.ru',
3344136, '1975-12-10', 'teacher');
-- Mihail Metelskiy password "math"
INSERT INTO users (first_name, last_name, email,
password_hash, birthday, role)
VALUES ('Nikolay', 'Batin', 'batin@gmail.com',
-599163109, '1980-04-12', 'teacher');
-- Nikolay Batin password "computer"

INSERT INTO users (first_name, last_name, email,
password_hash, birthday, role)
VALUES ('Ulia', 'Drozdova', 'ulia@gmail.com',
415761883, '1992-11-29', 'admin');
-- Admin password "really_hard_password"

INSERT INTO teacher_subjects (teacher_id, subject_name)
VALUES (6, 'math');
INSERT INTO teacher_subjects (teacher_id, subject_name)
VALUES (7, 'KIT');
INSERT INTO teacher_subjects (teacher_id, subject_name)
VALUES (7, 'DB');

INSERT INTO courses (course_name, begin_of_course, 
end_of_course, course_teacher, max_students_quantity, finished)
VALUES ('math', '2019-10-10', '2020-01-10', 6, 20, TRUE);
INSERT INTO courses (course_name, begin_of_course,
end_of_course, course_teacher, max_students_quantity, finished)
VALUES ('KIT', '2020-11-20', '2021-05-25', 7, 30, FALSE);
INSERT INTO courses (course_name, begin_of_course,
end_of_course, course_teacher, max_students_quantity, finished)
VALUES ('Programming', '2021-02-15', '2021-05-25', NULL, 50, FALSE);

INSERT INTO course_programs (course_id, 
step, step_name, step_description, start_date, end_date)
VALUES (1, 1, 'Simple math', 
'At this step students will learn, how to use +, -, *, / operations.',
'2020-10-10', '2020-11-10');
INSERT INTO course_programs (course_id, 
step, step_name, step_description, start_date, end_date)
VALUES (1, 2, 'Equation bases',
'Students will learn how to solve equations.',
'2020-11-11', '2021-01-01');
INSERT INTO course_programs (course_id, 
step, step_name, step_description, start_date, end_date)
VALUES (1, 3, 'Exams',
'Students will pass exams on simple math operations and skill to solve equations',
'2021-01-02', '2021-01-10');
INSERT INTO course_programs (course_id, 
step, step_name, step_description, start_date, end_date)
VALUES (2, 1, 'Excel',
'At this step students will learn how to use excel.',
'2020-11-20', '2021-02-10');
INSERT INTO course_programs (course_id, 
step, step_name, step_description, start_date, end_date)
VALUES (2, 2, 'Word', 
'Students will get bases of Word Text Editor',
'2021-02-11', '2021-04-15');
INSERT INTO course_programs (course_id, 
step, step_name, step_description, start_date, end_date)
VALUES (2, 3, 'Excel VBA',
'At this step students will learn how to write VBA programs.',
'2021-04-16', '2021-05-20');
INSERT INTO course_programs (course_id, 
step, step_name, step_description, start_date, end_date)
VALUES (2, 4, 'Exams',
'Students will pass exams on knowleges of base Excel, Excel VBA.',
'2021-05-21', '2021-05-25');
INSERT INTO course_programs (course_id, 
step, step_name, step_description, start_date, end_date)
VALUES (3, 1, 'C++',
'Bases of C++, Some info about Code Editors.',
'2021-02-15', '2021-03-20');
INSERT INTO course_programs (course_id, 
step, step_name, step_description, start_date, end_date)
VALUES (3, 2, 'Java',
'Bases of OOP, Tomcat, TestNG.',
'2021-03-21', '2021-04-20');
INSERT INTO course_programs (course_id, 
step, step_name, step_description, start_date, end_date)
VALUES (3, 3, 'Java',
'Bases of HTTP, Serlets, Databases',
'2021-04-21', '2021-05-20');
INSERT INTO course_programs (course_id, 
step, step_name, step_description, start_date, end_date)
VALUES (3, 4, 'Exams',
NULL,
'2021-05-21', '2021-05-25');

INSERT INTO student_course_references (student_id, course_id)
VALUES (1, 1);
INSERT INTO student_course_references (student_id, course_id)
VALUES (2, 1);
INSERT INTO student_course_references (student_id, course_id)
VALUES (3, 1);
INSERT INTO student_course_references (student_id, course_id)
VALUES (4, 2);
INSERT INTO student_course_references (student_id, course_id)
VALUES (5, 2);
INSERT INTO student_course_references (student_id, course_id)
VALUES (3, 3);

INSERT INTO hometasks (course_id, hometask_title, 
hometask_description,
begin_date, deadline)
VALUES (1, '+ - * / Practice', 
'Find X, Y, Z:\n4+2=X\n(3-1)/2=Y\n5*4=Z\n',
'2020-10-10', '2020-11-01');
INSERT INTO hometasks (course_id, hometask_title, 
hometask_description,
begin_date, deadline)
VALUES (1, 'Equation Pratice',
'Find X, Y, Z:\nX*2=4\nY+5=15\n2*Z-15=15',
'2020-11-13', '2020-12-25');
INSERT INTO hometasks (course_id, hometask_title, 
hometask_description,
begin_date, deadline)
VALUES (1, 'Tasks to prepare for Exam',
'Find X, Y, Z:\n5+2=X\n6-3=Y\n2+1=Z\nFind A, B, C\nA+4=5\n3*B-4=8\n15/C=5',
'2021-01-01', '2021-01-02');

INSERT INTO hometask_responce (hometask_id, student_id, answer)
VALUES (1, 1, 'X=6, Y=1, Z=20');
INSERT INTO hometask_responce (hometask_id, student_id, answer)
VALUES (1, 2, 'X=6, Y=2, Z=20');
INSERT INTO hometask_responce (hometask_id, student_id, answer)
VALUES (1, 3, 'X=5, Y=3, Z=21');
INSERT INTO hometask_responce (hometask_id, student_id, answer)
VALUES (2, 1, 'X=2, Y=10, Z=15');
INSERT INTO hometask_responce (hometask_id, student_id, answer)
VALUES (2, 2, 'X=2, Y=9, Z=14');
INSERT INTO hometask_responce (hometask_id, student_id, answer)
VALUES (2, 3, 'X=2, Y=10, Z=15');
INSERT INTO hometask_responce (hometask_id, student_id, answer)
VALUES (3, 1, 'X=7, Y=3, Z=3, A=1, B=12, C=3');
INSERT INTO hometask_responce (hometask_id, student_id, answer)
VALUES (3, 2, 'X=7, Y=3, Z=3, A=1, B=12, C=3');
INSERT INTO hometask_responce (hometask_id, student_id, answer)
VALUES (3, 3, 'X=7, Y=3, Z=3, A=1, B=12, C=3');

UPDATE hometask_responce SET
mark=7
WHERE hometask_id = 1 AND student_id = 1;
UPDATE hometask_responce SET
mark=8
WHERE hometask_id = 1 AND student_id = 2;
UPDATE hometask_responce SET
mark=7
WHERE hometask_id = 1 AND student_id = 3;
UPDATE hometask_responce SET
mark=9
WHERE hometask_id = 2 AND student_id = 1;
UPDATE hometask_responce SET
mark=5
WHERE hometask_id = 2 AND student_id = 2;
UPDATE hometask_responce SET
mark=8
WHERE hometask_id = 2 AND student_id = 3;
UPDATE hometask_responce SET
mark=10
WHERE hometask_id = 3 AND student_id = 1;
UPDATE hometask_responce SET
mark=10
WHERE hometask_id = 3 AND student_id = 2;
UPDATE hometask_responce SET
mark=9
WHERE hometask_id = 3 AND student_id = 3;

INSERT INTO student_mark_review (student_id, teacher_id,
course_id, finished, mark, finished_date, review, got_certificate)
VALUES (1, 6, 1, TRUE, 10, '2020-01-10',
'Good student. complited all tasks correct. very smart student. i\' like to work with this student once again.', 
TRUE);
INSERT INTO student_mark_review (student_id, teacher_id,
course_id, finished, mark, finished_date, review, got_certificate)
VALUES (2, 6, 1, TRUE, 7, '2020-01-10',
'Good student. Complited all tasks good enought. Need to practice more. Very polite student. Did a great God at course.', TRUE);
INSERT INTO student_mark_review (student_id, teacher_id,
course_id, finished, mark, finished_date, review, got_certificate)
VALUES (3, 6, 1, TRUE, 5, '2020-01-10',
'First task complited poorly, but after that made some worked on his self and made some success at course. But passed exam badly, because didn\'t complited task to prepare for exam.', 
FALSE);
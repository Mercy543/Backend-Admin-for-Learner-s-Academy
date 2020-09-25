use academydb;
/**tclass table**/
CREATE TABLE class
(
classID INT auto_increment PRIMARY KEY,
description NVARCHAR(100)
);
ALTER TABLE emp MODIFY COLUMN name VARCHAR(100);

/**teacher table**/
CREATE TABLE teacher
(
teacherID INT auto_increment PRIMARY KEY,
firstname NVARCHAR(25),
lastname NVARCHAR(25),
email NVARCHAR(25),
phone NVARCHAR(15)
);

/**student table**/
CREATE TABLE student
(
studentid INT auto_increment PRIMARY KEY,
firstname VARCHAR(25),
lastname VARCHAR(25),
classid int(11) ,
enrolled_date timestamp default now(),
FOREIGN KEY fk_student_class(classid)
REFERENCES class(classid)
);


/**teacher subject table**/
CREATE TABLE teacher_subject(
st_id int(11) auto_increment PRIMARY KEY,
teacherid INT NOT NULL,
classid int not null,
scode varchar(40) NOT NULL,
FOREIGN KEY fk_tt(teacherid) REFERENCES teacher(teacherid),
FOREIGN KEY fk_tc(classid) REFERENCES class(classid),
FOREIGN KEY fk_ts(scode) REFERENCES subject(scode),
CONSTRAINT ts_unique UNIQUE (classid,scode)

);

/**student subject table**/
CREATE TABLE student_subject(
ss_id int(11) auto_increment PRIMARY KEY,
studentid INT NOT NULL,
scode varchar(40) NOT NULL,
FOREIGN KEY fk_stud_stud(studentid) REFERENCES student(studentid),
FOREIGN KEY fk_stud_sub(scode) REFERENCES subject(scode),
CONSTRAINT ss_unique UNIQUE (studentid,scode)

);

/**new class**/
CREATE procedure add_class(IN cdescription varchar(10))
insert into class(description) values(cdescription);

/**studentbyid**/
CREATE PROCEDURE find_student(IN sid int(100)) 
SELECT * from student where studentid=sid;


/**teacher detail**/
select t.teacherID,t.firstname,t.lastname,t.email,t.phone,ts.scode from teacher t   
left join teacher_subject ts on t.teacherid =ts.teacherid;


/**teacherbyteacherid**/
CREATE  PROCEDURE `find_teacherbyid`(IN tid int(100))
Select t.teacherID,firstname,lastname,s.scode,s.description,c.description 
from teacher t left join teacher_subject ts on t.teacherid=ts.teacherid left join subject s on s.scode = ts.scode
left join class c on c.classid=s.classid where t.teacherID=tid;


/**studentbyclassid**/
CREATE  PROCEDURE `find_studentbyclassid`(IN cid int(40))
Select studentid,firstname,lastname,description,enrolled_date 
from class c join student s on c.classID=s.classid where c.classid =cid

/**subject detail**/
SELECT s.scode,s.description,s.classid,firstname,lastname,t.teacherid 
FROM subject s  join teacher_subject  ts on s.scode=ts.scode join teacher t on t.teacherid=ts.teacherid;

/**teacherbyclassid**/
CREATE PROCEDURE `find_teacherbyclassid`(IN cid int(40))
Select firstname,lastname,t.teacherid,s.description,ts.scode,c.classid,c.description
from class c join teacher_subject ts on c.classid =ts.classid 
join teacher t on t.teacherid=ts.teacherid  
join subject s on s.scode=ts.scode where c.classid=cid;

/**subjectbyclassid**/
CREATE PROCEDURE `find_subjectbyclassid`(IN cid int(40))
Select c.classid,c.description,s.description,s.scode,s.unit from class c 
join  subject s on s.classid=c.classid  where c.classid =cid;



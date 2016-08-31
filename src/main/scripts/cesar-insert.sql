set client_min_messages=WARNING;

-----------------
-- ethnicities --
-----------------

insert into ethnicities (id, name) values
    ( 100, 'White' );
insert into ethnicities (id, name) values
    ( 101, 'Black' );
insert into ethnicities (id, name) values
    ( 102, 'Hispanic/Latino' );
insert into ethnicities (id, name) values
    ( 103, 'Asian' );
insert into ethnicities (id, name) values
    ( 104, 'Pacific Islander' );

------------
-- majors --
------------

insert into majors (id, symbol, name, is_regular) values
    ( 300, 'CE', 'Civil Engineering','t' );
insert into majors (id, symbol, name, is_regular) values
    ( 301, 'CS', 'Computer Science' ,'t');
insert into majors (id, symbol, name, is_regular) values
    ( 302, 'EE', 'Electrical Engineering','t' );
insert into majors (id, symbol, name, is_regular) values
    ( 303, 'ME', 'Mechanical Engineering','t' );
insert into majors (id, symbol, name, is_regular) values
    ( 304, 'TECH', 'Technology','t' );
insert into majors (id, symbol, name, is_regular) values
	( 305, 'MATH', 'Mathematics', 'f');
insert into majors (id, symbol, name, is_regular) values
	( 306, 'PHYS', 'Physics', 'f');
insert into majors (id, symbol, name, is_regular) values
    ( 307, 'GENERAL', 'General Education','f' );
    
--------------------------
-- high school programs --   
--------------------------

insert into high_school_programs (id, name) values
	( 400, 'STEP');
insert into high_school_programs (id, name) values
	( 401, 'MEP');
insert into high_school_programs (id, name) values
	( 402, 'MSP');
insert into high_school_programs (id, name) values
	( 403, 'LSAMP');
insert into high_school_programs (id, name) values
	( 404, 'EOP');
insert into high_school_programs (id, name) values
	( 405, 'VESTED');
insert into high_school_programs (id, name) values
	( 406, 'Young Black Scholars');
insert into high_school_programs (id, name) values
	( 407, 'Upper Bound');	
	
--------------------------
-- financial aid types  --   
--------------------------

insert into financial_aid_types (id, name) values
	( 500, 'Loans');
insert into financial_aid_types (id, name) values
	( 501, 'Grants');
insert into financial_aid_types (id, name) values
	( 502, 'Work_study');

	
	
--------------------------------------
-- extra curriculum activity types  --   
--------------------------------------

insert into extra_curriculum_activity_types (id, name) values
	( 600, 'Scholarships received');
insert into extra_curriculum_activity_types (id, name) values
	( 601, 'Internships');
insert into extra_curriculum_activity_types (id, name) values
	( 602, 'Conferences Attend');	
insert into extra_curriculum_activity_types (id, name) values
	( 603, 'Student Organizations');
insert into extra_curriculum_activity_types (id, name) values
	( 604, 'Honor Societies');
insert into extra_curriculum_activity_types (id, name) values
	( 605, 'Leadership Positions');
insert into extra_curriculum_activity_types (id, name) values
	( 606, 'Volunteer Experience');
insert into extra_curriculum_activity_types (id, name) values
	( 607, 'Student Projects Participation');
insert into extra_curriculum_activity_types (id, name) values
	( 608, 'Research Opportunities Obtained');
insert into extra_curriculum_activity_types (id, name) values
	( 609, 'Honor/Award Received');

-------------------------
--- appointment types ---
-------------------------
insert into appointment_types(id, name) values
( 200, 'Online');
insert into appointment_types(id, name) values
( 201,'In Person');
insert into appointment_types(id, name) values
( 202, 'Advisor Scheduled');
insert into appointment_types(id, name) values
( 203, 'By Phone');
insert into appointment_types(id, name) values
( 204, 'By Email');
insert into appointment_types(id, name) values
( 205, 'Others');

-----------------------------
-- Reasons for appointment --
-----------------------------
insert into reasons_for_appointment(id, name ) values
( 300, 'Academic Advisement');
insert into reasons_for_appointment(id, name ) values
( 301, 'Personal Advisement');
insert into reasons_for_appointment(id, name ) values
( 302, 'Financial Aid');
insert into reasons_for_appointment(id, name ) values
( 303, 'Form and Procedures');
insert into reasons_for_appointment(id, name ) values
( 304, 'Clubs and Organizations');
insert into reasons_for_appointment(id, name ) values
( 305, 'LSAMP');
insert into reasons_for_appointment(id, name ) values
( 306, 'Make An Appointment');
insert into reasons_for_appointment(id, name ) values
( 307, 'Scholarship/Internship');
insert into reasons_for_appointment(id, name ) values
( 308, 'General Questions');
insert into reasons_for_appointment(id, name ) values
( 309, 'Others');


-- service types --
-------------------
insert into service_types( id, name) values
	(700, 'Checking out books');
insert into service_types( id, name) values
	(701, 'Course Supplemental Meterial');
insert into service_types( id, name) values
	(702, 'Dry-erase markers');
insert into service_types( id, name) values
	(703, 'Dry-eraser');
insert into service_types( id, name) values
	(704, 'Others');

------------------------
-- visit reason types --
------------------------
insert into visit_reason_types( id, name ) values
	('800', 'Schedule an appointment in person');
insert into visit_reason_types( id, name ) values
	('801', 'Request a walk-in appointment');
insert into visit_reason_types( id, name ) values
	('802', 'Academic advisement apppointment');
insert into visit_reason_types( id, name ) values
	('803', 'Pick up forms');
insert into visit_reason_types( id, name ) values
	('804', 'Obtain general information without meeting with an advisor');
insert into visit_reason_types( id, name ) values
	('805', 'Personal advisement');
insert into visit_reason_types( id, name ) values
	('806', 'Change of major');
insert into visit_reason_types( id, name ) values
	('807', 'Financial aid');
insert into visit_reason_types( id, name ) values
	('808', 'External program advisement');
insert into visit_reason_types( id, name ) values
	('809', 'Others');
	
--------------------------
-- no seen reason types --
--------------------------
insert into no_seen_reason_types ( id, name ) values
	('900','Advisor booked for the day');
insert into no_seen_reason_types ( id, name ) values
	('901','Advisors absent');
insert into no_seen_reason_types ( id, name ) values
	('902','Waiting time too long');
insert into no_seen_reason_types ( id, name ) values
	('903','Others');

----------------
-- CS courses --
----------------
insert into courses(id, code, name, units, is_graduate_course) values
(1000,'CS101','Introduction to Higher Education for Computer Science Major',2, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1001,'CS120','Introduction to Web Site Development',3, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1002,'CS122','Using Relational Databases and SQL',3, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1003,'CS160','Introduction to Computer',3,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1004,'CS190','BASIC Programming',2,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1005,'CS201','Introduction to Programming',5,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1006,'CS202','Introduction to Object Oriented Programming',5, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1007,'CS203','Programming with Data Structures',5, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1008,'CS242','C Programming',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1009,'CS245','Using Operating System and Networks for Programming',3,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1010,'CS290',' Introduction to FORTRAN Programming',2,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1011,'CS301','Computer Ethics in the Information Age',1, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1012,'CS312','Data Structures and Algorithms',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1013,'CS320',' Web and Internet Programming',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1014,'CS332F','Functional Programming',2, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1015,'CS332L',' Logic Programming',2,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1016,'CS332C','C++ Object Oriented Programming',2,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1017,'CS337','Software Design',3,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1018,'CS340','Assembly Language and Systems Programming',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1019,'CS342','Object Oriented Programming Using C++',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1020,'CS345','UNIX and Shell Programming',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1021,'CS350','Foundations of Computer Graphics',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1022,'CS370','Parallel and Distributed Programming',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1023,'CS386','Introduction to Automata Theory',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1024,'CS420','Web Applications Architecture',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1025,'CS422','Principles of Data Base Systems',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1026,'CS437','Software Engineering',5, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1027,'CS440','Introduction to Operating Systems',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1028,'CS447','Computer Networks',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1029,'CS450','Computer Graphics',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1030,'CS451','Multimedia Software Systems',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1031,'CS454','Topics in Advanced Computer Science',1, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1032,'CS460','Artificial Intelligence',4,  'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1033,'CS461','Machine Learning',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1034,'CS470','Computer Networking Protocols',4, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1035,'CS480','Cryptography and Information Security',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1036,'CS486',' Computability and Intractability',4, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1037,'CS488','Compilers',4, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1038,'CS490','Computer Science Recapitulation',2,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1039,'CS496A','Software Design Laboratory',2,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1040,'CS496B','Software Design Laboratory',2,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1041,'CS496C','Software Design Laboratory',2, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1042,'CS499','Undergraduate Directed Study ',1, 'f');


----------------
-- EE Courses --
----------------
insert into courses(id, code, name, units,  is_graduate_course) values
(1244,'EE444','Computer Architecture',4, 'f');


----------------
--Math courses--
----------------
insert into courses(id, code, name, units,  is_graduate_course) values
(1600,'MATH103','Algebra and Trigonometry',4, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1601,'MATH206','Calculus I:Differentiation',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1602,'MATH207','Calculus II: Integration',4, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1603,'MATH208','Calculus III: Sequences, Series, and Coordinate Systems',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1604,'MATH248','Discrete Mathematics',4, 'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1605,'MATH255','Introduction to Matrix Theory',4,'f');
insert into courses(id, code, name, units,  is_graduate_course) values
(1606,'MATH270','Introduction to Probability and Statistics',4,'f');

-------------------
--Major_Courses ---
-------------------

insert into major_courses( major_id, course_id) values
(301,1000);
insert into major_courses( major_id, course_id) values
(301,1001);
insert into major_courses( major_id, course_id) values
(301,1002);
insert into major_courses( major_id, course_id) values
(301,1003);
insert into major_courses( major_id, course_id) values
(301,1004);
insert into major_courses( major_id, course_id) values
(301,1005);
insert into major_courses( major_id, course_id) values
(301,1006);
insert into major_courses( major_id, course_id) values
(301,1007);
insert into major_courses( major_id, course_id) values
(301,1008);
insert into major_courses( major_id, course_id) values
(301,1009);
insert into major_courses( major_id, course_id) values
(301,1010);
insert into major_courses( major_id, course_id) values
(301,1011);
insert into major_courses( major_id, course_id) values
(301,1012);
insert into major_courses( major_id, course_id) values
(301,1013);
insert into major_courses( major_id, course_id) values
(301,1014);
insert into major_courses( major_id, course_id) values
(301,1015);
insert into major_courses( major_id, course_id) values
(301,1016);
insert into major_courses( major_id, course_id) values
(301,1017);
insert into major_courses( major_id, course_id) values
(301,1018);
insert into major_courses( major_id, course_id) values
(301,1019);
insert into major_courses( major_id, course_id) values
(301,1020);
insert into major_courses( major_id, course_id) values
(301,1021);
insert into major_courses( major_id, course_id) values
(301,1022);
insert into major_courses( major_id, course_id) values
(301,1023);
insert into major_courses( major_id, course_id) values
(301,1024);
insert into major_courses( major_id, course_id) values
(301,1025);
insert into major_courses( major_id, course_id) values
(301,1026);
insert into major_courses( major_id, course_id) values
(301,1027);
insert into major_courses( major_id, course_id) values
(301,1028);
insert into major_courses( major_id, course_id) values
(301,1029);
insert into major_courses( major_id, course_id) values
(301,1030);
insert into major_courses( major_id, course_id) values
(301,1031);
insert into major_courses( major_id, course_id) values
(301,1032);
insert into major_courses( major_id, course_id) values
(301,1033);
insert into major_courses( major_id, course_id) values
(301,1034);
insert into major_courses( major_id, course_id) values
(301,1035);
insert into major_courses( major_id, course_id) values
(301,1036);
insert into major_courses( major_id, course_id) values
(301,1037);
insert into major_courses( major_id, course_id) values
(301,1038);
insert into major_courses( major_id, course_id) values
(301,1039);
insert into major_courses( major_id, course_id) values
(301,1040);
insert into major_courses( major_id, course_id) values
(301,1041);
insert into major_courses( major_id, course_id) values
(301,1042);

insert into major_courses( major_id, course_id) values
(301,1244);
insert into major_courses( major_id, course_id) values
(301,1600);
insert into major_courses( major_id, course_id) values
(301,1601);
insert into major_courses( major_id, course_id) values
(301,1602);
insert into major_courses( major_id, course_id) values
(301,1603);
insert into major_courses( major_id, course_id) values
(301,1604);
insert into major_courses( major_id, course_id) values
(301,1605);
insert into major_courses( major_id, course_id) values
(301,1606);

insert into major_courses( major_id, course_id) values
(302,1244);

insert into major_courses( major_id, course_id) values
(305,1600);
insert into major_courses( major_id, course_id) values
(305,1601);
insert into major_courses( major_id, course_id) values
(305,1602);
insert into major_courses( major_id, course_id) values
(305,1603);
insert into major_courses( major_id, course_id) values
(305,1604);
insert into major_courses( major_id, course_id) values
(305,1605);
insert into major_courses( major_id, course_id) values
(305,1606);








-------------------
-- prerequisites --
-------------------
insert into prerequisites( id ) values(100);
insert into prerequisites( id ) values(101);
insert into prerequisites( id ) values(102);
insert into prerequisites( id ) values(103);
insert into prerequisites( id ) values(104);
insert into prerequisites( id ) values(105);
insert into prerequisites( id ) values(106);
insert into prerequisites( id ) values(107);
insert into prerequisites( id ) values(108);
insert into prerequisites( id ) values(109);
insert into prerequisites( id ) values(110);
insert into prerequisites( id ) values(111);
insert into prerequisites( id ) values(112);
insert into prerequisites( id ) values(113);
insert into prerequisites( id ) values(114);
insert into prerequisites( id ) values(115);
insert into prerequisites( id ) values(116);
insert into prerequisites( id ) values(117);
insert into prerequisites( id ) values(118);
insert into prerequisites( id ) values(119);
insert into prerequisites( id ) values(120);
insert into prerequisites( id ) values(121);
insert into prerequisites( id ) values(122);
insert into prerequisites( id ) values(123);
insert into prerequisites( id ) values(124);
insert into prerequisites( id ) values(125);
insert into prerequisites( id ) values(126);
insert into prerequisites( id ) values(127);
insert into prerequisites( id ) values(128);
insert into prerequisites( id ) values(129);
insert into prerequisites( id ) values(130);
insert into prerequisites( id ) values(131);
insert into prerequisites( id ) values(132);
insert into prerequisites( id ) values(133);
insert into prerequisites( id ) values(134);
insert into prerequisites( id ) values(135);
insert into prerequisites( id ) values(136);
insert into prerequisites( id ) values(137);
insert into prerequisites( id ) values(138);
insert into prerequisites( id ) values(139);
insert into prerequisites( id ) values(140);
insert into prerequisites( id ) values(141);
insert into prerequisites( id ) values(142);
insert into prerequisites( id ) values(143);
insert into prerequisites( id ) values(144);
insert into prerequisites( id ) values(145);
insert into prerequisites( id ) values(146);
insert into prerequisites( id ) values(147);
insert into prerequisites( id ) values(148);
insert into prerequisites( id ) values(149);
insert into prerequisites( id ) values(150);
insert into prerequisites( id ) values(151);
insert into prerequisites( id ) values(152);
insert into prerequisites( id ) values(153);
insert into prerequisites( id ) values(154);
insert into prerequisites( id ) values(155);
insert into prerequisites( id ) values(156);
insert into prerequisites( id ) values(157);
insert into prerequisites( id ) values(158);
insert into prerequisites( id ) values(159);
insert into prerequisites( id ) values(160);
insert into prerequisites( id ) values(161);
insert into prerequisites( id ) values(162);
insert into prerequisites( id ) values(163);
insert into prerequisites( id ) values(164);
insert into prerequisites( id ) values(165);

--------------------------
-- course_prerequisites --
--------------------------
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1005, 100);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1006, 101);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1006, 102);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1007, 103);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1007, 104);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1007, 105);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1008, 106);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1009, 107);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1010, 108);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1011, 109);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1012, 110);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1012, 111);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1012, 112);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1013, 113);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1013, 114);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1013, 115);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1014, 116);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1015, 117);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1016, 118);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1017, 119);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1018, 120);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1019, 121);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1020, 122);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1021, 123);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1021, 124);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1021, 125);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1022, 126);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1022, 127);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1023, 128);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1023, 129);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1024, 130);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1025, 131);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1025, 132);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1026, 133);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1027, 134);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1027, 135);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1028, 136);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1028, 137);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1029, 138);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1029, 139);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1030, 140);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1030, 141);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1032, 142);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1033, 143);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1034, 144);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1034, 145);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1035, 146);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1035, 147);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1036, 148);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1037, 149);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1037, 150);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1037, 151);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1037, 152);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1038, 153);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1038, 154);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1038, 155);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1038, 156);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1038, 157);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1038, 158);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1038, 159);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1039, 160);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1039, 161);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1039, 162);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1039, 163);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1040, 164);
insert into course_prerequisites ( course_id, prerequisite_id ) values
(1041, 165);
--------------------------
-- prerequisite_courses --
--------------------------
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 100 , 1600);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 101 , 1005);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 102 , 1601);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 103 , 1006);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 104 , 1602);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 105 , 1604);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 106 , 1600);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 107 , 1006);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 108 , 1601);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 109 , 1007);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 110 , 1007);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 111 , 1603);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 112 , 1604);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 113 , 1001);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 114 , 1002);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 115 , 1007);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 116 , 1007);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 117 , 1007);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 118 , 1007);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 119 , 1007);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 120 , 1009);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 121 , 1008);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 122 , 1007);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 123 , 1007);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 124 , 1603);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 125 , 1605);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 126 , 1007);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 127 , 1009);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 128 , 1006);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 129 , 1604);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 130 , 1013);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 131 , 1002);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 132 , 1012);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 133 , 1012);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 134 , 1009);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 135 , 1012);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 136 , 1606);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 137 , 1027);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 138 , 1012);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 139 , 1021);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 140 , 1012);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 141 , 1019);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 142 , 1012);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 143 , 1012);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 144 , 1009);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 145 , 1012);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 146 , 1009);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 147 , 1012);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 148 , 1023);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 149 , 1012);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 150 , 1014);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 151 , 1015);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 152 , 1016);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 153 , 1606);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 154 , 1015);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 155 , 1016);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 156 , 1023);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 157 , 1026);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 158 , 1027);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 159 , 1244);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 160 , 1012);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 161 , 1013);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 162 , 1017);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 163 , 1023);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 164 , 1039);
insert into prerequisite_courses ( prerequisite_id, course_id ) values
( 165 , 1040);


----------------------
----week days --------
----------------------

insert into week_days( id, code, name, symbol) values
(300, 2, 'Monday', 'M');
insert into week_days( id, code, name, symbol) values
(301, 3, 'Tuesday', 'T');
insert into week_days( id, code, name, symbol) values
(302, 4, 'Wednesday', 'W');
insert into week_days( id, code, name, symbol) values
(303, 5, 'Thursday', 'R');
insert into week_days( id, code, name, symbol) values
(304, 6, 'Friday', 'F');
insert into week_days( id, code, name, symbol) values
(305, 7, 'Saturday', 'S');
insert into week_days( id, code, name, symbol) values
(306, 8, 'Sunday', 'U');


-----------
-- roles --
-----------

insert into roles (id, name, description) values
    ( 10, 'ROLE_NEWUSER', 'New users must update their account info.' );
insert into roles (id, name, description) values
    ( 11, 'ROLE_USER', 'Everybody.' );
insert into roles (id, name, description) values
    ( 20, 'ROLE_STUDENT', 'Students.' );
insert into roles (id, name, description) values
    ( 21, 'ROLE_ADVISOR', 'Advisors.' );
insert into roles (id, name, description) values
    ( 22, 'ROLE_STAFF', 'ARC staff.' );

---------------------------
-- users and authorities --
---------------------------

insert into users (id, username, password, enabled, cin, first_name, last_name, email) values
    ( 1000, 'xyang', md5('abcd'), 't', 1000, 'Xuan', 'Yang','xyang@localhost.localdomain');
insert into authorities (user_id, role_id) values ( 1000, 11 );
insert into authorities (user_id, role_id) values ( 1000, 20 );
insert into authorities (user_id, role_id) values ( 1000, 21 );
insert into authorities (user_id, role_id) values ( 1000, 22 );

insert into users (id, username, password, enabled, cin, first_name, last_name, email) values
    ( 1001, 'cysun', md5('abcd'), 't', 1001, 'Chengyu', 'Sun','cysun@localhost.localdomain');
insert into authorities (user_id, role_id) values ( 1001, 11 );
insert into authorities (user_id, role_id) values ( 1001, 20 );
insert into authorities (user_id, role_id) values ( 1001, 21 );
insert into authorities (user_id, role_id) values ( 1001, 22 );

insert into users (id, username, password, enabled, cin, first_name, last_name, email) values
    ( 1002, 'rpamula', md5('abcd'), 't', 1002, 'Raj', 'Pamula','rpamula@localhost.localdomain');
insert into authorities (user_id, role_id) values ( 1002, 11 );
insert into authorities (user_id, role_id) values ( 1002, 20 );
insert into authorities (user_id, role_id) values ( 1002, 21 );
insert into authorities (user_id, role_id) values ( 1002, 22 );

insert into users (id, username, password, enabled, cin, first_name, last_name, email) values
    ( 1003, 'fhidalg', md5('abcd'), 't', 1003, 'Frances', 'Hidalgo-Segura','fhidalg@localhost.localdomain');
insert into authorities (user_id, role_id) values ( 1003, 11 );
insert into authorities (user_id, role_id) values ( 1003, 20 );
insert into authorities (user_id, role_id) values ( 1003, 21 );
insert into authorities (user_id, role_id) values ( 1003, 22 );

insert into users (id, username, password, enabled, cin, first_name, last_name, email) values
    ( 1004, 'ecrosby', md5('abcd'), 't', 1004, 'Evelyn', 'Crosby','ecrosby@localhost.localdomain');
insert into authorities (user_id, role_id) values ( 1004, 11 );
insert into authorities (user_id, role_id) values ( 1004, 20 );
insert into authorities (user_id, role_id) values ( 1004, 21 );
insert into authorities (user_id, role_id) values ( 1004, 22 );

insert into users (id, username, password, enabled, cin, first_name, last_name, email) values
    ( 2000, 'jdoe', md5('abcd'), 't', 2000, 'John', 'Doe','jdoe@localhost.localdomain');
insert into authorities (user_id, role_id) values ( 2000, 11 );
insert into authorities (user_id, role_id) values ( 2000, 20 );

insert into stored_queries (id, name ,query, author_id, chart_title,chart_x_axis_label,chart_y_axis_label,transpose_results, enabled) values
    ( 300000, 'Completed Appointments','select is_show_up, count(student_id) from appointment_sections where is_show_up = ''true'' and end_time < LOCALTIMESTAMP group by is_show_up; ',1000,'Completed Appointments','students','counts', 'f','t');
insert into stored_queries (id, name ,query, author_id, chart_title,chart_x_axis_label,chart_y_axis_label,transpose_results, enabled) values
    ( 300001, 'Missed Appointments','select is_show_up, count(student_id) from appointment_sections where is_show_up = ''false'' and end_time < LOCALTIMESTAMP group by is_show_up; ',1000,'Missed Appointments','students','counts', 'f','t');
insert into stored_queries (id, name ,query, author_id, chart_title,chart_x_axis_label,chart_y_axis_label,transpose_results, enabled) values
    ( 300002, 'Walk In Appointments(Seen)','select is_walk_in_appointment, count(student_id) from appointment_sections where is_walk_in_appointment = ''true'' and end_time < LOCALTIMESTAMP and is_available = ''true'' group by is_walk_in_appointment; ',1000,'Walk In Appointments (Seen)','students','counts', 'f','t');
insert into stored_queries (id, name ,query, author_id, chart_title,chart_x_axis_label,chart_y_axis_label,transpose_results, enabled) values
    ( 300003, 'Walk In Appointments(No Seen)','select types.name, count(s.id) as students from no_seen_reasons s, no_seen_reason_types types where s.no_seen_reason_type_id  = types.id group by types.name; ',1000,'Walk In Appointments(No Seen)','reasons','student counts', 'f','t');
insert into stored_queries (id, name ,query, author_id, chart_title,chart_x_axis_label,chart_y_axis_label,transpose_results, enabled) values
    ( 300004, 'Material Usage Report','select types.name, count(s.id) as students from services s, service_types types where s.service_type_id  = types.id group by types.name; ',1000,'Material Usage Report','Service','counts', 'f','t');
insert into stored_queries (id, name ,query, author_id, chart_title,chart_x_axis_label,chart_y_axis_label,transpose_results, enabled) values
    ( 300005, 'Students By Reason Of Visit','select types.name, count(s.id) as students from visit_reasons s, visit_reason_types types where s.visit_reason_type_id  = types.id group by types.name; ',1000,'Students By Reason Of Visit','Reasons','counts', 'f','t');
insert into stored_queries (id, name ,query, author_id, chart_title,chart_x_axis_label,chart_y_axis_label,transpose_results, enabled) values
    ( 300006, 'Demographics Usage Report (Gender)','select gender , count(id) from users where gender is not null group by gender; ',1000,'Gender report','Genders','counts', 'f','t');
insert into stored_queries (id, name ,query, author_id, chart_title,chart_x_axis_label,chart_y_axis_label,transpose_results, enabled) values
    ( 300007, 'Demographics Usage Report (Major)','select m.name, count(u.id) from users u , majors m where m.id = u.major_id and m.is_regular = ''t'' group by m.name; ',1000,'Major report','Majors','counts', 'f','t');
insert into stored_queries (id, name ,query, author_id, chart_title,chart_x_axis_label,chart_y_axis_label,transpose_results, enabled) values
    ( 300008, 'Demographics Usage Report (Ethnicity)','select e.name, count(u.id) from users u , ethnicities e where e.id = u.ethnicity_id and e is not null group by e.name;',1000,'Ethnicity report','Ethnicities','counts', 'f','t');
insert into stored_queries (id, name ,query, author_id, chart_title,chart_x_axis_label,chart_y_axis_label,transpose_results, enabled) values
    ( 300009, 'Cancelled Appointments','select cancelled_by_student,count (distinct (student_id) ) as cancel_appointments from appointment_sections where student_id not in (select student_id from appointment_sections where cancelled_by_student = ''f'' ) group by cancelled_by_student; ',1000,'Cancelled Appointments','Cancelled appointment by students','students number', 'f','t');

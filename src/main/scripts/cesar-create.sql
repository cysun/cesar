set client_min_messages=WARNING;

create sequence hibernate_sequence minvalue 2000000;

create table ethnicities (
    id      integer primary key,
    name    varchar(255) not null unique
);

create table high_schools (
    id      integer primary key,
    name    varchar(255) not null,
    city    varchar(255),
    state   varchar(255)
);

create table high_school_programs (
    id      integer primary key,
    name    varchar(255) not null
);

create table majors (
    id      integer primary key,
    symbol  varchar(255) not null unique,
    name    varchar(255) not null unique,
    is_regular boolean not null
    
);

---------------------
-- users and roles --
---------------------

create table roles (
    id              integer primary key,
    name            varchar(255) not null unique,
    description     varchar(4092)
);

create table users (
-- account information --
    id              	integer primary key,
    username        	varchar(255) not null unique,
    password       		varchar(255) not null,
    enabled         	boolean not null default 't',
    cin             	varchar(255) not null unique,
    first_name      	varchar(255) not null,
    middle_name     	varchar(255),
    last_name       	varchar(255) not null,
    email           	varchar(255) not null unique,
    confirm_id     		varchar(255),
    current_advisor_id 	integer references users(id),
-- contact information --
    address1        varchar(255),
    address2        varchar(255),
    city            varchar(255),
    state           varchar(255),
    zip             varchar(255),
    country         varchar(255),
    home_phone      varchar(255),
    office_phone    varchar(255),
    cell_phone      varchar(255),
-- demographic information --
    gender                      char(1) check( gender = 'M' or gender = 'F' ),
    birthday                    date,
    ethnicity_id                integer references ethnicities(id),
    work_hours_per_week         integer,
    commute_time_in_minutes     integer,
-- program information --
    major_id                    integer references majors(id),
    quarter_admitted            integer,
    expected_graduation_date    date,
-- high school background --
    high_school_id              integer references high_schools(id),
    high_school_physics         boolean,
    high_school_chemistry       boolean,
    high_school_calculus        boolean,
    high_school_trigonometry    boolean,
    high_school_gpa             real,
    community_college_courses   varchar(1000),
-- Test Scores --
    elm             integer,
    ept             integer,
    sat_math        integer,
    sat_verbal      integer,
    act_math        integer,
    act_verbal      integer,
    eap_math        integer,
    eap_verbal      integer,
    ap_calculus_a   integer,
    ap_calculus_b   integer,
    ap_calculus_c   integer,
    ap_physics      integer,
    ap_chemistry    integer,
    ap_biology      integer
);

create index user_names_index on users ( lower(last_name||first_name) );
create index user_firstname_index on users ( lower(first_name) );
create index user_lastname_index on users ( lower(last_name) );
create index user_fullname_index on users ( lower(first_name || ' ' || last_name) );
create index user_username_index on users ( lower(username) );


create table authorities (
    user_id integer not null references users(id),
    role_id integer not null references roles(id),
  	primary key (user_id, role_id)
);

create table high_school_programs_attended (
    user_id                 integer not null references users(id),
    high_school_program_id  integer not null references high_school_programs(id),
  	primary key (user_id, high_school_program_id)
);



create table financial_aid_types (
    id      integer primary key,
    name    varchar(255)
);

create table financial_aids (
    id          integer primary key,
    type_id     integer not null references financial_aid_types(id),
    student_id  integer not null references users(id),
    details     varchar(8000)
);

create table extra_curriculum_activity_types (
    id      integer primary key,
    name    varchar(255)
);

create table extra_curriculum_activities (
    id          integer primary key,
    type_id     integer not null references extra_curriculum_activity_types(id),
    student_id  integer not null references users(id),
    description varchar(8000)
);

----------------
-- advisement --
----------------

create table advisements (
    id                  integer primary key,
    comment             varchar(8000) not null,
    student_id          integer not null references users(id),
    advisor_id          integer not null references users(id),
    create_date         timestamp,
    editable            boolean not null default 't',
    edited_by           integer references users(id),
    edit_date           timestamp,
    for_advisor_only    boolean not null default 'f',
    emailed_to_student  boolean not null default 'f'
);

------------------
--- courses ------
------------------

create table courses(
	id					integer primary key,
	code				varchar(255) not null,
	name				varchar(255),
	units				Integer not null,
	is_graduate_course	boolean not null default 'f'
);

create table prerequisites(
	id 					integer primary key
);

create table course_prerequisites(
	course_id			integer not null references courses(id),
	prerequisite_id		integer not null references prerequisites(id),
	primary key (course_id, prerequisite_id)
);

create table major_courses(
	major_id integer not null references majors(id),
    course_id integer not null references courses(id),
  	primary key (major_id, course_id)
);

create table courses_taken(
	id 					integer primary key,
	student_id			integer not null references users(id),
	course_id			integer not null references courses(id),
	quarter				varchar(20),
	year				varchar(10),
	grade				varchar(255)
);

create table prerequisite_courses(
	prerequisite_id		integer not null references prerequisites(id),
	course_id			integer not null references courses(id),
	primary key (prerequisite_id, course_id )
);

---------------
-- schedules --
---------------

create table schedules(
	id					integer primary key,
	quarter				integer,
	major_id			integer not null references majors(id)
);

create table sections(
	id 					integer primary key,
	course_id			integer not null references courses(id),
	start_time			varchar(255),
	end_time			varchar(255),
	info				varchar(255)

);

create table week_days(
	id							integer primary key,
	symbol						varchar(255) not null,
	code						integer not null,
	name						varchar(255) not null
); 

create table section_week_days(
	section_id 			integer not null references sections(id),
	week_day_id			integer not null references week_days(id),
	primary key(section_id, week_day_id)
);


create table schedule_sections(
	schedule_id			integer not null references schedules(id),
	section_id			integer not null references sections(id),
	primary key(schedule_id , section_id)
);

------------------
-- course plans --
------------------

create table course_plans(
	id					integer primary key,
	student_id			integer not null references users(id),
	advisor				char(255),
	time_stamp			timestamp,
	approved_date 		timestamp,
	approved			boolean not null default 'f'
);

create table quarter_plans(
	id					integer primary key,
	quarter				integer
);

create table course_quarter_plans(
	course_plan_id 		integer not null references course_plans(id),
	quarter_plan_id		integer not null references quarter_plans(id),
	primary key( course_plan_id, quarter_plan_id)
);

create table plan_courses(
	quarter_plan_id		integer not null references quarter_plans(id),
	course_id			integer not null references courses(id),
	primary key( quarter_plan_id, course_id )
);

----------------------
---- Appoitments -----
----------------------

create table appointment_types(
	id					integer primary key,
	name				varchar(255)
);

create table reasons_for_appointment(
	id					integer primary key,
	name				varchar(255)
);

create table appointment_sections(
	id  				integer primary key,
	title				varchar(255),
	start_time			timestamp,
	end_time			timestamp,
	start_time_int		integer,
	advisor_id			integer not null references users(id),
	student_id			integer references users(id),
	is_available		boolean not null default 't',
	is_show_up			boolean not null default 't',
	cancelled_by_advisor	boolean not null default 'f',
	cancelled_by_student	boolean not null default 'f',
	is_walk_in_appointment boolean not null default 'f',
	appointment_type_id integer not null references appointment_types(id),
	reason_for_appointment_id integer not null references reasons_for_appointment(id)
);


create table appointment_schedules(
	user_id					integer not null references users(id),
	appointment_section_id 	integer not null references appointment_sections(id),
	primary key( user_id ,appointment_section_id )
);

create table schedule_tables
(
 	id 					integer primary key,
 	is_register_period 	boolean,
 	created_date		timestamp,
 	updated_date		timestamp,
 	quarter				integer
);

create table schedule_table_sections
(
	id					integer primary key,
	start_time			integer
);

create table table_sections_mapping
(
	schedule_table_id 			integer not null references schedule_tables(id),
	schedule_table_section_id	integer not null references schedule_table_sections(id),
	primary key(schedule_table_id, schedule_table_section_id)
);	

create table participate_advisors
(
	schedule_table_section_id 	integer not null references schedule_table_sections(id),
	advisor_id					integer not null references users(id),
	primary key( schedule_table_section_id, advisor_id )
);

create table advisor_schedule_sections(
	id				integer primary key,
	start_time		integer
);
	
create table office_hours_schedule_sections (
	user_id					integer not null references users(id),
	advisor_schedule_section_id integer not null references advisor_schedule_sections(id),
	primary key ( user_id, advisor_schedule_section_id )
);

create table advisor_schedule_records(
	id						integer primary key,
	created_date			timestamp,
	log						varchar(255)
);

create table stored_queries(
	id                  integer primary key,
    name                varchar(255) not null unique,
    query               varchar(8000) not null,
    date                timestamp default current_timestamp,
    author_id           integer references users(id),
    chart_title         varchar(255),
    chart_x_axis_label  varchar(255),
    chart_y_axis_label  varchar(255),
    transpose_results   boolean not null default 'f',
    enabled             boolean not null default 'f',
    deleted             boolean not null default 'f'
);

create table service_types(
	id					integer primary key,
	name				varchar(255) not null
);

create table visit_reason_types(
	id					integer primary key,
	name				varchar(255) not null
);

create table no_seen_reason_types(
	id					integer primary key,
	name				varchar(255) not null
);

create table services(
	id					integer primary key,
	cin					varchar(255) not null,
	service_type_id		integer not null references service_types(id),
	create_date			timestamp
);

create table visit_reasons(
	id							integer primary key,
	cin							varchar(255) not null,
	visit_reason_type_id		integer not null references visit_reason_types(id),
	create_date			timestamp
);

create table no_seen_reasons(
	id							integer primary key,
	cin							varchar(255) not null,
	no_seen_reason_type_id		integer not null references no_seen_reason_types(id),
	create_date			timestamp
);

----------------
---version 1----
----------------
alter table sections add section_number char(20);
alter table sections add call_number char(20);
alter table sections add location varchar(255);
alter table sections add capacity char(20);
alter table sections add units integer not null default 4;
alter table sections add option_info varchar(255); 

alter table users alter column elm type varchar(255);
alter table users alter column ept type varchar(255);
alter table users alter column sat_math type varchar(255);
alter table users alter column sat_verbal type varchar(255);
alter table users alter column act_math type varchar(255);
alter table users alter column act_verbal type varchar(255);
alter table users alter column eap_math type varchar(255);
alter table users alter column eap_verbal type varchar(255);
alter table users alter column ap_calculus_a type varchar(255);
alter table users alter column ap_calculus_b type varchar(255);
alter table users alter column ap_calculus_c type varchar(255);
alter table users alter column ap_physics type varchar(255);
alter table users alter column ap_chemistry type varchar(255);
alter table users alter column ap_biology type varchar(255);
alter table users alter column high_school_gpa type varchar(255);
alter table users alter column work_hours_per_week type varchar(255);
alter table users alter column commute_time_in_minutes type varchar(255);
alter table courses add column deleted boolean not null default 'f';
alter table advisements add column deleted boolean not null default 't';
alter table course_plans alter column student_id drop not null;
alter table course_plans add column is_template boolean not null default 'f';
alter table course_plans add column deleted boolean not null default 'f';
alter table course_plans add column name varchar(255);
alter table course_plans add column note varchar(4000);
alter table course_plans add column major_id integer references majors(id);

create table courses_transferred (
    id          integer primary key,
    student_id  integer not null references users(id),
    advisor_id  integer not null references users(id),
    course_id	integer not null references courses(id),
    comment		varchar(4000),
    updated_date timestamp
    
);

create table courses_waived (
    id          integer primary key,
    student_id  integer not null references users(id),
    advisor_id  integer not null references users(id),
    course_id	integer not null references courses(id),
    comment		varchar(4000),
    updated_date timestamp
    
);

create table plan_sections(
	quarter_plan_id		integer not null references quarter_plans(id),
	section_id			integer not null references sections(id),
	primary key( quarter_plan_id, section_id )
);

create table generated_cins(
	id 			integer primary key
);

----------------
---version 2----
----------------
alter table quarter_plans add notes char(5000);
alter table quarter_plans add units integer;
alter table course_plans add units integer;
alter table courses add repeatable boolean default false;
alter table service_types add deleted boolean default false;
alter table visit_reason_types add deleted boolean default false;
alter table no_seen_reason_types add deleted boolean default false;

------------------------------
-- functions and procedures --
------------------------------

--
-- Given a date, returns the quarter code.
--
create or replace function quarter( p_date timestamp ) returns integer as $$
declare
    l_code integer := (extract(year from p_date) - 1800) * 10;
    l_week integer := extract(week from p_date);
begin
    if l_week < 13 then
        l_code := l_code + 1;
    elsif l_week < 25 then
        l_code := l_code + 3;
    elsif l_week < 38 then
        l_code := l_code + 6;
    else
        l_code := l_code + 9;
    end if;
    return l_code;
end;
$$ language plpgsql;

--
-- Translate a quarter code to string. The format parameter should either
-- be 'long' (e.g. 'Fall 2012') or 'short' (e.g. 'F12').
--
create or replace function quarter( p_code integer, p_format varchar default 'short' )
    returns varchar as $$
declare
    l_year      varchar;
    l_quarter   varchar;
begin
    l_year := cast( p_code/10+1800 as varchar );

    case p_code % 10
        when 1 then
            l_quarter = 'Winter';
        when 3 then
            l_quarter = 'Spring';
        when 6 then
            l_quarter = 'Summer';
        else
            l_quarter = 'Fall';
    end case;

    if p_format = 'long' then
        return l_quarter ||  ' ' || l_year;
    else
        l_year := substring( l_year, 3, 2 );
        if l_quarter = 'Winter' or l_quarter = 'Spring' or l_quarter = 'Fall' then
            l_quarter := substring( l_quarter, 1, 1 );
        else
            l_quarter := 'X';
        end if;
        return l_quarter || l_year;
    end if;
end;
$$ language plpgsql;

----------------
---version 3----
----------------
alter table appointment_sections add notes char(5000);

create table block (
        id int4 not null,
        end_date_time timestamp not null,
        start_date_time timestamp not null,
        advisor_id int4 not null,
        primary key (id)
);

alter table sections add enrollment_total integer not null default 0;

create table enrolled_sections (
        user_id int4 not null,
        section_id int4 not null,
        primary key (user_id, section_id)
);

create table section_students_enrolled (
        section_id int4 not null,
        user_id int4 not null,
        primary key (section_id, user_id)
);
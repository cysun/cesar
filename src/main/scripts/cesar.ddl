
    create table advisements (
        id int4 not null,
        comment varchar(255) not null,
        create_date timestamp,
        deleted bool not null,
        edit_date timestamp,
        editable bool not null,
        emailed_to_student bool not null,
        for_advisor_only bool not null,
        advisor_id int4 not null,
        edited_by int4,
        student_id int4 not null,
        primary key (id)
    );

    create table advisor_schedule_records (
        id int4 not null,
        created_date timestamp,
        log varchar(255),
        primary key (id)
    );

    create table advisor_schedule_sections (
        id int4 not null,
        start_time int4,
        primary key (id)
    );

    create table appointment_schedules (
        user_id int4 not null,
        appointment_section_id int4 not null
    );

    create table appointment_sections (
        id int4 not null,
        cancelled_by_advisor bool,
        cancelled_by_student bool,
        end_time timestamp not null,
        is_available bool,
        is_show_up bool,
        is_walk_in_appointment bool,
        notes varchar(255),
        start_time timestamp not null,
        start_time_int int4,
        title varchar(255),
        advisor_id int4 not null,
        appointment_type_id int4 not null,
        reason_for_appointment_id int4 not null,
        student_id int4,
        primary key (id)
    );

    create table appointment_types (
        id int4 not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table authorities (
        user_id int4 not null,
        role_id int4 not null,
        primary key (user_id, role_id)
    );

    create table block (
        id int4 not null,
        end_date_time timestamp not null,
        start_date_time timestamp not null,
        advisor_id int4 not null,
        primary key (id)
    );

    create table course_plans (
        id int4 not null,
        advisor varchar(255),
        approved bool,
        approved_date timestamp,
        deleted bool,
        is_template bool,
        name varchar(255),
        note varchar(255),
        time_stamp timestamp,
        units int4,
        major_id int4,
        student_id int4,
        primary key (id)
    );

    create table course_prerequisites (
        course_id int4 not null,
        prerequisite_id int4 not null,
        primary key (course_id, prerequisite_id)
    );

    create table course_quarter_plans (
        course_plan_id int4 not null,
        quarter_plan_id int4 not null,
        primary key (course_plan_id, quarter_plan_id)
    );

    create table courses (
        id int4 not null,
        code varchar(255) not null unique,
        deleted bool not null,
        is_graduate_course bool not null,
        name varchar(255),
        repeatable bool not null,
        units int4 not null,
        primary key (id)
    );

    create table courses_taken (
        id int4 not null,
        grade varchar(255),
        quarter varchar(255),
        year varchar(255),
        course_id int4 not null,
        student_id int4 not null,
        primary key (id)
    );

    create table courses_transferred (
        id int4 not null,
        comment varchar(255),
        updated_date timestamp,
        advisor_id int4 not null,
        course_id int4 not null,
        student_id int4 not null,
        primary key (id)
    );

    create table courses_waived (
        id int4 not null,
        comment varchar(255),
        updated_date timestamp,
        advisor_id int4 not null,
        course_id int4 not null,
        student_id int4 not null,
        primary key (id)
    );

    create table enrolled_sections (
        user_id int4 not null,
        section_id int4 not null,
        primary key (user_id, section_id)
    );

    create table ethnicities (
        id int4 not null,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table extra_curriculum_activities (
        id int4 not null,
        description varchar(255),
        student_id int4 not null,
        type_id int4 not null,
        primary key (id)
    );

    create table extra_curriculum_activity_types (
        id int4 not null,
        name varchar(255),
        primary key (id)
    );

    create table financial_aid_types (
        id int4 not null,
        name varchar(255),
        primary key (id)
    );

    create table financial_aids (
        id int4 not null,
        details varchar(255),
        student_id int4 not null,
        type_id int4 not null,
        primary key (id)
    );

    create table generated_cins (
        id int4 not null,
        primary key (id)
    );

    create table high_school_programs (
        id int4 not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table high_school_programs_attended (
        user_id int4 not null,
        high_school_program_id int4 not null,
        primary key (user_id, high_school_program_id)
    );

    create table high_schools (
        id int4 not null,
        city varchar(255),
        name varchar(255) not null,
        state varchar(255),
        primary key (id)
    );

    create table major_courses (
        major_id int4 not null,
        course_id int4 not null,
        primary key (major_id, course_id)
    );

    create table majors (
        id int4 not null,
        is_regular bool not null,
        name varchar(255) not null unique,
        symbol varchar(255) not null unique,
        primary key (id)
    );

    create table no_seen_reason_types (
        id int4 not null,
        deleted bool,
        name varchar(255) not null,
        primary key (id)
    );

    create table no_seen_reasons (
        id int4 not null,
        cin varchar(255),
        create_date timestamp,
        no_seen_reason_type_id int4,
        primary key (id)
    );

    create table office_hours_schedule_sections (
        user_id int4 not null,
        advisor_schedule_section_id int4 not null,
        primary key (user_id, advisor_schedule_section_id)
    );

    create table participate_advisors (
        schedule_table_section_id int4 not null,
        advisor_id int4 not null,
        primary key (schedule_table_section_id, advisor_id)
    );

    create table plan_courses (
        quarter_plan_id int4 not null,
        course_id int4 not null,
        primary key (quarter_plan_id, course_id)
    );

    create table plan_sections (
        quarter_plan_id int4 not null,
        section_id int4 not null,
        primary key (quarter_plan_id, section_id)
    );

    create table prerequisite_courses (
        prerequisite_id int4 not null,
        course_id int4 not null,
        primary key (prerequisite_id, course_id)
    );

    create table prerequisites (
        id int4 not null,
        primary key (id)
    );

    create table quarter_plans (
        id int4 not null,
        notes varchar(255),
        quarter int4,
        units int4,
        primary key (id)
    );

    create table reasons_for_appointment (
        id int4 not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table roles (
        id int4 not null,
        description varchar(255),
        name varchar(255) not null unique,
        primary key (id)
    );

    create table schedule_sections (
        schedule_id int4 not null,
        section_id int4 not null,
        primary key (schedule_id, section_id)
    );

    create table schedule_table_sections (
        id int4 not null,
        start_time int4,
        primary key (id)
    );

    create table schedule_tables (
        id int4 not null,
        created_date timestamp,
        is_register_period bool,
        quarter int4,
        updated_date timestamp,
        primary key (id)
    );

    create table schedules (
        id int4 not null,
        quarter int4,
        major_id int4 not null,
        primary key (id)
    );

    create table section_students_enrolled (
        section_id int4 not null,
        user_id int4 not null,
        primary key (section_id, user_id)
    );

    create table section_week_days (
        section_id int4 not null,
        week_day_id int4 not null,
        primary key (section_id, week_day_id)
    );

    create table sections (
        id int4 not null,
        call_number varchar(255),
        capacity varchar(255),
        end_time varchar(255),
        enrollment_total int4,
        info varchar(255),
        location varchar(255),
        option_info varchar(255),
        section_number varchar(255),
        start_time varchar(255),
        units int4 not null,
        course_id int4,
        primary key (id)
    );

    create table service_types (
        id int4 not null,
        deleted bool,
        name varchar(255) not null,
        primary key (id)
    );

    create table services (
        id int4 not null,
        cin varchar(255),
        create_date timestamp,
        service_type_id int4,
        primary key (id)
    );

    create table stored_queries (
        id int4 not null,
        chart_title varchar(255),
        chart_x_axis_label varchar(255),
        chart_y_axis_label varchar(255),
        date timestamp,
        deleted bool not null,
        enabled bool not null,
        name varchar(255),
        query varchar(255),
        transpose_results bool,
        author_id int4 not null,
        primary key (id)
    );

    create table table_sections_mapping (
        schedule_table_id int4 not null,
        schedule_table_section_id int4 not null,
        primary key (schedule_table_id, schedule_table_section_id)
    );

    create table users (
        id int4 not null,
        act_math varchar(255),
        act_verbal varchar(255),
        address1 varchar(255),
        address2 varchar(255),
        ap_biology varchar(255),
        ap_calculus_a varchar(255),
        ap_calculus_b varchar(255),
        ap_calculus_c varchar(255),
        ap_chemistry varchar(255),
        ap_physics varchar(255),
        birthday timestamp,
        cell_phone varchar(255),
        cin varchar(255) not null unique,
        city varchar(255),
        community_college_courses varchar(255),
        commute_time_in_minutes varchar(255),
        confirm_id varchar(255),
        country varchar(255),
        eap_math varchar(255),
        eap_verbal varchar(255),
        elm varchar(255),
        email varchar(255) not null unique,
        enabled bool not null,
        ept varchar(255),
        expected_graduation_date timestamp,
        first_name varchar(255) not null,
        gender varchar(255),
        high_school_calculus bool,
        high_school_chemistry bool,
        high_school_gpa varchar(255),
        high_school_physics bool,
        high_school_trigonometry bool,
        home_phone varchar(255),
        last_name varchar(255) not null,
        middle_name varchar(255),
        office_phone varchar(255),
        password varchar(255) not null,
        quarter_admitted int4,
        sat_math varchar(255),
        sat_verbal varchar(255),
        state varchar(255),
        username varchar(255) not null unique,
        work_hours_per_week varchar(255),
        zip varchar(255),
        current_advisor_id int4,
        ethnicity_id int4,
        high_school_id int4,
        major_id int4,
        primary key (id)
    );

    create table visit_reason_types (
        id int4 not null,
        deleted bool,
        name varchar(255) not null,
        primary key (id)
    );

    create table visit_reasons (
        id int4 not null,
        cin varchar(255),
        create_date timestamp,
        visit_reason_type_id int4,
        primary key (id)
    );

    create table week_days (
        id int4 not null,
        code int4,
        name varchar(255),
        symbol varchar(255),
        primary key (id)
    );

    alter table advisements 
        add constraint FK403428EDF0A3365B 
        foreign key (student_id) 
        references users;

    alter table advisements 
        add constraint FK403428ED783AEB6 
        foreign key (advisor_id) 
        references users;

    alter table advisements 
        add constraint FK403428EDBF40A8E9 
        foreign key (edited_by) 
        references users;

    alter table appointment_schedules 
        add constraint FKB5175C1C2567A2CB 
        foreign key (user_id) 
        references users;

    alter table appointment_schedules 
        add constraint FKB5175C1CBF3126CC 
        foreign key (appointment_section_id) 
        references appointment_sections;

    alter table appointment_sections 
        add constraint FKB243D6CE25F43188 
        foreign key (appointment_type_id) 
        references appointment_types;

    alter table appointment_sections 
        add constraint FKB243D6CEA5752737 
        foreign key (reason_for_appointment_id) 
        references reasons_for_appointment;

    alter table appointment_sections 
        add constraint FKB243D6CEF0A3365B 
        foreign key (student_id) 
        references users;

    alter table appointment_sections 
        add constraint FKB243D6CE783AEB6 
        foreign key (advisor_id) 
        references users;

    alter table authorities 
        add constraint FK2B0F1321803CDEEB 
        foreign key (role_id) 
        references roles;

    alter table authorities 
        add constraint FK2B0F13212567A2CB 
        foreign key (user_id) 
        references users;

    alter table block 
        add constraint FK597C48D783AEB6 
        foreign key (advisor_id) 
        references users;

    alter table course_plans 
        add constraint FK665C5D46F0A3365B 
        foreign key (student_id) 
        references users;

    alter table course_plans 
        add constraint FK665C5D469840C049 
        foreign key (major_id) 
        references majors;

    alter table course_prerequisites 
        add constraint FKA9DADB598E49088B 
        foreign key (course_id) 
        references courses;

    alter table course_prerequisites 
        add constraint FKA9DADB59843B35EB 
        foreign key (prerequisite_id) 
        references prerequisites;

    alter table course_quarter_plans 
        add constraint FK7D6815F353706942 
        foreign key (quarter_plan_id) 
        references quarter_plans;

    alter table course_quarter_plans 
        add constraint FK7D6815F3A26CCFE2 
        foreign key (course_plan_id) 
        references course_plans;

    alter table courses_taken 
        add constraint FK6A9A3980F0A3365B 
        foreign key (student_id) 
        references users;

    alter table courses_taken 
        add constraint FK6A9A39808E49088B 
        foreign key (course_id) 
        references courses;

    alter table courses_transferred 
        add constraint FK16DA22BFF0A3365B 
        foreign key (student_id) 
        references users;

    alter table courses_transferred 
        add constraint FK16DA22BF783AEB6 
        foreign key (advisor_id) 
        references users;

    alter table courses_transferred 
        add constraint FK16DA22BF8E49088B 
        foreign key (course_id) 
        references courses;

    alter table courses_waived 
        add constraint FKEDCAD6BDF0A3365B 
        foreign key (student_id) 
        references users;

    alter table courses_waived 
        add constraint FKEDCAD6BD783AEB6 
        foreign key (advisor_id) 
        references users;

    alter table courses_waived 
        add constraint FKEDCAD6BD8E49088B 
        foreign key (course_id) 
        references courses;

    alter table enrolled_sections 
        add constraint FKF99A71682567A2CB 
        foreign key (user_id) 
        references users;

    alter table enrolled_sections 
        add constraint FKF99A7168FC939109 
        foreign key (section_id) 
        references sections;

    alter table extra_curriculum_activities 
        add constraint FK2C3AFB82F0A3365B 
        foreign key (student_id) 
        references users;

    alter table extra_curriculum_activities 
        add constraint FK2C3AFB829E84B323 
        foreign key (type_id) 
        references extra_curriculum_activity_types;

    alter table financial_aids 
        add constraint FK3D69F8ADF0A3365B 
        foreign key (student_id) 
        references users;

    alter table financial_aids 
        add constraint FK3D69F8ADFF36857E 
        foreign key (type_id) 
        references financial_aid_types;

    alter table high_school_programs_attended 
        add constraint FK9027587B7E3261A1 
        foreign key (high_school_program_id) 
        references high_school_programs;

    alter table high_school_programs_attended 
        add constraint FK9027587B2567A2CB 
        foreign key (user_id) 
        references users;

    alter table major_courses 
        add constraint FKD6C5B7329840C049 
        foreign key (major_id) 
        references majors;

    alter table major_courses 
        add constraint FKD6C5B7328E49088B 
        foreign key (course_id) 
        references courses;

    alter table no_seen_reasons 
        add constraint FK2476FEA962123C16 
        foreign key (no_seen_reason_type_id) 
        references no_seen_reason_types;

    alter table office_hours_schedule_sections 
        add constraint FK82C771233A7537D 
        foreign key (advisor_schedule_section_id) 
        references advisor_schedule_sections;

    alter table office_hours_schedule_sections 
        add constraint FK82C771232567A2CB 
        foreign key (user_id) 
        references users;

    alter table participate_advisors 
        add constraint FK3A2ED3F4C8C624CD 
        foreign key (schedule_table_section_id) 
        references schedule_table_sections;

    alter table participate_advisors 
        add constraint FK3A2ED3F4783AEB6 
        foreign key (advisor_id) 
        references users;

    alter table plan_courses 
        add constraint FK39181CA253706942 
        foreign key (quarter_plan_id) 
        references quarter_plans;

    alter table plan_courses 
        add constraint FK39181CA28E49088B 
        foreign key (course_id) 
        references courses;

    alter table plan_sections 
        add constraint FK3860828453706942 
        foreign key (quarter_plan_id) 
        references quarter_plans;

    alter table plan_sections 
        add constraint FK38608284FC939109 
        foreign key (section_id) 
        references sections;

    alter table prerequisite_courses 
        add constraint FK98EC172F8E49088B 
        foreign key (course_id) 
        references courses;

    alter table prerequisite_courses 
        add constraint FK98EC172F843B35EB 
        foreign key (prerequisite_id) 
        references prerequisites;

    alter table schedule_sections 
        add constraint FK3414F6D61BFABECB 
        foreign key (schedule_id) 
        references schedules;

    alter table schedule_sections 
        add constraint FK3414F6D6FC939109 
        foreign key (section_id) 
        references sections;

    alter table schedules 
        add constraint FKF66BC0BC9840C049 
        foreign key (major_id) 
        references majors;

    alter table section_students_enrolled 
        add constraint FK3F501BB22567A2CB 
        foreign key (user_id) 
        references users;

    alter table section_students_enrolled 
        add constraint FK3F501BB2FC939109 
        foreign key (section_id) 
        references sections;

    alter table section_week_days 
        add constraint FKA0FAC0E8896CF7C0 
        foreign key (week_day_id) 
        references week_days;

    alter table section_week_days 
        add constraint FKA0FAC0E8FC939109 
        foreign key (section_id) 
        references sections;

    alter table sections 
        add constraint FK38805E2E8E49088B 
        foreign key (course_id) 
        references courses;

    alter table services 
        add constraint FK5235105E6BAA634 
        foreign key (service_type_id) 
        references service_types;

    alter table stored_queries 
        add constraint FK2D2F4ECA8620950B 
        foreign key (author_id) 
        references users;

    alter table table_sections_mapping 
        add constraint FKE3048C0EC8C624CD 
        foreign key (schedule_table_section_id) 
        references schedule_table_sections;

    alter table table_sections_mapping 
        add constraint FKE3048C0E38C2097A 
        foreign key (schedule_table_id) 
        references schedule_tables;

    alter table users 
        add constraint FK6A68E08921A1B50 
        foreign key (high_school_id) 
        references high_schools;

    alter table users 
        add constraint FK6A68E083603D309 
        foreign key (ethnicity_id) 
        references ethnicities;

    alter table users 
        add constraint FK6A68E089840C049 
        foreign key (major_id) 
        references majors;

    alter table users 
        add constraint FK6A68E08B665513C 
        foreign key (current_advisor_id) 
        references users;

    alter table visit_reasons 
        add constraint FK291C2FBBFB5F1871 
        foreign key (visit_reason_type_id) 
        references visit_reason_types;

    create sequence hibernate_sequence;

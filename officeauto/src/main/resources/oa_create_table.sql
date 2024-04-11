create table hibernate_sequence
(
    next_val bigint null
);

create table oa_dept
(
    dept_id     bigint       not null
        primary key,
    create_date datetime(6)  null,
    dept_name   varchar(50)  null,
    dept_remark varchar(500) null,
    modify_date datetime(6)  null,
    creater     varchar(50)  null,
    modifier    varchar(50)  null
);

create table oa_job
(
    job_code   varchar(100) not null
        primary key,
    job_name   varchar(50)  null,
    job_remark varchar(300) null
);

create table oa_user
(
    user_id       varchar(50)  not null
        primary key,
    check_date    datetime(6)  null,
    create_date   datetime(6)  null,
    modify_date   datetime(6)  null,
    user_answer   varchar(200) null,
    user_email    varchar(50)  null,
    user_name     varchar(50)  null,
    user_password varchar(50)  null,
    user_phone    varchar(50)  null,
    user_qqnum    varchar(50)  null,
    user_sex      smallint     null,
    user_status   smallint     null,
    user_tel      varchar(50)  null,
    user_question smallint     null,
    checker       varchar(50)  null,
    creater       varchar(50)  null,
    dept          bigint       null,
    job           varchar(100) null,
    modifier      varchar(50)  null,
    constraint fk_user_checker
        foreign key (checker) references oa_user (user_id),
    constraint fk_user_creater
        foreign key (creater) references oa_user (user_id),
    constraint fk_user_dept
        foreign key (dept) references oa_dept (dept_id),
    constraint fk_user_job
        foreign key (job) references oa_job (job_code),
    constraint fk_user_modifier
        foreign key (modifier) references oa_user (user_id)
);

alter table oa_dept
    add constraint fk_dept_creater
        foreign key (creater) references oa_user (user_id);

alter table oa_dept
    add constraint fk_dept_modifier
        foreign key (modifier) references oa_user (user_id);

create table oa_module
(
    module_code   varchar(100) not null
        primary key,
    create_date   datetime(6)  null,
    modify_date   datetime(6)  null,
    module_name   varchar(50)  null,
    module_remark varchar(500) null,
    module_url    varchar(100) null,
    creater       varchar(50)  null,
    modifier      varchar(50)  null,
    constraint fk_module_creater
        foreign key (creater) references oa_user (user_id),
    constraint fk_module_modifier
        foreign key (modifier) references oa_user (user_id)
);

create table oa_role
(
    role_id     bigint       not null
        primary key,
    create_date datetime(6)  null,
    modify_date datetime(6)  null,
    role_name   varchar(50)  null,
    role_remark varchar(500) null,
    creater     varchar(50)  null,
    modifier    varchar(50)  null,
    constraint fk_role_creater
        foreign key (creater) references oa_user (user_id),
    constraint fk_role_modifier
        foreign key (modifier) references oa_user (user_id)
);

create table oa_popedom
(
    popedom_id  bigint       not null
        primary key,
    create_date datetime(6)  null,
    creater     varchar(50)  null,
    module      varchar(100) null,
    opera       varchar(100) null,
    role        bigint       null,
    constraint fk_popedom_creater
        foreign key (creater) references oa_user (user_id),
    constraint fk_popedom_module
        foreign key (module) references oa_module (module_code),
    constraint fk_popedom_opera
        foreign key (opera) references oa_module (module_code),
    constraint fk_popedom_role
        foreign key (role) references oa_role (role_id)
);

create index idx_user_name
    on oa_user (user_name);

create table oa_user_role
(
    role_id bigint      not null,
    user_id varchar(50) not null,
    primary key (role_id, user_id),
    constraint FKqh2b12kyw829j0ecyyhd1n7wy
        foreign key (role_id) references oa_role (role_id),
    constraint FKs1rn2iypf8se3gsii6ofxo2ps
        foreign key (user_id) references oa_user (user_id)
);

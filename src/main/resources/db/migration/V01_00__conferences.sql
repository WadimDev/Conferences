-- названием,   уникальны
-- тематикой,
-- датами проведения и
-- количеством участников;
--create sequence conferences_id_seq;
create table conferences
(
    id                 bigserial primary key,
    title              varchar(255) not null unique,
    theme              varchar(255) not null,
    date               date         not null unique,
    participants_count int          not null
);


--create sequence talk_types_id_seq;
create table talk_types
(
    id   bigserial
        constraint talk_types_pk primary key,
    type varchar(255) not null unique
);


-- названием,           -	доклады уникальны по названию
-- описанием,
-- именем докладчика      -	докладчик не может подать больше 3 докладов
-- типом доклада (доклад, мастер-класс, воркшоп)
-- -	подача докладов разрешена не позже чем за месяц до начала конференции
--create sequence talks_id_seq;
create table talks
(
    id              bigserial primary key,
    conference_id   bigserial
        constraint conferences_fk references conferences,
    title           varchar(255) unique,
    description     varchar(255),
    rapporteur_name varchar(255),
    type_id         bigserial
        constraint talks_fk references talk_types
);

-- типом доклада (доклад, мастер-класс, воркшоп);
INSERT INTO public.talk_types (type)
VALUES ('доклад');
INSERT INTO public.talk_types (type)
VALUES ('мастер-класс');
INSERT INTO public.talk_types (type)
VALUES ('воркшоп');

--create type talk_type as enum ('доклад', 'мастер-класс', 'воркшоп');


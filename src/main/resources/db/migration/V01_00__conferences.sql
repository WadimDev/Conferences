-- названием,
-- тематикой,
-- датами проведения и
-- количеством участников;
create table conferences
(
    id                 bigserial primary key,
    name               varchar(255) not null,
    theme              varchar(255) not null,
    date               date         not null,
    participants_count smallint     not null
);

create type talk_type as enum ('доклад', 'мастер-класс', 'воркшоп');

create table talks
(
    id            bigserial primary key,
    conference_id bigserial,
    name          varchar(255),
    description   varchar(255),
    rapporteur    varchar(255),
    type          talk_type,
    constraint fk_conference_id foreign key (conference_id) references conferences (id)
);

--	добавление доклада в конференцию (POST на /conferences/{conference_id}/talks) с
-- названием,
-- описанием,
-- именем докладчика и
-- типом доклада (доклад, мастер-класс, воркшоп);

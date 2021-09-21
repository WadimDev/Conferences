-- названием,
-- тематикой,
-- датами проведения и
-- количеством участников;
CREATE TABLE conferences
(
    id                 BIGSERIAL primary key,
    name               varchar(255) not null,
    theme              varchar(255) not null,
    date               date         not null,
    participants_count smallint     NOT NULL
);
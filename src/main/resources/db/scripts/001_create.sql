create sequence hibernate_sequence start with 1 increment by 1;
create table game (
id bigint not null,
first_player_elo_changed integer not null,
game_finished timestamp,
second_player_elo_changed integer not null,
firstplayer_id integer,
secondplayer_id integer,
primary key (id)
);

create table player (
id integer not null,
elo integer not null,
login varchar(255),
name varchar(255) not null,
password varchar(255) default 'password',
school_id integer,
primary key (id),
constraint uk_login unique (login)
);

create table player_roles (
player_id integer not null,
roles varchar(255)
);

create table school (
id integer not null,
called varchar(255) not null,
primary key (id),
constraint uk_school unique (called)
);

alter table game add constraint FIRST_PLAYER_ID_FK foreign key (firstplayer_id) references player;

alter table game add constraint SECOND_PLAYER_ID_FK foreign key (secondplayer_id) references player;

alter table player add constraint SCHOOL_ID_FK foreign key (school_id) references school;

alter table player_roles add constraint FK_PLAYER_ID foreign key (player_id) references player;


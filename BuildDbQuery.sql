drop database if exists dnddb;

create database dnddb;

use dnddb;

create table roles(
id bigint primary key auto_increment,
title nvarchar(20) not null default 'user',
is_deleted bit not null default 0
);

create table users(
id bigint primary key auto_increment,
username nvarchar(50) not null,
password nvarchar(50) not null,
is_deleted bit not null default 0,
role_id bigint not null,
foreign key(role_id)
references roles(id)
);

create table classes(
id bigint primary key auto_increment,
name nvarchar(50) not null,
description text not null,
hit_dice int not null,
is_deleted bit not null default 0,
check(hit_dice>0)
);

CREATE TABLE characters(
id bigint primary key auto_increment,
name nvarchar(50) not null,
user_id bigint not null,
foreign key(user_id)
references users(id),
class_id bigint not null,
foreign key(class_id)
references classes(id),
level int not null default 0,
base_str int not null default 10,
base_dex int not null default 10,
base_con int not null default 10,
base_int int not null default 10,
base_wis int not null default 10,
base_cha int not null default 10,
is_deleted bit not null default 0,
inspiration int null default 0,
check(level>=0)
);

create table spells(
id bigint primary key auto_increment,
name nvarchar(50) not null,
level int not null default 0,
casting_time nvarchar(50) not null,
casting_range int not null default 0,
target nvarchar(50) not null,
components nvarchar(50) not null,
duration int not null default 0,
description text not null,
is_deleted bit not null default 0,
check(casting_range>=0),
check(casting_time>=0),
check(duration>=0),
check(level>=0)
);

create table character_spells(
	character_id bigint not null,
    foreign key(character_id)
    references characters(id),
	spell_id bigint not null,
    foreign key(spell_id)
    references spells(id),
    primary key(character_id, spell_id)
);

create table class_spells(
	class_id bigint not null,
    foreign key(class_id)
    references classes(id),
	spell_id bigint not null,
    foreign key(spell_id)
    references spells(id),
    primary key(class_id, spell_id)
);

create table proficiencies(
id bigint primary key auto_increment,
name nvarchar(50) not null,
type nvarchar(50) not null,
is_deleted bit not null default 0
);

create table proficiency_classes(
	class_id bigint not null,
    foreign key(class_id)
    references classes(id),
	proficiency_id bigint not null,
    foreign key(proficiency_id)
    references proficiencies(id),
    primary key(class_id, proficiency_id)
);

create table proficiency_characters(
	character_id bigint not null,
    foreign key(character_id)
    references characters(id),
	proficiency_id bigint not null,
    foreign key(proficiency_id)
    references proficiencies(id),
    primary key(character_id, proficiency_id),
    expertise bit not null default 0
);





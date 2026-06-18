create table guilds (
    id bigserial primary key,
    nom varchar(150) not null,
    slug varchar(100) not null unique
);

create table raids (
    id bigserial primary key,
    slug varchar(100) not null unique,
    nom varchar(150) not null,
    description text,
    niveau_recommande varchar(50),
    duree_estimee_minutes integer,
    difficulte varchar(30) not null,
    actif boolean not null
);

create table paliers (
    id bigserial primary key,
    raid_id bigint not null references raids(id),
    ordre integer not null,
    code varchar(50) not null,
    nom varchar(150) not null,
    resume text,
    objectif text,
    actions text,
    roles text,
    pieges text,
    conditions_reussite text,
    suite text,
    conseil text,
    faq text,
    actif boolean not null
);

create table raid_progressions (
    id bigserial primary key,
    guild_id bigint not null references guilds(id),
    raid_id bigint not null references raids(id),
    palier_actuel_ordre integer,
    statut varchar(30) not null,
    started_at timestamp not null,
    updated_at timestamp not null,
    unique (guild_id, raid_id)
);

create table palier_progressions (
    id bigserial primary key,
    progression_raid_id bigint not null references raid_progressions(id),
    palier_id bigint not null references paliers(id),
    statut varchar(30) not null,
    unlocked_at timestamp,
    validated_at timestamp,
    unique (progression_raid_id, palier_id)
);

create table chat_messages (
    id bigserial primary key,
    guild_id bigint not null,
    raid_id bigint not null,
    palier_id bigint not null,
    role_joueur varchar(50),
    question text not null,
    reponse text not null,
    created_at timestamp not null
);

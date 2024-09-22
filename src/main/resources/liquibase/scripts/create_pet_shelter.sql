-- liquibase formatted sql

-- changeset semenikhin:create_pet_shelter


-- Drop table

-- DROP TABLE public.shelters;

CREATE TABLE shelters (
	id BIGSERIAL primary key,
	address varchar(255) NULL,
	"name" varchar(255) NULL,
	phone_security varchar(255) NULL,
	phone_shelter varchar(255) NULL,
	shelter_type varchar(255) NULL,
	time_work varchar(255) NULL,
	CONSTRAINT shelters_pkey PRIMARY KEY (id),
	CONSTRAINT shelters_shelter_type_check CHECK (((shelter_type)::text = ANY ((ARRAY['CAT_SHELTER'::character varying,
	'DOG_SHELTER'::character varying])::text[])))
);


-- public.volunteers определение

-- Drop table

-- DROP TABLE public.volunteers;

CREATE TABLE volunteers (
	id BIGSERIAL primary key,
	chat_id int8 NULL,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	middle_name varchar(255) NULL,
	nick_name varchar(255) NULL,
	CONSTRAINT volunteers_pkey PRIMARY KEY (id)
);


-- public.adopters определение

-- Drop table

-- DROP TABLE public.adopters;

CREATE TABLE adopters (
	id BIGSERIAL primary key,
	probation_period bool NOT NULL,
	telegram_user_id int8 NULL,
	CONSTRAINT adopters_pkey PRIMARY KEY (id),
	CONSTRAINT ukoifed14ewk7iok3n5ypa6oual UNIQUE (telegram_user_id)
);


-- public.pets определение

-- Drop table

-- DROP TABLE public.pets;

CREATE TABLE pets (
	id BIGSERIAL primary key,
	age int4 NULL,
	free_status bool NULL,
	"name" varchar(255) NULL,
	pet_type varchar(255) NULL,
	sex varchar(255) NULL,
	adopter_id int8 NULL,
	shelter_id int4 NULL,
	CONSTRAINT pets_pet_type_check CHECK (((pet_type)::text = ANY ((ARRAY['CAT'::character varying, 'DOG'::character varying])::text[]))),
	CONSTRAINT pets_pkey PRIMARY KEY (id),
	CONSTRAINT pets_sex_check CHECK (((sex)::text = ANY ((ARRAY['MAN'::character varying, 'WOMAN'::character varying])::text[])))
);


-- public.pets_avatars определение

-- Drop table

-- DROP TABLE public.pets_avatars;

CREATE TABLE pets_avatars (
	id BIGSERIAL primary key,
	"data" oid NULL,
	file_path varchar(255) NULL,
	file_size int8 NULL,
	media_type varchar(255) NULL,
	pet_id int8 NULL,
	CONSTRAINT pets_avatars_pkey PRIMARY KEY (id),
	CONSTRAINT uko1dd72miyjgo2jdw381vtago4 UNIQUE (pet_id)
);


-- public.reports определение

-- Drop table

-- DROP TABLE public.reports;

CREATE TABLE reports (
	id BIGSERIAL primary key,
	date_report date NULL,
	file_path varchar(255) NULL,
	file_size int8 NULL,
	is_viewed bool NULL,
	media_type varchar(255) NULL,
	text_report varchar(255) NULL,
	adopter_id int8 NULL,
	pet_id int8 NULL,
	CONSTRAINT reports_pkey PRIMARY KEY (id)
);


-- public.telegram_users определение

-- Drop table

-- DROP TABLE public.telegram_users;

CREATE TABLE telegram_users (
	id BIGSERIAL primary key,
	car_number varchar(255) NULL,
	chat_id int8 NULL,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	middle_name varchar(255) NULL,
	nick_name varchar(255) NULL,
	phone_number varchar(255) NULL,
	shelter varchar(255) NULL,
	telegram_id int8 NULL,
	adopter_id int8 NULL,
	CONSTRAINT telegram_users_pkey PRIMARY KEY (id),
	CONSTRAINT uk9f0reubsjleukbmpvlym64v89 UNIQUE (adopter_id)
);


-- public.adopters внешние включи

ALTER TABLE adopters ADD CONSTRAINT fk51uit3rao25g22c1dngovpvcl FOREIGN KEY (telegram_user_id) REFERENCES telegram_users(id);


-- public.pets внешние включи

ALTER TABLE pets ADD CONSTRAINT fk8vxoqdbwqo1m3l9gwsa90ow1x FOREIGN KEY (adopter_id) REFERENCES adopters(id);
ALTER TABLE pets ADD CONSTRAINT fkcia4g747ywfkmssqfa0dghtxa FOREIGN KEY (shelter_id) REFERENCES shelters(id);


-- public.pets_avatars внешние включи

ALTER TABLE pets_avatars ADD CONSTRAINT fklvvxgo8ltg44t2ugjaqpqdqh9 FOREIGN KEY (pet_id) REFERENCES pets(id);


-- public.reports внешние включи

ALTER TABLE reports ADD CONSTRAINT fkairxd24rpb3qjeps4dsw9un10 FOREIGN KEY (adopter_id) REFERENCES adopters(id);
ALTER TABLE reports ADD CONSTRAINT fkc964hks5ar2hiui1ymjn7osoc FOREIGN KEY (pet_id) REFERENCES pets(id);


-- public.telegram_users внешние включи

ALTER TABLE telegram_users ADD CONSTRAINT fkp4clrpr9oo79g95wwpkdnnhk5 FOREIGN KEY (adopter_id) REFERENCES adopters(id);








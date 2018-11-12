DROP SCHEMA IF EXISTS sample CASCADE;
CREATE SCHEMA sample;

CREATE SEQUENCE sample.city_seq;
CREATE TABLE sample.city
(
  city_id bigint DEFAULT NEXTVAL ('sample.city_seq') NOT NULL,
  city_name character varying(255),
  CONSTRAINT city_pkey PRIMARY KEY (city_id)
 );
 
CREATE SEQUENCE sample.department_seq;
CREATE TABLE sample.department
(
  department_id bigint DEFAULT NEXTVAL ('sample.department_seq') NOT NULL,
  department_name character varying(255),
  city_id bigint REFERENCES sample.city(city_id),
  CONSTRAINT client_pkey PRIMARY KEY (department_id)
)

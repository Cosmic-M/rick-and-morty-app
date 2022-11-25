--liquibase formatted sql
--changeset <postgres>:<add-url-image-column-to-movie-character-table>

ALTER TABLE public.movie_character ADD image_url character varying(256);

--rollback ALTER TABLE DROP COLUMN image_url;
CREATE TABLE IF NOT EXISTS public.cards
(
    card_id integer NOT NULL,
    mobile_number varchar(15) NOT NULL,
    card_number varchar(100) NOT NULL,
    card_type varchar(100) NOT NULL,
    total_limit integer NOT NULL,
    amount_used integer NOT NULL,
    available_amount integer NOT NULL,
    created_at date NOT NULL,
    created_by varchar(20) NOT NULL,
    update_at date NOT NULL,
    update_by varchar(20) NOT NULL,
    CONSTRAINT cards_pkey PRIMARY KEY (card_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.cards
    OWNER to postgres;
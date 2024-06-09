CREATE TABLE IF NOT EXISTS public.accounts
(
    account_number integer NOT NULL,
    customer_id integer NOT NULL,
    account_type varchar(100) NOT NULL,
    branch_address varchar(200) NOT NULL,
    created_at date NOT NULL,
    created_by varchar(200) NOT NULL,
    update_at date ,
    update_by varchar(200) ,
    CONSTRAINT accounts_pkey PRIMARY KEY (account_number)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.accounts
    OWNER to postgres;


CREATE TABLE IF NOT EXISTS public.customers (
customer_id integer NOT NULL,
name varchar(100) NOT NULL,
email varchar(100) NOT NULL,
mobile_number varchar(20) NOT NULL,
created_at date NOT NULL,
created_by varchar(200) NOT NULL,
update_at date ,
update_by varchar(200) ,
CONSTRAINT customer_pkey PRIMARY KEY (customer_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.customers
    OWNER to postgres;

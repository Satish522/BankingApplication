CREATE TABLE IF NOT EXISTS public.loans
(
    loan_id integer NOT NULL,
    mobile_number varchar(15) NOT NULL,
    loan_number varchar(100) NOT NULL,
    loan_type varchar(100) NOT NULL,
    total_loan integer NOT NULL,
    amount_paid integer NOT NULL,
    outstanding_amount integer NOT NULL,
    created_at date NOT NULL,
    created_by varchar(200) NOT NULL,
    update_at date ,
    update_by varchar(200) ,
    CONSTRAINT loans_pkey PRIMARY KEY (loan_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.loans
    OWNER to postgres;
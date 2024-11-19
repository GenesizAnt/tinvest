CREATE TABLE fire
(
    id            SERIAL PRIMARY KEY,
    active        NUMERIC(20, 2),
    investments   NUMERIC(20, 2),
    dividend      NUMERIC(20, 2),
    commission    NUMERIC(20, 2),
    profitability NUMERIC(20, 2),
    annualYield   NUMERIC(20, 2),
    date          date
);
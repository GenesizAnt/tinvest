CREATE TABLE snapshot_position
(
    id           BIGSERIAL PRIMARY KEY,
    datesnapshot DATE           NOT NULL,
    amount       DECIMAL(19, 2) NOT NULL,
    ticker       VARCHAR(255)   NOT NULL,
    name         VARCHAR(255)   NOT NULL,
    shortname    VARCHAR(255)   NOT NULL
);
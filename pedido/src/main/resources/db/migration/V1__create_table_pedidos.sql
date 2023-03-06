CREATE TABLE pedidos
(
    id        BIGINT       NOT NULL,
    data_hora datetime     NULL,
    status    VARCHAR(255) NULL,
    CONSTRAINT pk_pedidos PRIMARY KEY (id)
);
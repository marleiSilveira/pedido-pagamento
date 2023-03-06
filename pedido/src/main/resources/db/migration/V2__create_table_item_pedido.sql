CREATE TABLE item_do_pedido
(
    id         BIGINT       NOT NULL,
    quantidade INT          NULL,
    descricao  VARCHAR(255) NULL,
    pedido_id  BIGINT       NOT NULL,
    CONSTRAINT pk_item_do_pedido PRIMARY KEY (id)
);

ALTER TABLE item_do_pedido
    ADD CONSTRAINT FK_ITEM_DO_PEDIDO_ON_PEDIDO FOREIGN KEY (pedido_id) REFERENCES pedidos (id);
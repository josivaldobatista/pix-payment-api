-- Migration para criar a tabela de pagamentos Pix
-- Seguindo a modelagem de cobranças, pagamentos e estados [cite: 3, 12]

CREATE TABLE pix_payment_db
(
    id         VARCHAR(36)  NOT NULL,
    txid       VARCHAR(255) NOT NULL,
    amount     NUMERIC(19, 2),
    status     VARCHAR(20)  NOT NULL,
    created_at TIMESTAMP    NOT NULL,

    CONSTRAINT pk_pix_payment_db PRIMARY KEY (id)
);

-- Index para otimizar buscas por TXID, que é o identificador da cobrança no QR Code [cite: 16]
CREATE INDEX idx_pix_payment_db_txid ON pix_payment_db (txid);
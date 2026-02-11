-- Adiciona a coluna paid_at para registrar o momento
-- em que o pagamento Pix foi efetivamente confirmado.
-- Essa coluna é NULLABLE porque pagamentos podem
-- permanecer em estado PENDING ou FAILED.

ALTER TABLE pix_payment_db
    ADD COLUMN paid_at TIMESTAMP;

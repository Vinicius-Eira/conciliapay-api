-- Ajustes no Gateway
ALTER TABLE gateway_transactions
ADD COLUMN payment_method VARCHAR(50),
ADD COLUMN installments INT DEFAULT 1,
ADD COLUMN nsu VARCHAR(100),
ADD COLUMN expected_payment_date DATE;

-- Ajustes no Banco
ALTER TABLE bank_transactions
ADD COLUMN transaction_type VARCHAR(20),
ADD COLUMN description TEXT;

-- Ajuste na Conciliação (Permitir Lotes 1-para-N)
ALTER TABLE reconciliations DROP CONSTRAINT IF EXISTS reconciliations_bank_transaction_id_key;
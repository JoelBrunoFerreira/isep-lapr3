-- USBD24:
-- Todos os registos relacionados com operações tem que ter registado o instante em que foram criados.
CREATE OR REPLACE TRIGGER trgCriacaoOperacaoTimestamp
    BEFORE INSERT ON Operacao
    FOR EACH ROW
BEGIN
    :NEW.DataCriacao := SYSDATE;
END;
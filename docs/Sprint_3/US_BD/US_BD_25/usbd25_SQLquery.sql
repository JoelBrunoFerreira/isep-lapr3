-- USBD25 Como Gestor Agrícola, pretendo que a identificação da operação seja um número sequencial, não gerado automaticamente pelo SGBD, que deve ser gerado no contexto da transação de registo da operação. Se este registo falhar, não deve haver consequências, nomeadamente a existência de "buracos" na numeração.

CREATE OR REPLACE TRIGGER trg_incrementar_operacao_id_before_insert
BEFORE INSERT ON Operacao
FOR EACH ROW
DECLARE
    ultimoID NUMBER;
BEGIN
    -- Seleciona o maior ID atual na tabela Operacoes
    SELECT NVL(MAX(OperacaoID), 0) INTO ultimoID FROM Operacao;

    -- Define o novo ID como o último ID mais um
    :NEW.OperacaoID := ultimoID + 1;
END;

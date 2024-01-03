--USBD27
--Não é possível alterar ou apagar os logs.
CREATE OR REPLACE TRIGGER trgApagarOuAlterarLogs
BEFORE DELETE OR UPDATE ON LOGOPERACOES
FOR EACH ROW
BEGIN
    RAISE_APPLICATION_ERROR(-20001, 'Não é permitido excluir ou atualizar registos de logs.');
END;
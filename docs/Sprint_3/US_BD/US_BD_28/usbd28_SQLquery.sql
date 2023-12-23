--USBD28
--Não é possível alterar ou apagar os logs.

CREATE OR REPLACE TRIGGER trgNaoApagarOperacao
BEFORE DELETE ON Operacao
FOR EACH ROW
BEGIN
    	RAISE_APPLICATION_ERROR(-20000, 'Não é permitido apagar operações, só anular.');
END;
--USBD28
--Não apagar uma operação:
CREATE OR REPLACE TRIGGER trgNaoApagarOperacao
BEFORE DELETE ON Operacao
FOR EACH ROW
BEGIN
    	RAISE_APPLICATION_ERROR(-20001, 'Não é permitido apagar operações, só anular.');
END;

-- Op. Plantação:
CREATE OR REPLACE TRIGGER trgNaoApagarOpPlantacao
BEFORE DELETE ON Plantacao
FOR EACH ROW
BEGIN
    	RAISE_APPLICATION_ERROR(-20001, 'Não é permitido apagar operações.');
END;

-- Op. Semeadura:
CREATE OR REPLACE TRIGGER trgNaoApagarOpSemeadura
BEFORE DELETE ON Semeadura
FOR EACH ROW
BEGIN
    	RAISE_APPLICATION_ERROR(-20001, 'Não é permitido apagar operações.');
END;

-- Op. Poda:
CREATE OR REPLACE TRIGGER trgNaoApagarOpPoda
BEFORE DELETE ON Poda
FOR EACH ROW
BEGIN
    	RAISE_APPLICATION_ERROR(-20001, 'Não é permitido apagar operações.');
END;

-- Op. Rega
CREATE OR REPLACE TRIGGER trgNaoApagarOpRega
BEFORE DELETE ON Rega
FOR EACH ROW
BEGIN
    	RAISE_APPLICATION_ERROR(-20001, 'Não é permitido apagar operações.');
END;

-- Op. Colheita
CREATE OR REPLACE TRIGGER trgNaoApagarOpColheita
BEFORE DELETE ON Colheita
FOR EACH ROW
BEGIN
    	RAISE_APPLICATION_ERROR(-20001, 'Não é permitido apagar operações.');
END;

-- Op. Monda
CREATE OR REPLACE TRIGGER trgNaoApagarOpMonda
BEFORE DELETE ON Monda
FOR EACH ROW
BEGIN
    	RAISE_APPLICATION_ERROR(-20001, 'Não é permitido apagar operações.');
END;

-- Op. Incorporação Solo
CREATE OR REPLACE TRIGGER trgNaoApagarOpIncorporacaoSolo
BEFORE DELETE ON IncorporacaoSolo
FOR EACH ROW
BEGIN
    	RAISE_APPLICATION_ERROR(-20001, 'Não é permitido apagar operações.');
END;

-- Op. Mobilização Solo
CREATE OR REPLACE TRIGGER trgNaoApagarOpMobilizacaoSolo
BEFORE DELETE ON MobilizacaoSolo
FOR EACH ROW
BEGIN
    	RAISE_APPLICATION_ERROR(-20001, 'Não é permitido apagar operações.');
END;

-- Op. Aplicação Fator Produção
CREATE OR REPLACE TRIGGER trgNaoApagarOpAplicacaoFatorProducao
BEFORE DELETE ON AplicacaoFatorProducao
FOR EACH ROW
BEGIN
    	RAISE_APPLICATION_ERROR(-20001, 'Não é permitido apagar operações.');
END;
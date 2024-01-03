--USBD26
--Criar um log sempre que haja uma introdução ou alteração de operação:
--OPERACAO
CREATE OR REPLACE TRIGGER trg_criacao_alteracao_operacao
    AFTER UPDATE ON OPERACAO
                     FOR EACH ROW
DECLARE
log_id LOGOPERACOES.LOGID%type;
    tipo LOGOPERACOES.TIPOLOG%type := 'Atualização';
BEGIN
SELECT NVL(MAX(LOGID), 0) + 1 INTO log_id
FROM LOGOPERACOES;

INSERT INTO LOGOPERACOES (LogID, TIPOLOG, OPERACAOID, TipoOperacao, DataCriacao, DataRealizacao, Estado)
VALUES (log_id, tipo, :NEW.OPERACAOID, :NEW.TIPOOPERACAO, :NEW.DATACRIACAO, :NEW.DATAREALIZACAO, :NEW.ESTADO);
END;

--OP MONDA
CREATE OR REPLACE TRIGGER trg_criacao_alteracao_monda
    BEFORE INSERT OR UPDATE ON Monda
    FOR EACH ROW
DECLARE
    log_id LOGOPERACOES.LOGID%type;
    tipo LOGOPERACOES.TIPOLOG%type;
    data_realizacao OPERACAO.DATAREALIZACAO%type;
    data_criacao OPERACAO.DATACRIACAO%type;
    tipo_op OPERACAO.TIPOOPERACAO%type;
    estado_op OPERACAO.ESTADO%type;
BEGIN
    SELECT NVL(MAX(LOGID), 0) + 1 INTO log_id FROM LOGOPERACOES;

    SELECT TIPOOPERACAO, DATAREALIZACAO, DATACRIACAO, ESTADO
    INTO tipo_op, data_realizacao, data_criacao, estado_op
    FROM OPERACAO WHERE OPERACAO.OPERACAOID = :NEW.OPERACAOID;

    IF INSERTING THEN
        tipo := 'Inserção';
    ELSE
        tipo := 'Atualização';
    END IF;

    INSERT INTO LOGOPERACOES (LogID, TIPOLOG, OPERACAOID, TipoOperacao, DataCriacao, DataRealizacao, Estado, CultivoID, Area, Modo)
    VALUES (log_id, tipo, :NEW.OPERACAOID, tipo_op, data_criacao, data_realizacao, estado_op, :NEW.CULTIVOID, :NEW.AREA, :NEW.MODO);
END;

--OP SEMEADURA
CREATE OR REPLACE TRIGGER trg_criacao_alteracao_semeadura
    BEFORE INSERT OR UPDATE ON SEMEADURA
    FOR EACH ROW
DECLARE
    log_id LOGOPERACOES.LOGID%type;
    tipo LOGOPERACOES.TIPOLOG%type;
    data_realizacao OPERACAO.DATAREALIZACAO%type;
    data_criacao OPERACAO.DATACRIACAO%type;
    tipo_op OPERACAO.TIPOOPERACAO%type;
    estado_op OPERACAO.ESTADO%type;
BEGIN
    SELECT NVL(MAX(LOGID), 0) + 1 INTO log_id FROM LOGOPERACOES;

    SELECT TIPOOPERACAO, DATAREALIZACAO, DATACRIACAO, ESTADO
    INTO tipo_op, data_realizacao, data_criacao, estado_op
    FROM OPERACAO WHERE OPERACAO.OPERACAOID = :NEW.OPERACAOID;

    IF INSERTING THEN
        tipo := 'Inserção';
    ELSE
        tipo := 'Atualização';
    END IF;

    INSERT INTO LOGOPERACOES (LogID, TIPOLOG, OPERACAOID, TipoOperacao, DataCriacao, DataRealizacao, Estado, CultivoID, Area, Quantidade)
    VALUES (log_id, tipo, :NEW.OPERACAOID, tipo_op, data_criacao, data_realizacao, estado_op, :NEW.CULTIVOID, :NEW.AREA, :NEW.QUANTIDADE);
END;

--OP PLANTAÇÃO
CREATE OR REPLACE TRIGGER trg_criacao_alteracao_plantacao
    BEFORE INSERT OR UPDATE ON PLANTACAO
    FOR EACH ROW
DECLARE
    log_id LOGOPERACOES.LOGID%type;
    tipo LOGOPERACOES.TIPOLOG%type;
    data_realizacao OPERACAO.DATAREALIZACAO%type;
    data_criacao OPERACAO.DATACRIACAO%type;
    tipo_op OPERACAO.TIPOOPERACAO%type;
    estado_op OPERACAO.ESTADO%type;
BEGIN
    SELECT NVL(MAX(LOGID), 0) + 1 INTO log_id FROM LOGOPERACOES;

    SELECT TIPOOPERACAO, DATAREALIZACAO, DATACRIACAO, ESTADO
    INTO tipo_op, data_realizacao, data_criacao, estado_op
    FROM OPERACAO WHERE OPERACAO.OPERACAOID = :NEW.OPERACAOID;

    IF INSERTING THEN
        tipo := 'Inserção';
    ELSE
        tipo := 'Atualização';
    END IF;

    INSERT INTO LOGOPERACOES (LogID, TIPOLOG, OPERACAOID, TipoOperacao, DataCriacao, DataRealizacao, Estado, CultivoID, Quantidade)
    VALUES (log_id, tipo, :NEW.OPERACAOID, tipo_op, data_criacao, data_realizacao, estado_op, :NEW.CULTIVOID, :NEW.QUANTIDADE);
END;

--OP Colheita
CREATE OR REPLACE TRIGGER trg_criacao_alteracao_colheita
    BEFORE INSERT OR UPDATE ON COLHEITA
    FOR EACH ROW
DECLARE
    log_id LOGOPERACOES.LOGID%type;
    tipo LOGOPERACOES.TIPOLOG%type;
    data_realizacao OPERACAO.DATAREALIZACAO%type;
    data_criacao OPERACAO.DATACRIACAO%type;
    tipo_op OPERACAO.TIPOOPERACAO%type;
    estado_op OPERACAO.ESTADO%type;
BEGIN
    SELECT NVL(MAX(LOGID), 0) + 1 INTO log_id FROM LOGOPERACOES;

    SELECT TIPOOPERACAO, DATAREALIZACAO, DATACRIACAO, ESTADO
    INTO tipo_op, data_realizacao, data_criacao, estado_op
    FROM OPERACAO WHERE OPERACAO.OPERACAOID = :NEW.OPERACAOID;

    IF INSERTING THEN
        tipo := 'Inserção';
    ELSE
        tipo := 'Atualização';
    END IF;

    INSERT INTO LOGOPERACOES (LogID, TIPOLOG, OPERACAOID, TipoOperacao, DataCriacao, DataRealizacao, Estado, CultivoID, Quantidade, PRODUTOID)
    VALUES (log_id, tipo, :NEW.OPERACAOID, tipo_op, data_criacao, data_realizacao, estado_op, :NEW.CULTIVOID, :NEW.QUANTIDADEPRODUTO, :NEW.PRODUTOID);
END;

--OP Rega
CREATE OR REPLACE TRIGGER trg_criacao_alteracao_rega
    BEFORE INSERT OR UPDATE ON REGA
    FOR EACH ROW
DECLARE
    log_id LOGOPERACOES.LOGID%TYPE := 0; -- Initialize log_id with 0
    tipo LOGOPERACOES.TIPOLOG%TYPE;
    data_realizacao OPERACAO.DATAREALIZACAO%TYPE;
    data_criacao OPERACAO.DATACRIACAO%TYPE;
    tipo_op OPERACAO.TIPOOPERACAO%TYPE;
    estado_op OPERACAO.ESTADO%TYPE;
    area_fp APLICACAOFATORPRODUCAO.AREA%TYPE := null;
BEGIN
    SELECT NVL(MAX(LOGID), 0) + 1 INTO log_id FROM LOGOPERACOES;

    SELECT TIPOOPERACAO, DATAREALIZACAO, DATACRIACAO, ESTADO
    INTO tipo_op, data_realizacao, data_criacao, estado_op
    FROM OPERACAO WHERE OPERACAO.OPERACAOID = :NEW.OPERACAOID;

    IF INSERTING THEN
        tipo := 'Inserção';
    ELSE
        tipo := 'Atualização';
    END IF;

    IF tipo_op = 'Fertirrega' THEN
        SELECT APLICACAOFATORPRODUCAO.AREA INTO area_fp
        FROM APLICACAOFATORPRODUCAO
        WHERE APLICACAOFATORPRODUCAO.OPERACAOID = :NEW.OPERACAOID;
    END IF;
    INSERT INTO LOGOPERACOES (LogID, TIPOLOG, OPERACAOID, TipoOperacao, DataCriacao, DataRealizacao, Estado, CultivoID, Hora, SETORID, Duracao, AREA)
    VALUES (log_id, tipo, :NEW.OPERACAOID, tipo_op, data_criacao, data_realizacao, estado_op, :NEW.CULTIVOID, :NEW.HORA, :NEW.SETORID, :NEW.DURACAO, area_fp);
END;


--OP APLICAÇÃO FATOR PRODUÇÃO
CREATE OR REPLACE TRIGGER trg_criacao_alteracao_aplicacaoFP
    BEFORE INSERT OR UPDATE ON APLICACAOFATORPRODUCAO
    FOR EACH ROW
DECLARE
    log_id LOGOPERACOES.LOGID%type;
    tipo LOGOPERACOES.TIPOLOG%type;
    data_realizacao OPERACAO.DATAREALIZACAO%type;
    data_criacao OPERACAO.DATACRIACAO%type;
    tipo_op OPERACAO.TIPOOPERACAO%type;
    estado_op OPERACAO.ESTADO%type;

BEGIN
    SELECT NVL(MAX(LOGID), 0) + 1 INTO log_id FROM LOGOPERACOES;

    SELECT TIPOOPERACAO, DATAREALIZACAO, DATACRIACAO, ESTADO
    INTO tipo_op, data_realizacao, data_criacao, estado_op
    FROM OPERACAO WHERE OPERACAO.OPERACAOID = :NEW.OPERACAOID;

    IF INSERTING THEN
        tipo := 'Inserção';
    ELSE
        tipo := 'Atualização';
    END IF;

    IF tipo_op = 'Aplicação Fator Produção' THEN
        INSERT INTO LOGOPERACOES (LogID, TIPOLOG, OPERACAOID, TipoOperacao, DataCriacao, DataRealizacao, Estado, CultivoID, Area)
        VALUES (log_id, tipo, :NEW.OPERACAOID, tipo_op, data_criacao, data_realizacao, estado_op, :NEW.CULTIVOID, :NEW.AREA);
   END IF;
END;

--OP MOBILIZAÇÃO SOLO
CREATE OR REPLACE TRIGGER trg_criacao_alteracao_mobilizacaoSolo
    BEFORE INSERT OR UPDATE ON MOBILIZACAOSOLO
    FOR EACH ROW
DECLARE
    log_id LOGOPERACOES.LOGID%type;
    tipo LOGOPERACOES.TIPOLOG%type;
    data_realizacao OPERACAO.DATAREALIZACAO%type;
    data_criacao OPERACAO.DATACRIACAO%type;
    tipo_op OPERACAO.TIPOOPERACAO%type;
    estado_op OPERACAO.ESTADO%type;
BEGIN
    SELECT NVL(MAX(LOGID), 0) + 1 INTO log_id FROM LOGOPERACOES;

    SELECT TIPOOPERACAO, DATAREALIZACAO, DATACRIACAO, ESTADO
    INTO tipo_op, data_realizacao, data_criacao, estado_op
    FROM OPERACAO WHERE OPERACAO.OPERACAOID = :NEW.OPERACAOID;

    IF INSERTING THEN
        tipo := 'Inserção';
    ELSE
        tipo := 'Atualização';
    END IF;

    INSERT INTO LOGOPERACOES (LogID, TIPOLOG, OPERACAOID, TipoOperacao, DataCriacao, DataRealizacao, Estado, PARCELAID, Area)
    VALUES (log_id, tipo, :NEW.OPERACAOID, tipo_op, data_criacao, data_realizacao, estado_op, :NEW.PARCELAID, :NEW.AREA);
END;

--OP INCORPORAÇÃO SOLO
CREATE OR REPLACE TRIGGER trg_criacao_alteracao_incorporacaoSolo
    BEFORE INSERT OR UPDATE ON INCORPORACAOSOLO
    FOR EACH ROW
DECLARE
    log_id LOGOPERACOES.LOGID%type;
    tipo LOGOPERACOES.TIPOLOG%type;
    data_realizacao OPERACAO.DATAREALIZACAO%type;
    data_criacao OPERACAO.DATACRIACAO%type;
    tipo_op OPERACAO.TIPOOPERACAO%type;
    estado_op OPERACAO.ESTADO%type;
BEGIN
    SELECT NVL(MAX(LOGID), 0) + 1 INTO log_id FROM LOGOPERACOES;

    SELECT TIPOOPERACAO, DATAREALIZACAO, DATACRIACAO, ESTADO
    INTO tipo_op, data_realizacao, data_criacao, estado_op
    FROM OPERACAO WHERE OPERACAO.OPERACAOID = :NEW.OPERACAOID;

    IF INSERTING THEN
        tipo := 'Inserção';
    ELSE
        tipo := 'Atualização';
    END IF;

    INSERT INTO LOGOPERACOES (LogID, TIPOLOG, OPERACAOID, TipoOperacao, DataCriacao, DataRealizacao, Estado, CULTIVOID, Area)
    VALUES (log_id, tipo, :NEW.OPERACAOID, tipo_op, data_criacao, data_realizacao, estado_op, :NEW.CULTIVOID, :NEW.AREA);
END;

--OP PODA
CREATE OR REPLACE TRIGGER trg_criacao_alteracao_poda
    BEFORE INSERT OR UPDATE ON PODA
    FOR EACH ROW
DECLARE
    log_id LOGOPERACOES.LOGID%type;
    tipo LOGOPERACOES.TIPOLOG%type;
    data_realizacao OPERACAO.DATAREALIZACAO%type;
    data_criacao OPERACAO.DATACRIACAO%type;
    tipo_op OPERACAO.TIPOOPERACAO%type;
    estado_op OPERACAO.ESTADO%type;
BEGIN
    SELECT NVL(MAX(LOGID), 0) + 1 INTO log_id FROM LOGOPERACOES;

    SELECT TIPOOPERACAO, DATAREALIZACAO, DATACRIACAO, ESTADO
    INTO tipo_op, data_realizacao, data_criacao, estado_op
    FROM OPERACAO WHERE OPERACAO.OPERACAOID = :NEW.OPERACAOID;

    IF INSERTING THEN
        tipo := 'Inserção';
    ELSE
        tipo := 'Atualização';
    END IF;

    INSERT INTO LOGOPERACOES (LogID, TIPOLOG, OPERACAOID, TipoOperacao, DataCriacao, DataRealizacao, Estado, CULTIVOID, QUANTIDADE)
    VALUES (log_id, tipo, :NEW.OPERACAOID, tipo_op, data_criacao, data_realizacao, estado_op, :NEW.CULTIVOID, :NEW.QUANTIDADE);
END;

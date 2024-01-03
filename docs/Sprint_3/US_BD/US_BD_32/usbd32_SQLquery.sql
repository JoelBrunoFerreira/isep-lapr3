-- USBD32 Como Gestor Agrıcola, pretendo registar uma operacao de rega, incluindo a componente de fertirrega (se aplicavel).

CREATE OR REPLACE PROCEDURE registar_operacao_rega(
    setor_id IN SETOR.SETORID%type,
    data_realizacao IN OPERACAO.DATAREALIZACAO%type,
    hora_rega IN REGA.HORA%type,
    duracao_rega IN REGA.DURACAO%type,
    receita_id IN RECEITA.RECEITAID%type
)
IS
    v_setor_id SETOR.SETORID%type;
    v_receita_id RECEITA.RECEITAID%type;
    operacao_id OPERACAO.OPERACAOID%type;
    tipo_operacao OPERACAO.TIPOOPERACAO%TYPE;
    estado_operacao OPERACAO.ESTADO%TYPE;
    fatores_producao Lista;
    quantidades_fator_producao_por_area Lista;
    quantidade_fator_producao DOUBLE PRECISION;
    unidades Lista;
    unidade_id Unidade.UnidadeID%TYPE;
    cultivos Lista;
    quantidade_arvores CULTIVO.QUANTIDADE%TYPE;
    area_planta DOUBLE PRECISION := 45;
    area_aplicacao APLICACAOFATORPRODUCAO.AREA%TYPE;
    kg_ha_unidade_id UNIDADE.UNIDADEID%TYPE;
    l_ha_unidade_id UNIDADE.UNIDADEID%TYPE;
    kg_unidade_id UNIDADE.UNIDADEID%TYPE;
    l_unidade_id UNIDADE.UNIDADEID%TYPE;
    e_setor_not_found EXCEPTION;
    e_receita_not_found EXCEPTION;
    PRAGMA EXCEPTION_INIT(e_setor_not_found, -20003);
    PRAGMA EXCEPTION_INIT(e_receita_not_found, -20004);
BEGIN
    -- Inicia uma transação
SAVEPOINT transaction_start;

tipo_operacao := CASE WHEN receita_id IS NOT NULL THEN 'Fertirrega' ELSE 'Rega' END;
    estado_operacao := CASE WHEN TRUNC(data_realizacao) >= TRUNC(CURRENT_DATE) THEN 'Pendente' ELSE 'Realizado' END;

BEGIN
        -- Verifica se o setor existe
SELECT SetorID
INTO v_setor_id
FROM Setor
WHERE SetorID = setor_id;

-- Procura os ID dos Cultivos para aquele setor
SELECT CultivoID
           BULK COLLECT INTO cultivos
FROM Setor_Cultivo
WHERE SetorID = v_setor_id;

IF tipo_operacao = 'Fertirrega' THEN
SELECT ReceitaID
INTO v_receita_id
FROM RECEITA
WHERE RECEITAID = receita_id;

-- Vai buscar os fatores de producao
SELECT FatorProducaoID, Quantidade, UnidadeID
    BULK COLLECT INTO fatores_producao, quantidades_fator_producao_por_area, unidades
FROM Receita_FatorProducao
WHERE ReceitaID = v_receita_id;

END IF;

EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- Se não encontrar o setor ou a receita
            IF v_setor_id IS NULL THEN
                RAISE e_setor_not_found;
            ELSIF v_receita_id IS NULL AND tipo_operacao = 'Fertirrega' THEN
                RAISE e_receita_not_found;
END IF;
END;

    -- Para cada cultivo:
FOR i IN 1..cultivos.COUNT LOOP

        -- Insere na tabela Operacao
        INSERT INTO Operacao (DataRealizacao, TipoOperacao, Estado)
        VALUES (data_realizacao, tipo_operacao, estado_operacao);

SELECT MAX(OperacaoID) INTO operacao_id FROM Operacao;

-- Se Fertirrega:
IF tipo_operacao = 'Fertirrega' THEN
SELECT Quantidade
INTO quantidade_arvores
FROM Cultivo
WHERE CultivoID = cultivos(i);

area_aplicacao := area_planta * quantidade_arvores / 10000;

INSERT INTO AplicacaoFatorProducao(OperacaoID, Area, CultivoID)
VALUES (operacao_id, area_aplicacao, cultivos(i));

-- Para cada fator producao:
FOR j IN 1..fatores_producao.COUNT LOOP
                quantidade_fator_producao := area_aplicacao * quantidades_fator_producao_por_area(j);

SELECT UnidadeID INTO kg_ha_unidade_id FROM Unidade WHERE DescricaoUnidade = 'kg/ha';
SELECT UnidadeID INTO l_ha_unidade_id FROM Unidade WHERE DescricaoUnidade = 'l/ha';
SELECT UnidadeID INTO kg_unidade_id FROM Unidade WHERE DescricaoUnidade = 'kg';
SELECT UnidadeID INTO l_unidade_id FROM Unidade WHERE DescricaoUnidade = 'l';

unidade_id := CASE
                    WHEN unidades(j) = kg_ha_unidade_id THEN kg_unidade_id
                    WHEN unidades(j) = l_ha_unidade_id THEN l_unidade_id
END;


INSERT INTO AplicacaoFatorProducao_FatorProducao(OperacaoID, FatorProducaoID, QuantidadeFatorProducao, UnidadeID)
VALUES (operacao_id, fatores_producao(j), quantidade_fator_producao, unidade_id);
END LOOP;
END IF;

        -- Insere na tabela Rega com o ID de Operacao
INSERT INTO Rega(OperacaoID, Hora, Duracao, SetorID, CultivoID)
VALUES (operacao_id, hora_rega, duracao_rega, v_setor_id, cultivos(i));

END LOOP;

COMMIT;

IF tipo_operacao = 'Fertirrega' THEN
        DBMS_OUTPUT.PUT_LINE('Sucesso: Operação de Fertirrega registada.');
ELSE
        DBMS_OUTPUT.PUT_LINE('Sucesso: Operação de Rega registada.');
END IF;
EXCEPTION
    WHEN e_setor_not_found THEN
        ROLLBACK TO transaction_start;
        RAISE_APPLICATION_ERROR(-20003, 'Setor não encontrado.');
WHEN e_receita_not_found THEN
        ROLLBACK TO transaction_start;
        RAISE_APPLICATION_ERROR(-20004, 'Receita não encontrada.');
WHEN NO_DATA_FOUND THEN
        ROLLBACK TO transaction_start;
        RAISE_APPLICATION_ERROR(-20001, 'Insucesso: Dados necessários não encontrados.');
WHEN OTHERS THEN
        ROLLBACK TO transaction_start;
        RAISE_APPLICATION_ERROR(-20001, 'Ocorreu um erro: ' || SQLERRM);
END registar_operacao_rega;

-- Caso Sucesso:

DECLARE
setor_id SETOR.SETORID%type := 10;
    data_realizacao OPERACAO.DATAREALIZACAO%type := TO_DATE('2023-09-04', 'YYYY-MM-DD');
    hora_rega REGA.HORA%type := TO_TIMESTAMP( '05:00', 'hh24:mi');
    duracao_rega REGA.DURACAO%type := 90;
    receita_id RECEITA.RECEITAID%type := 11;
    operacao_id OPERACAO.OPERACAOID%type;
    parcela_id PARCELA.PARCELAID%TYPE;
    cultura_id CULTURA.CULTURAID%TYPE;
    especie_vegetal_id ESPECIEVEGETAL.ESPECIEVEGETALID%TYPE;
    designacao_parcela PARCELA.DESIGNACAO%TYPE;
    v_variedade CULTURA.VARIEDADE%TYPE;
    nome_comum ESPECIEVEGETAL.NOMECOMUM%TYPE;
    nome_comercial FATORPRODUCAO.NOMECOMERCIAL%type;
    unidade_designacao UNIDADE.DESCRICAOUNIDADE%TYPE;
BEGIN
    registar_operacao_rega(setor_id, data_realizacao, hora_rega, duracao_rega, receita_id);

FOR r_operacao IN (
        SELECT Operacao.OPERACAOID, Operacao.DATAREALIZACAO, Operacao.DATACRIACAO, Operacao.TIPOOPERACAO, Operacao.ESTADO
        FROM Operacao
        JOIN REGA ON Operacao.OPERACAOID = REGA.OPERACAOID
        WHERE Operacao.DataRealizacao = data_realizacao
            AND Rega.SetorID = setor_id
            AND Rega.Hora = hora_rega
            AND Operacao.TipoOperacao IN ('Fertirrega', 'Rega')
    ) LOOP
        operacao_id := r_operacao.OPERACAOID;
        DBMS_OUTPUT.PUT_LINE('Operacao: ' || 'ID: ' || r_operacao.OPERACAOID || ', Data Realizacao: ' || r_operacao.DataRealizacao || ', Data Criacao: ' || r_operacao.DataCriacao ||  ', Tipo Operacao: ' || r_operacao.TipoOperacao || ', Estado: ' || r_operacao.Estado);

FOR r_rega IN (SELECT * FROM Rega WHERE OperacaoID = operacao_id) LOOP
SELECT PARCELAID, CULTURAID INTO parcela_id, cultura_id FROM CULTIVO WHERE CULTIVOID = r_rega.CULTIVOID;
SELECT DESIGNACAO INTO designacao_parcela FROM PARCELA WHERE PARCELAID = parcela_id;
SELECT ESPECIEVEGETALID, VARIEDADE INTO especie_vegetal_id, v_variedade FROM CULTURA WHERE CULTURAID = cultura_id;
SELECT NOMECOMUM INTO nome_comum FROM ESPECIEVEGETAL WHERE ESPECIEVEGETALID = especie_vegetal_id;
DBMS_OUTPUT.PUT_LINE('Rega: ' || 'ID: ' || r_rega.OPERACAOID || ', Hora: ' || TO_CHAR(r_rega.Hora, 'hh24:mi') || ', Duracao: ' || r_rega.Duracao || ', Setor ID: ' || r_rega.SetorID || ', Parcela: ' || designacao_parcela || ', Cultura: ' || nome_comum || ' ' ||  v_variedade);
END LOOP;

        IF receita_id IS NOT NULL THEN
            FOR r_aplicacao IN (SELECT * FROM AplicacaoFatorProducao WHERE OperacaoID = operacao_id) LOOP
                DBMS_OUTPUT.PUT_LINE( 'Aplicacao Fator Producao' || 'ID: ' || r_aplicacao.OPERACAOID || ', Area: ' || TO_CHAR(r_aplicacao.Area, '0.999') || ' ha, Parcela: ' || designacao_parcela || ', Cultura: ' || nome_comum || ' ' || v_variedade);
END LOOP;


FOR r_fator_producao IN (SELECT * FROM AplicacaoFatorProducao_FatorProducao WHERE OperacaoID = operacao_id) LOOP
SELECT FATORPRODUCAO.NOMECOMERCIAL
INTO nome_comercial
FROM APLICACAOFATORPRODUCAO_FATORPRODUCAO
         JOIN FATORPRODUCAO ON APLICACAOFATORPRODUCAO_FATORPRODUCAO.FatorProducaoID = FATORPRODUCAO.FatorProducaoID
WHERE APLICACAOFATORPRODUCAO_FATORPRODUCAO.OperacaoID = operacao_id AND FATORPRODUCAO.FATORPRODUCAOID = r_fator_producao.FATORPRODUCAOID;

SELECT DESCRICAOUNIDADE
INTO unidade_designacao
FROM UNIDADE
WHERE UnidadeID = r_fator_producao.UnidadeID;

DBMS_OUTPUT.PUT_LINE('Fator Producao: ' || nome_comercial || ', Quantidade: ' || TO_CHAR(r_fator_producao.QuantidadeFatorProducao, '0.999') || ' ' ||  unidade_designacao);
END LOOP;
END IF;
        DBMS_OUTPUT.PUT_LINE(CHR(10));
END LOOP;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erro: ' || SQLERRM);
END;

-- Caso Insucesso:

DECLARE
setor_id SETOR.SETORID%type := 10;
    data_realizacao OPERACAO.DATAREALIZACAO%type := TO_DATE('2023-09-04', 'YYYY-MM-DD');
    hora_rega REGA.HORA%type := TO_TIMESTAMP( '05:00', 'hh24:mi');
    duracao_rega REGA.DURACAO%type := 90;
    receita_id RECEITA.RECEITAID%type := 50;
BEGIN
    registar_operacao_rega(setor_id, data_realizacao, hora_rega, duracao_rega, receita_id);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erro: ' || SQLERRM);
END;
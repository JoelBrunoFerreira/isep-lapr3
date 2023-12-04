-- USBD11 - Como Gestor Agrıcola, quero registar uma operacao de semeadura

CREATE OR REPLACE PROCEDURE registar_operacao_semeadura (
    -- Procedimento recebe os seguintes 6 parametros, cada um corresponde a colunas em tabelas especificas na BD
    nome_parcela PARCELA.DESIGNACAO%TYPE,
    especie_vegetal ESPECIEVEGETAL.NOMECOMUM%TYPE,
    variedade_planta CULTURA.VARIEDADE%TYPE,
    data_realizacao OPERACAO.DATAREALIZACAO%TYPE,
    quantidade_semeadura SEMEADURA.QUANTIDADE%TYPE,
    area_semeadura SEMEADURA.AREA%TYPE
) IS
    -- Declaração de variaveis locais que serão usadas no corpo do procedimento
    operacao_id OPERACAO.OPERACAOID%type;
    cultivo_id CULTIVO.CULTIVOID%type;
    parcela_id PARCELA.PARCELAID%type;
    cultura_id CULTURA.CULTURAID%type;
    especie_vegetal_id ESPECIEVEGETAL.ESPECIEVEGETALID%type;
    area_parcela PARCELA.AREA%TYPE;

BEGIN -- Corpo da função, contém a parte executavel
-- Procura o ID da Parcela pelo nome
SELECT PARCELAID, AREA INTO parcela_id, area_parcela FROM Parcela
WHERE DESIGNACAO = nome_parcela;

IF area_semeadura > area_parcela THEN
    RAISE_APPLICATION_ERROR(-20001, 'Área da semeadura maior que a área da parcela.');
END IF;

-- Procura o ID da Especie Vegetal pelo nome
SELECT ESPECIEVEGETALID INTO especie_vegetal_id
FROM EspecieVegetal
WHERE NOMECOMUM = especie_vegetal;

-- Procura o ID da Cultura pelo nome
SELECT CULTURAID INTO cultura_id FROM Cultura
WHERE Variedade = variedade_planta AND EspecieVegetalID = especie_vegetal_id;

-- Procura o ID do Cultivo correspondente

SELECT COUNT(*) INTO cultivo_id
FROM Cultivo
WHERE ParcelaID = parcela_id AND data_realizacao BETWEEN DATAINICIO AND DATAFIM;

IF cultivo_id > 0 /*AND (data_realizacao > CURRENT_DATE)*/ -- Não funciona a data no futuro
THEN
    RAISE_APPLICATION_ERROR(-20001, 'Parcela com cultivos existentes');
ELSE
    SELECT NVL(MAX(CULTIVOID), 0) + 1 INTO cultivo_id FROM CULTIVO;

    INSERT INTO CULTIVO (cultivoid, culturaid, parcelaid, datainicio, datafim, quantidade, unidadeid, estadofenologicoid)
    VALUES (cultivo_id, cultura_id, parcela_id, data_realizacao, data_realizacao+120, quantidade_semeadura, 2, NULL);
END IF;
-- Gera um novo ID para a operação
SELECT MAX(OperacaoID) + 1 INTO operacao_id FROM OPERACAO;

-- Insere na tabela Operacao e obtem o ID gerado
INSERT INTO Operacao (OperacaoID, DataRealizacao, TipoOperacao)
VALUES (operacao_id, data_realizacao, 'Semeadura');
--   RETURNING OperacaoID INTO operacao_id;

-- Insere na tabela Semeadura com o ID da Operacao
INSERT INTO Semeadura (OperacaoID, Quantidade, Area, CultivoID)
VALUES (operacao_id, quantidade_semeadura, area_semeadura, cultivo_id);
DBMS_OUTPUT.PUT_LINE('Sucesso: Operação de Semeadura registada.');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001, 'Dados necessários não encontrados.');
    WHEN OTHERS THEN
        -- Em caso de outro erro, lança uma exceção
        RAISE;
END registar_operacao_semeadura;


-- Bloco de teste sucesso
DECLARE
    v_nome_parcela PARCELA.DESIGNACAO%type := 'Horta Nova'; -- Substituir pelo nome da parcela
    v_especie_vegetal ESPECIEVEGETAL.NOMECOMUM%type := 'Nabo greleiro'; -- Substituir pela especie vegetal
    v_variedade_planta CULTURA.VARIEDADE%type := 'Senhora Conceição'; -- Substituir pela variedade
    v_data_realizacao OPERACAO.DATAREALIZACAO%type := TO_DATE('20-09-2023', 'dd-mm-yyyy'); -- Substituir pela data
    v_quantidade_semeadura SEMEADURA.QUANTIDADE%TYPE := 0.9; -- Substituir pela quantidade semeada
    v_area_semeadura SEMEADURA.AREA%type := 0.3; -- Substituir pela area

BEGIN
    -- Chama o procedimento
    registar_operacao_semeadura(
            v_nome_parcela,
            v_especie_vegetal,
            v_variedade_planta,
            v_data_realizacao,
            v_quantidade_semeadura,
            v_area_semeadura
    );

    -- Se nenhuma excepção for levantada, imprime a seguinte mensagem:
    DBMS_OUTPUT.PUT_LINE('Procedimento executado correctamente!');
EXCEPTION
    WHEN OTHERS THEN
        -- Se existir excepções, imprime a seguinte mensagem::
        DBMS_OUTPUT.PUT_LINE('Erro: ' || SQLERRM);
END;


-- Bloco de teste insucesso
DECLARE
    v_nome_parcela PARCELA.DESIGNACAO%type := 'Campo Novo'; -- Substituir pelo nome da parcela
    v_especie_vegetal ESPECIEVEGETAL.NOMECOMUM%type := 'Nabo Greleiro'; -- Substituir pela especie vegetal
    v_variedade_planta CULTURA.VARIEDADE%type := 'Senhora Conceição'; -- Substituir pela variedade
    v_data_realizacao OPERACAO.DATAREALIZACAO%type := TO_DATE('19-09-2023', 'dd-mm-yyyy'); -- Substituir pela data
    v_quantidade_semeadura SEMEADURA.QUANTIDADE%TYPE := 1.8; -- Substituir pela quantidade semeada
    v_area_semeadura SEMEADURA.AREA%type := 0.75; -- Substituir pela area
BEGIN
    -- Call the procedure
    registar_operacao_semeadura(
            v_nome_parcela,
            v_especie_vegetal,
            v_variedade_planta,
            v_data_realizacao,
            v_quantidade_semeadura,
            v_area_semeadura
    );

    -- If no exception is raised, print success message
    DBMS_OUTPUT.PUT_LINE('Procedure executed successfully.');
EXCEPTION
    WHEN OTHERS THEN
        -- If an exception occurs, print the error message
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
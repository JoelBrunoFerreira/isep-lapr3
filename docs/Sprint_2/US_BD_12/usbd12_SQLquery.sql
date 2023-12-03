-- Registar uma operação de monda, USBD12
CREATE OR REPLACE PROCEDURE proc_USBD12 (
    -- Procedimento recebe os seguintes 5 parametros, cada um corresponde a colunas em tabelas especificas na BD
    nome_parcela PARCELA.DESIGNACAO%type,
    especie_vegetal ESPECIEVEGETAL.NOMECOMUM%type,
    variedade_planta CULTURA.VARIEDADE%type,
    data_realizacao  OPERACAO.DATAREALIZACAO%type,
    area_monda  MONDA.AREA%type
)
    -- Declaração de variaveis locais que serão usadas no corpo do procedimento
    IS
    operacao_id OPERACAO.OPERACAOID%type;
    cultivo_id CULTIVO.CULTIVOID%type;
    parcela_id PARCELA.PARCELAID%type;
    cultura_id CULTURA.CULTURAID%type;
    especie_vegetal_id ESPECIEVEGETAL.ESPECIEVEGETALID%type;
    fator_producao_id FATORPRODUCAO.FATORPRODUCAOID%type := null;
    modo_monda MONDA.MODO%type := '';

BEGIN
    -- Procura o ID da Parcela pelo nome
    SELECT PARCELAID INTO parcela_id
    FROM Parcela
    WHERE DESIGNACAO = nome_parcela;

-- Procura o ID da Especie Vegetal pelo nome
    SELECT ESPECIEVEGETALID INTO especie_vegetal_id
    FROM EspecieVegetal
    WHERE NOMECOMUM = especie_vegetal;

-- Procura o ID da Cultura pelo nome
    SELECT CULTURAID INTO cultura_id
    FROM Cultura
    WHERE Variedade = variedade_planta AND EspecieVegetalID = especie_vegetal_id;

-- Procura o ID do Cultivo correspondente
    SELECT CultivoID INTO cultivo_id
    FROM Cultivo
    WHERE ParcelaID = parcela_id AND CulturaID = cultura_id AND data_realizacao BETWEEN DATAINICIO AND DATAFIM;

-- Gera um novo ID para a operação
    SELECT MAX(OperacaoID)+1 INTO operacao_id FROM OPERACAO;

-- Insere na tabela Operacao
    INSERT INTO Operacao (OperacaoID, DataRealizacao, TipoOperacao)
    VALUES (operacao_id, data_realizacao, 'Monda');

-- Insere na tabela Monda com o ID de Operacao
    INSERT INTO Monda (OperacaoID, Modo, Area, CultivoID, FatorProducaoID)
    VALUES (operacao_id, modo_monda, area_monda, cultivo_id, fator_producao_id);
    DBMS_OUTPUT.PUT_LINE('Sucesso: Operação de monda registada.');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Insucesso: Dados necessários não encontrados.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Ocorreu um erro: ' || SQLERRM);
END proc_USBD12;


-- Bloco de teste sucesso
DECLARE
    v_nome_parcela PARCELA.DESIGNACAO%type := 'Campo Novo'; -- Replace with actual value
    v_especie_vegetal ESPECIEVEGETAL.NOMECOMUM%type := 'Cenoura'; -- Replace with actual value
    v_variedade_planta CULTURA.VARIEDADE%type := 'Danvers Half Long'; -- Replace with actual value
    v_data_realizacao OPERACAO.DATAREALIZACAO%type := TO_DATE('08-09-2023', 'dd-mm-yyyy'); -- Replace with actual value
    v_area_monda MONDA.AREA%type := 0.5; -- Replace with actual value
BEGIN
    -- Call the procedure
    proc_USBD12(
            v_nome_parcela,
            v_especie_vegetal,
            v_variedade_planta,
            v_data_realizacao,
            v_area_monda
    );

    -- If no exception is raised, print success message
    DBMS_OUTPUT.PUT_LINE('Procedure executed successfully.');
EXCEPTION
    WHEN OTHERS THEN
        -- If an exception occurs, print the error message
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;


-- Bloco de teste insucesso
DECLARE
    v_nome_parcela PARCELA.DESIGNACAO%type := 'Campo Novo'; -- Replace with actual value
    v_especie_vegetal ESPECIEVEGETAL.NOMECOMUM%type := 'Cenoura'; -- Replace with actual value
    v_variedade_planta CULTURA.VARIEDADE%type := 'Danvers Half Long'; -- Replace with actual value
    v_data_realizacao OPERACAO.DATAREALIZACAO%type := TO_DATE('11-10-2023', 'dd-mm-yyyy'); -- Replace with actual value
    v_area_monda MONDA.AREA%type := 0.5; -- Replace with actual value
BEGIN
    -- Call the procedure
    proc_USBD12(
            v_nome_parcela,
            v_especie_vegetal,
            v_variedade_planta,
            v_data_realizacao,
            v_area_monda
    );

    -- If no exception is raised, print success message
    DBMS_OUTPUT.PUT_LINE('Procedure executed successfully.');
EXCEPTION
    WHEN OTHERS THEN
        -- If an exception occurs, print the error message
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
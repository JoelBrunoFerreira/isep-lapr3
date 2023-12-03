-- USBD14 - Como Gestor Agrıcola, quero registar uma operacao de aplicacao de fator de producao

CREATE OR REPLACE PROCEDURE registar_operacao_aplicacao_fator_producao (
    -- Procedimento recebe os seguintes 7 parametros, cada um corresponde a colunas em tabelas especificas na BD
    nome_parcela PARCELA.DESIGNACAO%TYPE,
    especie_vegetal ESPECIEVEGETAL.NOMECOMUM%TYPE,
    c_variedade CULTURA.VARIEDADE%TYPE,
    data_realizacao OPERACAO.DATAREALIZACAO%TYPE,
    quantidade_fator_producao APLICACAOFATORPRODUCAO.QUANTIDADEFATORPRODUCAO%TYPE,
    area_aplicacao APLICACAOFATORPRODUCAO.AREA%TYPE,
    nome_comercial FATORPRODUCAO.NOMECOMERCIAL%TYPE
) AS
    -- Declaração de variaveis locais que serão usadas no corpo do procedimento
    operacao_id OPERACAO.OPERACAOID%type;
    cultivo_id CULTIVO.CULTIVOID%type;
    parcela_id PARCELA.PARCELAID%type;
    cultura_id CULTURA.CULTURAID%type;
    especie_vegetal_id ESPECIEVEGETAL.ESPECIEVEGETALID%type;
    fator_producao_id FATORPRODUCAO.FATORPRODUCAOID%TYPE;
    area_parcela PARCELA.AREA%TYPE;

BEGIN
    -- Procura o ID da Parcela pelo nome
    SELECT ParcelaID, Area INTO parcela_id, area_parcela
    FROM Parcela
    WHERE Designacao = nome_parcela;
    DBMS_OUTPUT.PUT_LINE('Parcela ID encontrado: ' || parcela_id);

    IF area_aplicacao > area_parcela THEN
        RAISE_APPLICATION_ERROR(-20001, 'Erro: Área de aplicação maior que a área da parcela.');
    END IF;

    IF c_variedade IS NOT NULL THEN
        -- Procura o ID da Especie Vegetal pelo nome
        SELECT EspecieVegetalID INTO especie_vegetal_id
        FROM EspecieVegetal
        WHERE NomeComum = especie_vegetal;
        DBMS_OUTPUT.PUT_LINE('Especie ID encontrada: ' || especie_vegetal_id);

        -- Procura o ID da Cultura pelo nome
        SELECT CulturaID INTO cultura_id
        FROM Cultura
        WHERE Variedade = c_variedade AND EspecieVegetalID = especie_vegetal_id;
        DBMS_OUTPUT.PUT_LINE('Cultura ID encontrada: ' || cultura_id);

        -- Procura o ID do Cultivo correspondente
        SELECT CultivoID INTO cultivo_id
        FROM Cultivo
        WHERE ParcelaID = parcela_id AND CulturaID = cultura_id;
        DBMS_OUTPUT.PUT_LINE('Cultivo ID encontrada: ' || cultivo_id);
    ELSE
        -- Procura o ID do Cultivo correspondente
        SELECT CultivoID INTO cultivo_id
        FROM Cultivo
        WHERE ParcelaID = parcela_id AND CulturaID IS NULL;
        DBMS_OUTPUT.PUT_LINE('Cultivo ID encontrado: ' || cultivo_id);
    END IF;

    -- Procura o ID do Produto
    SELECT FatorProducaoID INTO fator_producao_id
    FROM FatorProducao
    WHERE NomeComercial = nome_comercial;

    -- Gera um novo ID para a operação
    SELECT MAX(OperacaoID) + 1 INTO operacao_id FROM OPERACAO;

    -- Insere na tabela Operacao
    INSERT INTO Operacao (OperacaoID, DataRealizacao, TipoOperacao)
    VALUES (operacao_id, data_realizacao, 'Aplicação Fator Produção');

    -- Insere na tabela Colheita com o ID da Operacao
    INSERT INTO AplicacaoFatorProducao (OperacaoID, QuantidadeFatorProducao, Area, FatorProducaoID, CultivoID)
    VALUES (operacao_id, quantidade_fator_producao, area_aplicacao, fator_producao_id, cultivo_id);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001, 'Dados necessários não encontrados.');
    WHEN OTHERS THEN
        -- Em caso de outro erro, lança uma exceção
        RAISE;
END registar_operacao_aplicacao_fator_producao;

-- Caso de sucesso: Registar uma operação de aplicação de fator de produção no Campo Novo, em 06/10/2023, de Fertimax Extrume de Cavalo, no solo, 1.1 ha, 4000 kg

DECLARE
    nome_parcela PARCELA.DESIGNACAO%type := 'Campo Novo'; -- Replace with actual value
    especie_vegetal ESPECIEVEGETAL.NOMECOMUM%type := null;
    c_variedade CULTURA.VARIEDADE%type := null;
    data_realizacao OPERACAO.DATAREALIZACAO%type := TO_DATE('08-09-2023', 'dd-mm-yyyy'); -- Replace with actual value
    quantidade_fator_producao APLICACAOFATORPRODUCAO.QUANTIDADEFATORPRODUCAO%type := 4000; -- Replace with actual value
    area_aplicacao APLICACAOFATORPRODUCAO.AREA%type := 1.1;
    nome_comercial FATORPRODUCAO.NOMECOMERCIAL%type := 'Fertimax Extrume de Cavalo'; -- Replace with actual value
BEGIN
    -- Call the procedure
    registar_operacao_aplicacao_fator_producao(
            nome_parcela,
            especie_vegetal,
            c_variedade,
            data_realizacao,
            quantidade_fator_producao,
            area_aplicacao,
            nome_comercial
    );

    -- If no exception is raised, print success message
    DBMS_OUTPUT.PUT_LINE('Procedure executed successfully.');
EXCEPTION
    WHEN OTHERS THEN
        -- If an exception occurs, print the error message
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;


-- Caso de insucesso: Registar uma operação de aplicação de fator de produção no Campo Novo, em 08/10/2023, de Fertimax Extrume de Cavalo, no solo, 2.1 ha, 8000 kg

DECLARE
    nome_parcela PARCELA.DESIGNACAO%type := 'Campo Novo'; -- Replace with actual value
    especie_vegetal ESPECIEVEGETAL.NOMECOMUM%type := null;
    c_variedade CULTURA.VARIEDADE%type := null;
    data_realizacao OPERACAO.DATAREALIZACAO%type := TO_DATE('08-10-2023', 'dd-mm-yyyy'); -- Replace with actual value
    quantidade_fator_producao APLICACAOFATORPRODUCAO.QUANTIDADEFATORPRODUCAO%type := 8000; -- Replace with actual value
    area_aplicacao APLICACAOFATORPRODUCAO.AREA%type := 2.1;
    nome_comercial FATORPRODUCAO.NOMECOMERCIAL%type := 'Fertimax Extrume de Cavalo'; -- Replace with actual value
BEGIN
    -- Call the procedure
    registar_operacao_aplicacao_fator_producao(
            nome_parcela,
            especie_vegetal,
            c_variedade,
            data_realizacao,
            quantidade_fator_producao,
            area_aplicacao,
            nome_comercial
    );

    -- If no exception is raised, print success message
    DBMS_OUTPUT.PUT_LINE('Procedure executed successfully.');
EXCEPTION
    WHEN OTHERS THEN
        -- If an exception occurs, print the error message
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;

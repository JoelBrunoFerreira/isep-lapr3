-- Função para criar uma operação de monda, USBD12:
CREATE OR REPLACE FUNCTION fnc_USBD12 (
    nome_parcela IN PARCELA.DESIGNACAO%type,
    especie_vegetal IN ESPECIEVEGETAL.NOMECOMUM%type,
    variedade_planta IN CULTURA.VARIEDADE%type,
    data_realizacao IN OPERACAO.DATAREALIZACAO%type,
    area_monda IN MONDA.AREA%type
) RETURN NUMBER
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

RETURN 1;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0; -- Return 0 for failure
WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Ocorreu um erro: ' || SQLERRM);
RETURN 0; -- Return 0 for failure
END fnc_USBD12;

-- Bloco de teste para sucesso
DECLARE
result NUMBER;
    designacao_parcela PARCELA.Designacao%type := 'Campo Novo';
    data_realizacao OPERACAO.DATAREALIZACAO%type := TO_DATE('08-09-2023','dd-mm-yyyy');
    especie_vegetal ESPECIEVEGETAL.NOMECOMUM%type := 'Cenoura';
    variedade_planta CULTURA.VARIEDADE%type := 'Danvers Half Long';
    area_monda MONDA.AREA%type := 0.5;
BEGIN
    -- Call the function with sample values
    result := fnc_USBD12(designacao_parcela,especie_vegetal,variedade_planta,data_realizacao,area_monda);

    -- Check the result and print appropriate message
    IF result = 1 THEN
        DBMS_OUTPUT.PUT_LINE('Sucesso');
ELSE
        DBMS_OUTPUT.PUT_LINE('Insucesso');
END IF;
END;

-- Bloco de teste para insucesso
DECLARE
result NUMBER;
    designacao_parcela PARCELA.Designacao%type := 'Campo Novo';
    data_realizacao OPERACAO.DATAREALIZACAO%type := TO_DATE('11-10-2023','dd-mm-yyyy');
    especie_vegetal ESPECIEVEGETAL.NOMECOMUM%type := 'Cenoura';
    variedade_planta CULTURA.VARIEDADE%type := 'Danvers Half Long';
    area_monda MONDA.AREA%type := 0.5;
BEGIN
    -- Call the function with sample values
    result := fnc_USBD12(designacao_parcela,especie_vegetal,variedade_planta,data_realizacao,area_monda);

    -- Check the result and print appropriate message
    IF result = 1 THEN
        DBMS_OUTPUT.PUT_LINE('Sucesso');
ELSE
        DBMS_OUTPUT.PUT_LINE('Insucesso');
END IF;
END;
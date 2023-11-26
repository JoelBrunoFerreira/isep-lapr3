-- USBD14 - Como Gestor Agrıcola, quero registar uma operacao de aplicacao de fator de producao

CREATE OR REPLACE PROCEDURE registar_operacao_aplicacao_fator_producao (
    nome_parcela IN NVARCHAR2,
    especie_vegetal IN NVARCHAR2,
    c_variedade IN NVARCHAR2,
    data_realizacao IN DATE,
    quantidade_fator_producao IN NUMBER,
    area_aplicacao in Number,
    nome_comercial IN NVARCHAR2
) AS
    operacao_id NUMBER;
    cultivo_id NUMBER;
    parcela_id NUMBER;
    cultura_id NUMBER;
    especie_vegetal_id NUMBER;
    fator_producao_id NUMBER;
BEGIN
    -- Procura o ID da Parcela pelo nome
SELECT ParcelaID INTO parcela_id
FROM Parcela
WHERE Designacao = nome_parcela;

IF c_variedade IS NOT NULL THEN
        -- Procura o ID da Especie Vegetal pelo nome
SELECT EspecieVegetalID INTO especie_vegetal_id
FROM EspecieVegetal
WHERE EspecieVegetalID = especie_vegetal;

-- Procura o ID da Cultura pelo nome
SELECT CulturaID INTO cultura_id
FROM Cultura
WHERE Variedade = c_variedade AND EspecieVegetalID = especie_vegetal_id;
ELSE
        cultura_id := 0;
END IF;

    -- Procura o ID do Cultivo correspondente
SELECT CultivoID INTO cultivo_id
FROM Cultivo
WHERE ParcelaID = parcela_id AND CulturaID = cultura_id;

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

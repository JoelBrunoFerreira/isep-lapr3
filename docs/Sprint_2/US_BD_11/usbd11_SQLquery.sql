-- USBD11 - Como Gestor Agrıcola, quero registar uma operacao de semeadura

CREATE OR REPLACE PROCEDURE registar_operacao_semeadura (
    nome_parcela IN NVARCHAR2,
    especie_vegetal IN NVARCHAR2,
    c_variedade IN NVARCHAR2,
    data_realizacao IN DATE,
    quantidade_semeadura IN NUMBER,
    area_semeadura IN NUMBER
) AS
    operacao_id NUMBER;
    cultivo_id NUMBER;
    parcela_id NUMBER;
    cultura_id NUMBER;
    especie_vegetal_id NUMBER;
BEGIN
    -- Procura o ID da Parcela pelo nome
SELECT ParcelaID INTO parcela_id
FROM Parcela
WHERE Designacao = nome_parcela;

-- Procura o ID da Especie Vegetal pelo nome
SELECT EspecieVegetalID INTO especie_vegetal_id
FROM EspecieVegetal
WHERE EspecieVegetalID = especie_vegetal;

-- Procura o ID da Cultura pelo nome
SELECT CulturaID INTO cultura_id
FROM Cultura
WHERE Variedade = c_variedade AND EspecieVegetalID = especie_vegetal_id;

-- Procura o ID do Cultivo correspondente
SELECT CultivoID INTO cultivo_id
FROM Cultivo
WHERE ParcelaID = parcela_id AND CulturaID = cultura_id;

-- Gera um novo ID para a operação
SELECT MAX(OperacaoID) + 1 INTO operacao_id FROM OPERACAO;

-- Insere na tabela Operacao e obtem o ID gerado
INSERT INTO Operacao (OperacaoID, DataRealizacao, TipoOperacao)
VALUES (operacao_id, data_realizacao, 'Semeadura')
    RETURNING OperacaoID INTO operacao_id;

-- Insere na tabela Semeadura com o ID da Operacao
INSERT INTO Semeadura (OperacaoID, Quantidade, Area, CultivoID)
VALUES (operacao_id, quantidade_semeadura, area_semeadura, cultivo_id);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001, 'Dados necessários não encontrados.');
WHEN OTHERS THEN
        -- Em caso de outro erro, lança uma exceção
        RAISE;
END registar_operacao_semeadura;

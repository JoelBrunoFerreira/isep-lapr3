-- USBD13 - Como Gestor Agrıcola, quero registar uma operacao de colheita

CREATE OR REPLACE PROCEDURE registar_operacao_colheita (
    nome_parcela IN NVARCHAR2,
    data_realizacao IN DATE,
    quantidade_colhida IN NUMBER,
    nome_produto IN NVARCHAR2
) AS
    operacao_id NUMBER;
    cultivo_id NUMBER;
    parcela_id NUMBER;
    cultura_id NUMBER;
    produto_id NUMBER;
BEGIN
    -- Procura o ID da Parcela pelo nome
SELECT ParcelaID INTO parcela_id
FROM Parcela
WHERE Designacao = nome_parcela;

-- Procura o ID do Produto
SELECT ProdutoID, CULTURAID INTO produto_id, cultura_id
FROM Produto
WHERE DesignacaoProduto = nome_produto;

-- Procura o ID do Cultivo correspondente
SELECT CultivoID INTO cultivo_id
FROM Cultivo
WHERE ParcelaID = parcela_id AND CulturaID = cultura_id;


-- Gera um novo ID para a operação
SELECT MAX(OperacaoID) + 1 INTO operacao_id FROM OPERACAO;

-- Insere na tabela Operacao
INSERT INTO Operacao (OperacaoID, DataRealizacao, TipoOperacao)
VALUES (operacao_id, data_realizacao, 'Colheita');

-- Insere na tabela Colheita com o ID da Operacao
INSERT INTO Colheita (OperacaoID, QuantidadeProduto, ProdutoID, CultivoID)
VALUES (operacao_id, quantidade_colhida, produto_id, cultivo_id);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001, 'Dados necessários não encontrados.');
WHEN OTHERS THEN
        -- Em caso de outro erro, lança uma exceção
        RAISE;
END registar_operacao_colheita;
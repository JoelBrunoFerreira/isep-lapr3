-- USBD13 - Como Gestor Agrıcola, quero registar uma operacao de colheita

CREATE OR REPLACE PROCEDURE registar_operacao_colheita (
    -- Procedimento recebe os seguintes 4 parametros, cada um corresponde a colunas em tabelas especificas na BD
    nome_parcela PARCELA.DESIGNACAO%TYPE,
    data_realizacao OPERACAO.DATAREALIZACAO%type,
    quantidade_colhida COLHEITA.QUANTIDADEPRODUTO%TYPE,
    nome_produto PRODUTO.DESIGNACAOPRODUTO%TYPE
) IS
    operacao_id OPERACAO.OPERACAOID%type;
    cultivo_id CULTIVO.CULTIVOID%type;
    parcela_id PARCELA.PARCELAID%type;
    cultura_id CULTURA.CULTURAID%type;
    produto_id PRODUTO.PRODUTOID%TYPE;
BEGIN
    -- Procura o ID da Parcela pelo nome
    SELECT ParcelaID INTO parcela_id FROM Parcela
        WHERE Designacao = nome_parcela;

    -- Procura o ID do Produto
    SELECT ProdutoID, CULTURAID INTO produto_id, cultura_id FROM Produto
        WHERE DesignacaoProduto = nome_produto;

    -- Procura o ID do Cultivo correspondente
    SELECT CultivoID INTO cultivo_id FROM Cultivo
        WHERE ParcelaID = parcela_id AND CulturaID = cultura_id;

    -- Gera um novo ID para a operação
    SELECT MAX(OperacaoID) + 1 INTO operacao_id FROM OPERACAO;

    -- Insere na tabela Operacao
    INSERT INTO Operacao (OperacaoID, DataRealizacao, TipoOperacao)
    VALUES (operacao_id, data_realizacao, 'Colheita');

    -- Insere na tabela Colheita com o ID da Operacao
    INSERT INTO Colheita (OperacaoID, QuantidadeProduto, ProdutoID, CultivoID)
    VALUES (operacao_id, quantidade_colhida, produto_id, cultivo_id);
    DBMS_OUTPUT.PUT_LINE('Sucesso: Operação de colheita registada.');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001, 'Dados necessários não encontrados.');
    WHEN OTHERS THEN
        -- Em caso de outro erro, lança uma exceção
        RAISE;
END registar_operacao_colheita;


-- Caso de sucesso: Registar uma operação de colheita no Campo Grande, em 05/11/2023, de azeitona Galega, 100 kg

DECLARE
    nome_parcela PARCELA.DESIGNACAO%type := 'Campo Grande'; -- Substituir pelo nome da parcela
    data_realizacao OPERACAO.DATAREALIZACAO%type := TO_DATE('08-09-2023', 'dd-mm-yyyy'); -- Substituir pela data
    quantidade_colhida COLHEITA.QUANTIDADEPRODUTO%type := 100; -- Substituir pela quantidade colhida
    nome_produto PRODUTO.DESIGNACAOPRODUTO%type := 'Azeitona Galega'; -- Substituir pela designação do produto
BEGIN
    -- Call the procedure
    registar_operacao_colheita(
            nome_parcela,
            data_realizacao,
            quantidade_colhida,
            nome_produto
    );

    -- If no exception is raised, print success message
    DBMS_OUTPUT.PUT_LINE('Procedure executed successfully.');
EXCEPTION
    WHEN OTHERS THEN
        -- If an exception occurs, print the error message
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;

-- Caso de insucesso: Registar uma operação de colheita no Campo Grande, em 05/10/2023, de Maçã Golden, 800 kg

DECLARE
    nome_parcela PARCELA.DESIGNACAO%type := 'Campo Grande'; -- Replace with actual value
    data_realizacao OPERACAO.DATAREALIZACAO%type := TO_DATE('05-10-2023', 'dd-mm-yyyy'); -- Replace with actual value
    quantidade_colhida COLHEITA.QUANTIDADEPRODUTO%type := 800; -- Replace with actual value
    nome_produto PRODUTO.DESIGNACAOPRODUTO%type := 'Maçã Golden Delicious'; -- Replace with actual value
BEGIN
    -- Call the procedure
    registar_operacao_colheita(
            nome_parcela,
            data_realizacao,
            quantidade_colhida,
            nome_produto
    );

    -- If no exception is raised, print success message
    DBMS_OUTPUT.PUT_LINE('Procedure executed successfully.');
EXCEPTION
    WHEN OTHERS THEN
        -- If an exception occurs, print the error message
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
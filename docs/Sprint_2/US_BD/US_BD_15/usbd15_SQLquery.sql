-- USBD15 - Como Gestor Agrıcola, quero registar uma operacao de poda

CREATE OR REPLACE PROCEDURE registar_operacao_poda (
    nome_parcela PARCELA.DESIGNACAO%TYPE,
    especie_vegetal ESPECIEVEGETAL.NOMECOMUM%TYPE,
    c_variedade CULTURA.VARIEDADE%TYPE,
    data_realizacao OPERACAO.DATAREALIZACAO%TYPE,
    quantidade_poda PODA.QUANTIDADE%TYPE
) AS
    operacao_id OPERACAO.OPERACAOID%type;
    cultivo_id CULTIVO.CULTIVOID%type;
    parcela_id PARCELA.PARCELAID%type;
    cultura_id CULTURA.CULTURAID%type;
    especie_vegetal_id ESPECIEVEGETAL.ESPECIEVEGETALID%type;
    cultivo_quantidade CULTIVO.QUANTIDADE%type;
BEGIN

    IF data_realizacao > CURRENT_DATE THEN
        RAISE_APPLICATION_ERROR(-20001, 'Data inserida é superior à atual, não se pode efetuar operações no futuro.');
    END IF;

    -- Procura o ID da Parcela pelo nome
    SELECT ParcelaID INTO parcela_id
    FROM Parcela
    WHERE Designacao = nome_parcela;

    -- Procura o ID da Especie Vegetal pelo nome
    SELECT EspecieVegetalID INTO especie_vegetal_id
    FROM EspecieVegetal
    WHERE NOMECOMUM = especie_vegetal;


    -- Procura o ID da Cultura pelo nome
    SELECT CulturaID INTO cultura_id
    FROM Cultura
    WHERE Variedade = c_variedade AND EspecieVegetalID = especie_vegetal_id;

    -- Procura o ID do Cultivo correspondente
    SELECT CultivoID, CULTIVO.QUANTIDADE INTO cultivo_id, cultivo_quantidade
    FROM Cultivo
    WHERE ParcelaID = parcela_id AND CulturaID = cultura_id AND data_realizacao BETWEEN DATAINICIO AND CURRENT_DATE;

    IF quantidade_poda > cultivo_quantidade THEN
        RAISE_APPLICATION_ERROR(-20001, 'Quantidade da poda maior que a quantidade do cultivo.');
    END IF;

    -- Gera um novo ID para a operação
    SELECT MAX(OperacaoID) + 1 INTO operacao_id FROM OPERACAO;

    -- Insere na tabela Operacao
    INSERT INTO Operacao (OperacaoID, DataRealizacao, TipoOperacao)
    VALUES (operacao_id, data_realizacao, 'Poda');

    -- Inserindo na tabela Poda
    INSERT INTO Poda (OperacaoID, Quantidade, CultivoID)
    VALUES (operacao_id, quantidade_poda, cultivo_id);
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR(-20001,'Insucesso: Dados necessários não encontrados.');
    WHEN OTHERS THEN
        RAISE_APPLICATION_ERROR(-20001,'Ocorreu um erro: ' || SQLERRM);
END registar_operacao_poda;
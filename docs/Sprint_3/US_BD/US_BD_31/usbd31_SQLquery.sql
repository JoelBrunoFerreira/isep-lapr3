-- USBD31 Como Gestor Agrıcola, pretendo registar uma receita de fertirrega para usar em operacoes de rega.
CREATE OR REPLACE TYPE Lista AS VARRAY(200) OF VARCHAR2(50);

CREATE OR REPLACE PROCEDURE registar_receita_fertirrega (
    receita_id IN Receita.ReceitaID%type,
    nomes_comerciais IN Lista,
    quantidades IN Lista,
    unidades IN Lista
) AS
    fator_producao_id  FatorProducao.FatorProducaoID%type;
    unidade_id Unidade.UnidadeID%type;
    e_fator_producao_not_found EXCEPTION;
    e_unidade_not_found EXCEPTION;
    PRAGMA EXCEPTION_INIT(e_fator_producao_not_found, -20003);
    PRAGMA EXCEPTION_INIT(e_unidade_not_found, -20004);
BEGIN
    -- Inicia uma transação
    SAVEPOINT transaction_start;

    -- Insere na tabela Receita
    INSERT INTO Receita (ReceitaID)
    VALUES (receita_id);

    -- Verifica se as listas têm o mesmo tamanho
    IF nomes_comerciais.COUNT <> quantidades.COUNT OR nomes_comerciais.COUNT <> unidades.COUNT THEN
        RAISE_APPLICATION_ERROR(-20002, 'As listas de nomes comerciais, quantidades e unidades não correspondem.');
    END IF;

        -- Itera sobre as listas de nomes comerciais e quantidades
    FOR i IN 1..nomes_comerciais.COUNT LOOP
        BEGIN
            fator_producao_id := NULL;
            unidade_id := NULL;

            -- Procura o ID do Produto
            SELECT FatorProducaoID INTO fator_producao_id
            FROM FatorProducao
            WHERE NomeComercial = nomes_comerciais(i);

            -- Procura o ID da Unidade
            SELECT UnidadeID INTO unidade_id
            FROM Unidade
            WHERE DescricaoUnidade = unidades(i);

            -- Insere na tabela Receita_FatorProducao com o ID da Receita
            INSERT INTO Receita_FatorProducao (ReceitaID, FatorProducaoID, Quantidade, UnidadeID)
            VALUES (receita_id, fator_producao_id, TO_NUMBER(quantidades(i)), unidade_id);
        EXCEPTION
            WHEN NO_DATA_FOUND THEN
                -- Se não encontrar o fator de produção ou a unidade
                IF fator_producao_id IS NULL THEN
                    RAISE e_fator_producao_not_found;
                ELSIF unidade_id IS NULL THEN
                    RAISE e_unidade_not_found;
                END IF;
        END;
    END LOOP;

    -- Se correr bem, commit das alterações
    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Sucesso: Receita registada.');

EXCEPTION
    WHEN e_fator_producao_not_found THEN
        ROLLBACK TO transaction_start;
        RAISE_APPLICATION_ERROR(-20003, 'Fator de produção não encontrado.');
    WHEN e_unidade_not_found THEN
        ROLLBACK TO transaction_start;
        RAISE_APPLICATION_ERROR(-20004, 'Unidade não encontrada.');
    WHEN NO_DATA_FOUND THEN
        ROLLBACK TO transaction_start;
        RAISE_APPLICATION_ERROR(-20001, 'Dados necessários não encontrados.');
    WHEN OTHERS THEN
        ROLLBACK TO transaction_start;
        RAISE;
END registar_receita_fertirrega;

-- CASO DE SUCESSO:

-- <Caso de sucesso>
-- Inserir Receita 22
-- Tecniferti MOL, Tecniferti, 60 l/ha
-- Kiplant AllGrip, Asfertglobal, 2 l/ha
-- soluSOP 52, K+S, 2.5 kg/ha

-- Fazer pesquisa para mostrar que a receita ficou registada, incluindo os 3 componentes.
-- <\Caso de sucesso>

DECLARE
    v_nomes_comerciais Lista := Lista('Tecniferti MOL', 'Kiplant AllGrip', 'SoluSOP 52');
    v_quantidades Lista := Lista('60', '2', '2,5');
    v_designacao_unidade Lista := Lista('l/ha', 'l/ha', 'kg/ha');
BEGIN
    registar_receita_fertirrega(22, v_nomes_comerciais, v_quantidades, v_designacao_unidade);

    FOR rec IN (SELECT Receita.ReceitaID, FatorProducao.NomeComercial, Receita_FatorProducao.Quantidade, Unidade.DescricaoUnidade
                FROM Receita
                JOIN Receita_FatorProducao ON Receita.ReceitaID = Receita_FatorProducao.ReceitaID
                JOIN FatorProducao ON Receita_FatorProducao.FatorProducaoID = FatorProducao.FatorProducaoID
                JOIN Unidade ON Receita_FatorProducao.UnidadeID = Unidade.UnidadeID
                WHERE Receita.ReceitaID = 22)
    LOOP
        DBMS_OUTPUT.PUT_LINE('Receita ID: ' || rec.ReceitaID || ', Nome Comercial: ' || rec.NomeComercial || ', Quantidade: ' || rec.Quantidade || ', Unidade: ' || rec.DescricaoUnidade);
    END LOOP;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erro: ' || SQLERRM);
END;


-- CASO INSUCESSO:

-- <Caso de insucesso>
-- Inserir Receita 23
-- Tecniferti MOL, Tecniferti, 60 l/ha
-- Kiplant AllFit Plus, Asfertglobal, 2.5 l/ha
--
-- Deve dar erro por não existir um dos componentes registado no sistema.
-- <\Caso de insucesso>

DECLARE
    v_nomes_comerciais Lista := Lista('Tecniferti MOL', 'Kiplant AllFit Plus');
    v_quantidades Lista := Lista('60', '2,5');
    v_designacao_unidade Lista := Lista('l/ha', 'l/ha');
BEGIN
    registar_receita_fertirrega(25, v_nomes_comerciais, v_quantidades, v_designacao_unidade);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erro: ' || SQLERRM);
END;





CREATE OR REPLACE FUNCTION obter_aplicacoes_fator_producao(data_inicio DATE, data_fim DATE, designacao_parcela NVARCHAR2)
    RETURN SYS_REFCURSOR
    IS
    aplicacoes_fator_producao SYS_REFCURSOR;
BEGIN
OPEN aplicacoes_fator_producao FOR
SELECT TipoFatorProducao.DescricaoTipoFatorProducao,
       Operacao.DataRealizacao,
       FatorProducao.NomeComercial,
       Cultivo.ParcelaID,
       COALESCE(EspecieVegetal.NomeComum || ' ' || Cultura.Variedade, NULL) AS Cultura
FROM AplicacaoFatorProducao
         INNER JOIN FatorProducao ON AplicacaoFatorProducao.FatorProducaoID = FatorProducao.FatorProducaoID
         INNER JOIN TipoFatorProducao
                    ON FatorProducao.TipoFatorProducaoID = TipoFatorProducao.TipoFatorProducaoID
         INNER JOIN Operacao ON AplicacaoFatorProducao.OperacaoID = Operacao.OperacaoID
         INNER JOIN Cultivo ON Cultivo.CultivoID = AplicacaoFatorProducao.CultivoID
         INNER JOIN Parcela ON Parcela.ParcelaID = Cultivo.ParcelaID
         LEFT JOIN Cultura ON Cultura.CulturaID = Cultivo.CulturaID
         LEFT JOIN EspecieVegetal ON EspecieVegetal.EspecieVegetalID = Cultura.EspecieVegetalID
WHERE Operacao.DataRealizacao BETWEEN data_inicio AND data_fim
  AND Parcela.Designacao = designacao_parcela
ORDER BY Cultivo.ParcelaID, TipoFatorProducao.DescricaoTipoFatorProducao, Operacao.DataRealizacao;
RETURN aplicacoes_fator_producao;
END obter_aplicacoes_fator_producao;


DECLARE
    -- Declaração de parâmetros de input
    data_inicio_param        DATE := TO_DATE('01-01-2019', 'dd-mm-yyyy');
    data_fim_param           DATE := TO_DATE('06-07-2023', 'dd-mm-yyyy');
    designacao_parcela_param NVARCHAR2(100) := 'Lameiro do Moinho';

    -- Declaração de variáveis para obter dados do cursor
    descricao_tipo_fator     VARCHAR2(100);
    data_realizacao          DATE;
    nome_comercial           VARCHAR2(100);
    parcela_id               NUMBER;
    cultura                  VARCHAR2(100);

    -- Declara o cursor
    result_cursor            SYS_REFCURSOR;
BEGIN
    -- chamar a função e armazenar o cursor na sua variável
    result_cursor := obter_aplicacoes_fator_producao(data_inicio_param, data_fim_param, designacao_parcela_param);

    -- Imprimir os parâmetros para verificação

    DBMS_OUTPUT.PUT_LINE('Parcela = ' || designacao_parcela_param);

    -- Ciclo sobre o cursor para obter os dados
    LOOP
        FETCH result_cursor INTO descricao_tipo_fator, data_realizacao, nome_comercial, parcela_id, cultura;

        -- Sai do ciclo se não encontrar mais dados
        EXIT WHEN result_cursor%NOTFOUND;

        -- Imprime os dados obtidos
        DBMS_OUTPUT.PUT_LINE('Tipo Fator Producao: ' || descricao_tipo_fator ||
                             ', Data Realizacao: ' || TO_CHAR(data_realizacao, 'dd-mm-yyyy') ||
                             ', Nome Comercial: ' || nome_comercial ||
                             ', Cultura: ' || NVL(cultura, 'Sem cultura'));
    END LOOP;

    -- Fecha o cursor
    CLOSE result_cursor;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Sem dados para mostrar.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Ocorreu um erro: ' || SQLERRM);
END;
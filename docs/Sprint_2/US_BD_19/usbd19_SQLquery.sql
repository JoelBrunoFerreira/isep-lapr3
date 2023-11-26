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



CREATE OR REPLACE PROCEDURE imprimir_aplicacoes_fator_producao(data_inicio DATE, data_fim DATE, designacao_parcela NVARCHAR2)
AS
    cursor_aplicacoes   SYS_REFCURSOR;
    v_TipoFatorProducao NVARCHAR2(100);
    v_DataRealizacao    DATE;
    v_NomeComercial     NVARCHAR2(100);
    v_ParcelaID         INT;
    v_Cultura           NVARCHAR2(200);
BEGIN
    -- Chama a função e obtém o cursor
    cursor_aplicacoes := obter_aplicacoes_fator_producao(data_inicio, data_fim, designacao_parcela);

    -- Loop para percorrer cada linha do cursor
    LOOP
FETCH cursor_aplicacoes INTO v_TipoFatorProducao, v_DataRealizacao, v_NomeComercial, v_ParcelaID, v_Cultura;
        EXIT WHEN cursor_aplicacoes%NOTFOUND;

        -- Imprime os resultados
        DBMS_OUTPUT.PUT_LINE('Tipo Fator Producao: ' || v_TipoFatorProducao ||
                             ', Data Realizacao: ' || TO_CHAR(v_DataRealizacao, 'DD/MM/YYYY') ||
                             ', Nome Comercial: ' || v_NomeComercial ||
                             ', Parcela ID: ' || v_ParcelaID ||
                             ', Cultura: ' || v_Cultura);
END LOOP;

    -- Fecha o cursor
CLOSE cursor_aplicacoes;
EXCEPTION
    WHEN OTHERS THEN
        -- Em caso de erro, imprime a mensagem de erro e fecha o cursor
        DBMS_OUTPUT.PUT_LINE('Erro: ' || SQLERRM);
        IF cursor_aplicacoes%ISOPEN THEN
            CLOSE cursor_aplicacoes;
END IF;
END imprimir_aplicacoes_fator_producao;


BEGIN
    imprimir_aplicacoes_fator_producao(TO_DATE('2019-01-01', 'YYYY-MM-DD'),
                                       TO_DATE('2023-07-06', 'YYYY-MM-DD'),
                                       'Lameiro do Moinho');
END;
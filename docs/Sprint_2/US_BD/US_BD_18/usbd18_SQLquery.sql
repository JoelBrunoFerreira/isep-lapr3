-- USBD18 - Como Gestor Agrícola, pretendo obter a lista de operaç˜oes realizadas numa dada parcela, para cada tipo de operaç˜ao, num dado intervalo de tempo.

CREATE OR REPLACE FUNCTION obter_operacoes (
    nome_parcela IN VARCHAR2,
    data_inicio IN DATE,
    data_fim IN DATE
) RETURN SYS_REFCURSOR AS
    operacoes SYS_REFCURSOR;
    parcela_id NUMBER;
BEGIN
    -- Busca o ID da Parcela pela designação
    SELECT ParcelaID INTO parcela_id FROM Parcela WHERE Designacao = nome_parcela;

    OPEN operacoes FOR
        SELECT
            Operacao.TipoOperacao,
            Operacao.DataRealizacao,
            EspecieVegetal.NomeComum || ' ' || Cultura.Variedade AS Cultura,
            -- Detalhes específicos de cada operação
            CASE WHEN Operacao.TipoOperacao = 'Plantação' THEN Plantacao.Quantidade END AS QuantidadePlantada,
            CASE WHEN Operacao.TipoOperacao = 'Semeadura' THEN Semeadura.Quantidade END AS QuantidadeSemeada,
            CASE WHEN Operacao.TipoOperacao = 'Semeadura' THEN Semeadura.Area END AS AreaSemeada,
            CASE WHEN Operacao.TipoOperacao = 'Poda' THEN Poda.Quantidade END AS QuantidadePodada,
            CASE WHEN Operacao.TipoOperacao = 'Rega' THEN Rega.Hora END AS HoraRega,
            CASE WHEN Operacao.TipoOperacao = 'Rega' THEN Rega.Duracao END AS DuracaoRega,
            CASE WHEN Operacao.TipoOperacao = 'Rega' THEN Rega.SetorID END AS SetorRega,
            CASE WHEN Operacao.TipoOperacao = 'Incorporação no Solo' THEN IncorporacaoSolo.Area END AS AreaIncorporacaoSolo,
            CASE WHEN Operacao.TipoOperacao = 'Colheita' THEN Colheita.QuantidadeProduto END AS QuantidadeProdutoColhido,
            CASE WHEN Operacao.TipoOperacao = 'Colheita' THEN Produto.DesignacaoProduto END AS DesignacaoProduto,
            CASE WHEN Operacao.TipoOperacao = 'Monda' THEN Monda.Modo END AS ModoMonda,
            CASE WHEN Operacao.TipoOperacao = 'Monda' THEN Monda.Area END AS AreaMonda,
            CASE WHEN Operacao.TipoOperacao = 'Monda' THEN FatorProducaoMonda.NomeComercial END AS FatorProducaoMonda,
            CASE WHEN Operacao.TipoOperacao = 'Aplicação Fator Produção' THEN AplicacaoFatorProducao.QuantidadeFatorProducao END AS QuantidadeFatorProducaoAplicado,
            CASE WHEN Operacao.TipoOperacao = 'Aplicação Fator Produção' THEN AplicacaoFatorProducao.Area END AS AreaAplicacaoFatorProducao,
            CASE WHEN Operacao.TipoOperacao = 'Aplicação Fator Produção' THEN FatorProducaoAplicado.NomeComercial END AS FatorProducaoAplicado,
            CASE WHEN Operacao.TipoOperacao = 'Mobilização do Solo' THEN MobilizacaoSolo.Area END AS AreaMobilizacaoSolo
        FROM Operacao

        LEFT JOIN Plantacao ON Operacao.OperacaoID = Plantacao.OperacaoID
        LEFT JOIN Semeadura ON Operacao.OperacaoID = Semeadura.OperacaoID
        LEFT JOIN Poda ON Operacao.OperacaoID = Poda.OperacaoID
        LEFT JOIN IncorporacaoSolo ON Operacao.OperacaoID = IncorporacaoSolo.OperacaoID
        LEFT JOIN Colheita ON Operacao.OperacaoID = Colheita.OperacaoID
        LEFT JOIN Monda ON Operacao.OperacaoID = Monda.OperacaoID
        LEFT JOIN AplicacaoFatorProducao ON Operacao.OperacaoID = AplicacaoFatorProducao.OperacaoID
        LEFT JOIN Rega ON Operacao.OperacaoID = Rega.OperacaoID
        LEFT JOIN MobilizacaoSolo ON Operacao.OperacaoID = MobilizacaoSolo.OperacaoID

        LEFT JOIN Produto ON Colheita.ProdutoID = Produto.ProdutoID
        LEFT JOIN FatorProducao FatorProducaoMonda ON Monda.FatorProducaoID = FatorProducaoMonda.FatorProducaoID
        LEFT JOIN FatorProducao FatorProducaoAplicado ON AplicacaoFatorProducao.FatorProducaoID = FatorProducaoAplicado.FatorProducaoID

        LEFT JOIN Cultivo ON Colheita.CultivoID = Cultivo.CultivoID OR Plantacao.CultivoID = Cultivo.CultivoID OR Semeadura.CultivoID = Cultivo.CultivoID OR Poda.CultivoID = Cultivo.CultivoID OR IncorporacaoSolo.CultivoID = Cultivo.CultivoID OR Monda.CultivoID = Cultivo.CultivoID OR AplicacaoFatorProducao.CultivoID = Cultivo.CultivoID
        LEFT JOIN Cultura ON Cultura.CulturaID = Cultivo.CulturaID
        LEFT JOIN EspecieVegetal ON EspecieVegetal.EspecieVegetalID = Cultura.EspecieVegetalID

        WHERE (Cultivo.ParcelaID = parcela_id OR Rega.ParcelaID = parcela_id OR MobilizacaoSolo.ParcelaID = parcela_id) AND Operacao.DataRealizacao BETWEEN data_inicio AND data_fim
        ORDER BY Operacao.TipoOperacao, Operacao.DataRealizacao;

    RETURN operacoes;
EXCEPTION
    WHEN OTHERS THEN
        IF operacoes%ISOPEN THEN
            CLOSE operacoes;
        END IF;
        RAISE;
END obter_operacoes;

-- Caso de teste

CREATE OR REPLACE PROCEDURE imprimir_operacoes(
    nome_parcela NVARCHAR2,
    data_inicio DATE,
    data_fim DATE
) AS
    cursor_operacoes   SYS_REFCURSOR;
    tipo_operacao     NVARCHAR2(255);
    data_realizacao   DATE;
    cultura          NVARCHAR2(255);
    quantidade_plantada       DOUBLE PRECISION := 0;
    quantidade_semeada             DOUBLE PRECISION := 0;
    area_semeada                   DOUBLE PRECISION := 0;
    quantidade_podada              DOUBLE PRECISION := 0;
    hora_rega                      TIMESTAMP;
    duracao_rega                   NUMBER := 0;
    setor_rega                     NUMBER := 0;
    area_incorporacao_solo         DOUBLE PRECISION := 0;
    quantidade_produto_colhido     DOUBLE PRECISION := 0;
    designacao_produto             NVARCHAR2(255);
    modo_monda                     NVARCHAR2(255);
    area_monda                     DOUBLE PRECISION := 0;
    fator_producao_monda           NVARCHAR2(255);
    quantidade_fator_producao_aplicado DOUBLE PRECISION := 0;
    area_aplicacao_fator_producao         DOUBLE PRECISION := 0;
    fator_producao_aplicado               NVARCHAR2(255);
    area_mobilizacao_solo                 DOUBLE PRECISION := 0;
    quantidade_relevante           NVARCHAR2(255);

BEGIN
    -- Chama a função e obtém o cursor
    cursor_operacoes := obter_operacoes(nome_parcela, data_inicio, data_fim);

    -- Loop para percorrer cada linha do cursor
    LOOP
        FETCH cursor_operacoes INTO tipo_operacao, data_realizacao, cultura,
                                          quantidade_plantada, quantidade_semeada, area_semeada,
                                          quantidade_podada, hora_rega, duracao_rega, setor_rega,
                                          area_incorporacao_solo, quantidade_produto_colhido, designacao_produto,
                                          modo_monda, area_monda, fator_producao_monda,
                                          quantidade_fator_producao_aplicado, area_aplicacao_fator_producao,
                                          fator_producao_aplicado, area_mobilizacao_solo;
        EXIT WHEN cursor_operacoes%NOTFOUND;

        CASE tipo_operacao
            WHEN 'Plantação' THEN
                quantidade_relevante := 'Quantidade Plantada: ' || TO_CHAR(quantidade_plantada);
            WHEN 'Semeadura' THEN
                quantidade_relevante := 'Quantidade Semeada: ' || TO_CHAR(quantidade_semeada) || ', Area Semeada: ' || TO_CHAR(area_semeada, 'FM9990.99');
            WHEN 'Poda' THEN
                quantidade_relevante := 'Quantidade Podada: ' || TO_CHAR(quantidade_podada);
            WHEN 'Rega' THEN
                quantidade_relevante := 'Hora Rega: ' || TO_CHAR(hora_rega, 'HH24:MI:SS') || ', Duracao Rega: ' || TO_CHAR(duracao_rega) || ', Setor Rega: ' || TO_CHAR(setor_rega);
            WHEN 'Colheita' THEN
                quantidade_relevante := 'Quantidade Produto Colhido: ' || TO_CHAR(quantidade_produto_colhido) || ', Designacao Produto: ' || designacao_produto;
            WHEN 'Incorporação no Solo' THEN
                quantidade_relevante := 'Area Incorporacao Solo: ' || TO_CHAR(area_incorporacao_solo, 'FM9990.99');
            WHEN 'Monda' THEN
                quantidade_relevante := 'Modo Monda: ' || modo_monda || ', Area Monda: ' || TO_CHAR(area_monda, 'FM9990.99') || ', Fator Producao Monda: ' || fator_producao_monda;
            WHEN 'Aplicação Fator Produção' THEN
                quantidade_relevante := 'Quantidade Fator Producao Aplicado: ' || TO_CHAR(quantidade_fator_producao_aplicado) || ', Area Aplicacao Fator Producao: ' || TO_CHAR(area_aplicacao_fator_producao, 'FM9990.99') || ', Fator Producao Aplicado: ' || fator_producao_aplicado;
            WHEN 'Mobilização do Solo' THEN
                quantidade_relevante := 'Area Mobilizacao Solo: ' || TO_CHAR(area_mobilizacao_solo, 'FM9990.99');
            ELSE
                quantidade_relevante := 'Outra Operação';
        END CASE;


        -- Imprime os resultados
        DBMS_OUTPUT.PUT_LINE('Tipo Operacao: ' || tipo_operacao ||
                             ', Data Realizacao: ' || TO_CHAR(data_realizacao, 'YYYY-MM-DD') ||
                             ', Cultura: ' || cultura ||
                             ', ' || quantidade_relevante);


    END LOOP;

    -- Fecha o cursor
    CLOSE cursor_operacoes;
EXCEPTION
    WHEN OTHERS THEN
        -- Em caso de erro, imprime a mensagem de erro e fecha o cursor
        DBMS_OUTPUT.PUT_LINE('Erro: ' || SQLERRM);
        IF cursor_operacoes%ISOPEN THEN
            CLOSE cursor_operacoes;
        END IF;
END imprimir_operacoes;

BEGIN
    imprimir_operacoes('Campo Novo',
                       TO_DATE('2023-07-01', 'YYYY-MM-DD'),
                       TO_DATE('2023-10-02', 'YYYY-MM-DD')
    );
END;
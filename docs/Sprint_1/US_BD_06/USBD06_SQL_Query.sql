-- US BD06
-- Como Gestor Agrícola, pretendo saber o numero de factores de produção aplicados numa dada parcela, para cada tipo de factor, num dado intervalo de tempo.

-- SQL Query

SELECT
    TipoFatorProducao.DescricaoTipoFatorProducao, COUNT(Operacao.OperacaoID) AS NumeroOperacoes
    FROM
    Operacao
INNER JOIN
    Parcela ON Operacao.ParcelaID = Parcela.ParcelaID
INNER JOIN
    FatorProducao ON Operacao.FatorProducaoID = FatorProducao.FatorProducaoID
INNER JOIN
    TipoFatorProducao ON FatorProducao.TipoFatorProducaoID = TipoFatorProducao.TipoFatorProducaoID
WHERE
    Operacao.DataRealizacao BETWEEN TO_DATE('2022-01-01', 'YYYY-MM-DD') AND TO_DATE('2023-09-15', 'YYYY-MM-DD')
AND Parcela.Designacao = 'Vinha'
GROUP BY TipoFatorProducao.DescricaoTipoFatorProducao;
-- US BD07
-- Como Gestor Agrícola, pretendo saber o numero de operações realizadas numa dada parcela, para cada tipo de operação, num dado intervalo de tempo.

-- SQL Query

SELECT
    TipoOperacao.DesignacaoTipoOperacao, COUNT(*) as NumberOfOperations
FROM
    Operacao
INNER JOIN
        Parcela ON Operacao.ParcelaID = Parcela.ParcelaID
INNER JOIN
        TipoOperacao ON Operacao.TipoOperacaoID = TipoOperacao.TipoOperacaoID
WHERE
    Parcela.Designacao = 'Vinha' AND Operacao.DataRealizacao BETWEEN TO_DATE('2022-01-01', 'YYYY-MM-DD')
        AND TO_DATE('2023-09-15', 'YYYY-MM-DD')
GROUP BY TipoOperacao.DesignacaoTipoOperacao;
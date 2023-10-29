-- US BD09
-- Como Gestor Agrícola, pretendo saber o número de aplicaçóes de cada tipo de factor de produção aplicados na instalação agricola num dado intervalo de tempo.

-- SQL Query
SELECT TipoFatorProducao.DescricaoTipoFatorProducao, COUNT(Operacao.OperacaoID) AS NumeroOperacoes FROM Operacao
INNER JOIN FatorProducao ON Operacao.FatorProducaoID = FatorProducao.FatorProducaoID
INNER JOIN TipoFatorProducao ON FatorProducao.TipoFatorProducaoID = TipoFatorProducao.TipoFatorProducaoID
WHERE Operacao.DataRealizacao BETWEEN TO_DATE('data_inicio', 'YYYY-MM-DD') AND TO_DATE('data_fim', 'YYYY-MM-DD')
GROUP BY TipoFatorProducao.DescricaoTipoFatorProducao

-- US BD08
-- Como Gestor Agrícola, pretendo saber o factor de produção com mais aplicações na instalação agricula num dado intervalo de tempo.

-- SQL Query
SELECT NomeComercial AS "Fator de Produção", Total AS "Aplicações"
FROM (SELECT FatorProducaoID, COUNT(*) AS Total
      FROM Operacao
      WHERE FatorProducaoID IS NOT NULL AND DataRealizacao BETWEEN TO_DATE('12/01/2015','DD/MM/YYYY') AND TO_DATE('12/01/2024','DD/MM/YYYY')
      GROUP BY FatorProducaoID) QuantidadeFator
         INNER JOIN FatorProducao ON FatorProducao.FatorProducaoID = QuantidadeFator.FatorProducaoID
WHERE TOTAL = (SELECT MAX(Total) FROM (SELECT FatorProducaoID, COUNT(*) AS Total FROM Operacao
                                       WHERE FatorProducaoID IS NOT NULL AND DataRealizacao BETWEEN TO_DATE('12/01/2015','DD/MM/YYYY') AND TO_DATE('12/01/2024','DD/MM/YYYY')
                                       GROUP BY FatorProducaoID));


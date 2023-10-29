-- US BD05 
-- Como Gestor Agrícola, pretendo saber a quantidade de produtos colhidos numa dada parcela, para cada produto, num dado intervalo de tempo.

-- SQL Query

SELECT
    Produto.DesignacaoProduto,
    SUM(Operacao_Produto.Quantidade) AS QuantidadeColhida
    FROM
    Operacao_Produto
JOIN
    Produto ON Operacao_Produto.ProdutoID = Produto.ProdutoID
JOIN
    Operacao ON Operacao_Produto.OperacaoID = Operacao.OperacaoID
JOIN
    Parcela ON Operacao.ParcelaId = Parcela.ParcelaID
WHERE
    Operacao.DataRealizacao BETWEEN TO_DATE('2022-11-12', 'YYYY-MM-DD') AND TO_DATE('2023-01-12', 'YYYY-MM-DD')
AND Parcela.Designacao = 'Lameiro da ponte'
GROUP BY
    Produto.DesignacaoProduto;

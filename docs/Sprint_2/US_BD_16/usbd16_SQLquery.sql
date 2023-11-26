CREATE
OR REPLACE FUNCTION obter_produtos_colhidos (

    data_inicio IN DATE,

    data_fim IN DATE,

    designacao_parcela IN NVARCHAR2

) RETURN SYS_REFCURSOR AS

    produtos SYS_REFCURSOR;

BEGIN

OPEN produtos FOR

SELECT EspecieVegetal.DesignacaoEspecie, Produto.DesignacaoProduto

FROM Produto

         INNER JOIN Colheita ON Colheita.ProdutoID = Produto.ProdutoID

         INNER JOIN Operacao ON Colheita.OPERACAOID = Operacao.OPERACAOID

         INNER JOIN Cultivo ON Cultivo.CultivoID = Colheita.CultivoID

         INNER JOIN Parcela ON Cultivo.ParcelaId = Parcela.ParcelaID

         INNER JOIN Cultura ON Cultivo.CulturaId = Cultura.CulturaId

         INNER JOIN EspecieVegetal ON Cultura.ESPECIEVEGETALID = EspecieVegetal.ESPECIEVEGETALID

WHERE Operacao.DataRealizacao BETWEEN data_inicio AND data_fim

  AND Parcela.Designacao = designacao_parcela

GROUP BY EspecieVegetal.DesignacaoEspecie, Produto.DesignacaoProduto;

RETURN produtos;

EXCEPTION

    WHEN OTHERS THEN

        -- Em caso de erro, fecha o cursor e propaga a exceção

        IF c_produtos IS OPEN THEN

            CLOSE c_produtos;

END IF;

        RAISE;

END obter_produtos_colhidos;

DECLARE

c_result SYS_REFCURSOR;

    especie
NVARCHAR2(255);

    produto
NVARCHAR2(255);

BEGIN

    c_result := obter_produtos_colhidos(TO_DATE('2023-05-20', 'YYYY-MM-DD'), TO_DATE('2023-11-06', 'YYYY-MM-DD'), 'Campo Novo');
 
    LOOP

FETCH c_result INTO v_especie, v_produto;

        EXIT
WHEN c_result%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE
('Espécie: ' || v_especie || ', Produto: ' || v_produto);

END LOOP;

CLOSE c_result;

EXCEPTION

    WHEN OTHERS THEN

        IF c_result IS OPEN THEN

            CLOSE c_result;

END IF;

        RAISE;

END;

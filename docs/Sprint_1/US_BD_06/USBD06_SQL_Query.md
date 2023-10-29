# US BD06
* Como Gestor Agrícola, pretendo saber o numero de factores de produção aplicados numa dada parcela, para cada tipo de factor, num dado intervalo de tempo.

### SQL Query

```sql
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
    Operacao.DataRealizacao BETWEEN TO_DATE('data_inicio', 'YYYY-MM-DD') AND TO_DATE('data_fim', 'YYYY-MM-DD')
AND Parcela.Designacao = 'Nome_Parcela'
GROUP BY TipoFatorProducao.DescricaoTipoFatorProducao
```

### Caso Prático

Para o intervalo de tempo entre **2022-01-01** e **2023-09-15**, para a Parcela "Vinha", o resultado é:


```sql
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
GROUP BY TipoFatorProducao.DescricaoTipoFatorProducao
```

### Resultados

![USBD06_SQL_Query_Output.png](Images%2FUSBD06_SQL_Query_Output.png)

### Validação dos Dados

Para validar os dados, foram analisados os dados da tabela **Operacao** e **FatorProducao** do ficheiro legacy.

> **Observação:** Na tabela "Operações", aplicou-se um filtro para considerar apenas as operações cuja Data está dentro do intervalo de tempo em estudo e cujo FatorProducao é diferente de NULL.

As imagens das tabelas são mostradas a seguir:

![TabelaOperacoes.png](Images%2FTabelaOperacoes.png)
![TabelaFatorProducao.png](Images%2FTabelaFatorProducao.png)

A análise da tabela "Operações" permitiu identificar o tipo de fator de produção aplicados nas operações durante o período em estudo:

| Fator de produção       | Numero de Aplicações |
|-------------------------|---------------------:|
| Calda Bordaleza ASCENZA |                    4 |


Consultando a tabela "Fator de Produção", foi possível determinar o tipo de fator de produção:

- Calda Bordalesa ASCENZA: Fitofármaco

Desta forma, ao agruparmos os fatores de produção por tipo e somando o número de operações, obtemos o seguinte resultado:

| Tipo fator produção | Numero de Aplicações |
|---------------------|---------------------:|
| Fitofármaco         |                    4 |

Em resumo, durante o período em estudo, foram realizadas 4 operações utilizando fitofármacos na parcela "Vinha".
# US BD09
* Como Gestor Agrícola, pretendo saber o número de aplicaçóes de cada tipo de factor de produção aplicados na instalação agricola num dado intervalo de tempo.

### SQL Query

```sql
SELECT TipoFatorProducao.DescricaoTipoFatorProducao, COUNT(Operacao.OperacaoID) AS NumeroOperacoes FROM Operacao
    INNER JOIN FatorProducao ON Operacao.FatorProducaoID = FatorProducao.FatorProducaoID
    INNER JOIN TipoFatorProducao ON FatorProducao.TipoFatorProducaoID = TipoFatorProducao.TipoFatorProducaoID
    WHERE Operacao.DataRealizacao BETWEEN TO_DATE('data_inicio', 'YYYY-MM-DD') AND TO_DATE('data_fim', 'YYYY-MM-DD')
    GROUP BY TipoFatorProducao.DescricaoTipoFatorProducao
```

### Caso Prático 

Para o intervalo de tempo entre **2022-01-12** e **2023-09-12**, o resultado é:


```sql
SELECT TipoFatorProducao.DescricaoTipoFatorProducao, COUNT(Operacao.OperacaoID) AS NumeroOperacoes FROM Operacao
    INNER JOIN FatorProducao ON Operacao.FatorProducaoID = FatorProducao.FatorProducaoID
    INNER JOIN TipoFatorProducao ON FatorProducao.TipoFatorProducaoID = TipoFatorProducao.TipoFatorProducaoID
    WHERE Operacao.DataRealizacao BETWEEN TO_DATE('2022-01-12', 'YYYY-MM-DD') AND TO_DATE('2023-09-12', 'YYYY-MM-DD')
    GROUP BY TipoFatorProducao.DescricaoTipoFatorProducao
```

### Resultados


| DESCRICAOTIPOFATORPRODUCAO | NUMEROOPERACOES |
|----------------------------|----------------:|
| Adubo                       |               3 |
| Fitofármaco                 |               2 |


### Validação dos Dados

Para validar os dados, foram analisados os dados da tabela **Operacao** e **FatorProducao** do ficheiro legacy.





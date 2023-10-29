# US BD02

Como Product Owner, pretendo que seja elaborado o modelo relacional (nível lógico).

### Restrições de integridade

* As quantidades não podem ter valores ser negativos.
* A soma das áreas das culturas de uma determinada parcela não pode ser superior à àrea dessa mesma parcela.
* A data de fim do cultivo não pode ser anterior à data de inicio do cultivo.
* Apenas as operações do tipo Colheita têm Produtos associados.
* Apenas as operações do tipo Fertilização e Aplicação de Fitofármaco têm Fatores Agricolas associados.
* Uma Cultura do Tipo Permanente não pode ter mais do que uma colheita.
* Cada cultivo tem um ciclo de cultura i.e. uma plantação/preparação e uma ou mais colheitas. 
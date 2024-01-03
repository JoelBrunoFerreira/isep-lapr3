# US BD 24
* Todos os registos relacionados com operações tem que ter registado o instante em que foram criados.

### SQL Query

```sql
CREATE OR REPLACE TRIGGER trgCriacaoOperacaoTimestamp
    BEFORE INSERT ON Operacao
    FOR EACH ROW
BEGIN
    :NEW.DataCriacao := SYSDATE;
END;
```
### Caso Sucesso

### Resultado

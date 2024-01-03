-- USBD33
-- Calcular e retornar uma lista de cultivos que tiveram a maior duração de rega num determinado ano civil.
CREATE OR REPLACE FUNCTION fncCultivosMaiorRega(
    ano_civil IN VARCHAR2
)
    RETURN SYS_REFCURSOR
    IS
    cur_result SYS_REFCURSOR;
    consumo_total REGA.DURACAO%type;

BEGIN
    -- Preenche o consumo_total com a duracao de rega máxima durante o ano_civil, passado como parametro
SELECT MAX(DuracaoTotal) INTO consumo_total
FROM (
         Select SUM(DURACAO) as DuracaoTotal
         FROM REGA
                  INNER JOIN OPERACAO ON REGA.OPERACAOID = OPERACAO.OPERACAOID
         WHERE OPERACAO.TIPOOPERACAO = 'Rega'
           AND OPERACAO.DATAREALIZACAO BETWEEN TO_DATE('01-01-'||ano_civil, 'dd-mm-yyyy') AND TO_DATE('31-12-'||ano_civil, 'dd-mm-yyyy')
         GROUP BY SETORID);
IF consumo_total != 0 THEN
        DBMS_OUTPUT.PUT_LINE('CONSUMO TOTAL = '||consumo_total||' min.');
END IF;

    -- Abre o cursor para armazenar o resultado da consulta
OPEN cur_result FOR
SELECT DISTINCT ESP.NOMECOMUM || ' ' || CULTURA.VARIEDADE AS CULTURA
FROM CULTURA
         INNER JOIN CULTIVO ON CULTURA.CULTURAID = CULTIVO.CULTURAID
         INNER JOIN ESPECIEVEGETAL ESP ON CULTURA.ESPECIEVEGETALID = ESP.ESPECIEVEGETALID
WHERE CULTIVO.CULTURAID IS NOT NULL
  AND CULTIVO.CULTIVOID IN (
    SELECT CultivoID
    FROM SETOR_CULTIVO
    WHERE SETOR_CULTIVO.SETORID IN (
        SELECT SETORID
        FROM REGA
                 INNER JOIN OPERACAO ON REGA.OPERACAOID = OPERACAO.OPERACAOID
        WHERE OPERACAO.TIPOOPERACAO = 'Rega'
          AND OPERACAO.DATAREALIZACAO BETWEEN TO_DATE('01-01-'||ano_civil, 'dd-mm-yyyy') AND TO_DATE('31-12-'||ano_civil, 'dd-mm-yyyy')
        GROUP BY SETORID
        HAVING SUM(DURACAO) = consumo_total));
RETURN cur_result;
END;


-- BLOCO DE TESTE:
DECLARE
cur_result SYS_REFCURSOR;
    v_cultivo VARCHAR2(255);
BEGIN
    cur_result := fncCultivosMaiorRega('2023');

    -- Verifica se o cursor não está vazio
    IF cur_result%NOTFOUND THEN
        DBMS_OUTPUT.PUT_LINE('Não existem dados.');
ELSE
        LOOP
            FETCH cur_result INTO v_cultivo;
            EXIT WHEN cur_result%NOTFOUND;

            DBMS_OUTPUT.PUT_LINE('Cultivo: ' || v_cultivo);
END LOOP;
END IF;
    -- Fecha o cursor
CLOSE cur_result;
END;
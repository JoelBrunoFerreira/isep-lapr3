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
    -- Preenche o consumo_total com a duracao de rega máxima durante o ano_civil passado como parametro
SELECT MAX(DuracaoTotal) INTO consumo_total FROM (
                                                     SELECT SUM(DURACAO) AS DuracaoTotal
                                                     FROM REGA R
                                                              INNER JOIN OPERACAO O ON R.OPERACAOID = O.OPERACAOID
                                                     WHERE O.DATAREALIZACAO BETWEEN TO_DATE('01-01-' || ano_civil, 'dd-mm-yyyy') AND TO_DATE('31-12-' || ano_civil, 'dd-mm-yyyy')
                                                     GROUP BY SetorID, ParcelaID);

IF consumo_total != 0 THEN
        DBMS_OUTPUT.PUT_LINE('CONSUMO TOTAL = '||consumo_total||' min.');
END IF;

    -- Abre o cursor para armazenar o resultado da consulta
OPEN cur_result FOR
SELECT DISTINCT ESP.NOMECOMUM || ' ' || CULTURA.VARIEDADE AS CONCATENADO
FROM CULTURA
         INNER JOIN CULTIVO C ON CULTURA.CULTURAID = C.CULTURAID
         INNER JOIN ESPECIEVEGETAL ESP ON CULTURA.ESPECIEVEGETALID = ESP.ESPECIEVEGETALID
WHERE C.CULTIVOID IN (
    -- Seleciona os IDs de cultivo que correspondem aos critérios especificados
    SELECT DISTINCT C.CULTIVOID
    FROM CULTIVO C
             INNER JOIN REGA R ON C.ParcelaID = R.ParcelaID
             INNER JOIN OPERACAO O ON R.OPERACAOID = O.OPERACAOID
    WHERE R.PARCELAID IN (
        -- Seleciona IDs de parcela com base nas condições de data e duração de rega
        SELECT PARCELAID
        FROM REGA
        WHERE O.DATAREALIZACAO BETWEEN TO_DATE('01-01-' || ano_civil, 'dd-mm-yyyy') AND TO_DATE('31-12-' || ano_civil, 'dd-mm-yyyy')
        GROUP BY SetorID, ParcelaID
        HAVING SUM(DURACAO) = consumo_total
    )
      AND C.CULTURAID IS NOT NULL
);
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
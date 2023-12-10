-- Função para a USBD20
CREATE OR REPLACE FUNCTION fncUSBD20(
    parcela_designacao PARCELA.DESIGNACAO%type,
    data_inicio OPERACAO.DATAREALIZACAO%type,
    data_fim OPERACAO.DATAREALIZACAO%type
)
    RETURN SYS_REFCURSOR
    IS
    res_cursor SYS_REFCURSOR;
BEGIN
OPEN res_cursor FOR
SELECT DISTINCT TO_CHAR(OPERACAO.DATAREALIZACAO, 'MON/YYYY') AS MES_ANO,
                SUM(REGA.DURACAO) AS TOTAL_DURATION
FROM OPERACAO
         INNER JOIN REGA ON OPERACAO.OPERACAOID = REGA.OPERACAOID
         INNER JOIN PARCELA ON REGA.PARCELAID = PARCELA.PARCELAID
WHERE PARCELA.DESIGNACAO = parcela_designacao
  AND OPERACAO.DATAREALIZACAO BETWEEN data_inicio AND data_fim
GROUP BY TO_CHAR(OPERACAO.DATAREALIZACAO, 'MON/YYYY');
RETURN res_cursor;
END fncUSBD20;


-- Bloco para teste:
DECLARE
result_cursor SYS_REFCURSOR;
    designacao_parcela_param PARCELA.Designacao%type := 'Lameiro da Ponte';
    data_inicio_param OPERACAO.DATAREALIZACAO%type := TO_DATE('01-06-2023','dd-mm-yyyy');
    data_fim_param OPERACAO.DATAREALIZACAO%type := TO_DATE('06-11-2023','dd-mm-yyyy');
    mes_ano VARCHAR2(20);
    duracao REGA.DURACAO%type;
BEGIN
    result_cursor := fncUSBD20(designacao_parcela_param, data_inicio_param, data_fim_param);

    DBMS_OUTPUT.PUT_LINE(designacao_parcela_param);
    LOOP
FETCH result_cursor INTO mes_ano, duracao;
        EXIT WHEN result_cursor%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE(mes_ano || ' - ' || duracao ||' min');
END LOOP;

CLOSE result_cursor;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Sem dados para mostrar.');
WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Ocorreu um erro: ' || SQLERRM);
END;
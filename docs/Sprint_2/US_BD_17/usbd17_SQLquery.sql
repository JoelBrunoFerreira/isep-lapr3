-- Função para a USBD17
CREATE OR REPLACE FUNCTION fncUSBD17(
    designacao_parcela PARCELA.Designacao%type,
    data_inicio OPERACAO.DATAREALIZACAO%type,
    data_fim OPERACAO.DATAREALIZACAO%type)
    RETURN SYS_REFCURSOR
    IS
    res_cursor SYS_REFCURSOR;
BEGIN
OPEN res_cursor FOR
SELECT F.NOMECOMERCIAL, E.DESIGNACAO, FPE.QUANTIDADE
FROM ELEMENTO E
         INNER JOIN FATORPRODUCAO_ELEMENTO FPE ON E.ELEMENTOID = FPE.ELEMENTOID
         INNER JOIN FATORPRODUCAO F ON FPE.FATORPRODUCAOID = F.FATORPRODUCAOID
WHERE F.FATORPRODUCAOID IN (SELECT AFP.FATORPRODUCAOID
                            FROM APLICACAOFATORPRODUCAO AFP
                                     INNER JOIN OPERACAO O ON AFP.OPERACAOID = O.OPERACAOID
                            WHERE AFP.CULTIVOID IN (SELECT C.CULTIVOID
                                                    FROM CULTIVO C
                                                             INNER JOIN PARCELA P ON C.PARCELAID = P.PARCELAID
                                                    WHERE P.DESIGNACAO = designacao_parcela)
                              AND O.DATAREALIZACAO BETWEEN data_inicio AND data_fim
                            GROUP BY AFP.FATORPRODUCAOID)
ORDER BY F.NOMECOMERCIAL,
         E.DESIGNACAO;
RETURN res_cursor;
END fncUSBD17;


-- Bloco para teste:
DECLARE
result_cursor            SYS_REFCURSOR;
    designacao_parcela_param PARCELA.Designacao%type      := 'Lameiro do Moinho';
    data_inicio_param        OPERACAO.DATAREALIZACAO%type := TO_DATE('01-01-2019', 'dd-mm-yyyy');
    data_fim_param           OPERACAO.DATAREALIZACAO%type := TO_DATE('06-07-2023', 'dd-mm-yyyy');
    nome_comercial           FATORPRODUCAO.NOMECOMERCIAL%type;
    designacao_elemento      ELEMENTO.DESIGNACAO%type;
    quantidade               FATORPRODUCAO_ELEMENTO.QUANTIDADE%type;

BEGIN
    result_cursor := fncUSBD17(designacao_parcela_param, data_inicio_param, data_fim_param);
    DBMS_OUTPUT.PUT_LINE(designacao_parcela_param);
    LOOP
FETCH result_cursor INTO nome_comercial, designacao_elemento, quantidade;
        EXIT WHEN result_cursor%NOTFOUND;

        DBMS_OUTPUT.PUT_LINE('Nome Comercial: ' || nome_comercial || ', Designacao: ' || designacao_elemento ||
                             ', Quantidade: ' || quantidade || '%.');
END LOOP;

CLOSE result_cursor;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Sem dados para mostrar.');
WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Ocorreu um erro: ' || SQLERRM);
END;


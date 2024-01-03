#include "header.h"

int main(int argc, char **argv)
{   
    // verifica se os parametros sao validos
    if (verificar_argumentos(argc, argv) == 1)
    {
        return 1;
    }

    // alloca memória para o vector de sensores e preeche com info do config file
    Sensor *sensores = preencher_sensoresInfo(argv[2]);

    if (sensores == NULL)
    {
        printf("Erro ao ler os ficheiros de informação dos sensores.\n");
        return 1;
    }

    processador(sensores, argv[1], atoi(argv[4]), argv[3]);

    // libertar a alocação de memória utilizada
    int size = 0;
    while (sensores[size].sensor_id != 0)
    {
        free(sensores[size].buffer_circular);
        free(sensores[size].mediana);
        sensores[size].buffer_circular = NULL;
        sensores[size].mediana = NULL;
        size++;
    }
    free(sensores);

    return 0;
}

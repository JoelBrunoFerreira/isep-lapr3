#include "header.h"

//void realloc_sensores(Sensor* ptr, int counter){
//    Sensor* tmp = NULL;
//
//    tmp = (Sensor*)realloc(ptr, ((counter+1)*sizeof(Sensor)));
//
//    if (tmp == NULL)
//    {
//        printf("Não é possível aumentar o vector.");
//        exit(EXIT_FAILURE);
//    }else{
//        ptr = tmp;
//    }    
//    free(tmp);
//}

//verifica os argumentos passados pelo utilizador
int verificar_argumentos(int argc, char **argv)
{

    if (argc != 5)
    {
        printf("Número de argumentos incorreto!\n");
        return 1;
    }
    // Construct the directory path
    char dir_path[20];  // Adjust the size as needed
    // Construct the directory path
    snprintf(dir_path, sizeof(dir_path), "../%s", argv[3]);
    if (criarOuVerificarDiretorio(dir_path) == 1)
    {
        return 1;
    }

    if (*argv[4] < 0)
    {
        return 1;
    }

    return 0;
}
// verifica se um diretório existe, se não existe cria
int criarOuVerificarDiretorio(char *dir_path){
    // Check if the directory exists
    struct stat st = {0};
    if (stat(dir_path, &st) == -1) {
        // Directory doesn't exist, so create it
        if (mkdir(dir_path, 0777) == -1) {
            perror("Erro ao criar o diretório.\n");
            return 1;  // Return 0 to indicate failure
        }
        printf("Diretório '%s' criado.\n", dir_path);
    } else {
        printf("Diretório '%s' já existe.\n", dir_path);
    }

    return 0; 
}
//faz a contagem do numero de linhas num ficheiro
int linhas_fConfig(char ficheiroConfig[]){
    int count = 0;
    FILE *file = NULL;
    char line[50];
    file = fopen(ficheiroConfig, "r");
    if (file == NULL) {
        printf("Erro ao abrir o ficherio.");
    }
    else{
        while (fgets(line, sizeof(line), file) != NULL){
            count++;
        }
    }
    fclose(file);
    return count;
}

// US07 Obter a partir do ficheiro config os structs de sensores
Sensor* preencher_sensoresInfo(char ficheiroConfig[]){    
    Sensor* sensores = (Sensor*)calloc(linhas_fConfig(ficheiroConfig), sizeof(Sensor));
    if (sensores == NULL)
    {
        return NULL;
    }
    
    FILE *file = NULL;
    char line[50];
    // Abrir ficheiro para leitura
    file = fopen(ficheiroConfig, "r");

    if (file == NULL)
    {
        printf("Erro ao abrir o ficheiro.");
        return NULL;
    }
    else{
       // Ler cada linha do ficheiro
        while (fgets(line, sizeof(line), file) != NULL) {
            int sensor_id;
            char sensor_type[24], unit[12];
            int buffer_size, window_len, timeout;

            // Converte os valores de cada linha
            sscanf(line, "%d#%49[^#]#%49[^#]#%d#%d#%d", &sensor_id, sensor_type, unit, &buffer_size, &window_len, &timeout);
            int index = sensor_id-1;
            sensores[index].sensor_id = sensor_id;
            strcpy(sensores[index].type, sensor_type);
            strcpy(sensores[index].unit, unit);
            sensores[index].buffer_circular = (int*)calloc(buffer_size,sizeof(int));
            sensores[index].mediana = (int*)calloc(window_len, sizeof(int));
            sensores[index].length = buffer_size;
            sensores[index].reader = 0;
            sensores[index].writer = 0;
            sensores[index].actual_time = 0;
            sensores[index].previous_time = 0;
            sensores[index].window_len = window_len;
            sensores[index].timeout = timeout;
            if ( sensores[index].buffer_circular == NULL || sensores[index].mediana == NULL)
            {
                printf("Erro ao alocar memória.\n");
                return NULL;
            }
        }
    }
    // Fechar o ficheiro
    fclose(file);
    return sensores;
}

// US08 recebe os dados enviados pelo componente ColetorDeDados
int receber_dados_sensor(char line[], int *sensor_id, int *value, int *time)
{
    // Tokens
    char sensor_id_token[] = "sensor_id:";
    char value_token[] = "value:";
    char time_token[] = "time:";

    if (line != NULL) {
        int get_sensor_id = extract_token(line, sensor_id_token, sensor_id);
        int get_value = extract_token(line, value_token, value);
        int get_time = extract_token(line, time_token, time);

        // Verifica se todos os tokens foram extraidos com sucesso
        if (get_sensor_id && get_value && get_time) {
            return 0; // Sucesso
        }

        return 1; //Erro ao obter valores
    }
    return 1;
}
// US09 insere os dados recebidos do componente ColetorDeDados nas estruturas de dados
void inserir_dados_sensor(Sensor *sensores, int* sensor_id, int* value, int* time)
{
    Sensor *sensor = NULL; 
    sensor = &sensores[*sensor_id - 1];

    enqueue_value(sensor->buffer_circular, sensor->length, &sensor->reader, &sensor->writer, *value);
    sensor->previous_time = sensor->actual_time;
    sensor->actual_time = *time;

}
// Implementa a moving median algorithm
int moving_median(Sensor *sensor) {
    int *buffer = NULL;
    buffer = sensor->buffer_circular;
    int *mediana = NULL;
    mediana = sensor->mediana;
    int reader = sensor->reader;
    int writer = sensor->writer;
    int length = sensor->length;
    int window_len = sensor->window_len;

    // verificar o timeout, se o sensor está em erro
    if ((sensor->actual_time - sensor->previous_time) > sensor->timeout)
    {
        return -1;
    }
   
    int mov_res = move_num_vec(buffer, length, &reader, &writer, window_len, mediana);
    
    // Se mov_res == 0, não ha valores suficientes para calcular a mediana
    if (mov_res == 0)
    {
        return 0;
    }
    else{

        int mediana_res = fnc_mediana(mediana, window_len);
        
        sensor->write_counter++;
        
        return mediana_res;
    }
}
// US10 serializa a informaçao armazenada nas estruturas de dados e escreve-a num ficheiro de texto
void escrever_dados_sensor(Sensor *sensores, char diretorio[])
{
    // Get the current date and time
    time_t rawtime;
    struct tm *timeinfo;
    char datetime_str[20]; // Format: YYYYMMDDHHMMSS
    time(&rawtime);
    timeinfo = localtime(&rawtime);
    strftime(datetime_str, sizeof(datetime_str), "%Y%m%d%H%M%S", timeinfo);

    // Construct the file path using the provided directory and current date
    char file_path[256]; // Adjust the size as needed
    snprintf(file_path, sizeof(file_path), "%s/%s_sensors.txt", diretorio, datetime_str);

    // Open the file for writing
    FILE *file = fopen(file_path, "w");

    // Check if the file was opened successfully
    if (file == NULL)
    {
        fprintf(stderr, "Erro ao abrir o ficheiro: %s\n", file_path);
    }
    else
    {
        // "sensor_id,write_counter,type,unit,mediana#\n");
        for (int i = 0; i < 10; i++)
        {
            int mediana_res = moving_median(&sensores[i]);
            // Write data to the file
            if (mediana_res == -1)
            {
                fprintf(file, "%d,%d,%s,%s,%s#\n", sensores[i].sensor_id, sensores[i].write_counter, sensores[i].type, sensores[i].unit, "erro");

            }
             else if (mediana_res > 0)
             {
                fprintf(file, "%d,%d,%s,%s,%d#\n", sensores[i].sensor_id, sensores[i].write_counter, sensores[i].type, sensores[i].unit, mediana_res);
             }
        }
    }
    // Close the file
    fclose(file);
}
// US11 Algoritmo do componente
void processador(Sensor *sensores, char ficheiroColetor[], int leituras, char dirSaida[])
{
    // Construir o diretorio de saída:
    char dir_path[20];
    snprintf(dir_path, sizeof(dir_path), "../%s", dirSaida);
    // Construir o ficheiro para leitura:
    FILE *file = NULL;
    char line[100];

    // Abrir ficheiro para leitura
    file = fopen(ficheiroColetor, "r");

    if (file == NULL)
    {
        printf("Erro ao abrir o ficheiro.");
    }
    else
    {
        while (1)
        {
            int cycle_count = 0;

            while ((cycle_count < leituras) && (fgets(line, sizeof(line), file) != NULL))
            {
                // variáveis para armazenar os valores dos token
                int sensor_id = -1;
                int value = -1;
                int time = -1;
                
                if (!(receber_dados_sensor(line, &sensor_id, &value, &time)))
                {
                    inserir_dados_sensor(sensores, &sensor_id, &value, &time);
                    // Incrementa o numero de ciclos se leitura correta
                    cycle_count++;
                }
                // se cycle_count == leituras, escreve dados no ficheiro de saida
                if (cycle_count == leituras)
                {
                    escrever_dados_sensor(sensores, dir_path);
                }
            }
        }
    }
    fclose(file);
}

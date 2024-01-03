#include "header.h"
int global_tamanho_dados;
// verifica se um diretório existe, se não existe cria
int criarOuVerificarDiretorio(char *path)
{
    char dir_path[16]; // Adjust the size as needed

    // Construct the directory path
    snprintf(dir_path, sizeof(dir_path), "../%s", path);

    // Check if the directory exists
    struct stat st = {0};
    if (stat(dir_path, &st) == -1)
    {
        // Directory doesn't exist, so create it
        if (mkdir(dir_path, 0777) == -1)
        {
            perror("Error creating directory");
            return 1; // Return 0 to indicate failure
        }
        printf("Directory '%s' created.\n", dir_path);
    }
    else
    {
        printf("Directory '%s' already exists.\n", dir_path);
    }

    return 0; // Return 1 to indicate success
}
// verifica os argumentos passados pelo utilizador
int verificar_argumentos(int argc, char **argv)
{

    if (argc != 4)
    {
        printf("Número de argumentos incorreto!\n");
        return 1;
    }

    if (criarOuVerificarDiretorio(argv[2]) == 1)
    {
        return 1;
    }
    if (*argv[3] < 0)
    {
        return 1;
    }

    return 0;
}
// faz a contagem do numero de linhas num ficheiro
int linhas_fConfig(char ficheiroConfig[])
{
    char path[50];
    snprintf(path, sizeof(path), "%s", ficheiroConfig);

    int count = 0;
    FILE *file = NULL;
    char line[50];
    file = fopen(path, "r");
    if (file == NULL)
    {
        printf("Erro ao abrir o ficherio.");
    }
    else
    {
        while (fgets(line, sizeof(line), file) != NULL)
        {
            count++;
        }
    }
    fclose(file);
    return count;
}

// USAC12
SensorData *usac12(char *ficheiro_recente)
{
    global_tamanho_dados = linhas_fConfig(ficheiro_recente);
    SensorData *dados = (SensorData *)calloc(sizeof(SensorData), global_tamanho_dados);
    return dados;
}
void popula_dados_sensores(SensorData *dados, char *dirEntrada, char *ficheiroConfig, int tamanho)
{
    char path[50];
    snprintf(path, sizeof(path), "../%s/%s", dirEntrada, ficheiroConfig);

    FILE *file = NULL;
    char line[50];
    file = fopen(path, "r");

    if (file == NULL)
    {
        printf("Erro ao abrir o ficheiro.");
    }
    else
    {
        for (int i = 0; i < tamanho; i++)
        {
            int sensor_id = 0;
            int write_counter = 0;
            char type[24];
            char unit[12];
            double mediana = -9999.0;

            fgets(line, sizeof(line), file);
            sscanf(line, "%d,%d,%[^,],%[^,],%lf#", &sensor_id, &write_counter, type, unit, &mediana);
            int index = sensor_id - 1;
            dados[index].sensor_id = sensor_id;
            dados[index].write_counter = write_counter;
            strcpy(dados[index].type, type);
            strcpy(dados[index].unit, unit);
            dados[index].mediana = mediana;  
        }
    }
    fclose(file);
}
int popula_dados_sensor(SensorData *dados, char *dirEntrada, char *ficheiroConfig, int index)
{
    char path[50];
    snprintf(path, sizeof(path), "../%s/%s", dirEntrada, ficheiroConfig);
    int tamanho = linhas_fConfig(path);
    
    FILE *file = NULL;
    char line[50];
    
    file = fopen(path, "r");

    if (file == NULL)
    {
        printf("Erro ao abrir o ficheiro.");
        return 1;
    }
    else
    {

        for (int i = 0; i < tamanho; i++)
        {
            int sensor_id = 0;
            int write_counter = 0;
            char type[24];
            char unit[12];
            double mediana = -9999.0;

            fgets(line, sizeof(line), file);
            sscanf(line, "%d,%d,%[^,],%[^,],%lf#", &sensor_id, &write_counter, type, unit, &mediana);
            if (sensor_id == (index + 1))
            {
                dados[index].sensor_id = sensor_id;
                dados[index].write_counter = write_counter;
                strcpy(dados[index].type, type);
                strcpy(dados[index].unit, unit);
                dados[index].mediana = mediana;
                fclose(file);
                return 0;
            }
        }
        fclose(file);
        return 1;
    }
}
// USAC13
struct dirent **dir_files(int *num)
{
    struct dirent **namelist;
    int n;

    n = scandir("../intermedio", &namelist, NULL, alphasort);
    *num = n;
    if (n < 0)
        perror("scandir");

    return namelist;
}
void usac13(SensorData *dados, char *dirSaida, char *dirEntrada)
{
    struct dirent **namelist = NULL;
    int n = 0;
    namelist = dir_files(&n);

    if (n > 2)
    {
        char path[1040];
        snprintf(path, sizeof(path), "../%s/%s", dirEntrada, namelist[n - 1]->d_name);
        int tamanho = linhas_fConfig(path);

        popula_dados_sensores(dados, dirEntrada, namelist[n - 1]->d_name, tamanho);

        char dir_path[MAX_LEN];
        snprintf(dir_path, sizeof(dir_path), "../%s", dirSaida);

        char file_path[150]; // Adjust the size as needed
        snprintf(file_path, sizeof(file_path), "%s/sensors_output.csv", dir_path);

        // Open the file for writing
        FILE *file = fopen(file_path, "w");

        if (file == NULL)
        {
            printf("Erro ao abrir o ficheiro.");
        }
        else
        {
            for (int i = 0; i < global_tamanho_dados; i++)
            {
                int res = 1;
                int m = n;
                if (dados[i].sensor_id == 0 /*|| dados[i].write_counter == 0*/)
                {
                    while ((res == 1) && (m > 3))
                    {
                        m--;
                        res = popula_dados_sensor(dados, dirEntrada, namelist[m - 1]->d_name, i);
                    }
                    if (res == 0)
                    {

                        fprintf(file, "Sensor ID: %d, Counter: %d, Tipo: %s, Unidade: %s, Valor: %.2f\n", dados[i].sensor_id, dados[i].write_counter, dados[i].type, dados[i].unit, (dados[i].mediana / 100));
                    }
                }
                else
                {

                    fprintf(file, "Sensor ID: %d, Counter: %d, Tipo: %s, Unidade: %s, Valor: %.2f\n", dados[i].sensor_id, dados[i].write_counter, dados[i].type, dados[i].unit, (dados[i].mediana / 100));
                }
            }
        }
        fclose(file);
    }
    free(namelist);
}
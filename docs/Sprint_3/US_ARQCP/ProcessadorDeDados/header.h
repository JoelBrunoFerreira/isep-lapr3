#ifndef FUNCTIONS_H
#define FUNCTIONS_H
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <time.h>
//Assembly:
int extract_token(char* input, char* token, int* output);
void enqueue_value(int* array, int length, int* read, int* write, int value);
int move_num_vec(int* array, int length, int* read, int* write, int num, int* vec);
int fnc_mediana(int* vec, int num);

//C:
int verificar_argumentos(int argc, char **argv);
typedef struct{
    int sensor_id;
    char type[24];
    char unit[12];
    int* buffer_circular;
    int* mediana;
    int reader;
    int writer;
    int length;
    int actual_time;
    int previous_time;
    int timeout;
    int write_counter;
    int window_len;
} Sensor; //size:88 bytes
int criarOuVerificarDiretorio(char *dir_path);
Sensor* preencher_sensoresInfo(char ficheiroConfig[]);
void processador(Sensor *sensores, char ficheiroColetor[], int leituras, char ficheiroSaida[]);

#endif

//typedef struct{
//    int length;
//    int writer;
//    int reader;
//    int value;
//}Buffer;
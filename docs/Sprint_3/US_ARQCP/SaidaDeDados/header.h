#ifndef FUNCTIONS_H
#define FUNCTIONS_H
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <signal.h>
#include <sys/types.h>
#include <time.h>
#include <sys/time.h>
#include <dirent.h>
#include <unistd.h>
#include <errno.h>

#define MAX_LEN 50

// C:
int verificar_argumentos(int argc, char **argv);
typedef struct
{
    int sensor_id;
    int write_counter;
    double mediana;
    char type[24];
    char unit[12];
} SensorData;
SensorData *usac12(char* ficheiro_recente);
void usac13(SensorData *dados, char *dirSaida, char *dirEntrada);
#endif
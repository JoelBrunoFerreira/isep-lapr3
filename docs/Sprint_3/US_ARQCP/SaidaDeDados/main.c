#include "header.h"

int global_argc;
char **global_argv;
SensorData *global_sensor_data = NULL;

void timer_handler()
{
   usac13(global_sensor_data, global_argv[2], global_argv[1]);
    
}
void time_cycle(){
    struct sigaction sa;
    struct itimerval timer;

    /* Install timer_handler as the signal handler for SIGVTALRM. */
    memset(&sa, 0, sizeof(sa));
    sa.sa_handler = &timer_handler;
    sigaction(SIGVTALRM, &sa, NULL);

    /* Configure the timer to expire after 1 second... */
    timer.it_value.tv_sec = (atoi(global_argv[3])/1000);
    timer.it_value.tv_usec = 0;

    /* ... and every 1 second after that. */
    timer.it_interval.tv_sec = (atoi(global_argv[3])/1000);
    timer.it_interval.tv_usec = 0;

    /* Start a virtual timer. It counts down whenever this process is executing. */
    setitimer(ITIMER_VIRTUAL, &timer, NULL);
    while (1);
}
int main(int argc, char **argv)
{
    // Verifica se os parametros sao validos
    if (verificar_argumentos(argc, argv) == 1)
    {
        return 1;
    }
    global_argc = argc;
    global_argv = argv;

    // Alocacao de memoria para o vetor de sensores
    char ficheiro_config[] = "../ProcessadorDeDados/config.txt";
    global_sensor_data = usac12(ficheiro_config);
    
    if (global_sensor_data == NULL)
    {
        printf("Não foi possível alocar memória para os dados.\n");
        return 1;
    }
    
    //usac13(global_sensor_data, global_argv[2], global_argv[1]);
    time_cycle();
        
    free(global_sensor_data);
    return 0;
}
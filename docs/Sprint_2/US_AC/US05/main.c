#include <stdio.h>
#include "asm.h"

int main()
{   
    int num = 1;
    int arr[] = {1};
    int result = mediana(arr, num);

    for (int i = 0; i < num; i++)
    {
        printf("%d\n", arr[i]);
    }
    printf("Mediana: %d\n", result);
    
    return 0;

}

#include <stdio.h>
#include "asm.h"

int main()
{   
    int num = 1;
    int arr[] = {1000};
    sort_array(arr, num);

    for (int i = 0; i < num; i++)
    {
        printf("%d\n", arr[i]);
    }
    
    return 0;

}

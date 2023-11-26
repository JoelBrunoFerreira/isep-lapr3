#include <stdio.h>
#include "asm.h"

int main()
{   /*

void test_Zero()
{ 
    run_test((int[]){0,0,0},3,0,2,5,(int[]){0,0,5},1,0); 
}
void test_Five()
{ 
    run_test((int[]){0,0,0,0},4,0,3,5,(int[]){0,0,0,5},1,0); 
}
*/ 
    int length = 4;
    int read = 1;
    int write = 1;
    int value = 5;

    int arr[] ={0,0,0,0};
    
    enqueue_value(arr,length, &read, &write, value);

    
    for (int i = 0; i < length; i++)
    {
        printf("%d\n", arr[i]);
    }

    printf("%d\n", read);
    printf("%d\n", write);

    return 0;

}

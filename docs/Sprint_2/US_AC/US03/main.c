#include <stdio.h>
#include "asm.h"
/*void test_One()
{ 
    run_test((int[]){0,0,0},3,0,0,1,0,(int[]){0,0,0},0,0); 
}
void test_Zero()
{ 
    run_test((int[]){1,0,0},3,0,1,1,1,(int[]){1},1,1); 
}
void test_Three()                                            
{ 
    run_test((int[]){1,2,3,4},4,3,2,3,1,(int[]){4,1,2},2,2); 
}
void test_Five()
{ 
    run_test((int[]){1,2,3,4},4,2,1,3,1,(int[]){3,4,1},1,1); 
}
*/
int main()
{   
    int arr[] = {1, 0, 0};
    int length = 3;
    int reader = 0;
    int writer = 1;
    int num = 1;
    int arr2[num];

    int result = move_num_vec(arr, length, &reader, &writer ,num , arr2);
    
    printf("Result: %d\n", result);
    
    for (int i = 0; i < length; i++)
    {
        printf("%d ", arr2[i]);
    }
    printf("\nReader = %d\nWriter = %d\n", reader,writer);

    
    
    return 0;

}

#include <stdio.h>
#include "asm.h"

int main()
{   
    //char strInput[] = "";
    //char strToken[] = "";
    char strInput[] = "atmospheric";
    char strToken[] = "atmos:";
    int result = 10;
    
    extract_token(strInput, strToken, &result);

    printf("Result: %d\n", result);
    
    return 0;
 
}

/* 

Realizado para ser invocado desde Mayusculas.java
Lo dejamos como a.out

Si lo ejecutamos convierte cada linea de minúsculas a mayusculas

*/

#include <stdio.h>
#include <string.h>
#include <ctype.h>
int main(int argc, char **argv)
{
    char buf[1024];
    int longitud;
    int i;
    while(fgets(buf, 1024, stdin) != NULL) {
        longitud = strlen(buf);
        for( i = 0; i < longitud; i++) 
            buf[i] = toupper(buf[i]);
        
        fputs(buf, stdout);
        fflush(stdout); 
    }
    return 0;
}
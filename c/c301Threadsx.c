#include <pthread.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

/*
   Crear una lista de threads
   - Compartir un string y que cada uno vaya añadiendo caracteres (1).

   - Que cada thread tenga un identificador, y lo imprima
   - y esperar que vayan finalizando en orden

    - pthread_t thread...
    - pthread_create(&thread, NULL,....
    - pthread_join(thread,....
    - pthread_exit(NULL);
    - gcc -lpthread

*/


int main(void)
{
    pthread_t thread;
    
    pthread_exit(0); //Return 0
}

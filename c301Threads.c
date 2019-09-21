#include <pthread.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>

/*
    Treads
    Compartición de memoria
    Códigos de error standard de unix

    - pthread_t thread...
    - pthread_create(&thread, NULL,....
    - pthread_join(thread,....
    - gcc -lpthread

*/

struct sData
{
    int  value;
    char text[1000];
};

void *start (void *voidData) {
    struct sData *data = voidData;
    data->value = 16;
    sleep(2);
    printf("%s\n", data->text); // Ojo
    printf("Thread %d done.\n", getpid());

    return 0;
}

int main(void)
{
    pthread_t thread;
    struct sData sd;
    int rc;

    strcpy (sd.text, "Tenemos dos vidas:\n la segunda comienza cuando nos damos cuenta\n de que sólo tenemos una”. (Confucio)");
    rc = pthread_create(&thread, NULL, start, &sd);

    if (rc)
    {
        printf("mithread1 error: %d", rc);
/*
These are the standard error codes in Linux or UNIX.

1 - Catchall for general errors
2 - Misuse of shell builtins (according to Bash documentation)
126 - Command invoked cannot execute
127 - “command not found”
128 - Invalid argument to exit
128+n - Fatal error signal “n”
130 - Script terminated by Control-C
255\* - Exit status out of range
*/
        return 1;
    }

    printf("Desde %d miro a ver si acabas...\n", getpid());
    pthread_join(thread, NULL);
    printf("mithread1 ya ha acabado con value a %d.\n", sd.value);
    return 0;
}

/* Salida de consola

airbook:dampsp santi$ gcc c301Threads.c -lpthread; ./a.out
Desde 1713 miro a ver si acabas...
Tenemos dos vidas:
 la segunda comienza cuando nos damos cuenta
 de que sólo tenemos una”. (Confucio)
Thread 1713 done.
mithread1 ya ha acabado con value a 16.
*/
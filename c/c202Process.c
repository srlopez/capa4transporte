#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h> //sleep, pid

/*
   
   - Variables de entorno
   - Simple aritmetica de punteros

   - Proceso padre A e hijo B
   - fork() + wait()

*/

int main(int arcg, char *argv[], char **envp)
{
    pid_t pidB, pidBw;
    int status;

    while (*envp)
        printf("%s\n", *envp++);

    pidB = fork();

    if (pidB == -1)
    {
        printf("No se ha podido crear B...");
        exit(pidB);
    }
    if (pidB == 0)
    {
        // Proceso B
        printf("B PID: %d, A PID: %d.\n", getpid(), getppid());
    }
    else
    {
        // Proceso A
        // que quiere a B y lo espera
        pidBw = wait(&status);
        printf("A PID: %d, PPID: %d.\n desde A: B PIB: %d/%d (%d)\n", getpid(), getppid(), pidB, pidBw, status);
    }
    exit(0);
}
/* Salida de la consola

airbook:dampsp santi$ gcc c202Process.c;./a.out 
TERM_PROGRAM=Apple_Terminal
TERM=xterm-256color
SHELL=/bin/bash
...
_=./a.out
B PID: 1311, A PID: 1310.
A PID: 1310, PPID: 319.
 desde A: B PIB: 1311/1311 (0)

*/
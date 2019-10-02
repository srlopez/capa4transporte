#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h> //sleep, pid

/*
   - parámetros en la línea de comandos
   - básico de manipulación de string
   - directivas de preprocesador #ifdef, #define

   - system()
   - execl()

*/

#define SYSTEM //SYSTEM, EXECL

int main(int arcg, char *argv[])
{

    pid_t pid_main;
    pid_t pid_parent;

    pid_main = getpid();
    pid_parent = getppid();

    printf("PID: %d\n", pid_main);
    printf("PPID: %d\n", pid_parent);

#ifdef SYSTEM
    /*
airbook:dampsp santi$ gcc c2Process01.c; ./a.out
Ejemplo de uso de system():
	Listado del directorio actual y envio a un fichero: 0
sh: notepad: command not found
	Editamos en GUI el fichero...0
	Este comando es erróneo: 32512
Fin de programa....

*/
    char cmdls[80], cmdedit[80], cmderror[80], cmdrm[80], outfile[80];
    strcpy(outfile, argv[0]);
    strcat(outfile, ".txt");

    strcpy(cmdls, "find . -maxdepth 1 -type f >  ");
    strcat(cmdls, outfile);
    
    strcpy(cmderror, "notepad 2>>");
    strcat(cmderror, outfile );
    strcat(cmderror, " " );
    strcat(cmderror, outfile );

    strcpy(cmdedit, "open -a TextEdit ");
    strcat(cmdedit, outfile);

    // strcpy(cmdrm, "rm  ");
    // strcat(cmdrm, outfile);

    printf("Ejemplo de uso de system():");
    printf("\n\tListado del directorio actual y envio a un fichero: ");
    printf("%d", system(cmdls));
    printf("\n\tEditamos en GUI el fichero...");
    printf("%d", system(cmdedit));
    printf("\n\tEste comando es erróneo: %d", system(cmderror));
    printf("%d", system(cmdrm)); // Borramos el archivo
/*
airbook:PSP01-Codigo santi$ gcc c201Process.c && ./a.out
Ejemplo de uso de system():
        Listado del directorio actual y envio a un fichero: 0
sh: gedit: command not found
        Abrimos con el gedit el fichero...0
        Este comando es erróneo: 32512
Fin de programa....
airbook:PSP01-Codigo santi$ 
*/
#endif

#ifdef EXECL
    printf("Ejemplo de execl()\n");

    printf("Busca el PID %d en la lista de procesos\n", pid_main);
    execl("/bin/ps", "ps", "-la", NULL);
    printf("¡¡¡ Esta sentencia printf no se ejecuta !!!\n");
    //Ni ninguna más tras el execl

/*
airbook:dampsp santi$ gcc c201Process.c;./a.out 
PID: 750
PPID: 319
Ejemplo de execl()
Busca el PID 750 en la lista de procesos
  UID   PID  PPID        F CPU PRI NI       SZ    RSS WCHAN     S             ADDR TTY           TIME CMD
 ...
    0   750   319     4106   0  31  0  4268408   1000 -      R+                  0 ttys000    0:00.00 ps -la
 ...
 airbook:dampsp santi$ 
*/
#endif


    sleep(3);
    printf("\nFin de programa....\n");
}

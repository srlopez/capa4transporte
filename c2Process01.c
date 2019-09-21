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
   - fork()


   
*/

#define EXECL //SYSTEM, EXECL

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
    strcpy(cmdedit, "open -a TextEdit ");
    strcat(cmdedit, outfile);
    strcpy(cmderror, "notepad  ");
    strcat(cmderror, outfile);
    strcpy(cmdrm, "( echo $$; echo $BASHPID );rm  ");
    strcat(cmdrm, outfile);

    printf("Ejemplo de uso de system():");
    printf("\n\tListado del directorio actual y envio a un fichero: ");
    printf("%d", system(cmdls));
    printf("\n\tEditamos en GUI el fichero...");
    printf("%d", system(cmdedit));
    printf("\n\tEste comando es erróneo: %d", system(cmderror));
    printf("%d", system(cmdrm)); // Borramos el archivo
/*
airbook:PSP01-Codigo santi$ gcc e1007System.c && ./a.out
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

    printf("Los archivos en el directorio son:\n");
    execl("/bin/ls", "ls", "-l", NULL);
    printf("¡¡¡ Esta sentencia printf no se ejecuta !!!\n");
    //Ni ninguna más tras el execl
#endif



    sleep(3);
    printf("\nFin de programa....\n");
}

/*


Operación   Procesos        Hilos
=========   =========       ==============
Crear       system          pthread_create
            fork
            exex    

Terminar    _exit()         pthread_exit

Código      wait()          pthread_yield
de retorno  waitpid()       

Identi      getpid()        
ficación    getppid()

Terminar    kill()          pthread_cancel



Procesos cooperantes
====================
En este tema hablaremos de procesos/hebras que cooperan:
- Comparten estado, normalmente mediante variables compartidas entre los diferentes procesos/hebras.
- Ejecución no determinista o difícil de reproducir, dado que esta sometida a condiciones de carrera.

Los procesos que cooperan se pueden utilizar para:
- Ganar velocidad, solapando actividades
- o realizando trabajo en paralelo.
- Compartir información entre trabajos.
- Estructurar mejor una aplicación 


Cómo se puede compartir
- Procesos independientes – El SO suministra medios para compartir memoria:
    Unix – IPC shared memory (shmget(), ...)
    Windows y Unix – archivos proyectados en memoria.
- Aplicaciones multihebradas – las hebras de una aplicación comparten memoria de forma natural, pues comparten el mismo espacio de direcciones.

Condición de carrera
Se produce una condición de carrera (race condition) cuando el resultado de la ejecución de dos o más procesos, que comparten variables comunes, depende de la velocidad relativa a la que cada proceso se ejecuta, es decir, del orden en el que se ejecutan las instrucciones.

Dos hebras ejecutan los código que se muestra abajo y comparten la variable i=0 (inicialemente) ¿Cuál de ellas gana?

#include <windows.h>
#include <stdio.h>
volatile INT i=0; // eliminar optimización compilador
void Incremento(void *) {
 while(i < 10) {
i= i + 1;
printf("Hebra 1 ha ganado\n");}
void Decremento(void *) {
while (i > -10) {
i= i- 1;
printf("Hebra 2 ha gandado\n"); }
void main(void) {
 HANDLE Hebras[2]; 
 Hebras[0]=CreateThread(0,0,Incremento,NULL,0, NULL);
 Hebras[1]=CreateThread(0,0,Decremento,NULL,0, NULL);
 WaitForMultipleObjects(2, handles, TRUE, INFINITE);
} // El valor de la variable depende de la ejecución


Ver 
    - productor consumidor = lector escritor
    - secciones criticas
https://lsi.ugr.es/jagomez/sisopi_archivos/3Sincronizacion.pdf

*/


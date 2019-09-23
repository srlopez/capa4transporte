#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <string.h>
#include <unistd.h>


pthread_mutex_t mutex;

struct thread_vars_t
{
  int number;

};


void longTask()
{
  /* A time taking task */
  sleep(rand()%2);
}


void *newtask(void *_number)
{
  struct thread_vars_t *vars = _number;

  /* BLOCK */
  pthread_mutex_lock(&mutex);
  /* BLOCK */

  int number = vars->number;
  longTask();
  vars->number = number+1;

  printf ("THREAD: number = %d\n", vars->number);
  
  /* UNBLOCK */
  pthread_mutex_unlock(&mutex);
  /* UNBLOCK */
  pthread_exit(NULL);
}

int main (int argc, char *argv[])
{
   pthread_t thread;
   int rc;
   int i;
   struct thread_vars_t *vars = malloc (sizeof(struct thread_vars_t));

   vars->number = 0;

   pthread_mutex_init(&mutex, NULL);

   printf ("Main process just started.\n");
   for (i=0; i<10; ++i)
     {
       rc = pthread_create(&thread, NULL, newtask, vars);
       if (rc)
     {
       printf("ERROR in pthread_create(): %d\n", rc);
       exit(-1);
     }
     }

   //longTask();

   // Hacer que el main espere a todas las tareas
   //pthread_join(NULL, NULL);

   printf ("Main process about to finish.\n");
   /* Last thing that main() should do */
   pthread_exit(NULL);
}
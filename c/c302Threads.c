#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <string.h>
#include <unistd.h>


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

  int number = vars->number;
  longTask();
  vars->number = number+1;

  printf ("THREAD id = %d\n", vars->number);

  pthread_exit(NULL);
}

int main (int argc, char *argv[])
{
   pthread_t thread;
   int rc;
   int i;
   struct thread_vars_t *vars = malloc (sizeof(struct thread_vars_t));

   vars->number = 0;

   printf ("Main recien empezado.\n");
   for (i=0; i<10; ++i)
     {
       rc = pthread_create(&thread, NULL, newtask, vars);
       if (rc)
     {
       printf("ERROR en pthread_create(): %d\n", rc);
       exit(-1);
     }
     }

  longTask();

   printf ("Main a punto de acabar.\n");
   /* Last thing that main() should do */
   pthread_exit(NULL);
}
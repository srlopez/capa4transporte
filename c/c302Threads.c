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
  /*  */
  sleep(rand() % 2);
}

void *newtask(void *_number)
{
  struct thread_vars_t *vars = _number;

  int number = vars->number;
  longTask(); // usaríamos number dentro de la tarea
  vars->number = number + 1;

  printf("THREAD number = %d\n", vars->number);

  pthread_exit(NULL);
}

int main(int argc, char *argv[])
{
  pthread_t thread;
  int rc;
  int i;
  struct thread_vars_t *vars = malloc(sizeof(struct thread_vars_t));

  vars->number = 0;

  printf("Main recien empezado.\n");
  for (i = 0; i < 10; ++i)
  // al lanzar 10 sumas deberíamos obtener el resultado de 10
  {
    rc = pthread_create(&thread, NULL, newtask, vars);
    if (rc)
    {
      printf("ERROR en pthread_create(): %d\n", rc);
      exit(-1);
    }
  }

  longTask();

  printf("Main a punto de acabar.\n");
  /* Lo último en main() que deberíamos hacer */
  pthread_exit(NULL);
}
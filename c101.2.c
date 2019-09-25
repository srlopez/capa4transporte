/*
        C 101
*/

#include <stdio.h>
#include <string.h>
#include <stdlib.h> //malloc
#include <assert.h>

static int Twice(int num);
char *MakeStringInHeap(const char *source);

int main()
{
  // --- Declaración de varibles Enteros ---
  char c;
  c = 'A';
  short s;
  int i;
  long l;
  // --- Declaración de variables de coma flotante ---
  float f;
  double d;
  long double ld;
  // --- Operadores ---
  //=, ==, ++, +,-,*,/,%, !,&& ||,+=
  // etc....

  /* --- printf ----
https://es.wikipedia.org/wiki/Printf

DATA TYPE	   MEMORY   RANGE                     FORMAT
            (BYTES)		                          SPECIFIER
short int	    2	    -32,768 	                  %hd
                    32,767
unsigned short int	2	0                         %hu
                    65,535	              
unsigned int	4	    0 	                        %u
                    4,294,967,295
int	          4	    -2,147,483,648            	%d
                    2,147,483,647
long int	    4	    -2,147,483,648            	%ld
                    2,147,483,647
unsigned long int	4	0                 	        %lu
                    4,294,967,295
long long int	8	    -(2^63)           	        %lld
                    (2^63)-1
unsigned long long int	8	0                     %llu
                    18,446,744,073,709,551,615	
signed char	  1	    -128      	                %c
                    127
unsigned char	1	    0     	                    %c
                    255
float	        4		                              %f
double	      8		                              %lf
long double	  12		                            %Lf
*/

  // --- control de flujo ---
  i = c == 'A' ? 11 : 7;

  if (c == 'B')
  {
    printf("true %d! \n", i);
  }
  else
  {
    printf("false %d! \n", i);
  };

  switch (i)
  {
  case 11:
    printf("Primer case\n");
    break; // <- Break evita continuar en el siguiente bloque 'case'
  case 44:
    printf("Segundo case\n");
  default:
    printf("default case\n");
  }

  while (i-- >= 0)
  { //<- operador -- ejecutado despues de la comparación
    printf("%d ", i);
  }
  printf("\n--- while ---\n");

  for (i = 10; i > 0; i--)
  {
    if (i < 5)
      continue;
    printf("%d ", i);
  }
  printf("\n--- for ---\n");

  do
  {
    printf("%d ", i++);
  } while (i <= 10);
  printf("\n--- do ---\n");

loop:;
  printf("%d ", i--);
  if (i > 0)
    goto loop;
  printf("\n--- goto ---\n");

  //--- structuras y uniones ---
  // https://www.programiz.com/c-programming/c-unions

  struct fraction
  {
    int numerator;
    int denominator;
  };

  struct fraction f1, f2; // declaramos dos fractions
  f1.numerator = 22;
  f1.denominator = 7;
  struct fraction f3 = {1, 2};
  struct fraction f4 = {.denominator = 2, .numerator = 1};
  f2 = f1;
  printf("cociente: %f\n", (double)f1.numerator / f1.denominator);
  printf("resto: %d\n", f1.numerator % f1.denominator);

  union u {
    short int i;
    char c;
    float f;
    char str[20]; // un array de char
  } data;

  union u d2;

  data.i = 10;
  data.f = 220.5;
  // 01000011 00100000
  //  C        space
  strcpy(data.str, "C Programming");

  printf("data.i : %d (%lu bytes)\n", data.i, sizeof(data.i));
  // 00100000 01000011 = 8259
  // little-endian

  printf("data.c : %c\n", data.c);
  printf("data.f : %f\n", data.f);
  printf("data.str : %s\n", data.str);

  // ---- arrays ---- base 0
  int scores[100];
  scores[0] = 13;
  scores[1] = 15;
  scores[99] = 42;

  int board[10][10]; // dos dimensiones
  board[0][0] = 13;
  board[9][9] = 13;

  struct fraction numbers[1000]; // array de structuras
  numbers[0].numerator = 22;
  numbers[0].denominator = 7;

  // --- punteros ---
  // recordar ---  NULL, e inicialización! ---
  // https://en.wikipedia.org/wiki/Null_pointer#History

  int *pi = NULL;
  char *pc;

  struct fraction *pf1, *pf2;
  struct fraction **fp;
  struct fraction fract_array[20];
  struct fraction *fract_ptr_array[20];
  struct node
  {
    int data;
    struct node *next;
  };

  pi = (int *)&scores; //Primer casting
  *pi = 11;
  printf("pi=%p, i=%d\n", (void *)pi, scores[0]); //otro casting

  pi = &(f1.numerator); // Apunta a otro int
  *pi = 22;
  pi = &(f1.denominator); // idem otro int
  *pi = 7;
  // Comprobar pi--
  printf("pi=%p, i=%d\n", (void *)pi, *(pi - 1));

  // --- funciones ---
  // abajo
  printf("Twice de %d = %d\n", 7, Twice(7));

  // ---- Strings + <strings.h>
  char string[1000];
  int len;
  strcpy(string, "Tenemos dos vidas,\n y la segunda comienza cuando te das cuenta\n que sólo tienes una.");
  len = strlen(string);
  printf("\"%s\"\ntiene %d carácteres\n", string, len);

  // --- typedef ---
  typedef struct fraction Fraction;
  Fraction fraction, f5;
  fraction.denominator = 7;
  f5.numerator = 6;

  typedef struct treenode *Tree;
  struct treenode
  {
    int data;
    Tree smaller, larger; // idem "struct treenode *smaller, *larger"
  };

  /*
https://es.wikibooks.org/wiki/Programaci%C3%B3n_en_C/Manejo_din%C3%A1mico_de_memoria
void* malloc(size_t size) 
void free(void* block) 
void* realloc(void* block, size_t size);
*/

  int a[1000];
  int *b;
  b = (int *)malloc(sizeof(int) * 1000);
  assert(b != NULL); // check
  a[123] = 13;
  b[123] = 13;
  free(b);

  char *str;
  str = MakeStringInHeap("Hola Mundo C101! desde el Heap\0");

  //---- Punteros a funciones
  // https://www.geeksforgeeks.org/function-pointer-in-c/


  // Sobreescritura 'errónea' de un fin de string
  struct sobreescritura
  {
    char stra[14];
    char strb[15];
  } test;

  strcpy(test.stra, "C Programming");
  strcpy(test.strb, "by Brian"); // W. Kernighan and Dennis M. Ritchie");

  printf("Antes--> \"%s\" (%lu)\n", test.stra, strlen(test.stra));
  char *p = test.stra + strlen(test.stra); //<- la dirección final de stra
  *p = '*';                                //<- y la sobreeescribimos
  printf("Después> \"%s\" (%lu)\n", test.stra, strlen(test.stra));
  p  = p - 7; //<- cambiamos el puntero
  *p = 0;     //<- finalizamos el string
  printf("y más--> \"%s\" (%lu)\n", test.stra, strlen(test.stra));


  printf("%s\n", str);
  free(str); //<- espacio de memoria creado en la pila. Lo liberamos
  return 0;
}

// ----- Funciones
/* devuelve el doble, lo triplica y se lo resta*/
static int Twice(int num)
{
  int result = num * 3;
  result = result - num;
  return (result);
}

char *MakeStringInHeap(const char *source)
{
  char *newString;
  newString = (char *)malloc(strlen(source) + 1);
  // +1 para el '\0'
  assert(newString != NULL);
  strcpy(newString, source);
  return (newString);
}

// void, by value y by reference, const.
// Hacer ejemplo swap
// Hacer ejemplo num primo
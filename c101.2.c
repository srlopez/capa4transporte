/*
        C 101
*/

#include <stdio.h>
#include <string.h>

static int Twice(int num);

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

%d ó %i	entero en base 10 con signo (int)
%u	entero en base 10 sin signo (int)
%o	entero en base 8 sin signo (int)
%x	entero en base 16, letras en minúscula (int)
%X	entero en base 16, letras en mayúscula (int)
%f	Coma flotante decimal de precisión simple (float)
%lf	Coma flotante decimal de precisión doble (double)
%e	La notación científica (mantisa / exponente), minúsculas (decimal precisión simple ó doble)
%E	La notación científica (mantisa / exponente), mayúsculas (decimal precisión simple ó doble)
%c	caracter (char)
%s	cadena de caracteres (string)
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

  // ---- Strings + <strings.h>
  char string[1000];
  int len;
  strcpy(string, "Tenemos dos vidas, y la segunda comienza cuando te das cuenta que sólo tienes una.");
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

  // --- funciones ---
  // abajo
  printf("Twice de %d = %d\n", 7, Twice(7));

  //---- Punteros a funciones
  // ∫https://www.geeksforgeeks.org/function-pointer-in-c/

  printf("Hello, World C101! \n");
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

// void, by value y by reference, const.
// Hacer ejemplo swap
// Hacer ejemplo num primo
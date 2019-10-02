/* 

Práctica de punteros
Hacer una función que intercambie los valores de dos variables

Ojo!!!!
La he puesto con errores!!!!!

Aquí hay ejemplos con punteros
https://www.programiz.com/c-programming/c-pointers


 */
#include <stdio.h>

/*
   Swap declaración  
   No hace falta si está definida
   antes de su invcación
*/

//void swap(int*, int*); 
int main()
{
   int x, y;

   printf("Introduce valores para x e y\n");
   scanf("%d%d", &x, &y);

   printf("Antes de swap\nx = %d\ny = %d\n", x, y);

   swap(&x, &y);

   printf("Después de swap\nx = %d\ny = %d\n", x, y);

   return 0;
}
/*
Swap  definición
*/
void swap(int *a, int *b)
{
   int *t;

   *t = *b;
   *b = *a;
   *a = t;
}

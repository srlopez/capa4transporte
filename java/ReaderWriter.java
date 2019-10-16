/*
RAMA Problema propuesto

Escribe una clase llamada ReaderWriter que cree dos threads 
que accedan simultáneamente a un buffer de 10.000 (args 0) enteros. 
Uno de ellos lee en el buffer y el otro escribe en el mismo. 

Indefinidamente, ( o args 2) el thread escritor debe escribir el mismo valor (o no) en todos 
los elementos del buffer incrementando en uno el valor en cada pasada. 

El thread lector debe ir comprobando que todos los números
del buffer son iguales, mostrando un mensaje de error 
en caso contrario o un mensaje de
"correcto" si la condición se cumple. 


El código a realizar utilizará un monitor para acceder
al buffer si se indica un parámetro (args 1) al ejecutar el programa. 
En caso contrario, los threads
accederán al buffer sin hacer uso del monitor.


Esta no es el tradicional problema del lector Escritor.
La lectura y/o escritura se hce de todo el bloque, 
con lo que el escritor puede sobre escribir el buffer sin que el 
lector lo haya leido, y el lector puede releer el bloque sin que el escritor
lo haya escrito!!!!!


*/

import java.nio.IntBuffer;
import java.util.Random;

class Escritor extends Thread {
    private int bloqueo, len, veces;
    private IntBuffer buffer;
    private Object mutex;
    private int contador;

    Random rand = new Random();

    Escritor(int opcion, Object mutex, IntBuffer buf, int veces) {
        this.bloqueo = opcion;
        this.veces = veces;

        this.buffer = buf;
        this.mutex = mutex;
        this.contador = 0;
    }

    private void escribir() {
        int i;
        System.out.println("Escritor " +  contador);

        buffer.put(0, contador);
        for (i = 1; i < buffer.capacity(); i++) {
            if (rand.nextInt(10) < 3)
                buffer.put(i, contador);
            else
                buffer.put(i, 3453);
            //System.out.println("Escritor " + i + " " + buffer.get(i));
        }
            contador++;
    }

    public void run() {
        int v = veces;
        while (v-->0) {
            if (this.bloqueo == 1) {
                synchronized (this.mutex) {
                    escribir();
                }
            } else {
                escribir();

            }
        }
    }
}

class Lector extends Thread {
    private int bloqueo, veces;
    private IntBuffer buffer;
    private Object mutex;

    Lector(int opcion, Object mutex, IntBuffer buf, int veces) {
        this.bloqueo = opcion;
        this.buffer = buf;
        this.mutex = mutex;
        this.veces = veces;
    }

    private void leer() {
        int i=0;
        System.out.print(buffer.capacity()+"[");
        while(i<buffer.capacity()){
            System.out.print(buffer.get(0) == buffer.get(i)?".":"X");
            i++;
        }
        System.out.println("]"+buffer.get(0));

    }

    public void run() {
        int v = veces;
        while (v-->0) {
            if (this.bloqueo == 1) {
                synchronized (this.mutex) {
                    leer();
                }
            } else {
                leer();
            }
        }
    }
}

public class ReaderWriter {
    public static void main(String[] args) {
        int len = args.length < 1 ? 10 : Integer.parseInt("0" + args[0]);
        int msn = args.length < 2 ? 1 : Integer.parseInt("0" + args[1]);
        int veces = args.length < 3 ? 5 : Integer.parseInt("0" + args[2]);

        System.out.println("len " + len);
        System.out.println("mutex " + msn);
        System.out.println("veces " + veces);

        IntBuffer buf = IntBuffer.allocate(len);
        Object mutex = new Object();
        // Modificar primer parámetro entre:
        // 0 = No usar mutex
        // 1 = Usar mutex
        Lector r = new Lector(msn, mutex, buf, veces);
        Escritor w = new Escritor(msn, mutex, buf, veces);
        r.start();
        w.start();
        try {
            r.join();
            w.join();
        } catch (InterruptedException ex) {
            System.out.println("Hilo principal interrumpido.");
        }
        System.out.println("Proceso acabando.");
    }
}

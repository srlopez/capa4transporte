/*
airbook:java santi$ gcc mayusculas.c 
airbook:java santi$ javac Mayusculas.java
airbook:java santi$ java Mayusculas
hola amigo
[HOLA AMIGO]
desde java a c y vuelta en mayusculas
[DESDE JAVA A C Y VUELTA EN MAYUSCULAS]
Finalizando
airbook:java santi$ 

*/

import java.io.*;

public class Mayusculas {
    public static void main(String args[]) {
    String line;
    try{
        Process hijo = new ProcessBuilder("./a.out").start();
        BufferedReader br = new BufferedReader(new InputStreamReader(hijo.getInputStream()));
        PrintStream ps = new PrintStream(hijo.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        // Para salir del bucle se puede pulsar Ctrl-D
        // Finaliza la entrada
        while ((line = in.readLine()) != null) {
            ps.println(line);
            ps.flush(); // IMP: Comprueba envío de datos
            if ((line = br.readLine()) != null) 
                System.out.println("["+line+"]");
        }
        System.out.println("Finalizando");
    } catch (IOException e) {
        System.out.println("Error ocurrió durante la ejecución. Descripción del error: " + e.getMessage());
        }
    }
}
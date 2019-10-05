/*

airbook:java santi$ javac RunProcess.java 
airbook:java santi$ java RunProcess open -a TextEdit RunProcess.java
La ejecución de [open, -a, TextEdit, RunProcess.java] devuelve 0
airbook:java santi$ 

En OSX el programa arranca la aplicación y vuelve.
En OSX hemos de añadir open -W -a TextEdit RunProcess.java
En Windows el programa espera hasta que cerremos la aplicación lanzada
*/

import java.io.IOException;
import java.util.Arrays;

class RunProcess {

    public static void main(String[] args) throws IOException {
 
        if (args.length <= 0) {
        System.err.println("Se necesita un programa a ejecutar");
        System.exit(-1);
        }

        ProcessBuilder pb = new ProcessBuilder(args);
        try {
            Process process = pb.start();
            //Thread.sleep(3000);
            //process.destroy();
            //process.destroyForcibly();
            int retorno = process.waitFor();
            System.out.println("La ejecución de " +
            Arrays.toString(args) + " devuelve " + retorno);
        }catch(IOException ex){
            System.err.println("Excepción de E/S!!");
            System.exit(-1);
        }catch(InterruptedException ex){
            System.err.println("El proceso hijo finalizó de forma incorrecta");
            System.exit(-1);
            }
        }
}
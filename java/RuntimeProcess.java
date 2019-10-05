/*
airbook:java santi$ javac RuntimeProcess.java
airbook:java santi$ java RuntimeProcess open -a TextEdit RunProcess.java
airbook:java santi$ 

Este programa en OSX no 'destroy' la aplicación
En Windows si lo hace
*/

import java.io.IOException;

public class RuntimeProcess {
    public static void main(String[] args) throws InterruptedException{
        if (args.length <= 0) {
        System.err.println("Se necesita un programa a ejecutar");
        System.exit(-1);
        }
    Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(args);
            Thread.sleep(3000);
            process.destroy();
        }catch(IOException ex){
            System.err.println("Excepción de E/S!!");
            System.exit(-1);
        }
    }
}
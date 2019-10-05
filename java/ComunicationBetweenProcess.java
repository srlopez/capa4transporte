/*
airbook:java santi$ javac ComunicationBetweenProcess.java
airbook:java santi$ java ComunicationBetweenProcess ls -la
Salida del proceso [ls, -la]:
total 72
drwxr-xr-x  11 santi  staff   352  5 oct 11:46 .
drwxr-xr-x   7 santi  staff   224  2 oct 11:37 ..
-rw-r--r--   1 santi  staff  1583  5 oct 11:47 ComunicationBetweenProcess.class
...
ç-rw-r--r--   1 santi  staff  2771  5 oct 10:47 SimpleThreads.java
airbook:java santi$ 

En Windows el parámetro CMD.EX /C DIR
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
public class ComunicationBetweenProcess {

    public static void main(String args[]) throws IOException {
        Process process = new ProcessBuilder(args).start();
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        System.out.println("Salida del proceso " + Arrays.toString(args) + ":");
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

}
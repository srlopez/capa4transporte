/*
airbook:java santi$ javac Ejemplo8.java 
airbook:java santi$ java Ejemplo8
airbook:java santi$ cat ejemplo8.txt ejemplo8.err
-rw-r--r--  1 santi  staff  1158  5 oct 12:46 ComunicationBetweenProcess.java
...
-rw-r--r--  1 santi  staff  2771  5 oct 10:47 SimpleThreads.java
/Users/santi/Dev/PSP/dampsp/java
ls: *jm*: No such file or directory
airbook:java santi$ 
*/

import java.io.File;
import java.io.IOException; 

public class Ejemplo8 { 
  public static void main(String args[]) throws IOException {
    ProcessBuilder pb = new ProcessBuilder("bash");
    
    File fBat = new File("ejemplo8.sh");
    File fOut = new File("ejemplo8.txt");
    File fErr = new File("ejemplo8.err");
 
    pb.redirectInput(fBat);
    pb.redirectOutput(fOut);
    pb.redirectError(fErr); 
    pb.start(); 

  }
}// Ejemplo8

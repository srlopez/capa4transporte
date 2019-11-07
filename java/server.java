
import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
//import java.util.StringTokenizer;
//import java.io.OutputStream;
//import java.io.InputStream;
//import java.util.stream.Collectors;


public class server implements Runnable{ 
    private Socket connect;

    public server(Socket client) {
        connect = client;
    }
    
    //Arranca el proceso
    public static void main(String[] args) {
        try {
            int port = Integer.valueOf(args[0]);

            ServerSocket escucha = new ServerSocket(port);
            System.out.println("escuchando en: " + port + "\n");
            
            while (true) {
                server miServer = new server(escucha.accept());
                System.out.println("accept en (" + new Date() + ")");

                // y se auto duplica cada vez que se acepta una conexión
                new Thread(miServer).start();
            }
            
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) { 
            System.err.println(e.getMessage());
        } 
    }

    //En el thread tratamos la conexión
    @Override
    public void run() {
        BufferedReader in = null; 
        String request = null;

        PrintWriter out = null;


        try {
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            out = new PrintWriter(connect.getOutputStream());

            String input = in.readLine();

            // Muchas clases para parsear la primera linea
            // StringTokenizer parse = new StringTokenizer(input);
            // String method = parse.nextToken().toUpperCase(); 
            // request = parse.nextToken().toLowerCase();
            // System.out.println(method +": " + request );

            // OJO: Si activo el bucle, la página 
            // se queda esperando a localhost .... y
            // acaba dando ERR_EMPTY_RESPONSE cuando cierro
            // el server

            // do {
                 System.out.println(input );
            // } while ((input = in.readLine()) != null); 

            //a lo facil para escribir
            out.println("HTTP/1.1 200 ");
            out.println("Content-Type: text/html"); //text/plain
            out.println("Connection: closed");
            out.println("");
            out.println ("<p style='font-family:courier;font-size:160%;'>HOLA  (" + new Date() + ")</p>");
            out.flush();


            in.close();
            out.close();
            connect.close(); 
        } catch (IOException ioe) {
            System.err.println(ioe);
        } 
        System.out.println("...");
    }
}
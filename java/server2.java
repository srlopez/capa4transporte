
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


public class server2 implements Runnable{ 
    private Socket connect;

    public server2(Socket client) {
        connect = client;
    }
    
    //Arranca el proceso
    public static void main(String[] args) {
        try {
            int port = Integer.valueOf(args[0]);

            ServerSocket escucha = new ServerSocket(port);
            System.out.println("escuchando en: " + port + "\n");
            
            while (true) {
                server2 miServer = new server2(escucha.accept());
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

            for( int i=0; i<5;i++){
 
                String input = in.readLine();

                //do {
                    System.out.println("Recibo: "+input );
                //} while ((input = in.readLine()) != null); 

                //a lo facil para escribir
                out.println ( Integer.valueOf(input)*i);
                out.flush();
            }
           
            in.close();
            out.close();
            connect.close(); 
 
        } catch (IOException ioe) {
            System.err.println(ioe);
        } 
        System.out.println("...");
    }
}
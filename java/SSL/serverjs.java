import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import javax.net.ssl.SSLServerSocketFactory;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.SSLHandshakeException;


public class serverjs implements Runnable{ 
    private Socket connect;
    private String[] map = {
        "áéíóúÑñ!?¿"
    };

    private String encoding ="ISO-8859-1";//"Latin-1"; //"UTF-8";   //"ISO-8859-1"

    public serverjs(Socket client) {
        connect = client;
    }
    

    // Arrancar el proceso
    // En una consola      java server (4444)     
    // En otra consola     java client                en la misma máquina 
    //                     java client 4444 ipserver  en otro equipo

    public static void main(String[] args) {
        try {
            int port = args.length == 0? 4444: Integer.valueOf(args[0]);

            // System.setProperty("javax.net.ssl.keyStore", "keystore");
            // System.setProperty("javax.net.ssl.keyStorePassword", "123456");
            // SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            // ServerSocket socket = factory.createServerSocket(port);

            ServerSocket socket = new ServerSocket(port);
            System.out.println("SERVER listening in " + port);
            
            while (true) {
                server miServer = new server(socket.accept());

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
        Random random = new Random();

        PrintWriter out = null;
        String ip = connect.getRemoteSocketAddress().toString();

        System.out.println("SERVER accept: "+ip);
        try {
            String input = null; 
            in = new BufferedReader(new InputStreamReader(connect.getInputStream()));//,encoding));
            out = new PrintWriter(connect.getOutputStream());

            input = in.readLine(); 
            while(input.compareTo("END")!=0){
                System.out.println("SERVER input: "+input+" ("+ip+")");

                //out.println (new String(map[random.nextInt(map.length)].getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                out.println (map[random.nextInt(map.length)]);
                out.flush();
                input = in.readLine(); 
            }
            System.out.println("SERVER BYE BYE "+ip);
            out.println ("BYE BYE");
            out.flush();

            in.close();
            out.close();
            connect.close(); 
 
        } catch (IOException ioe) {
            System.err.println(ioe);
        } 
        System.out.println("SERVER close:  "+ip);
    }
}
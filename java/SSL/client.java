
import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import java.net.Socket;
import java.net.InetSocketAddress;

public class client { 
 

    //Arranca el proceso
    public static void main(String[] args) {  // PORT IP // PORT
        try {
            int port = args.length == 0? 4444: Integer.valueOf(args[0]);
            String ip = args.length <2?"localhost":args[1];
            String input = "Hola Mundo";

            //InputStream is = null;
            BufferedReader in = null; 
            PrintWriter out = null;
            BufferedReader keyboard = null;

            System.out.println("CLIENT connecting ... " +ip+ ":"+ port);

            System.setProperty("javax.net.ssl.trustStore", "truststore");
            System.setProperty("javax.net.ssl.trustStorePassword", "123456");
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket socket = (SSLSocket) factory.createSocket(ip, port);

            // Socket socket = new Socket();
            // InetSocketAddress server = new InetSocketAddress(ip, port);
            // socket.connect(server);

            System.out.println("CLIENT connect en (" + new Date() + ")");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            keyboard = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));

            out = new PrintWriter(socket.getOutputStream());

            while(input.compareTo("BYE BYE")!=0){
                System.out.println("Mensaje: (END para acabar)" );
                out.println(keyboard.readLine());
                out.flush();

                input = in.readLine();
                System.out.println(input);
            }

            in.close();
            out.close();
            socket.close();
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) { 
            System.out.println("CLIENT error: "+e.getMessage());
        } 
    }

}
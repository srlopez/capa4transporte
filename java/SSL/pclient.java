
import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import java.net.Socket;
import java.net.InetSocketAddress;

import java.util.ArrayList;

public class pclient { 
 

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

            in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "ISO-8859-1"));
            keyboard = new BufferedReader(new InputStreamReader(System.in, "ISO-8859-1"));
            out = new PrintWriter(socket.getOutputStream());


            //LECTURA DEL BODY DEL MENSAJE
            String line = "";
            Integer i = 1;
            ArrayList<String> body = new ArrayList<String>(); 

            while(input.compareToIgnoreCase("BYE BYE")!=0){
                

                //LECTURA DE CABECERA
                System.out.println("");
                System.out.println("          Pulsa ENTER (MSG) para enviar un mensaje,");
                System.out.println("          o escribe END para acabar la comunicación");
                System.out.print("$ (MSG)> " );
                System.out.flush();
                body.clear();
                i = 0;

                line = keyboard.readLine(); //<-- PIDO LA CABECERA POR KEYBOARD
                switch(line) {

                    case "END":
                        //CABECERA
                        body.add("END"); 
                        //BODY
                        body.add(""); //<-- se añade la última linea  vacía
                        break;

                    default: //MSG = ENTER en CABECERA
                        //CABECERA
                        body.add("MSG"); 
                        //PIDO EL BODY DEL MENSAJE
                        do {
                            System.out.printf("$ Body #%d> ",++i );
                            System.out.flush();
                            line = keyboard.readLine();
                            body.add(line); //<-- se añade la última linea EL RETURN vacía
                        } while (line.compareTo("")!=0);
            
                    }

                // ENVIO DE CABECERA Y BODY 
                for (String s : body) out.println(s);
                out.flush();

                // LECTURA DE LA RESPUESTA
                try {
                    input = in.readLine();
                } catch (Exception e) {
                    input = "BYE BYE";
                    System.out.println("OMG!!! ME HAN ECHADO!!!");
                }

                System.out.println(input.replace("@@", "\n"));
            }

            in.close();
            out.close();
            socket.close();
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) { 
            System.out.println("CLIENT error: "+e.getMessage());
        } 
    }

}
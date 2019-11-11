
import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
//import java.util.StringTokenizer;
//import java.io.OutputStream;
import java.io.InputStream;
//import java.util.stream.Collectors;

//import org.omg.CosNaming.IstringHelper;

import java.net.InetSocketAddress;

public class server2client { 
 

    //Arranca el proceso
    public static void main(String[] args) {
        try {
            String ip = args[0];
            int port = Integer.valueOf(args[1]);

            System.out.println("a");

            Socket s = new Socket();
            System.out.println("b");

            System.out.println(ip+ ":"+ port + "\n");
            InetSocketAddress server = new InetSocketAddress(ip, port);

            InputStream is = null;
            BufferedReader in = null; 
            PrintWriter out = null;

            s.connect(server);
            System.out.println("connect en (" + new Date() + ")");
            is = s.getInputStream();
            in = new BufferedReader(new InputStreamReader(is));
            out = new PrintWriter(s.getOutputStream());



            for(int i=0; i<5; i++){
               
                     //a lo facil para escribir
                     // Mensaje de ida
                     out.println(i);
                     out.flush();

                    // Respuesta del servidor
                    String input = in.readLine();
                    //byte[] msg = new byte[256];
                    //is.read(msg);
                    //String input = new String(msg) ;
                    //do {
                         System.out.println(input );
                    //} while ((input = in.readLine()) != null); 


            }
            in.close();
            out.close();
            s.close();
        } catch (IOException | NumberFormatException | ArrayIndexOutOfBoundsException e) { 
            System.out.println("error:");
            System.err.println(e.getMessage());
        } 
    }

}
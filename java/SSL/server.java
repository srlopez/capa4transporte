import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import javax.net.ssl.SSLServerSocketFactory;
import java.net.SocketTimeoutException;
import javax.net.ssl.SSLHandshakeException;


public class server implements Runnable{ 
    private Socket connect;
    private String[] map = {
        "Tenemos dos vidas: la segunda comienza cuando nos damos cuenta de que sólo tenemos una. (Confucio)",	
        "Damas y caballeros, estos son mis principios. Si no les gustan tengo otros",
        "Quien se transforma a sí mismo, transforma el mundo",
        "Lo que haces por ti se desvanece cuando mueres. Lo que haces por el resto conforma tu legado. Kalu Ndukwe Kalu",
        "Yo no soy vegetariano, pero como animales que sí lo son",
        "Nunca pertenecería a un club que admitiera como miembro a alguien como yo",
        "La experiencia no es lo que te sucede, sino lo que haces con lo que te sucede",
        "Jamás olvido una cara, pero en su caso estaré encantado de hacer una excepción",
        "Cada santo tiene un pasado y todo pecador tiene un futuro",
        "Los hombres incapaces de pensar por sí mismos, no piensan en absoluto. Oscar Wilde",
        "Es mejor estar callado y parecer tonto, que hablar y despejar las dudas definitivamente",
        "La vida no trata de encontrarse a uno mismo, sino de crearse a uno mismo. George Bernard Shaw",
        "Si no te gusta algo, cámbialo. Si no lo puedes cambiar, cambia tu actitud. Facebook",
        "Hasta cuando bromeo digo la verdad. Y no es ningún chiste",
        "Tanto si piensas que puedes como si no, estarás en lo cierto. Henry Ford",
        "Yo no soy vegetariano, pero como animales que sí lo son",
        "Disculpen si les llamo caballeros, pero es que no los conozco muy bien"
    };

    public server(Socket client) {
        connect = client;
       
       
    }
    

    // Arrancar el proceso
    // En una consola      java server (4444)     
    // En otra consola     java client                en la misma máquina 
    //                     java client 4444 ipserver  en otro equipo

    public static void main(String[] args) {
        try {
            int port = args.length == 0? 4444: Integer.valueOf(args[0]);

            System.setProperty("javax.net.ssl.keyStore", "keystore");
            System.setProperty("javax.net.ssl.keyStorePassword", "123456");
            SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            ServerSocket socket = factory.createServerSocket(port);

            // ServerSocket socket = new ServerSocket(port);
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
            in = new BufferedReader(new InputStreamReader(connect.getInputStream(),"UTF-8"));
            out = new PrintWriter(connect.getOutputStream());

            input = in.readLine(); 
            while(input.compareTo("END")!=0){
                System.out.println("SERVER input: "+input+" ("+ip+")");

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
        System.out.println("...");
    }
}
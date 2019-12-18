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
        "Tenemos dos vidas: la segunda comienza cuando nos damos cuenta de que sólo tenemos una. @@Confucio",	
        "Damas y caballeros, estos son mis principios. Si no les gustan tengo otros @@Groucho Marx",
        "Quien se transforma a sí mismo, transforma el mundo@@ Dalai Lama",
        "Lo que haces por ti se desvanece cuando mueres.@@Lo que haces por el resto conforma tu legado.@@Kalu Ndukwe Kalu",
        "Yo no soy vegetariano, pero como animales que sí lo son@@Groucho Marx",
        "Nunca pertenecería a un club que admitiera como miembro a alguien como yo@@Groucho Marx",
        "La experiencia no es lo que te sucede, sino lo que haces con lo que te sucede@@Aldous Huxley",
        "Jamás olvido una cara, pero en su caso estaré encantado de hacer una excepción@@Groucho Marx",
        "Cada santo tiene un pasado y todo pecador tiene un futuro@@Oscar Wilde",
        "Los hombres incapaces de pensar por sí mismos, no piensan en absoluto.@@Oscar Wilde",
        "Es mejor estar callado y parecer tonto, que hablar y despejar las dudas definitivamente@@Groucho Marx",
        "La vida no trata de encontrarse a uno mismo, sino de crearse a uno mismo.@@George Bernard Shaw",
        "Si no te gusta algo, cámbialo. Si no lo puedes cambiar, cambia tu actitud@@Facebook",
        "Hasta cuando bromeo digo la verdad. Y no es ningún chiste@@Groucho Marx",
        "Tanto si piensas que puedes como si no, estarás en lo cierto.@@Henry Ford",
        "Yo no soy vegetariano, pero como animales que sí lo son@@Groucho Marx",
        "Disculpen si les llamo caballeros, pero es que no los conozco muy bien@@Groucho Marx",
        "- Buenos días, quería una camiseta de un personaje inspirador.@@"+
        "- ¿Ghandi?@@"+
        "- No, mediani.",
        "- ¿Dónde cuelga Superman su supercapa?@@"+
        "- En superchero",
        "-  Buenos días. Busco trabajo.@@"+
        "- ¿Le interesa de jardinero?@@"+
        "- ¿Dejar dinero? ¡Si lo que busco es trabajo!",
        "- ¿Qué es un pez en un cine?@@"+
        "- Un mero espectador",
        "- Hola, soy paraguayo y quiero pedirle la mano de su hija para casarme con ella.@@"+
        "- ¿Para qué?@@"+
        "- Paraguayo.",
        "- ¿Para que van una caja al gimnasio?@@"+
        "-  Para hacerse caja fuerte.",
        "¿Si los zombies se deshacen con el paso del tiempo?@@¿zombiodegradables?",
        "- ¿Cuál es el país que primero te llama y luego te asusta?@@"+
        "- EE.UU.",
        "- Hola, me llamo Paco. ¿Y tú?@@"+
        "- No, yo no",
        "- Intenté enamorar a una programadora, pero no se de Java@@"+
        "- ¿Has probado com Pilar?",
        "¿QuiénSabeComoArreglarLaBarraEspaciadora?.",
        "- ¿Qué son 8 bocabits?@@"+
        "- Un bocabyte.",
        "- Dime con quién andas y te diré quién eres.@@"+
        "- No ando con nadie...@@"+
        "- Eres feo.",
        "- ¡Rápido, necesitamos sangre!@@"+
        "- Yo soy 0 positivo.@@"+
        "- Pues muy mal, necesitamos una mentalidad optimista.",
        "- Van dos ciegos y le dice uno al otro:@@"+
        "- Ojalá lloviera...@@"+
        "- Ojalá yo también...",
        "- Mamá, mamá, ¿me haces un bocata de jamón?@@"+
        "- ¿York?@@"+
        "- Sí, túrk.",
        "- Te detesto.@@"+
        "- K de kilo.@@"+
        "- ¿Qué dices?@@"+
        "- No sé... has empezado tú.",
        "- No tienes ni idea de lo que soporto@@"+
        "- Una ciudad de Portugal",
        "- Papá, que significa sintaxis@@"+
        "- Que tienes que coger el autobús@@"+
        "- Gracias papá, eres genial@@"+
        "- Uno que ha estudiao",
        "- Una cita por Tinder, todo son nervios:@@"+
        "- Hola, soy Antonio@@"+
        "- Hola... soy Sara... Vaya, si eres vizco@@"+
        "- No, lo que pasa es que tengo un ojo tan bonito que el otro se lo queda mirando",
        "- ¡Hombre! ¡Cristiano Ronaldo! Beienvenido@@"+
        "- Muy amable camarero, ponga una de calamares a la rumana, por favor.@@"+
        "- Será a la romana.@@"+
        "- Irina, cariño, dile al camarero este de dónde eres...",
        "- Tu rezas antes de comer.@@"+
        "- No señor. Mi madre es buena cocinera.",
        "- ¿Tu sabías que las cajas negras de los aviones en realidad son naranjas?@@"+
        "- ¡Que me dices! ¿no son cajas?",
        "Google es mujer. @@No te deja terminar una frase y ya te esta dando sugerencias.",
        "-  Amor mío, me gustaría que todo volviese a ser como antes.@@"+
        "- ¿Cómo cuando nos conocimos?@@"+
        "- No, antes...",
        "- Dos cojos (con pata de palo),... y se va la luz en el ascensor:@@"+
        "- ¡Qué mala pata!@@"+
        "- ¡Quema la tuya!!!"
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
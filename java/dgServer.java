import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class dgServer {

public static String IP = "239.0.0.1";
public static final int PORT = 4567;
public static final String FIN = "fin";

   public static void send(String str, String ipAddress, int port) throws IOException {
      
      System.out.println(">> "+str);

      byte[] msg = str.getBytes();
      InetAddress group = InetAddress.getByName(ipAddress);
      DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);

      DatagramSocket socket = new DatagramSocket();
      socket.send(packet);
      socket.close();
   
    }

   public static void main(String[] args) throws IOException {
      if (args.length>0) { IP="localhost"; } //Unicast
      System.out.println("| "+IP+" |");
      do{
        send("mensaje i", IP, PORT);
      }while(1==2);
      send(FIN, IP, PORT);
   }
}
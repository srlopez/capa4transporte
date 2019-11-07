import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class mcServer {

public static final String IP = "239.0.0.1";
public static final int PORT = 4567;
public static final String FIN = "fin";

   public static void send(String message, String ipAddress, int port) throws IOException {
      
      DatagramSocket socket = new DatagramSocket();
      InetAddress group = InetAddress.getByName(ipAddress);
      byte[] msg = message.getBytes();
      DatagramPacket packet = new DatagramPacket(msg, msg.length, group, port);
      socket.send(packet);
      socket.close();
   
    }

   public static void main(String[] args) throws IOException {
      do{
        send("mensaje i", IP, PORT);
      }while(1==2);
      send(FIN, IP, PORT);
   }
}
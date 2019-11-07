import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class dgClientM implements Runnable {

/*
224.0.1.0	238.255.255.255	Internet
239.0.0.0	239.255.255.255	local
*/

   public static String IP = "239.0.0.1"; //Multicast
   public static final int PORT = 4567;
   public static final String FIN = "fin";
   public static final int SIZE = 1024;// 1K máximo creo! 64K de paquete

   public static void main(String[] args) {
      System.out.println("MULTICAST");
      new Thread(new dgClientM()).start();
   }

   public void recibir(String ip, int port) throws IOException {
      byte[] buffer=new byte[SIZE];
      String msg;

      InetAddress group=InetAddress.getByName(IP);
      MulticastSocket socket = new MulticastSocket(PORT);
      socket.joinGroup(group);
   
      System.out.println("esperando al fin ...");
      do{
         DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
         socket.receive(packet);
         msg=new String(packet.getData(),
                        packet.getOffset(),
                        packet.getLength());

         //System.out.println(buffer);
         System.out.println("<< "+msg);
      } while(! FIN.equals(msg));

      //Si Multicast
      socket.leaveGroup(group);
      socket.close();
   }

   @Override
   public void run(){
   try {
        recibir(IP,PORT);
   }catch(IOException e){
        e.printStackTrace();
   }
}
}
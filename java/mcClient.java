import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;



public class mcClient implements Runnable {

/*
224.0.1.0	238.255.255.255	Internet
239.0.0.0	239.255.255.255	local
*/
   public static final String IP = "239.0.0.1";
   public static final int PORT = 4567;
   public static final String FIN = "fin";
   public static final int SIZE = 1024;// 1K máximo creo! 64K de paquete

   public static void main(String[] args) {
      new Thread(new mcClient()).start();
   }

   public void recibir(String ip, int port) throws IOException {
      byte[] buffer=new byte[SIZE];
      String msg;
      MulticastSocket socket=new MulticastSocket(PORT);
      InetAddress group=InetAddress.getByName(IP);
      socket.joinGroup(group);
      do{
         System.out.println("esperando al fin ...");
         DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
         socket.receive(packet);
         msg=new String(packet.getData(),
                        packet.getOffset(),
                        packet.getLength());

         System.out.println(msg);
      } while(! FIN.equals(msg));

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
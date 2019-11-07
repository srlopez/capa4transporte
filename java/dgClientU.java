import java.io.IOException;
import java.net.DatagramPacket;

import java.net.InetSocketAddress;
import java.net.DatagramSocket;



public class dgClientU implements Runnable {

/*
224.0.1.0	238.255.255.255	Internet
239.0.0.0	239.255.255.255	local
*/
   public static final String IP = "localhost";
   public static final int PORT = 4567;
   public static final String FIN = "fin";
   public static final int SIZE = 1024;// 1K máximo creo! 64K de paquete

   public static void main(String[] args) {
      System.out.println("UNICAST");
      new Thread(new dgClientU()).start();
   }

   public void recibir(String ip, int port) throws IOException {
      byte[] buffer=new byte[SIZE];
      String msg;

      // No podemos tener dos clientes esperando en IP:PORT
      InetSocketAddress addr = new InetSocketAddress(IP,PORT);
      DatagramSocket socket = new DatagramSocket(addr);
      
      System.out.println("esperando al fin ...");
      do{
         DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
         socket.receive(packet);
         msg=new String(packet.getData(),
                        packet.getOffset(),
                        packet.getLength());

         //System.out.println(packet.getData());
         System.out.println("<< "+msg);
      } while(! FIN.equals(msg));

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
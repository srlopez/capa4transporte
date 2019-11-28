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
   public static int PORT = 4567;
   public static final String FIN = "fin";
   public static final int SIZE = 1024;// 1K, máximo 64K de paquete

   // No es necesario lanzar threads aquí
   // Recibe de una IP PORT una lista
   // de mensajes hasta que recibe el fin

   public static void main(String[] args) {
      if (args.length>0) { 
         PORT= Integer.parseInt(args[0]);
      } //Unicast  Si paso un parametro es IP Multicast
      System.out.println("MULTICAST");
      new Thread(new dgClientM()).start();
   }

   // Pueden lanzarse varios clientes Multicast en la misma máquina
   // Lee mensajes hasta 'fin'
   public void recibir(String ip, int port) throws IOException {
      byte[] buffer=new byte[SIZE];
      String msg;

      InetAddress group=InetAddress.getByName(IP);
      MulticastSocket socket = new MulticastSocket(PORT);
      socket.joinGroup(group);
   
      System.out.println("esperando a "+FIN+" ...");
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
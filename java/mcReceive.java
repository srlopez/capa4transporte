import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class mcReceive {

   public static void main(String[] args) throws IOException {
       
        InetAddress group = InetAddress.getByName("228.5.6.7");
        MulticastSocket s = new MulticastSocket(6789);
        s.joinGroup(group);
        Scanner scanner = new Scanner(System.in);
// join a Multicast group and send the group salutations
        String msg = "";
    while (! msg.equals("fin")) {
   

        byte[] buf = new byte[1000];
        DatagramPacket recv = new DatagramPacket(buf, buf.length);
        s.receive(recv);
        msg = new String(recv.getData()).trim();
        
        System.out.println(
            "Message from " + recv.getAddress().getHostAddress() + ": " + msg);
    }
// OK, I'm done talking - leave the group...
s.leaveGroup(group);
scanner.close();
   }
}
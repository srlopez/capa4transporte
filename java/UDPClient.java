import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    private DatagramSocket udpSocket;
    private InetAddress serverAddress;
    private int port;
    private Scanner scanner;
    private InetAddress ipMulticast;
    private MulticastSocket MSocket;

    private UDPClient(String destinationAddr, int port) throws IOException {
        this.serverAddress = InetAddress.getByName(destinationAddr);
        this.port = port;
        this.ipMulticast = InetAddress.getByName("239.0.0.1");
        this.MSocket = new MulticastSocket(this.port);
        this.MSocket.joinGroup(this.ipMulticast);

        this.udpSocket = new DatagramSocket(this.port);
        this.scanner = new Scanner(System.in);
    }
    public static void main(String[] args) throws NumberFormatException, IOException {        
        UDPClient sender = new UDPClient(args[0], Integer.parseInt(args[1]));
        System.out.println("-- Running UDP Client at " + InetAddress.getLocalHost() + " --");
        sender.start(); 
    }
    private int start() throws IOException {
        String in;
        while (true) {
            in = scanner.nextLine();
            
            DatagramPacket p = new DatagramPacket(
                in.getBytes(), in.getBytes().length, serverAddress, port);
            
            this.udpSocket.send(p); 
            //====== 
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            String msg;
            this.MSocket.receive(packet);
            msg = new String(packet.getData()).trim();
            
            System.out.println(
                "Message from " + packet.getAddress().getHostAddress() + ": " + msg);
            //=====
            

        }
    }
}
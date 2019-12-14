// SSLServer.java

import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import javax.net.ssl.SSLHandshakeException;

public class SSLServer implements Runnable {

    private final static int DefaultPort = 24601;
    private int Port = DefaultPort;

    SSLServer() { }

    SSLServer(int port) { Port = port; }

    // private static void createServer() {
    //     createServer(DefaultPort);
    // }

    /**
     * Create a socket server at passed port.
     *
     * @param port Port onto which server is socketed.
     */
    private static void createServer(int port) {

        try {
            System.out.println("-"+String.format("CREATING SSL SERVER: localhost:%d", port));
            SSLServerSocketFactory factory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
            // Established server socket at port.
            ServerSocket serverSocket = factory.createServerSocket(port);

            while (true) {
                Socket socket = serverSocket.accept();
                // Once client has connected, use socket stream to send a prompt message to client.
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                // Prompt for client.
                printWriter.println("Enter server msg: ");

                // Get input stream produced by client (to read sent message).
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String output = bufferedReader.readLine();

                // Output sent message from client.
                printWriter.println(output);

                // Close writer and socket.
                printWriter.close();
                socket.close();

                // Output message from client.
                System.out.println(String.format("[FROM Client] %s", output));

                // Loop back, awaiting a new client connection.
            }
        } catch (SSLHandshakeException exception) {
            System.out.println(exception);
        } catch(SocketTimeoutException exception) {
            System.out.println(exception);
        } catch (IOException exception) {
            System.out.println(exception);
        }
    }

    @Override
    public void run() {

        System.setProperty("javax.net.ssl.keyStore", "keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
    
        // Create server instance.
        createServer(Port);
    }
}
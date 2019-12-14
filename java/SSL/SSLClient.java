// SSLClient.java

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import javax.net.ssl.SSLHandshakeException;


public class SSLClient implements Runnable {
    private final static String DefaultHost = "localhost";
    private final static int DefaultPort = 24601;
    private final static int DefaultTimeout = 2000;
    private final static boolean DefaultShouldSleep = false;

    private String Host = DefaultHost;
    private int Port = DefaultPort;
    private int Timeout = DefaultTimeout;
    private boolean ShouldSleep = DefaultShouldSleep;

    private String Message;

    SSLClient() {
    }

    public SSLClient(String host, int port) {
        Host = host;
        Port = port;
    }

    // public SSLClient(String host) {
    //     Host = host;
    // }

    // public SSLClient(int port) {
    //     Port = port;
    // }

    // public SSLClient(boolean shouldSleep) {
    //     ShouldSleep = shouldSleep;
    // }

    // private void connect() {
    //     connect(DefaultHost, DefaultPort, DefaultTimeout, DefaultShouldSleep);
    // }

    // private void connect(String host) {
    //     connect(host, DefaultPort, DefaultTimeout, DefaultShouldSleep);
    // }

    // private void connect(int port) {
    //     connect(DefaultHost, port, DefaultTimeout, DefaultShouldSleep);
    // }

    // private void connect(boolean shouldSleep) {
    //     connect(DefaultHost, DefaultPort, DefaultTimeout, shouldSleep);
    // }

    /**
     * Attempt SSL connection to passed host and port as client.
     *
     * @param host        Host to connect to.
     * @param port        Port to connect to.
     * @param timeout     Timeout (in milliseconds) to allow for socket connection.
     * @param shouldSleep Indicates if thread should be artificially slept.
     */
    private void connect(String host, int port, int timeout, boolean shouldSleep) {

        try {
            System.out.println("-"+
            String.format("CONNECTING TO %s:%d", host, port));

            while (true) {
                SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
                SSLSocket socket = (SSLSocket) factory.createSocket(host, port);

                // Read input stream from server and output said message.
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Check if artificial sleep should occur, to simulate connection delay.
                if (shouldSleep) {
                    // Sleep for half a second.
                    Thread.sleep(500);
                }

                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                System.out.println("[FROM Server] " + reader.readLine());

                // Await user input via System.in (standard input stream).
                BufferedReader userInputBR = new BufferedReader(new InputStreamReader(System.in));
                // Save input message.
                Message = userInputBR.readLine();

                // Send message to server via output stream.
                writer.println(Message);

                // If message is 'quit' or 'exit', intentionally disconnect.
                if ("quit".equalsIgnoreCase(Message) || "exit".equalsIgnoreCase(Message)) {
                    System.out.println("-"+"DISCONNECTING");
                    socket.close();
                    break;
                }

                System.out.println("[TO Server] " + reader.readLine());
            }
        } catch (SSLHandshakeException exception) {
            System.out.println(exception);
        } catch (SocketTimeoutException exception) {
            System.out.println(exception);
        } catch (InterruptedException | IOException exception) {
            System.out.println(exception);
        }
    }

    @Override
    public void run() {
        System.setProperty("javax.net.ssl.trustStore", "truststore");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        // Attempt connection.
        connect(Host, Port, Timeout, ShouldSleep);
    }
}
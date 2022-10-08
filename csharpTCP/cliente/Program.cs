// A C# program for Client
using System;
using System.Net;
using System.Net.Sockets;
using System.Text;

void ExecuteClient(String hostIP, int port)
{

    try
    {

        // Establish the remote endpoint
        // for the socket. This example
        // uses port 11111 on the local
        // computer.


        //IPHostEntry ipHost = Dns.GetHostEntry(Dns.GetHostName());
        IPAddress ipAddr = IPAddress.Parse(hostIP); //ipHost.AddressList[0];
        IPEndPoint localEndPoint = new IPEndPoint(ipAddr, port);

        //JORGE ====
        //  ipHost = Dns.GetHostEntry("10.10.17.102");
        // IPAddress ipAddr = ipHost.AddressList[0];
        // IPEndPoint localEndPoint = new IPEndPoint(ipAddr, 8080);

        //ANASS
        //  ipHost = Dns.GetHostEntry("10.10.17.121");
        // IPAddress ipAddr = ipHost.AddressList[0];
        // IPEndPoint localEndPoint = new IPEndPoint(ipAddr, 9000);

        // Creation TCP/IP Socket using
        // Socket Class Constructor
        Socket sender = new Socket(ipAddr.AddressFamily,
                   SocketType.Stream, ProtocolType.Tcp);

        try
        {

            // Connect Socket to the remote
            // endpoint using method Connect()
            sender.Connect(localEndPoint);

            // We print EndPoint information
            // that we are connected
            Console.WriteLine("Socket connected to -> {0} ",
                          sender.RemoteEndPoint.ToString());

            // Creation of message that
            // we will send to Server
            byte[] messageSent = Encoding.ASCII.GetBytes("Santi Client");//\nLine2\n<EOF>");
            int byteSent = sender.Send(messageSent);

            // Data buffer
            byte[] messageReceived = new byte[1024];

            // We receive the message using
            // the method Receive(). This
            // method returns number of bytes
            // received, that we'll use to
            // convert them to string
            int byteRecv = sender.Receive(messageReceived);
            Console.WriteLine("Recibido:\n{0}",
                  Encoding.ASCII.GetString(messageReceived,
                                             0, byteRecv));

            // Close Socket using
            // the method Close()
            sender.Shutdown(SocketShutdown.Both);
            sender.Close();
        }

        // Manage of Socket's Exceptions
        catch (ArgumentNullException ane)
        {

            Console.WriteLine("ArgumentNullException : {0}", ane.ToString());
        }

        catch (SocketException se)
        {

            Console.WriteLine("SocketException : {0}", se.ToString());
        }

        catch (Exception e)
        {
            Console.WriteLine("Unexpected exception : {0}", e.ToString());
        }
    }

    catch (Exception e)
    {

        Console.WriteLine(e.ToString());
    }
}


string[] arguments = Environment.GetCommandLineArgs();
string hostIP = arguments.Length > 1 ? arguments[1] : "127.0.0.1";
int port = arguments.Length > 2 ? Int32.Parse(arguments[2]) : 11111;
ExecuteClient(hostIP, port);

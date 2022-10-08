// A C# Program for Server
using System;
using System.Net;
using System.Net.Sockets;
using System.Text;


void ExecuteServer(int port)
{
    // Establish the local endpoint
    // for the socket. Dns.GetHostName
    // returns the name of the host
    // running the application.
    IPHostEntry ipHost = Dns.GetHostEntry(Dns.GetHostName());
    IPAddress ipAddr = IPAddress.Any;//ipHost.AddressList[0];
    IPEndPoint localEndPoint = new IPEndPoint(ipAddr, port);
    //IPEndPoint localEndPoint = new IPEndPoint(IPAddress.All, 11111);

    // Creation TCP/IP Socket using
    // Socket Class Constructor
    Socket listener = new Socket(ipAddr.AddressFamily, SocketType.Stream, ProtocolType.Tcp);

    try
    {
        // Using Bind() method we associate a
        // network address to the Server Socket
        // All client that will connect to this
        // Server Socket must know this network
        // Address
        listener.Bind(localEndPoint);

        // Using Listen() method we create
        // the Client list that will want
        // to connect to Server
        listener.Listen(10); //Longitud máxima de la cola de conexiones pendientes

        int i = 0; // Un contador para enviar respuestas distintas cada petición

    NewConnetion: // Uso de Etiquetas 
        while (true)
        {

            Console.WriteLine($"Waiting connection in port {port} ...");

            // Suspend while waiting for
            // incoming connection Using
            // Accept() method the server
            // will accept connection of client
            Socket clientSocket = listener.Accept();
            //https://learn.microsoft.com/es-es/dotnet/api/system.net.sockets.socket.ttl?view=net-6.0
            clientSocket.ReceiveTimeout = 1000;

            Console.WriteLine($"accept RemoteEndPoint {clientSocket.RemoteEndPoint?.ToString()} waiting data...");
            // Data buffer
            byte[] bytes = new Byte[1024];
            string data = "";

            while (true) // ciclo de lectura en chuncks de 1K haste <EOF>
            {

                try
                {
                    int numByte = clientSocket.Receive(bytes);
                    data += Encoding.ASCII.GetString(bytes, 0, numByte);
                    Console.WriteLine("Text received -> {1} \n{0} ", data, data.Length);

                    //if (data.IndexOf("<EOF>") > -1) //Nos lo saltamos, leemos hasta 1K
                    break;
                }
                catch (System.Net.Sockets.SocketException e)
                {
                    // Timout de clientSocket.Receive(bytes);
                    //Console.WriteLine(e.ToString());
                    clientSocket.Shutdown(SocketShutdown.Both);
                    clientSocket.Close();
                    goto NewConnetion; // Uso de GOTO!!!!🥵
                }

            }

            String firstLine = data.Split("\n")[0];

            byte[] message = Encoding.ASCII.GetBytes("HTTP/1.1 200 OK\n\nTCP Server\n" + firstLine + $"\nResponse #{++i}");

            // Send a message to Client
            // using Send() method
            clientSocket.Send(message);

            // Close client Socket using the
            // Close() method. After closing,
            // we can use the closed Socket
            // for a new Client Connection
            clientSocket.Shutdown(SocketShutdown.Both);

            clientSocket.Close();

        }
    }


    catch (Exception e)
    {
        Console.WriteLine(e.ToString());
    }
}

string[] arguments = Environment.GetCommandLineArgs();
int port = arguments.Length > 1 ? Int32.Parse(arguments[1]) : 11111;
ExecuteServer(port);
// Java Program to illustrate Client Side implementation
// of Simple Calculator using UDP
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class client_java_udp
{
    public static void main(String args[]) throws IOException
    {
    	BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

        // Step 1:Create the socket object for carrying
        // the data
        DatagramSocket ds = new DatagramSocket();

        System.out.println("Enter Ip address of the Server:");
        String hostip = sc.readLine();
        InetAddress ip = InetAddress.getByName(hostip);
        System.out.println(ip);
        
        byte buf[] = null;

        // loop while user not enters "bye"
        while (true)
        {
            System.out.print("Enter the equation with space between oprand and operator: ");
            String inp = sc.readLine();
            System.out.println("Enter the port number:");
            int port = Integer.parseInt(sc.readLine());
            
            buf = new byte[65535];

            // convert the String input into the byte array.
            buf = inp.getBytes();

            // Step 2:Create the datagramPacket for sending the data.
            DatagramPacket DpSend =
                      new DatagramPacket(buf, buf.length, ip, port);

            // invoke the send call to actually send the data.
            ds.send(DpSend);

            // break the loop if user enters "bye"
            if (inp.equals("bye")) {
                break;
            }

            buf = new byte[65535];
            DatagramPacket DpReceive =
                                 new DatagramPacket(buf, buf.length);
            ds.receive(DpReceive);

            System.out.println("Answer = " +
                                new String(buf,0,buf.length));
        }
    }
}

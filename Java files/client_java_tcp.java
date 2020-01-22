import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class client_java_tcp
{
    public static void main(String[] args) throws IOException
    {
    	BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Ip address of the Server:");
        String hostip = sc.readLine();
        InetAddress ip = InetAddress.getByName(hostip);
        System.out.println(ip);
        System.out.println("Enter the port number:");
        int port = 0;
        while(true) {
	        try {
	        	port = Integer.parseInt(sc.readLine());
	        	break;
	        } catch (NumberFormatException e) {
	        	System.out.println("Enter a valid port number");
	        }
        }
        if(port>=0 || port <= 65345)
        {

	          // Step 1: Open the socket connection.
	          Socket s = new Socket(ip, port);
	                  
	          // Step 2: Communication-get the input and output stream
	          DataInputStream dis = new DataInputStream(s.getInputStream());
	          DataOutputStream dos = new DataOutputStream(s.getOutputStream());        	
	        	
	        while (true)
	        {
	        	
	            // Enter the equation in the form-
	            // "operand1 operation operand2"
	            System.out.print("Enter the equation with space between oprand and operator: ");
	            
	
	            String inp = sc.readLine();
	            System.out.println("Value entered is : " + inp);
	                // send the equation to server
		            dos.writeUTF(inp);
		
		            // wait till request is processed and sent back to client
		            if(inp.equals("bye")) {
		            	break;
		            }
		            String ans = dis.readUTF();
		            System.out.println("Answer=" + ans);
	        }
        }
        else 
        {
        	System.out.println("Invalid Port Number. Terminating the programm.");
        }
    }
}

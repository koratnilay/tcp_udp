// Java Program to illustrate Server Side implementation
// of Simple Calculator using UDP
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.StringTokenizer;
import java.util.Stack;

public class server_java_udp
{
    public static void main(String[] args) throws IOException
    {
        // Create a socket to listen at port 1234
        DatagramSocket ds = new DatagramSocket(1234);
        byte[] buf = null;
        DatagramPacket DpReceive = null;
        DatagramPacket DpSend = null;
        while (true)
        {
            buf = new byte[65535];

            // create a DatgramPacket to receive the data.
            DpReceive = new DatagramPacket(buf, buf.length);

            // receive the data in byte buffer.
            ds.receive(DpReceive);

            String inp = new String(buf, 0, buf.length);

            //To remove extra spaces.
            inp=inp.trim();
            System.out.println("Equation Received:- " + inp);

            // Exit the server if the client sends "bye"
            if (inp.equals("bye"))
            {
                System.out.println("Client sent bye.....EXITING");
                break;
            }

            int result = evaluate(inp);
            
            System.out.println("Sending the result...");
            String res = Integer.toString(result);

            // Clear the buffer after every message.
            buf = res.getBytes();

            // get the port of client.
            int port = DpReceive.getPort();

            DpSend = new DatagramPacket(buf, buf.length,
                          InetAddress.getLocalHost(), port);
            ds.send(DpSend);
        }
    }
    
    public static int evaluate(String expression)
    {
        char[] tokens = expression.toCharArray();
 
         // Stack for numbers: 'values'
        Stack<Integer> values = new Stack<Integer>();
 
        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<Character>();
 
        for (int i = 0; i < tokens.length; i++)
        {
             // Current token is a whitespace, skip it
            if (tokens[i] == ' ')
                continue;
 
            // Current token is a number, push it to stack for numbers
            if (tokens[i] >= '0' && tokens[i] <= '9')
            {
                StringBuffer sbuf = new StringBuffer();
                // There may be more than one digits in number
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.toString()));
            }
 
            // Current token is an opening brace, push it to 'ops'
            else if (tokens[i] == '(')
                ops.push(tokens[i]);
 
            // Closing brace encountered, solve entire brace
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                  values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }
 
            // Current token is an operator.
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                     tokens[i] == '*' || tokens[i] == '/')
            {
                // While top of 'ops' has same or greater precedence to current
                // token, which is an operator. Apply operator on top of 'ops'
                // to top two elements in values stack
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                  values.push(applyOp(ops.pop(), values.pop(), values.pop()));
 
                // Push current token to 'ops'.
                ops.push(tokens[i]);
            }
        }
 
        // Entire expression has been parsed at this point, apply remaining
        // ops to remaining values
        while (!ops.empty())
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
 
        // Top of 'values' contains result, return it
        return values.pop();
    }
 
    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }
 
    // A utility method to apply an operator 'op' on operands 'a' 
    // and 'b'. Return the result.
    public static int applyOp(char op, int b, int a)
    {
        switch (op)
        {
        case '+':
            return a + b;
        case '-':
            return a - b;
        case '*':
            return a * b;
        case '/':
            if (b == 0)
                throw new
                UnsupportedOperationException("Cannot divide by zero");
            return a / b;
        }
        return 0;
    }
}

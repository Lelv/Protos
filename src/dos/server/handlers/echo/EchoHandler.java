package dos.server.handlers.echo;

import dos.server.handlers.ConnectionHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


/**
 * Connection Handler que implementa protocolo ECHO.  
 * 
 * 
 * @author Fernando Zunino
 * @since Aug 21, 2011
 */
public class EchoHandler implements ConnectionHandler {
    
    private static final int BUFSIZE = 1;   // Size of receive buffer


    @Override
    public void handle(final Socket s) throws IOException {
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
        
        int recvMsgSize;   // Size of received message
        byte[] receiveBuf = new byte[BUFSIZE];  // Receive buffer

        // Receive until client closes connection, indicated by -1 return
        if ((recvMsgSize = in.read(receiveBuf)) != -1) {
          out.write(receiveBuf, 0, recvMsgSize);
        }
        
        s.close();  // Close the socket.  We are done with this client!
    }
}

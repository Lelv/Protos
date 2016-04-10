package ocho;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by root on 4/10/16.
 */
public class ServerHandler implements Runnable{

    private Socket socketFuente;
    private Socket socketDestino;

    private static final int BUFSIZE = 1024;

    public ServerHandler(Socket socketFuente, Socket socketDestino){
        this.socketFuente = socketFuente;
        this.socketDestino = socketDestino;
    }

    @Override
    public void run() {
        try {
            // Create socket that is connected to server on specified port

            System.out.println("Connected to server...");

            InputStream inDestino = socketDestino.getInputStream();
            OutputStream outDestino = socketDestino.getOutputStream();

            InputStream inOriginal = socketFuente.getInputStream();
            OutputStream outOriginal = socketFuente.getOutputStream();

            int recvMsgSize;   // Size of received message
            byte[] receiveBuf = new byte[BUFSIZE];  // Receive buffer

            // Receive until client closes connection, indicated by -1 return
            while ((recvMsgSize = inOriginal.read(receiveBuf)) != -1) {
                outDestino.write(receiveBuf, 0, recvMsgSize);
            }

            socketFuente.close();  // Close the socket.  We are done with this client!
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                socketFuente.close();
                System.out.println("Cerre socket");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package diez;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by root on 4/10/16.
 */
public class SocketReadWrite implements Runnable{

    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket socket;
    private static final int BUFSIZE = 1024;

    public SocketReadWrite(InputStream is, OutputStream os, Socket socket){
        this.inputStream = is;
        this.outputStream = os;
        this.socket = socket;
    }

    @Override
    public void run() {
        int recvMsgSize;   // Size of received message
        byte[] receiveBuf = new byte[BUFSIZE];  // Receive buffer

        try {
            // Receive until client closes connection, indicated by -1 return
            while ((recvMsgSize = inputStream.read(receiveBuf)) != -1) {
                outputStream.write(receiveBuf, 0, recvMsgSize);
            }
            if(socket != null) socket.close();
            System.out.println("Socket cerrado");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

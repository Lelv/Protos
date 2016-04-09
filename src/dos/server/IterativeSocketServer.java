package dos.server;

import dos.server.handlers.ConnectionHandler;
import dos.server.handlers.echo.EchoHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class IterativeSocketServer {
    public IterativeSocketServer(final int port, final InetAddress interfaz, final ConnectionHandler handler)
            throws IOException {
        final ServerSocket server = new ServerSocket(port, 50, interfaz);

        System.out.printf("Escuchando en %s\n", server.getLocalSocketAddress());
        while (true) {
            final Socket socket = server.accept();
            String s = socket.getRemoteSocketAddress().toString();
            System.out.printf("Se conecto %s\n", s);
            handler.handle(socket);
            if (!socket.isClosed()) {
                socket.close();
                System.out.printf("Terminando %s\n", s);
            }
        }
    }

    public static void main(String[] args) {
        try {
            new IterativeSocketServer(20007, InetAddress.getByName("localhost"), new EchoHandler());
        } catch (final Exception e) {
            System.out.println("Ocurrio un error");
            e.printStackTrace();
        }
    }
}

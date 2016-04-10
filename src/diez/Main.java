package diez;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by root on 4/10/16.
 */
public class Main {

    public static void main(String[] args) {
        formaDos();

    }

    private static void formaUno(){
        int puertoEscuchar = 20010;

        String ipDestino = "localhost";
        int puertoDestino = 20011;

        //Servidor
        ServerSocket server = null;
        Socket socketDestino = null;
        try {
            server = new ServerSocket(puertoEscuchar, 50, InetAddress.getByName("localhost"));
            socketDestino = new Socket(ipDestino, puertoDestino);

            System.out.printf("Escuchando en %s\n", server.getLocalSocketAddress());
            while (true) {
                Socket socketFuente = server.accept();
                String s = socketFuente.getRemoteSocketAddress().toString();
                System.out.printf("Se conecto %s\n", s);

                prepareHandlersUno(socketFuente, socketDestino);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                server.close();
                socketDestino.close();
                System.out.println("Cerre sockets y server");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void formaDos(){
        int puertoEscuchar = 20010;
        String ipDestino = "localhost";
        int puertoDestino = 20011;

        //Servidor
        ServerSocket server = null;
        Socket socketDestino = null;
        try {
            server = new ServerSocket(puertoEscuchar, 50, InetAddress.getByName("localhost"));
            socketDestino = new Socket(ipDestino, puertoDestino);

            System.out.printf("Escuchando en %s\n", server.getLocalSocketAddress());

            Socket socketFuente = server.accept();
            String s = socketFuente.getRemoteSocketAddress().toString();
            System.out.printf("Se conecto %s\n", s);

            prepareHandlersDos(socketFuente, socketDestino);

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                server.close();
                socketDestino.close();
                System.out.println("Cerre sockets y server");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void prepareHandlersUno(Socket socketFuente, Socket socketDestino) throws IOException{
        SocketReadWrite client = new SocketReadWrite(socketDestino.getInputStream(), socketFuente.getOutputStream(), socketFuente);
        SocketReadWrite server = new SocketReadWrite(socketFuente.getInputStream(), socketDestino.getOutputStream(), null);

        new Thread(client).start();
        new Thread(server).start();
    }

    private static void prepareHandlersDos(Socket socketFuente, Socket socketDestino) throws IOException{
        SocketReadWrite client = new SocketReadWrite(socketDestino.getInputStream(), socketFuente.getOutputStream(), socketFuente);
        SocketReadWrite server = new SocketReadWrite(socketFuente.getInputStream(), socketDestino.getOutputStream(), socketDestino);

        new Thread(client).start();
        new Thread(server).run();
    }
}

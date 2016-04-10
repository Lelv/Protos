package diez;

import ocho.ServerHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by root on 4/10/16.
 */
public class Main {

    public static void main(String[] args) {
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
                Thread t = new Thread(new ServerHandler(socketFuente, socketDestino));
                t.start();
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
}

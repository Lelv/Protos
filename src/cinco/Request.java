package cinco;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;

/**
 * Created by lelv on 4/2/16.
 */
public class Request {

    public static void main(String[] args){
        String urlString = "http://www.google.com:80";

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String host = url.getHost();
        int port = url.getPort();
        if(port<0) port = 80;
        System.out.println("Puerto " + port);

        String request = "GET / HTTP/1.1\r\nHost: " + host + "\r\n\n\n";

        Socket socket = null;
        OutputStream os = null;
        InputStream is = null;
        try {
            socket = new Socket(host, port);
            os = socket.getOutputStream();
            is = socket.getInputStream();

            os.write(request.getBytes());

            Response response = analizeResponse(is);
            System.out.println("Header: ");
            System.out.println(response.getHeader());
            System.out.println("Body: ");
            System.out.println(response.getBody());



        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(is != null) is.close();
                if(os != null) os.close();
                if(socket!=null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static Response analizeResponse(InputStream is){

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        Response response = new Response();
        String line;
        boolean header = true;
        boolean foundConnection = false;

        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                if(line.compareTo("") == 0){
                    if(header){
                        response.setHeader(sb.toString());
                        sb = new StringBuilder();
                        header = false;
                    }
                }else{
                    sb.append(line);
                    System.out.println("Linea: " + line);
                    if(!foundConnection && header && line.length() >10 && line.substring(0,10).compareTo("Connection") == 0){
                        if(line.substring(12).compareTo("close")==0){
                            response.setPersistent(false);
                        }
                        foundConnection = true;
                    }
                    sb.append('\n');
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        response.setBody(sb.toString());


        return response;
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
                System.out.println(i++ + ") " +line);

                sb.append('\n');
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}

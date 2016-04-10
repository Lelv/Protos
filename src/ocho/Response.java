package ocho;

/**
 * Created by lelv on 4/6/16.
 */
public class Response {

    private String header;
    private String body;

    private boolean persistent;

    public Response(){
        persistent = true;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }
}

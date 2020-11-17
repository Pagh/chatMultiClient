import java.io.*;
import java.net.*;

public class ChatClient {
    private final static int serverPort = 6789;
    private Socket s;
    private BufferedReader input;
    private SendThread sendThread;
    private ReadThread readThread;
    
    public static void main(String args[]) throws UnknownHostException, IOException {
        ChatClient client = new ChatClient();
        client.connect();
    } 
        
    public void connect() {
        
        try {
            //Recupero ip localhost
            InetAddress ip = InetAddress.getByName("localhost"); 
          
            //Conessione al server all' ip = "localhost" e serverport = 6789
            s = new Socket(ip, serverPort);

            sendThread = new SendThread();
            readThread = new ReadThread();
        }
        catch(Exception ex) {
           ex.toString();
        }    
    }

    
   
}
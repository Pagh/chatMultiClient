
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SendThread extends Thread{
    
    
    private BufferedReader input;
    private BufferedReader DalServer;
    private DataOutputStream VersoServer;
    private Socket s;
    private ReadThread readThread;
    
    
        public SendThread() {
            start();
            input = new BufferedReader(new InputStreamReader(System.in));
        }

        /**
	 * Avvio del thread e gestione de i messaggi in uscita al client o server
	 */
        @Override
        public void run() {
            try {
                String name = input.readLine();
                VersoServer.writeBytes(name + '\n');
                for(;;) {
                    String mex = input.readLine();
                    if(mex.equalsIgnoreCase("3")) {
                        //chiusura della connessione
                        VersoServer.writeBytes("Connessione in chiusura..." + '\n');
                        VersoServer.writeBytes(mex + '\n');
                        close();
                        break;
                    }
                    else if(mex == "1"){
                        //invio del messaggio da inviare a tutti
                       mex = input.readLine();
                       System.out.println("IO: " + mex);
                       VersoServer.writeBytes(mex + '\n');
                    }
                    else if(mex == "2"){
                        //invio del nome del destinatario
                        mex = input.readLine();
                        //invio del messaggio
                        mex = input.readLine();
                        System.out.println("IO: " + mex);
                        VersoServer.writeBytes(mex + '\n');
                    }
                }
            }
            catch (Exception ex) {
                ex.toString();
                System.exit(1);
            }
        }

        public void close() {
            try {
                DalServer.close();
                VersoServer.close();
                s.close();
                readThread.close();
                this.stop();
            }
            catch(Exception ex) {
                ex.toString();
            }
            System.exit(0);
        }
    }

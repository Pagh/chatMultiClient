import java.io.BufferedReader;

 public class ReadThread extends Thread{
    
    private BufferedReader DalServer;
    
        public ReadThread() {
            start();
        }
        /**
	 * Avvio del thread e gestione de i messagi in arrivo sul server da client o server
	 */
        @Override
        public void run() {
            try {
                for(;;) {
                    String mex = DalServer.readLine();
                    if(mex != null) System.out.println(mex);
                }
            }
            catch (Exception ex) {
                ex.toString();
                System.exit(1);
            }
        }

        public void close() {
            try {
                this.stop();
            }
            catch(Exception ex) {
                ex.toString();
            }
            System.exit(0);
        }
    }
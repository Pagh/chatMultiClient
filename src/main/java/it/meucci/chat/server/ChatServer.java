package it.meucci.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

public class ChatServer {
	/**
	 * Elenco client collegati
	 */
	List<ClientHandler> clients = new ArrayList<ClientHandler>();
        

	public void avviaServer() {
		ServerSocket ss;
		try {
			//il server si mette in ascolto sulla porta in ingresso
                        System.out.println("server in attesa");
			ss = new ServerSocket(6789);
			while (true) {
				Socket s = ss.accept();
				// ricevuta la connessione di un client, lo gestisce clienthandler
				ClientHandler clientHandler = new ClientHandler(this, s);
				clientHandler.start();
				
				//salvo il client handler
				clients.add(clientHandler);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
                        System.out.println("Errore durante l'istanza del messaggio!");
                        System.exit(1);
		}
	}
        
        /**
	 * Invio a tutti
	 * 
	 * @param msg
	 */
	public void inviaMessaggioATutti(String msg) {
		for (ClientHandler client : clients) {
			client.inviaMessaggio(msg);
		}
	}

	/**
	 * Invio ad un client
	 * 
	 * @param nome
	 * @param msg
	 */
	public void inviaMessaggioAUnClient(String nome, String msg) {
		for (ClientHandler client : clients) {
			if (client.getName() != null && client.getName().equals(nome)) {
				client.inviaMessaggio(msg);
			}
		}
	}

	public void rimuoviClient(String nome) {
		for (ClientHandler client : clients) {
			if (client.getName() != null && client.getName().equals(nome)) {
				clients.remove(client);
				// XXX elimina il client dal vettore
			}
		}

	}

        public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.avviaServer();
    }


}

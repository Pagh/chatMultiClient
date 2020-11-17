package it.meucci.chat.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread {
	private BufferedReader dis = null;
	private DataOutputStream dos = null;
	private List<String> utenti= new ArrayList<String>();
	private String name;
	private ChatServer chatServer;
	private Socket s;
        String tipoMessaggio;
	
	private boolean collegato = true;

	/**
	 * Inizializza gli stream input e output
	 * @param chatServer
	 * @param s
	 */
	public ClientHandler(ChatServer chatServer, Socket s) {
		try {
			this.chatServer = chatServer;
			this.s = s;
			this.dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.dos = new DataOutputStream(s.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Avvio del thread e gestione de i messagi in arrivo sul server da un client
	 */
        @Override
	public void run() {
            
		try {
			//per prima cosa il server riceve dal client il nome
                        dos.writeBytes("Inserisci username" + '\n');
                        name = dis.readLine();
                        
                        
                        if(utenti.isEmpty()) utenti.add(name);
                        else{
                            for(int i = 0; i < utenti.size(); i++){
                                while(name.equals(utenti.get(i))){
                                    dos.writeBytes("Nome utente non disponibile, inserirne uno nuovo" + '\n');
                                    name = dis.readLine();
                                }
                            }
                            utenti.add(name);
                        }
                        System.out.println(name + " connesso");
                        dos.writeBytes(name + " connesso" + '\n' + '\n');
                        for(int i = 0; i < chatServer.clients.size(); i++){
                            chatServer.clients.get(i).dos.writeBytes("Utenti connessi:" + '\n');
                                for(int j = 0; j < utenti.size(); j++) {
                                    chatServer.clients.get(i).dos.writeBytes("Utente:" + utenti.get(j) + '\n');
                                }
                            chatServer.clients.get(i).dos.writeBytes("\n");
                        }
                        
			while (collegato) {
                            System.out.println("Digitare 1 se si vuole inviare un messaggio a tutti, 2 se si vuole inviare un messaggio ad un client, 3 se si vuole interrompere la connession al server '\n' ");
                            tipoMessaggio = dis.readLine();
                            
				//il client ha inviato un messaggio al server
				//XXX analizzare il messaggio ricevuto
				//per capire se è un messaggio ad un client o a tutti
				 //1=chat a tutti,2=chat a uno, 
				if (tipoMessaggio == "1"){
                                    System.out.println("Inserisci il messaggio da inviare a tutti");
                                    String msgRicevuto = dis.readLine();
                                    chatServer.inviaMessaggioATutti(msgRicevuto);

                                }
				else if (tipoMessaggio == "2") {
                                    System.out.println("Inserisci l'username del destinatario");
                                    String nomeDest = dis.readLine();
                                    System.out.println("Inserisci il messaggio");
                                    String msgRicevuto = dis.readLine();
                                    chatServer.inviaMessaggioAUnClient(nomeDest, msgRicevuto);
				} else if (tipoMessaggio == "3") {
					//il client mi ha mandato un messaggio di disconnessione
                                        disconnetti();
                                        chatServer.rimuoviClient(this.name);
				}
			}
		}catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Errore durante l'istanza del messaggio!");
                    System.exit(1);  
		}
	}
	
	/**
	 * chiude il socket e chiude il thread
	 */
        
	public void disconnetti() {
		try {
                        this.collegato = false;
			this.s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getClienName() {
		return this.name;
	}
	
	public void inviaMessaggio(String msg) {
		try {
			dos.writeUTF(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

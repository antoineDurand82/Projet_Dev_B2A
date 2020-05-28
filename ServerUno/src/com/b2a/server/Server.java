package com.b2a.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.b2a.server.ServerThread;

public class Server {
	
	private ServerThread[] serverThreads = new ServerThread[4];
	private int numberOfThreads;
	
	public int getNumberOfThread() {
		return this.numberOfThreads;
	}
	
	private ArrayList<String> noms = new ArrayList<>();
	// m�thode stockeNom(String)
	public int stockeNom(String nom) {
		noms.add(nom);
		System.out.println("Identifiant " + nom + " (indice " + (noms.size()-1) + ")");
		return noms.size()-1;
	}
	
	// m�thode getNbClients()
	public int getNbClients() {
		return noms.size();
	}
	// m�thode getAutre(int)
	public String getAutre(int i) {
		return (i == 0)?noms.get(1):noms.get(0);
	}
	
	public void clientConnection() {
		try {
			ServerSocket serverSocket = new ServerSocket(4567);
			Socket socket;
			while(true) {
				socket = serverSocket.accept();
				System.out.println("A client has logged in !");
				PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
			    BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			    socketOut.println("Oui c'est bien la connexion au serveur");
				if (this.numberOfThreads < 4) {
					ServerThread serverThread = new ServerThread(this, socket);
					Thread thread = new Thread(serverThread);
					thread.start();
					this.serverThreads[this.numberOfThreads] = serverThread;
					this.numberOfThreads++;
				}
//				if(this.game.isOver()) {
//					for(ServerThread serverThread : this.serverThreads) {
//						serverThread.terminate();
//						this.numberOfThreads--;
//						this.serverThreads[this.numberOfThreads] = null;
//					}
//				}
			}
		} catch (Exception exc) {
			System.err.println(exc.getMessage());
		}
	}
	
	public Server() {
		clientConnection();
		
	}
	
	public static void main(String[] args) {
		new Server();
	}
}

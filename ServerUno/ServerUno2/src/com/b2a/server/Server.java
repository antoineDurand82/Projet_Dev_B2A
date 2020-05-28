package com.b2a.server;

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
	// méthode stockeNom(String)
	public int stockeNom(String nom) {
		noms.add(nom);
		System.out.println("Identifiant " + nom + " (indice " + (noms.size()-1) + ")");
		return noms.size()-1;
	}
	
	// méthode getNbClients()
	public int getNbClients() {
		return noms.size();
	}
	// méthode getAutre(int)
	public String getAutre(int i) {
		return (i == 0)?noms.get(1):noms.get(0);
	}
	
	public void razClients() {
		noms.clear();
	}
	
	public void clientConnection() {
		try {
			ServerSocket serverSocket = new ServerSocket(4567);
			while(true) {
				Socket socketClient1 = serverSocket.accept();
				System.out.println("Client 1 just logged in !");
				System.out.println("Looking for an opponent");
				Socket socketClient2 = serverSocket.accept();
				System.out.println("Client 2 just logged in !");
				System.out.println("Get ready for battle, tut ututututututututtutut");
				
				GameSession gameSession = new GameSession()
				
//				if (this.numberOfThreads < 2) {
//					ServerThread serverThread = new ServerThread(this, socket);
//					Thread thread = new Thread(serverThread);
//					thread.start();
//					this.serverThreads[this.numberOfThreads] = serverThread;
//					this.numberOfThreads++;
//				}
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

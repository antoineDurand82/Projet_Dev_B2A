package com.b2a.server;

import java.net.ServerSocket;
import java.net.Socket;

import com.b2a.game.Game;
import com.b2a.server.ServerThread;

public class Server {
	
	private Game game;
	private ServerThread[] serverThreads = new ServerThread[4];
	private int numberOfThreads;
	
	public int getNumberOfThread() {
		return this.numberOfThreads;
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public void clientConnection() {
		try {
			ServerSocket serverSocket = new ServerSocket(4567);
			Socket socket;
			while(true) {
				socket = serverSocket.accept();
				System.out.println("A client has logged in !");
				if (this.numberOfThreads < 4) {
					ServerThread serverThread = new ServerThread(this, socket);
					Thread thread = new Thread(serverThread);
					thread.start();
					this.serverThreads[this.numberOfThreads] = serverThread;
					this.numberOfThreads++;
				}
				if(this.game.isOver()) {
					for(ServerThread serverThread : this.serverThreads) {
						serverThread.terminate();
						this.numberOfThreads--;
						this.serverThreads[this.numberOfThreads] = null;
					}
				}
			}
		} catch (Exception exc) {
			System.err.println(exc.getMessage());
		}
	}
	
	public Server() {
		clientConnection();
		
	}
}

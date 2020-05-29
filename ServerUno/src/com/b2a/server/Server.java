package com.b2a.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONObject;

import com.b2a.server.ServerThread;
import com.b2a.server.uno.game.Game;
import com.b2a.server.uno.game.GameSession;
import com.b2a.server.uno.game.Player;
import com.b2a.server.uno.view.UNOCard;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;

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
			System.out.println("Server waiting for connection...");
			ServerSocket serverSocket = new ServerSocket(4567);
			Socket socket;
			
//			final GsonBuilder builder = new GsonBuilder();
//            final Gson gson = builder.create();
            
            
			while(true) {
				
				Socket socketPlayer1 = serverSocket.accept();
                System.out.println("The first client has logged in !");
                PrintWriter socketOutPlayer1 = new PrintWriter(socketPlayer1.getOutputStream(), true);
                BufferedReader socketInPlayer1 = new BufferedReader(new InputStreamReader(socketPlayer1.getInputStream()));
                
                
                String startPlayer1Out = socketInPlayer1.readLine();
//                
//                final String player1Name = gson.fromJson(startPlayer1Out, String.class);
                Player player1 = Player.fromString(startPlayer1Out);
                
                Socket socketPlayer2 = serverSocket.accept();
                System.out.println("The second client has logged in !");
                PrintWriter socketOutPlayer2 = new PrintWriter(socketPlayer2.getOutputStream(), true);
                BufferedReader socketInPlayer2 = new BufferedReader(new InputStreamReader(socketPlayer2.getInputStream()));
                
                
                String startPlayer2Out = socketInPlayer2.readLine();
                
//                final String player2Name = gson.fromJson(startPlayer2Out, String.class);
                Player player2 = Player.fromString(startPlayer2Out);
                
                Game newGame = new Game(2, player1, player2);
                
                
                
//                System.out.println(player1.toString() + " : Player 1 in serv");
//                System.out.println(player2.toString() + " : Player 2 in serv");
                
                GameSession newGameSession = new GameSession(newGame);
                
                Player player1ToSend = newGame.getPlayers()[0];
                System.out.println(player1ToSend.toString());
                
//                final String player1Sending = gson.toJson(player1ToSend);
                socketOutPlayer1.println(player1.toString());
                socketOutPlayer2.println(player1.toString());
//            	System.out.println(player1.toString() + " : player1");
            	
//            	Player player2ToSend = newGame.getPlayers()[1];
//            	final String player2Sending = gson.toJson(player2ToSend);
            	socketOutPlayer2.println(player2.toString());
            	socketOutPlayer1.println(player2.toString());
//            	System.out.println(player2.toString() + " : player2");
            	
            	
            	
            	
                
                while (!newGame.isOver()) {
					
					
					Player player1Sending = newGame.getPlayers()[0];
					
					socketOutPlayer1.println(player1Sending.toString());
					
                	UNOCard topcardToSend1 = newGameSession.getPlayedCards().peek();
                	System.out.println(topcardToSend1 + " : topcardToSend1");
                	ObjectOutputStream objectOutTopCards1 = new ObjectOutputStream(socketPlayer1.getOutputStream());
                	System.out.println(objectOutTopCards1 + " : objectOutTopCards1");
                	objectOutTopCards1.writeObject(topcardToSend1);
                	System.out.println(objectOutTopCards1 + " : objectOutTopCards1WithTopCard");
                	objectOutTopCards1.close();
					//toPlayer1.put("whoseTurn", newGame.whoseTurn());
					
					
					JSONObject toPlayer2 = new JSONObject();
					LinkedList<UNOCard> cardsToSend2 = newGame.getPlayers()[0].getAllCards();
                	ObjectOutputStream objectOutCards2 = new ObjectOutputStream(socketPlayer2.getOutputStream());
                	objectOutCards2.writeObject(cardsToSend2);
                	objectOutCards2.close();
                	UNOCard topcardToSend2 = newGameSession.getPlayedCards().peek();
                	ObjectOutputStream objectOutTopCards2 = new ObjectOutputStream(socketPlayer2.getOutputStream());
                	objectOutTopCards2.writeObject(topcardToSend2);
                	objectOutTopCards2.close();
					toPlayer2.put("whoseTurn", newGame.whoseTurn());
					
					if(newGame.whoseTurn() == player1.getName()) {
						String player1Out = socketInPlayer1.readLine();
		                JSONObject dataPlayer1 = new JSONObject(player1Out);
		                String askPlayer1 = (String) dataPlayer1.get("askCard");
		                if(askPlayer1 == "yes") {
		                	UNOCard cardToSend = newGame.getCard();
		                	ObjectOutputStream objectOut = new ObjectOutputStream(socketPlayer1.getOutputStream());
		                	objectOut.writeObject(cardToSend);
		                	objectOut.close();
		                }
		                ObjectInputStream objectIn = new ObjectInputStream(socketPlayer1.getInputStream());
		                UNOCard cardPlayed = (UNOCard) objectIn.readObject();
		                newGameSession.playThisCard(cardPlayed);
		                objectIn.close();
		                
					} else if (newGame.whoseTurn() == player2.getName()) {
						
						String player2Out = socketInPlayer2.readLine();
		                JSONObject dataPlayer2 = new JSONObject(player2Out);
		                String askPlayer2 = (String) dataPlayer2.get("askCard");
						if(askPlayer2 == "yes") {
		                	UNOCard cardToSend = newGame.getCard();
		                	ObjectOutputStream objectOut = new ObjectOutputStream(socketPlayer2.getOutputStream());
		                	objectOut.writeObject(cardToSend);
		                	objectOut.close();
		                }
		                ObjectInputStream objectIn = new ObjectInputStream(socketPlayer2.getInputStream());
		                UNOCard cardPlayed = (UNOCard) objectIn.readObject();
		                newGameSession.playThisCard(cardPlayed);
		                objectIn.close();
		                
					}
					
				}
                
                socketOutPlayer1.close();
                socketInPlayer1.close();
                socketPlayer1.close();
                System.out.println("The first client has disconnected !");
                
                socketOutPlayer2.close();
                socketInPlayer2.close();
                socketPlayer2.close();
                System.out.println("The second client has disconnected !");
//				if (this.numberOfThreads < 4) {
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

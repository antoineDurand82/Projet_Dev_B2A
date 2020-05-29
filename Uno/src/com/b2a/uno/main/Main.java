package com.b2a.uno.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.json.JSONObject;

import com.b2a.serversession.GameSession;
import com.b2a.uno.game.Game;
import com.b2a.uno.game.Player;
import com.b2a.uno.view.MainFrame;
import com.b2a.uno.view.UNOCard;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Main {
	
	private static Socket socket;
	private static PrintWriter socketOut;
	private static BufferedReader socketIn;
	private static Player player;
	private static GameSession newGameSession;
	
	public Main() {
		try {
			this.socket = new Socket("localhost", 4567);
			this.socketOut = new PrintWriter(socket.getOutputStream(), true);
            this.socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		
		try {
            new Main();
            String name = JOptionPane.showInputDialog("Enter your name :");
            
//            final GsonBuilder builder = new GsonBuilder();
//            final Gson gson = builder.create();
            
            
//            String dataClient = gson.toJson(name);
//            
//            if(dataClient != null)
        	socketOut.println(name);
            
        	String YouIn = socketIn.readLine();
            System.out.println((YouIn.toString()) + " : is your name");
            Player player = Player.fromString(YouIn);
            
            String player2In = socketIn.readLine();
            System.out.println((player2In.toString()) + " : is your opponent");
//            final Player player2 = gson.fromJson(player2In, Player.class);
            Player player2 = Player.fromString(player2In);
        	
            
        	Game newGame = new Game(player, player2);
        	newGameSession = new GameSession(newGame);
        			
//            String servOut;
//            while((servOut = socketIn.readLine()) != null) {
//                // La tu print ce que le serv t'envoie
//            	System.out.println(servOut + " : ServOut");
//            	JSONObject dataServ = new JSONObject(servOut);
//            	System.out.println(dataServ + " : dataServ");
////            	player1.setMyCards((LinkedList<UNOCard>) dataServ.get("playerHand"));
//            	ObjectInputStream objectIn = new ObjectInputStream(Main.getSocket().getInputStream());
//            	LinkedList<UNOCard> myCards = (LinkedList<UNOCard>) objectIn.readObject();
//            	getPlayer().setMyCards(myCards);
//                objectIn.close();
//            }
            socketOut.close();
            socketIn.close();
            socket.close();
        } catch (Exception exc) {
            System.err.println(exc.getMessage());
        }
		
		
//		//Create players
//		
//		String name2 = JOptionPane.showInputDialog("Player 2");
		//Create Frame and invoke it.
		
		
		SwingUtilities.invokeLater(new Runnable() {					
			public void run() {
				JFrame frame;
				try {
					frame = new MainFrame(newGameSession);
					frame.setVisible(true);
					frame.setResizable(false);
					frame.setLocation(200, 100);
					frame.pack();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
			}
		});	
	}

	public static PrintWriter getSocketOut() {
		return socketOut;
	}

	public static BufferedReader getSocketIn() {
		return socketIn;
	}

	public static Socket getSocket() {
		return socket;
	}

	public static Player getPlayer() {
		return player;
	}

	public static void setPlayer(Player player) {
		Main.player = player;
	}

}

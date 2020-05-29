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

import com.b2a.uno.game.Player;
import com.b2a.uno.view.MainFrame;
import com.b2a.uno.view.UNOCard;


public class Main {
	
	private static Socket socket;
	private static PrintWriter socketOut;
	private static BufferedReader socketIn;
	private static Player player;
	
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
            String name = JOptionPane.showInputDialog("Player 1");
            setPlayer(new Player(name));
            JSONObject dataClient = new JSONObject();
            dataClient.put("name", name);
            if(dataClient.toString() != null)
            	socketOut.println(dataClient.toString());
            String servOut;
            while((servOut = socketIn.readLine()) != null) {
                // La tu print ce que le serv t'envoie
            	JSONObject dataServ = new JSONObject(servOut);
//            	player1.setMyCards((LinkedList<UNOCard>) dataServ.get("playerHand"));
            	ObjectInputStream objectIn = new ObjectInputStream(Main.getSocket().getInputStream());
            	LinkedList<UNOCard> myCards = (LinkedList<UNOCard>) objectIn.readObject();
            	getPlayer().setMyCards(myCards);
                objectIn.close();
            }
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
					frame = new MainFrame();
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

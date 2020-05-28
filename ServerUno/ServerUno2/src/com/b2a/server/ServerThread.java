package com.b2a.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ServerThread implements Runnable {
	
	private Server parent;
	private PrintWriter s_out;
	private BufferedReader s_in;
	private Socket s;
	private int playerPlaying;
	private volatile boolean isOn;
	
	public ServerThread(Server parent, Socket s) {
		this.parent = parent;
		this.s = s;
		try {
			this.s_out = new PrintWriter(s.getOutputStream(), true);
			this.s_in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch(Exception exc) {
			System.err.println(exc.getMessage());
		}
	}
	
	public void terminate() {
		this.isOn = false;
	}

	@Override
	public void run() {
		try {
			String nom = s_in.readLine();
			playerPlaying = parent.stockeNom(nom);
			// tant qu'une 2e personne ne s'est pas connectée : attendre
			while (parent.getNbClients()!=2) {
				Thread.sleep(500);
			}
			// envoi au client le nom de l'autre
			s_out.println("Coucou de " + parent.getAutre(playerPlaying));
			
			// on coupe les connexions
			Thread.sleep(1000);
			s_out.close();
			s_in.close();
			s.close();
			parent.razClients();
		} catch (Exception exc) {
			System.err.println(exc.getMessage());
		}

	}

}

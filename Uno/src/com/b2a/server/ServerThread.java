package com.b2a.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.JSONObject;

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
			while(this.isOn) {
				if(this.parent.getNumberOfThread() == 4) {
					if(this.parent.getGame().isOver()) {
						JSONObject jsonMessage = new JSONObject();
					} else {
						s_out.println("");
						break;
					}
				}
			}
		} catch (Exception exc) {
			System.err.println(exc.getMessage());
		}

	}

}

package com.b2a.view;

import javax.swing.JFrame;

import com.b2a.interfaces.GameConstants;
import com.b2a.server.GameSession;


public class MainFrame extends JFrame implements GameConstants {
	
	private Session mainPanel;
	private GameSession gameSession;
	
	public MainFrame(){	
		this.gameSession = new GameSession();
		CARDLISTENER.setGameSession(this.gameSession);
		BUTTONLISTENER.setGameSession(this.gameSession);
		
		this.mainPanel = this.gameSession.getSession();
		add(this.mainPanel);
	}
}
package com.b2a.uno.view;

import java.io.IOException;

import javax.swing.JFrame;

import com.b2a.serversession.GameSession;
import com.b2a.uno.interfaces.GameConstants;


public class MainFrame extends JFrame implements GameConstants {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7934296668448854805L;
	private Session mainPanel;
	private GameSession gameSession;
	
	public MainFrame(GameSession newGameSession) throws ClassNotFoundException, IOException{	
		this.gameSession = newGameSession;
		CARDLISTENER.setGameSession(this.gameSession);
		BUTTONLISTENER.setGameSession(this.gameSession);
		
		this.mainPanel = this.gameSession.getSession();
		add(this.mainPanel);
	}
}

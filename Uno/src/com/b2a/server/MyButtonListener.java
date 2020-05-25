package com.b2a.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyButtonListener implements ActionListener {
	
	GameSession myGameSession;
	
	public void setGameSession(GameSession gameSession) {
		this.myGameSession = gameSession;
	}
	
	public void drawCard() {
		if(myGameSession.canPlay) {
			myGameSession.requestCard();
		}
	}
	
	public void sayUno() {
		if(myGameSession.canPlay)
			myGameSession.submitSaidUNO();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

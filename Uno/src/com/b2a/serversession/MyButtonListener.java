package com.b2a.serversession;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MyButtonListener implements ActionListener {
	
	GameSession myGameSession;
	
	public void setGameSession(GameSession gameSession) {
		this.myGameSession = gameSession;
	}
	
	public void drawCard() throws ClassNotFoundException, IOException {
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

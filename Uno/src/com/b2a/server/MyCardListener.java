package com.b2a.server;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyCardListener extends MouseAdapter {
	
	UNOCard sourceCard;
	GameSession myGameSession;
	
	public void setGameSession(GameSession gameSession) {
		this.myGameSession = gameSession;
	}
	
	public void mousePressed(MouseEvent e) {
		sourceCard = (UNOCard) e.getSource();
		
		try {
			if(myGameSession.canPlay)
				myGameSession.playThisCard(sourceCard);
		} catch (NullPointerException exc) {
			exc.printStackTrace();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		super.mouseEntered(e);
		
		sourceCard = (UNOCard) e.getSource();
		Point p = soucreCard.getLocation();
		p.y -=20;
		sourceCard.setLocation(p);
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		sourceCard = (UNOCard) e.getSource();
		Point p = sourceCard.getLocation();
		p.y +=20;
		sourceCard.setLocation(p);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
}
